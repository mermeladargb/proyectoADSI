package org.example;

import org.example.Pelicula;

import java.sql.*;
import java.util.ArrayList;

public class DBGestor {
    private static final String DB_URL = "jdbc:sqlite:database.db";


    private Connection conectar() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(DB_URL);
        } catch (SQLException e) {
            System.out.println("Error de conexion: " + e.getMessage());
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

              /*
                Usuario solicitadaPor = new Usuario(rs.getString("solicitadaPor"));
                Admin aceptadaPor = new Admin(rs.getString("aceptadaPor"));

                pelicula.setSolicitadaPor(solicitadaPor);
                pelicula.setAceptadaPor(aceptadaPor);
*/
                peliculas.add(pelicula);
            }
        } catch (SQLException e) {
        }
        return peliculas;
    }

    public void guardarPelicula(Pelicula pelicula) {
        String sql = "INSERT INTO peliculas(ID, titulo, descripcion, aceptada, solicitadaPor, aceptadaPor) VALUES(?, ?, ?, ?, ?, ?)";

        try (Connection conn = conectar();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, pelicula.getID());
            ps.setString(2, pelicula.getTitulo());
            ps.setString(3, pelicula.getDescripcion());
            ps.setBoolean(4, pelicula.aceptada());
            ps.setString(5, pelicula.getSolicitadaPor().getUsername());
            ps.setString(6, pelicula.getAceptadaPor().getNombre());

            ps.executeUpdate();
        } catch (SQLException e) {
        }
    }
}
