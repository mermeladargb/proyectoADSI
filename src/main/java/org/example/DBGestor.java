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
    /**Carga todos los objetos con todos los datos guardados en la DB.*/
    public void cargarDatos() {
        cargarPeliculas();
        cargarUsuarios();
        cargarValoraciones();
        cargarAlquileres();
        cargarListas();
    }

    /**Carga las valoraciones guardadas en la DB en el objeto*/
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

            System.out.println("Valoraciones cargadas correctamente");
        } catch (SQLException e) {
            System.out.println("Error al cargar valoraciones: " + e.getMessage());
        }
    }

    /**Ejecuta consultas sobre la base de datos*/
    public void ejecutarConsulta(String sql) {
        try (Connection conn = conectar();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            System.out.println("Consulta ejecutada");
        } catch (SQLException e) {
        }
    }
    /**Carga las peliculas guardadas en la DB en el objeto*/
    public void cargarPeliculas() {
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT p.id, p.titulo, p.descripcion, p.aceptada, " + "p.username_solicitador, p.username_admin, " +
                    "u1.nombre AS solicitadaPorNombre, u1.apellido AS solicitadaPorApellido, " + "u2.nombre AS aceptadaPorNombre, u2.apellido AS aceptadaPorApellido " +
                    "FROM peliculas p " + "LEFT JOIN usuarios u1 ON p.username_solicitador = u1.username " + "LEFT JOIN usuarios u2 ON p.username_admin = u2.username;";

            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt("id");
                String titulo = rs.getString("titulo");
                String descripcion = rs.getString("descripcion");
                boolean aceptada = rs.getBoolean("aceptada");

                Usuario solicitadaPor = null;
                if (rs.getString("username_solicitador") != null) {
                    solicitadaPor = new Usuario(rs.getString("username_solicitador"), null, rs.getString("solicitadaPorNombre"),
                            rs.getString("solicitadaPorApellido"), null, null, null, false);
                }

                Usuario aceptadaPor = null;
                if (rs.getString("username_admin") != null) {
                    aceptadaPor = new Usuario(rs.getString("username_admin"), null, rs.getString("aceptadaPorNombre"),
                            rs.getString("aceptadaPorApellido"), null, null, null, true);
                }
                Pelicula pelicula = new Pelicula(id, titulo, descripcion, solicitadaPor, aceptadaPor);
                pelicula.setAceptada(aceptada);

                GestorPeliculas.getGestorPeliculas().cargarPelicula(pelicula);
            }
            rs.close();
            System.out.println("Películas cargadas correctamente");
        } catch (Exception e) {
        }
    }

    /**Carga los usuarios guardadas en la DB en el objeto*/
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
            System.out.println("Usuarios cargados correctamente");
        } catch (SQLException e) {
            System.out.println("Error al cargar usuarios: " + e.getMessage());
        }
        return usuarios;
    }

    /**Carga las listas guardadas en la DB en el objeto*/
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
                Lista lista = new Lista(usuario, nombreLista, visible);
                GestorListas.getGestorListas().cargarLista(lista);

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
            System.out.println("Listas cargadas correctamente");
        } catch (SQLException e) {
            System.out.println("Error al cargar listas:" + e.getMessage());
        }
    }

    /**Carga los alquileres guardadas en la DB en el objeto*/
    public void cargarAlquileres() {
        String sql = "SELECT * FROM alquileres";

        try (Connection conn = conectar();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                String username = rs.getString("username_usuario");
                Usuario usuario = GestorUsuarios.getGestorUsuarios().getUsuario(username);
                if (usuario != null) {
                    Pelicula pelicula = GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(rs.getInt("id_pelicula"));
                    if (pelicula != null) {
                        usuario.añadirAlquiler(pelicula);
                    }
                }
            }
            System.out.println("Alquileres cargados correctamente");
        } catch (SQLException e) {
            System.out.println("Error al cargar alquileres:" + e.getMessage());
        }
    }

    /**Añade un alquiler en la base de datos.
     @param idPeli ID de la pelicula que se quiere alquilar.
     @param username Nombre de usuario del que hace el alquiler.
     @param fecha Fecha del alquiler.
     */
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

    /**Obtiene el ID de una lista especifica de la base de datos.
     @param username Nombre de usuario.
     @param nombreLista Nombre de la lista.
     @return ID de la lista o -1 si no se encuentra o hay un error.
     */
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
