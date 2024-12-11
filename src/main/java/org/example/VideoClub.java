package org.example;

import org.json.JSONObject;

public class VideoClub {
    private static final VideoClub unVideoClub = new VideoClub();


    public static VideoClub getUnVideoClub(){return unVideoClub;}



    public JSONObject mostrarPeliculasSimilares(String nombrePeli) {
        return new JSONObject();
    }

    public JSONObject seleccionarPelicula(int idPeli) {
        return new JSONObject();
    }

    public void alquilarPeli(String username) {

    }

    public JSONObject verAlquileres(String username) {
        return new JSONObject();
    }

    public JSONObject mostrarValoracionesAntiguas(String username, int idPelicula) {

        JSONObject peliculaJSON = new JSONObject();
        return new JSONObject();
    }

    public void puntuarPelicula(String username, int idPelicula, String reseña, int puntuacion){
        Usuario user = GestorUsuario.getUsuario(username);

    }

    public JSONObject mostrarReseñas(String username, int idPelicula) {

        return new JSONObject();
    }

    public JSONObject verificarRegistro(String nombre, String apellido, String username, String contraseña, String correo) {
        return new JSONObject();
    }

    public JSONObject verificarInicioDeSesion(String username, String contraseña) {
        return new JSONObject();
    }

    public JSONObject actualizarDatos(String nombre, String apellido, String username, String contraseña, String correo) {
        return new JSONObject();
    }

    public JSONObject mostrarSolicitudes() {
        return new JSONObject();
    }

    public JSONObject modificarCuentaSeleccionada(Usuario usuario) {
        return new JSONObject();
    }

    public JSONObject eliminarCuentaSeleccionada(Usuario usuario) {
        return new JSONObject();
    }

    public void crearLista(String username, String nombreLista) {
    }

    public JSONObject getListasUsuario(String username) {
        return new JSONObject();
    }

    public void añadirALista(String username, String nombreLista, int idPelicula) {
    }

    public JSONObject buscarLista(String nombreLista) {
        return new JSONObject();
    }

    public void cambiarVisibilidadLista(String username, String nombreLista, boolean visibilidad) {
    }
    
    public void ñó() {
    	System.out.println("ñó");
    }
    
    public static void main(String[] args) {
    	System.out.println("a");
    	JSONObject j = new JSONObject();
    	j.append("a", 12);
    	System.out.println(j.get("a"));
    	getUnVideoClub().ñó();
    }
}
