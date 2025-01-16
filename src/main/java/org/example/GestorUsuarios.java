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

    public Usuario getUsuario(String username) {
        for (Usuario user : usuarios) {
            if (user.getUsername().equals(username)) {
                return user;
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

    public boolean cuentaExistente(Usuario user) {
        return usuarios.contains(user);
    }

    public String registrarUsuario(String nombre, String contraseña, String apellido, String username, String correo, boolean es_Admin) {
        ArrayList<Alquiler> lista = new ArrayList<>();
        Usuario user = new Usuario(username, contraseña, nombre, apellido, correo, null, lista, es_Admin);
        if (cuentaValida(user)) {
            if (!cuentaExistente(user)) {
                solicitudes.add(user);  // Añadir a la lista de solicitudes en lugar de usuarios
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
            return "Cuenta eliminada correctamente";
        } else {
            return "La cuenta no está en la lista";
        }
    }

    public String modificarCuenta(String nombre, String contraseña, String apellido, String username, String correo, boolean es_Admin) {
        Usuario usuario = getUsuario(username);
        if (usuario != null) {
            // Actualizar la cuenta del usuario con los nuevos datos
            usuario.actualizarCuenta(username, contraseña, nombre, apellido, correo);
            return "Cuenta modificada correctamente";
        } else {
            return "Usuario no encontrado";
        }
    }

    public void addUsuario(Usuario unUsuario) {
        usuarios.add(unUsuario);
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

            // Buscar en la lista de solicitudes en lugar de en la lista de usuarios
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
}
