package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class VideoClub {
    private static final VideoClub unVideoClub = new VideoClub();
    private GestorPeliculas gestorPeliculas = GestorPeliculas.getGestorPeliculas();
    private GestorUsuarios gestorUsuarios = GestorUsuarios.getGestorUsuarios();
    private GestorSolicitudesPeliculas gestorSolicitudesPeliculas = GestorSolicitudesPeliculas.getmGestorSolicutudesPeliculas();

    private VideoClub() {}

    public static VideoClub getUnVideoClub(){ return unVideoClub; }

    public JSONObject mostrarPeliculasSimilares(String nombrePeli) {
        return gestorPeliculas.mostrarPeliculas(nombrePeli);
    }

    public JSONObject seleccionarPelicula(int idPeli) {
        Pelicula unaPelicula = gestorPeliculas.buscarPeliSeleccionada(idPeli);
        JSONObject JSON = new JSONObject();
        if (unaPelicula != null) {
            JSON.put("ID", unaPelicula.getID());
            JSON.put("titulo", unaPelicula.getTitulo());
            JSON.put("descrip", unaPelicula.getDescripcion());
            JSON.put("media", String.format("%.2f", unaPelicula.getMediaValoracion()));
        } else {
            JSON = null;
        }
        return JSON;
    }

    public void alquilarPeli(String username, int idPeli) {
        Pelicula unaPelicula = gestorPeliculas.buscarPeliSeleccionada(idPeli);
        Usuario unUsuario = gestorUsuarios.getUsuario(username);
        if (unUsuario != null && unaPelicula != null) {
            unUsuario.añadirAlquiler(unaPelicula);
        }
    }

    public JSONObject verAlquileres(String username) {
        Usuario unUsuario = gestorUsuarios.getUsuario(username);
        if (unUsuario != null) {
            return unUsuario.mostrarAlquileres();
        }
        return null;
    }

    public JSONObject mostrarValoracionesAntiguas(String username, int idPelicula) {
        Pelicula pelicula = gestorPeliculas.buscarPeliSeleccionada(idPelicula);
        Usuario user = gestorUsuarios.getUsuario(username);
        Valoracion valoracion = pelicula.getValoracion(user);

        JSONObject peliculaJSON = new JSONObject();
        peliculaJSON.put("idPelicula", pelicula.getID());

        if (valoracion != null) {
            peliculaJSON.put("puntuacion", valoracion.getPuntuacion());
            peliculaJSON.put("descripcion", valoracion.getReseña());
        } else {
            peliculaJSON.put("puntuacion", JSONObject.NULL);
            peliculaJSON.put("descripcion", "");
        }
        return peliculaJSON;
    }

    public void puntuarPelicula(String username, int idPelicula, String reseña, int puntuacion) {
        Usuario user = gestorUsuarios.getUsuario(username);
        Pelicula pelicula = gestorPeliculas.buscarPeliSeleccionada(idPelicula);
        pelicula.guardarValoracion(user, reseña, puntuacion);
    }

    public JSONObject mostrarReseñas(String username, int idPelicula) {
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
        Usuario usuario = new Usuario(username, contraseña, nombre, apellido, correo, null, new ArrayList<Alquiler>(), false);
        if (gestorUsuarios.cuentaValida(usuario)) {
            if (!gestorUsuarios.cuentaExistente(usuario)) {
                gestorUsuarios.addSolicitud(usuario);  
                return new JSONObject().put("estado", "exitoso").put("mensaje", "Registro exitoso. Esperando aprobación del administrador.");
            } else {
                return new JSONObject().put("estado", "error").put("mensaje", "El nombre de usuario ya existe");
            }
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "Datos de registro no válidos");
        }
    }

    public JSONObject verificarInicioDeSesion(String username, String contraseña) {
        Usuario usuario = gestorUsuarios.getUsuario(username);
        if (usuario != null && usuario.getContraseña().equals(contraseña)) {
            return new JSONObject()
                .put("estado", "exitoso")
                .put("mensaje", "Inicio de sesión exitoso")
                .put("esAdmin", usuario.isEsAdmin()); 
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "Username o contraseña incorrecta");
        }
    }
    

    public JSONObject actualizarDatos(String nombre, String apellido, String username, String contraseña, String correo) {
        Usuario usuario = gestorUsuarios.getUsuario(username);
        if (usuario != null) {
            usuario.actualizarCuenta(username, contraseña, nombre, apellido, correo);
            return new JSONObject().put("estado", "exitoso").put("mensaje", "Datos actualizados correctamente");
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "Usuario no encontrado");
        }
    }
    
    public JSONObject mostrarSolicitudes() {
        List<Usuario> solicitudes = gestorUsuarios.getSolicitudes();
        JSONArray solicitudesArray = new JSONArray();
        for (Usuario solicitud : solicitudes) {
            JSONObject solicitudJSON = new JSONObject();
            solicitudJSON.put("username", solicitud.getUsername());
            solicitudesArray.put(solicitudJSON);
        }
        JSONObject resultado = new JSONObject();
        resultado.put("solicitudes", solicitudesArray);
        return resultado;
    }
    
    
    public JSONObject aceptarSolicitud(String adminUsername, String username) {
        Usuario adminUsuario = gestorUsuarios.getUsuario(adminUsername);
        if (adminUsuario != null && adminUsuario.isEsAdmin()) {
            Usuario usuario = gestorUsuarios.getUsuario(username);
            if (usuario != null && gestorUsuarios.getSolicitudes().contains(usuario)) {
                usuario.setAceptadoPor(adminUsuario); 
                gestorUsuarios.addUsuario(usuario);  
                gestorUsuarios.getSolicitudes().remove(usuario);  
                return new JSONObject().put("estado", "exitoso").put("mensaje", "Solicitud aceptada");
            } else {
                return new JSONObject().put("estado", "error").put("mensaje", "Solicitud no encontrada");
            }
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "No tienes permisos de administrador");
        }
    }
    
    
    public JSONObject rechazarSolicitud(String adminUsername, String username) {
        Usuario adminUsuario = gestorUsuarios.getUsuario(adminUsername);
        if (adminUsuario != null && adminUsuario.isEsAdmin()) {
            Usuario usuario = gestorUsuarios.getUsuario(username);
            if (usuario != null && gestorUsuarios.getSolicitudes().contains(usuario)) {
                gestorUsuarios.getSolicitudes().remove(usuario);  
                return new JSONObject().put("estado", "exitoso").put("mensaje", "Solicitud rechazada");
            } else {
                return new JSONObject().put("estado", "error").put("mensaje", "Solicitud no encontrada");
            }
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "No tienes permisos de administrador");
        }
    }
    
    
    public void eliminarCuentaSeleccionada(String username) {
        Usuario usuario = gestorUsuarios.getUsuario(username);
        if (usuario != null) {
            gestorUsuarios.eliminarCuenta(usuario);
        }
    }
    
    
    public JSONObject eliminarCuenta(String adminUsername, String username) {
        Usuario adminUsuario = gestorUsuarios.getUsuario(adminUsername);
        if (adminUsuario != null && adminUsuario.isEsAdmin()) {
            Usuario usuario = gestorUsuarios.getUsuario(username);
            if (usuario != null) {
                gestorUsuarios.eliminarCuenta(usuario);
                return new JSONObject().put("estado", "exitoso").put("mensaje", "Cuenta eliminada correctamente");
            } else {
                return new JSONObject().put("estado", "error").put("mensaje", "Usuario no encontrado");
            }
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "No tienes permisos de administrador");
        }
    }
    
    public JSONObject modificarCuenta(String adminUsername, String nombre, String apellido, String username, String contraseña, String correo) {
        Usuario adminUsuario = gestorUsuarios.getUsuario(adminUsername);
        if (adminUsuario != null && adminUsuario.isEsAdmin()) {
            Usuario usuario = gestorUsuarios.getUsuario(username);
            if (usuario != null) {
                usuario.actualizarCuenta(username, contraseña, nombre, apellido, correo);
                return new JSONObject().put("estado", "exitoso").put("mensaje", "Datos actualizados correctamente");
            } else {
                return new JSONObject().put("estado", "error").put("mensaje", "Usuario no encontrado");
            }
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "No tienes permisos de administrador");
        }
    }
    
    
    public ArrayList<JSONObject> getUsuariosJson() {
        ArrayList<JSONObject> usuariosJson = new ArrayList<>();
        for (Usuario usuario : gestorUsuarios.getUsuarios()) {
            JSONObject usuarioJson = new JSONObject();
            usuarioJson.put("username", usuario.getUsername());
            usuarioJson.put("esAdmin", usuario.isEsAdmin());
            usuariosJson.add(usuarioJson);
        }
        return usuariosJson;
    }
    
    public JSONObject obtenerDatosUsuario(String username) {
        Usuario usuario = gestorUsuarios.getUsuario(username);
        JSONObject datosUsuario = new JSONObject();
        datosUsuario.put("username", usuario.getUsername());
        datosUsuario.put("nombre", usuario.getNombre());
        datosUsuario.put("apellido", usuario.getApellido());
        datosUsuario.put("correo", usuario.getCorreo());
        datosUsuario.put("contraseña", usuario.getContraseña());
        return datosUsuario;
    }
    
    public void crearLista(String username, String nombreLista) {
        Usuario u = gestorUsuarios.getUsuario(username);
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
        Pelicula p = gestorPeliculas.buscarPeliSeleccionada(idPelicula);
        GestorListas.getGestorListas().añadirPeliculaALista(username, nombreLista, p);
    }
    
    public JSONObject buscarLista(String nombreLista) {
        List<Lista> listas = GestorListas.getGestorListas().buscarLista(nombreLista);
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();
        for (Lista l : listas) {
            JSONObject lista = new JSONObject();
            lista.put("username", l.getNombreUsuario());
            lista.put("nombreLista", l.getNombre());
            array.put(lista);
        }
        json.put("listas", array);
        return json;
    }
    
    public void cambiarVisibilidadLista(String username, String nombreLista) {
        GestorListas.getGestorListas().cambiarVisibilidadLista(username, nombreLista);
    }

    public void validarPelicula(String pTitulo, String pUser){
        //gestorSolicitudesPeliculas.validarPelicula(pTitulo, pUser);
        //TODO
    }
    
    public ArrayList<JSONObject> obtenerCatalogoPeliculas(){
        ArrayList<JSONObject> peliculas = API.main();
        return (peliculas);
    }

    public void pedirPelicula(String pTitulo, String pUser){
        gestorSolicitudesPeliculas.addSolicitud(pTitulo,pUser);
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