package org.example;

import java.util.ArrayList;
import java.util.regex.*;
import org.json.JSONObject; 

public class GestorUsuarios {
    private static GestorUsuarios mGestorUsuario = new GestorUsuarios();
    private ArrayList<Usuario> usuarios = new ArrayList<>();
    private ArrayList<Usuario> solicitudes = new ArrayList<>();

    private GestorUsuarios() {}

    public static GestorUsuarios getGestorUsuarios() {
        return mGestorUsuario;
    }

    //Obtener un usuario en concreto
    public Usuario getUsuario(String username) {
        for (Usuario user : usuarios) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        for (Usuario solicitud : solicitudes) {
            if (solicitud.getUsername().equals(username)) {
                return solicitud;
            }
        }
        return null;
    }
    
    

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }

    public ArrayList<Usuario> getSolicitudes() {
        return solicitudes;
    }

    //Existe esa cuenta en el sistema
    public boolean cuentaExistente(Usuario user) {
        return usuarios.contains(user);
    }

    // Se comprueba si la cuenta cumple con las especificaciones y si existe o no
    public String registrarUsuario(String nombre, String contraseña, String apellido, String username, String correo, boolean es_Admin) {
    ArrayList<Alquiler> lista = new ArrayList<>();
    Usuario user = new Usuario(username, contraseña, nombre, apellido, correo, null, lista, es_Admin);
    if (cuentaValida(user)) {
        if (!cuentaExistente(user)) {
            addSolicitud(user);
            return "Solicitud de cuenta añadida correctamente. Un administrador debe aprobarla.";
        }
        return "Cuenta existente";
    }
    return "Cuenta no valida";
}


    public boolean cuentaValida(Usuario user) {
        boolean valido = true;

        // Verificar si ya existe un usuario con el mismo username
        for (Usuario u : this.usuarios) {
            if (u.getUsername().equals(user.getUsername())) {
                System.out.println("Error: El nombre de usuario ya existe.");
                valido = false;
                break;
            }
        }

        // Verificar longitud de la contraseña
        if (user.getContraseña().length() < 8) {
            System.out.println("Error: La contraseña debe tener al menos 8 caracteres.");
            valido = false;
        }

        // Verificar si la contraseña contiene al menos un carácter especial
        Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = specialCharPattern.matcher(user.getContraseña());
        if (!matcher.find()) {
            System.out.println("Error: La contraseña debe contener al menos un carácter especial.");
            valido = false;
        }

        // Verificar si el correo tiene la estructura correcta
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        matcher = emailPattern.matcher(user.getCorreo());
        if (!matcher.matches()) {
            System.out.println("Error: El correo electrónico no tiene una estructura válida.");
            valido = false;
        }

        return valido;
    }

    public String eliminarCuenta(Usuario user) {
        if (usuarios.contains(user)) {
            usuarios.remove(user);

            String sql = "DELETE FROM usuarios WHERE id = '" + user.getUsername() + "'";
            DBGestor dbGestor = new DBGestor();
            dbGestor.ejecutarConsulta(sql);

            return "Cuenta eliminada correctamente";
        } else {
            return "La cuenta no está en la lista";
        }
    }
    public String modificarCuenta(String nombre, String contraseña, String apellido, String username, String correo, boolean es_Admin) {
        Usuario usuario = getUsuario(username);
        if (usuario != null) {
            if (usuario.actualizarCuenta(username, contraseña, nombre, apellido, correo)) {
                return "Cuenta modificada correctamente";
            } else {
                return "Datos no válidos";
            }
        } else {
            return "Usuario no encontrado";
        }
    }



    public void addUsuario(Usuario unUsuario) {
        usuarios.add(unUsuario);

        String sql = "INSERT INTO usuarios (username, nombre, apellido, contraseña, correo, es_admin, aceptado_por) " +
                "VALUES ('" + unUsuario.getUsername() + "', '"
                + unUsuario.getNombre() + "', '"
                + unUsuario.getApellido() + "', '"
                + unUsuario.getContraseña() + "', '"
                + unUsuario.getCorreo() + "', "
                + (unUsuario.isEsAdmin() ? 1 : 0) + ", "
                + (unUsuario.getAceptado_Por() != null ? "'" + unUsuario.getAceptado_Por().getUsername() + "'" : "NULL") + ")";

        DBGestor dbGestor = new DBGestor();
        dbGestor.ejecutarConsulta(sql);
    }

    public void addSolicitud(Usuario solicitud) {
        solicitudes.add(solicitud);
    }
    

    public void reset() {
        usuarios = new ArrayList<Usuario>();
        solicitudes = new ArrayList<Usuario>();
    }

    public JSONObject aceptarSolicitud(String adminUsername, String username) {
        Usuario adminUsuario = getUsuario(adminUsername);
        if (adminUsuario != null && adminUsuario.isEsAdmin()) {
            Usuario usuario = null;

            for (Usuario solicitud : getSolicitudes()) {
                if (solicitud.getUsername().equals(username)) {
                    usuario = solicitud;
                    break;
                }
            }

            if (usuario != null) {
                usuario.setAceptadoPor(adminUsuario);
                addUsuario(usuario);
                getSolicitudes().remove(usuario);
                return new JSONObject().put("estado", "exitoso").put("mensaje", "Solicitud aceptada");
            } else {
                return new JSONObject().put("estado", "error").put("mensaje", "Solicitud no encontrada");
            }
        } else {
            return new JSONObject().put("estado", "error").put("mensaje", "No tienes permisos de administrador");
        }
    }

    // Se comrpueba si el usuario existe y se annade  la pelicula
    public void alquilarPeli(String username, Pelicula unaPelicula) {
        Usuario unUsuario= getUsuario(username);
        if(unUsuario != null && unaPelicula!=null) {
            unUsuario.añadirAlquiler(unaPelicula);
        }


    }

    // Metodo para los alquileres que ha hecho el usuario
    public JSONObject verAlquileres(String username) {
        Usuario unUsuario =getUsuario(username);
        if (unUsuario != null) {
            return unUsuario.mostrarAlquileres();
        }
        return null;
    }
}
