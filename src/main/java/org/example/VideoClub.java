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

    //Busca peliculas similares a la cadena de nombrePeli
    public JSONObject mostrarPeliculasSimilares(String nombrePeli) {
        return gestorPeliculas.mostrarPeliculas(nombrePeli);
    }

    //Metodo que se emplea cuando un usuario escoge ver detalles de una Pelicula
    public JSONObject seleccionarPelicula(int idPeli) {

       return GestorPeliculas.getGestorPeliculas().verDetallesPelicula(idPeli);

    }

    // Metodo en el que un usuario alquila una pelicula
    public void alquilarPeli(String username, int idPeli) {
        Pelicula unaPelicula = gestorPeliculas.buscarPeliSeleccionada(idPeli);
        if ( unaPelicula != null) {
            gestorUsuarios.alquilarPeli(username,unaPelicula);
        }
    }
    /**Metodo para ver el historial de alquileres.
     @param username el nombre del usuario que quiere ver los alquileres.
     @return JSON con los alquileres.
     */
    public JSONObject verAlquileres(String username) {
        return gestorUsuarios.verAlquileres(username);

    }

    /**Metodo que devuelve la valoracion de la pelicula. Importante a la hora de valorar una pelicula ya valorada.
    @param username el nombre del usuario que va ha valorar la pelicula.
    @param idPelicula id de la pelicula que queremos valorar.
    @return peliculaJSON JSONobject con la valoracion de la pelicula. Contiene idPelicula, puntuacion y descripcion
    */
    public JSONObject mostrarValoracionesAntiguas(String username, int idPelicula) {
        Pelicula pelicula = gestorPeliculas.buscarPeliSeleccionada(idPelicula);
        Usuario user = gestorUsuarios.getUsuario(username);
        Valoracion valoracion = pelicula.getValoracion(user);

        JSONObject peliculaJSON = new JSONObject();
        peliculaJSON.put("idPelicula", pelicula.getID());
        //Condicion: si no existe la valoracion devuelve el JSON con estructura pero sin valores.
        if (valoracion != null) {
            peliculaJSON.put("puntuacion", valoracion.getPuntuacion());
            peliculaJSON.put("descripcion", valoracion.getReseña());
        } else {
            peliculaJSON.put("puntuacion", JSONObject.NULL);
            peliculaJSON.put("descripcion", "");
        }
        return peliculaJSON;
    }

    /**Metodo que sirve para poner una puntuacion y reseña a una pelicula. La guarda en la DB y objeto
     @param username el nombre del usuario que va ha puntuar la pelicula.
     @param idPelicula id de la pelicula que queremos valorar.
     @param reseña la reseña.
     @param puntuacion la puntuacion.
     */
    public void puntuarPelicula(String username, int idPelicula, String reseña, int puntuacion) {
        Usuario user = gestorUsuarios.getUsuario(username);
        Pelicula pelicula = gestorPeliculas.buscarPeliSeleccionada(idPelicula);
        pelicula.guardarValoracion(user, reseña, puntuacion);

        String sql = "INSERT OR REPLACE INTO valoraciones (username_usuario, id_pelicula, puntuacion, descripcion) VALUES ('"
                + username + "', " + idPelicula + ", " + puntuacion + ", '" + reseña + "')";
        DBGestor.getDBGestor().ejecutarConsulta(sql);
    }

    /**Metodo que sirve para ver las reseñas y puntuaciones de una pelicula concreta de todos los usuarios. El usuario solicitante sera el primero en el JSON.
     @param username el nombre del usuario que quiere ver las reseñas.
     @param idPelicula id de la pelicula que queremos ver las reseñas.
     @return resultadoJSON JSON con username, reseña y puntuacion.
     */
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
            Usuario usuario = null;
    
            // Buscar al usuario en la lista de solicitudes
            for (Usuario solicitud : gestorUsuarios.getSolicitudes()) {
                if (solicitud.getUsername().equals(username)) {
                    usuario = solicitud;
                    break;
                }
            }
    
            if (usuario != null) {
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
            Usuario usuario = null;
            for (Usuario solicitud : gestorUsuarios.getSolicitudes()) {
                if (solicitud.getUsername().equals(username)) {
                    usuario = solicitud;
                    break;
                }
            }
            if (usuario != null) {
                gestorUsuarios.getSolicitudes().remove(usuario);
                System.out.println("Solicitud rechazada para el usuario: " + username);
                return new JSONObject().put("estado", "exitoso").put("mensaje", "Solicitud rechazada");
            } else {
                System.out.println("Solicitud no encontrada para el usuario: " + username);
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

            String sql = "DELETE FROM usuarios WHERE username = '" + username + "'";
            DBGestor.getDBGestor().ejecutarConsulta(sql);
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
                if (!usuario.validarDatos()) {
                    return new JSONObject().put("estado", "error").put("mensaje", "Datos no válidos");
                }
                return new JSONObject().put("estado", "exitoso").put("mensaje", "Datos actualizados correctamente");
            } else {
                return new JSONObject().put("estado", "error").put("mensaje", "Usuario no encontrado");
            }
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "No tienes permisos de administrador");
        }
    }
    
    
    
    public JSONObject actualizarDatos(String nombre, String apellido, String username, String contraseña, String correo) {
        Usuario usuario = gestorUsuarios.getUsuario(username);
        if (usuario != null) {
            String resultado = gestorUsuarios.modificarCuenta(nombre, contraseña, apellido, username, correo, usuario.isEsAdmin());
            if (resultado.equals("Cuenta modificada correctamente")) {

                String sql = "UPDATE usuarios SET nombre = '" + nombre + "', apellido = '" + apellido + "', correo = '" + correo + "', contrasena = '" + contraseña
                        + "', username = '" + username + "' WHERE username = '" + username + "'";
                DBGestor.getDBGestor().ejecutarConsulta(sql);

                return new JSONObject().put("estado", "exitoso").put("mensaje", "Datos actualizados correctamente");
            } else {
                return new JSONObject().put("estado", "error").put("mensaje", resultado);
            }
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "Usuario no encontrado");
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
        if (usuario != null) {
            JSONObject datosUsuario = new JSONObject();
            datosUsuario.put("username", usuario.getUsername());
            datosUsuario.put("nombre", usuario.getNombre() != null ? usuario.getNombre() : "");
            datosUsuario.put("apellido", usuario.getApellido() != null ? usuario.getApellido() : "");
            datosUsuario.put("correo", usuario.getCorreo() != null ? usuario.getCorreo() : "");
            datosUsuario.put("contraseña", usuario.getContraseña());
            return datosUsuario;
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "Usuario no encontrado");
        }
    }
    
    
    
    public void crearLista(String username, String nombreLista) {
        Usuario u = gestorUsuarios.getUsuario(username);
        GestorListas.getGestorListas().crearLista(u, nombreLista);
        DBGestor.getDBGestor().updateSQL("INSERT INTO listas(nombre, visible, username) VALUES('" + nombreLista + "',false,'" + username + "')");
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
        int id = DBGestor.getDBGestor().getIdLista(username, nombreLista);
        DBGestor.getDBGestor().updateSQL("INSERT INTO pertenece_a VALUES(" + id + "," + idPelicula + ")");
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
    
    public ArrayList<JSONObject> obtenerCatalogoPeliculas(){
        ArrayList<JSONObject> peliculas = API.main();
        return (peliculas);
    }

    public void pedirPelicula(String pTitulo, String pUser){
        gestorSolicitudesPeliculas.addSolicitud(pTitulo,pUser);

        String sql = "INSERT INTO peliculas (titulo, descripcion, aceptada, username_solicitador) " +
                "VALUES ('" + pTitulo + "', 'Pendiente de aprobación', 0, '" + pUser + "')";

        DBGestor.getDBGestor().ejecutarConsulta(sql);
    }

    public ArrayList<SolicitudPelicula> pedirSolicitudesPeliculas(){
        return(gestorSolicitudesPeliculas.getSolicitudes());
    }

    public void aceptarSolicitudPelicula(String pTitulo, String pUser){
        gestorSolicitudesPeliculas.aceptarSolicitudPelicula(pTitulo,pUser);
    }

    public void rechazarSolicitudPelicula(String pTitulo){
        gestorSolicitudesPeliculas.rechazarSolicitudPelicula(pTitulo);
    }

    public void cargarDatos() {
        DBGestor.getDBGestor().cargarDatos();
    }
}