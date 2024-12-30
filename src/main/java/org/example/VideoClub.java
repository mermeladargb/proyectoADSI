package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoClub {
    private static final VideoClub unVideoClub = new VideoClub();
   private GestorPeliculas gestorPeliculas = GestorPeliculas.getGestorPeliculas();

    private VideoClub()
    {}

    public static VideoClub getUnVideoClub(){return unVideoClub;}



    public JSONObject mostrarPeliculasSimilares(String nombrePeli) {
        return gestorPeliculas.mostrarPeliculas(nombrePeli);
    }

    public JSONObject seleccionarPelicula(int idPeli) {
        Pelicula unaPelicula= GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(idPeli);
        JSONObject JSON = new JSONObject();
        if (unaPelicula!=null){
            JSON.put("ID",unaPelicula.getID());
            JSON.put("titulo",unaPelicula.getTitulo());
            JSON.put("descrip",unaPelicula.getDescripcion());
            JSON.put("media",  String.format("%.2f", unaPelicula.getMediaValoracion()));
        }
        else{
            JSON=null;
        }

        return  JSON;

    }

    public void alquilarPeli(String username, int idPeli) {
            Pelicula unaPelicula=gestorPeliculas.buscarPeliSeleccionada(idPeli);
            Usuario unUsuario= GestorUsuarios.getGestorUsuarios().getUsuario(username);
            if (unUsuario !=null && unaPelicula != null){
                unUsuario.añadirAlquiler(unaPelicula);
            }

    }

    public JSONObject verAlquileres(String username) {
        Usuario unUsuario=GestorUsuarios.getGestorUsuarios().getUsuario(username);
        if (unUsuario!=null){
            return unUsuario.mostrarAlquileres();
        }
        return null;

    }

    public JSONObject mostrarValoracionesAntiguas(String username, int idPelicula) {
        Pelicula pelicula = gestorPeliculas.buscarPeliSeleccionada(idPelicula);
        Usuario user = GestorUsuarios.getGestorUsuarios().getUsuario(username);
        Valoracion valoracion = pelicula.getValoracion(user);

        JSONObject peliculaJSON = new JSONObject();
        peliculaJSON.put("idPelicula", pelicula.getID());
        peliculaJSON.put("puntuacion", valoracion.getPuntuacion());
        peliculaJSON.put("descripcion", valoracion.getReseña());
        return peliculaJSON;
    }

    public void puntuarPelicula(String username, int idPelicula, String reseña, int puntuacion){
        Usuario user = GestorUsuarios.getGestorUsuarios().getUsuario(username);
        Pelicula pelicula = gestorPeliculas.buscarPeliSeleccionada(idPelicula);
        pelicula.guardarValoracion(user, reseña, puntuacion);
       // pelicula.calcularPromedio();
    }

    public JSONObject mostrarReseñas(String username, int idPelicula) {
        //Como parametro tenemos username para que al mostrar las reseñas aparezca en primera posicion nuestra reseña y puntuacion.
        //Usuario user = GestorUsuario.getUsuario(username);
        Pelicula pelicula = gestorPeliculas.buscarPeliSeleccionada(idPelicula);
        ArrayList<Valoracion> listaValoraciones = pelicula.verValoraciones(username);

        JSONArray valoracionesArray = new JSONArray();

        for (Valoracion valoracion : listaValoraciones) {
            JSONObject peliculaJSON = new JSONObject();
            peliculaJSON.put("username", valoracion.getUser());
            peliculaJSON.put("reseña", valoracion.getReseña());
            peliculaJSON.put("puntuacion", valoracion.getPuntuacion());

            valoracionesArray.put(peliculaJSON);
        }

        JSONObject resultadoJSON = new JSONObject();
        resultadoJSON.put("valoraciones", valoracionesArray);

        return resultadoJSON;
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
        Usuario u = GestorUsuarios.getGestorUsuarios().getUsuario(username);
        GestorListas.getGestorListas().crearLista(u, nombreLista);
    }

    public JSONObject getListasUsuario(String username) {
        List<String> listas = GestorListas.getGestorListas().getListasUsuario(username);
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray(listas);
        json.put("listas", array);
        return json;
    }

    public JSONObject getListaUsuario(String username, String nombreLista) {
        Lista lista = GestorListas.getGestorListas().getListaUsuario(username, nombreLista);
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray(lista.getPeliculas());
        json.put("nombreLista", lista.getNombre());
        json.put("visible", lista.esVisible());
        json.put("peliculas", array);
        return json;
    }

    public void añadirPeliculaALista(String username, String nombreLista, int idPelicula) {
        Pelicula p = GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(idPelicula);
        GestorListas.getGestorListas().añadirPeliculaALista(username, nombreLista, p);
    }

    public JSONObject buscarLista(String nombreLista) {
        List<Lista> listas = GestorListas.getGestorListas().buscarLista(nombreLista);
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (Lista l : listas) {
            JSONObject lista = new JSONObject();
            lista.put("username", l.getNombreUsuario());
            lista.put("pelicula", l.getNombre());
            array.put(lista);
        }
        json.put("listas", array);
        return json;
    }

    public void cambiarVisibilidadLista(String username, String nombreLista) {
        GestorListas.getGestorListas().cambiarVisibilidadLista(username, nombreLista);
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
