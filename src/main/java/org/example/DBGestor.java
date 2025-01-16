package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBGestor {
    private static final String DB_URL = "jdbc:sqlite:database.db";
    private static DBGestor mDBGestor = new DBGestor();

    public static DBGestor getDBGestor() {
        return mDBGestor;
    }

    private Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Error de conexion:" + e.getMessage());
        }
        return conn;
    }


    public ArrayList<Pelicula> cargarPeliculas() {
        ArrayList<Pelicula> peliculas = new ArrayList<>();
        String sql = "SELECT * FROM peliculas";

        try (Connection conn = conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Pelicula pelicula = new Pelicula();
                pelicula.setID(rs.getInt("ID"));
                pelicula.setTitulo(rs.getString("titulo"));
                pelicula.setDescripcion(rs.getString("descripcion"));
                pelicula.setAceptada(rs.getBoolean("aceptada"));

                String solicitadaPorUsername = rs.getString("username_solicitador");
                if (solicitadaPorUsername != null) {
                    Usuario solicitadaPor = GestorUsuarios.getGestorUsuarios().getUsuario(solicitadaPorUsername);
                    pelicula.setSolicitadaPor(solicitadaPor);
                }

                String aceptadaPorUsername = rs.getString("username_admin");
                if (aceptadaPorUsername != null) {
                    Usuario aceptadaPor = GestorUsuarios.getGestorUsuarios().getUsuario(aceptadaPorUsername);
                    if (aceptadaPor != null && aceptadaPor.isEsAdmin()) {
                        pelicula.setAceptadaPor(aceptadaPor);
                    }
                }
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar peliculas:" + e.getMessage());
        }
        return peliculas;
    }

    public ArrayList<Usuario> cargarUsuarios() {
        ArrayList<Usuario> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM usuarios";

        try (Connection conn = conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = new Usuario(
                        rs.getString("username"),
                        rs.getString("contraseña"),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("correo"),
                        null,
                        new ArrayList<>(),
                        rs.getBoolean("esAdmin")
                );

                //Si el usuario fue aceptado por otro administrador
                String aceptadoPorUsername = rs.getString("aceptadoPor");
                if (aceptadoPorUsername != null) {
                    Usuario aceptadoPor = GestorUsuarios.getGestorUsuarios().getUsuario(aceptadoPorUsername);
                    usuario.setAceptadoPor(aceptadoPor);
                }
                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    public ArrayList<Lista> cargarListas() {
        ArrayList<Lista> listas = new ArrayList<>();
        String sql = "SELECT * FROM listas";

        try (Connection conn = conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Usuario usuario = GestorUsuarios.getGestorUsuarios().getUsuario(rs.getString("username"));
                Lista lista = new Lista(usuario, rs.getString("nombre"));
                //TODO al cargar los datos mirar si la visibilidad se pone bien
                lista.cambiarVisibilidad();


                String sqlPeliculas = "SELECT peliculaID FROM lista_peliculas WHERE listaNombre = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlPeliculas)) {
                    ps.setString(1, lista.getNombre());
                    ResultSet rsPeliculas = ps.executeQuery();
                    while (rsPeliculas.next()) {
                        Pelicula pelicula = GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(rsPeliculas.getInt("peliculaID"));
                        if (pelicula != null) {
                            lista.añadirPelicula(pelicula);
                        }
                    }
                }
                listas.add(lista);
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar listas:" + e.getMessage());
        }
        return listas;
    }

    public void cargarAlquileres() {
        String sql = "SELECT * FROM alquileres";

        try (Connection conn = conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String username = rs.getString("username");
                Usuario usuario = GestorUsuarios.getGestorUsuarios().getUsuario(username);
                if (usuario != null) {
                    Pelicula pelicula = GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(rs.getInt("peliculaID"));
                    if (pelicula != null) {
                        usuario.añadirAlquiler(pelicula);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar alquileres:" + e.getMessage());
        }
    }

    public void guardarPelicula(Pelicula pelicula) {
        String sql = "INSERT INTO peliculas(ID, titulo, descripcion, aceptada, solicitadaPor, aceptadaPor) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pelicula.getID());
            ps.setString(2, pelicula.getTitulo());
            ps.setString(3, pelicula.getDescripcion());
            ps.setBoolean(4, pelicula.estaAceptada());
            ps.setString(5, pelicula.getSolicitadaPor().getUsername());
            ps.setString(6, pelicula.getAceptadaPor().getUsername());

            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void updateSQL(String consulta) {
        try (Connection conn = conectar()) {
            Statement st = conn.createStatement();
            st.execute(consulta);
        } catch (Exception e) {
            System.out.printf("Error al ejecutar '%s': %s", consulta, e.getMessage());
        }
    }
}
