package org.example;

import java.util.ArrayList;
import java.util.regex.*;

public class GestorUsuarios {
    private static GestorUsuarios mGestorUsuario = new GestorUsuarios();
    private ArrayList<Usuario> usuarios = new ArrayList<>();

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

    /*public String verAlquileres(Usuario user) {
        return user.mostrarAlquileres();
    }
    */
    public boolean cuentaExistente(Usuario user) {
        return usuarios.contains(user);
    }

    public String registrarUsuario(String nombre, String contraseña, String apellido, String username, String correo, boolean es_Admin) {
        ArrayList<Alquiler> lista = new ArrayList<>();
        Usuario user = new Usuario(username, contraseña, nombre, apellido, correo, null, lista, es_Admin);
        if (cuentaValida(user)) {
            if (!cuentaExistente(user)) {
                usuarios.add(user);
                return "Cuenta añadida al GestorUsuarios correctamente";
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
        ArrayList<Alquiler> lista = usuario.getAlquileres();
        Usuario user = new Usuario(username, contraseña, nombre, apellido, correo, null, lista, es_Admin);
        if (cuentaValida(user)) {
            usuarios.add(user);
            return "Cuenta añadida al GestorUsuarios correctamente";
        }
        return "Cuenta no valida";
    }
    public void addUsuario(Usuario unUsuario)
    {
        usuarios.add(unUsuario);
    }

    public void reset(){
        usuarios=new ArrayList<Usuario>();
    }
}
