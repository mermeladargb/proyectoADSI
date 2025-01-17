package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class DBGestor {
    private static final String DB_URL = "jdbc:sqlite:baseDeDatos.db";
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

    public void cargarDatos() {
        cargarPeliculas();
        cargarUsuarios();
        cargarValoraciones();
        cargarAlquileres();
        cargarListas();
    }

    public void cargarValoraciones() {
        String sql = "SELECT username_usuario, id_pelicula, puntuacion, descripcion FROM valoraciones";

        try (Connection conn = conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String username = rs.getString("username_usuario");
                int idPelicula = rs.getInt("id_pelicula");
                int puntuacion = rs.getInt("puntuacion");
                String descripcion = rs.getString("descripcion");

                Usuario usuario = GestorUsuarios.getGestorUsuarios().getUsuario(username);
                if (usuario == null) {
                    System.out.println("No existe el usuario " + username);
                    continue;
                }

                Pelicula pelicula = GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(idPelicula);
                if (pelicula == null) {
                    System.out.println("No existe la película con ID " + idPelicula);
                    continue;
                }

                pelicula.guardarValoracion(usuario, descripcion, puntuacion);
            }

            System.out.println("Valoraciones cargadas correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al cargar valoraciones: " + e.getMessage());
        }
    }

    public void ejecutarConsulta(String sql) {
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Consulta ejecutada");
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
        }
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
                String username = rs.getString("username");
                String contraseña = rs.getString("contraseña");
                String nombre = rs.getString("nombre");
                String apellido = rs.getString("apellido");
                String correo = rs.getString("correo");
                String aceptado_por = rs.getString("aceptado_por");
                boolean es_admin = rs.getBoolean("es_admin");
                Usuario usuario = new Usuario(
                        username,
                        contraseña,
                        nombre,
                        apellido,
                        correo,
                        null,
                        new ArrayList<>(),
                        es_admin
                );
                //Si el usuario fue aceptado por otro administrador
                if (aceptado_por != null) {
                    // Nota: puede suceder que el usuario haya sido aceptado por un administrador, pero que este
                    // no esté cargado todavía. En ese caso, el atributo aceptadoPor será nulo, pero el usuario
                    // es considerado correcto
                    Usuario aceptadoPor = GestorUsuarios.getGestorUsuarios().getUsuario(aceptado_por);
                    usuario.setAceptadoPor(aceptadoPor);
                    GestorUsuarios.getGestorUsuarios().addUsuario(usuario);
                }
                else {
                    GestorUsuarios.getGestorUsuarios().addSolicitud(usuario);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    public void cargarListas() {
        String sql = "SELECT * FROM listas";

        try (Connection conn = conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int idLista = rs.getInt("id");
                String username = rs.getString("username");
                String nombreLista = rs.getString("nombre");
                boolean visible = rs.getBoolean("visible");
                Usuario usuario = GestorUsuarios.getGestorUsuarios().getUsuario(username);
                if (usuario == null) {
                    System.out.println("No existe el usuario " + username);
                    return;
                }
                GestorListas.getGestorListas().crearLista(usuario, nombreLista);
                Lista lista = GestorListas.getGestorListas().getListaUsuario(username, nombreLista);
                if (visible)
                    lista.cambiarVisibilidad();

                String sqlPeliculas = "SELECT id_pelicula FROM pertenece_a WHERE id_lista = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlPeliculas)) {
                    ps.setInt(1, idLista);
                    ResultSet rsPeliculas = ps.executeQuery();
                    while (rsPeliculas.next()) {
                        Pelicula pelicula = GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(rsPeliculas.getInt("id_pelicula"));
                        if (pelicula != null) {
                            lista.añadirPelicula(pelicula);
                        }
                        else {
                            System.out.println("No existe la película con id " + rsPeliculas.getInt("id_pelicula"));
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println("Error al cargar listas:" + e.getMessage());
        }
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


    public void añadirAlquiler(int idPeli, String username, Date fecha){
        String sql = "INSERT INTO alquileres VALUES(?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setInt(2, idPeli);
            ps.setDate(3, new java.sql.Date(fecha.getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }

    public void updateSQL(String consulta) {
        try (Connection conn = conectar()) {
            Statement st = conn.createStatement();
            st.execute(consulta);
        } catch (Exception e) {
            System.out.printf("Error al ejecutar '%s': %s\n", consulta, e.getMessage());
        }
    }

    public int getIdLista(String username, String nombreLista) {
        String consulta = "SELECT id FROM listas WHERE username='" + username + "' AND nombre='" + nombreLista + "'";
        try (Connection conn = conectar()) {
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(consulta);
            return rs.getInt("id");
        } catch (Exception e) {
            System.out.printf("Error al ejecutar '%s': %s\n", consulta, e.getMessage());
            return -1;
        }
    }
}
