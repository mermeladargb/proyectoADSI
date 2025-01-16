package org.example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;

public class Usuario {
    private String username;
    private String contraseña;
    private String nombre;
    private String apellido;
    private String correo;
    private Usuario aceptado_Por;
    private ArrayList<Alquiler> lAlquileres;
    private boolean es_Admin;
    
    public Usuario(String username, String contraseña, String nombre, String apellido, String correo, Usuario aceptado_Por, ArrayList<Alquiler> lista, boolean es_Admin) {
        this.username=username;
        this.contraseña=contraseña;
        this.nombre=nombre;
        this.apellido=apellido;
        this.correo=correo;
        this.lAlquileres= lista;
        this.aceptado_Por=aceptado_Por;
        this.es_Admin=es_Admin;
    }
    
    public String getUsername() {
        return username;
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }
    
    public void añadirAlquiler(Pelicula unaPelicula) {
        Alquiler alquiler= new Alquiler( new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), unaPelicula);
        this.lAlquileres.add(alquiler);
        //DBGestor.getDBGestor().añadirAlquiler(unaPelicula.getID(),username, new Date());
    }
    
    public Usuario setAceptadoPor(Usuario admin){
        this.aceptado_Por=admin;
        return this.aceptado_Por;
    }

    public String getContraseña() {
        return contraseña;
    }   
    
    public void actualizarCuenta(String username, String contraseña, String nombre, String apellido, String correo) {
        this.username=username;
        this.contraseña=contraseña;
        this.nombre=nombre;
        this.apellido=apellido;
        this.correo=correo;
    }
    
    public String enviarSolicitud(Pelicula pelicula) {
        ArrayList<Pelicula> solicitudes = GestorSolicitudesPeliculas.getmGestorSolicutudesPeliculas().getSolicitudes();
        solicitudes.add(pelicula);
        String mensaje = "Enviado solicitud de la Pelicula al GestorSolicitudesPeliculas " + pelicula.getTitulo() + "\n";
        return mensaje;
    }

    public Usuario getAceptado_Por() {
        return aceptado_Por;
    }

    public boolean isEsAdmin() {
        return es_Admin;
    }

    public boolean validarDatos() {
        boolean aceptado = true;

        // Verificar longitud de la contraseña
        if (this.contraseña.length() < 8) {
            System.out.println("Error: La contraseña debe tener al menos 8 caracteres.");
            aceptado = false;
        }

        // Verificar si la contraseña contiene al menos un carácter especial
        Pattern specialCharPattern = Pattern.compile("[^a-zA-Z0-9]");
        Matcher matcher = specialCharPattern.matcher(this.contraseña);
        if (!matcher.find()) {
            System.out.println("Error: La contraseña debe contener al menos un carácter especial.");
            aceptado = false;
        }

        // Verificar si el correo tiene la estructura correcta
        Pattern emailPattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
        matcher = emailPattern.matcher(this.correo);
        if (!matcher.matches()) {
            System.out.println("Error: El correo electrónico no tiene una estructura válida.");
            aceptado = false;
        }

        return aceptado;
    }

    public ArrayList<Alquiler> getAlquileres() {
        return lAlquileres;
    }

    public String getCorreo() {
        return correo;
    }

    public String aceptarCuenta(Usuario unUsuario) {
        if (this.es_Admin) {    
            if (unUsuario.getAceptado_Por() != null) {
                String mensaje = "La cuenta ya ha sido aceptada por " + unUsuario.getAceptado_Por().getUsername();
                return mensaje;
            }
            unUsuario.setAceptadoPor(this);
            String mensaje = "Ha sido aceptado por " + this.getUsername();
            return mensaje;
        } else {
            return "El usuario no es administrador, no puede aceptar la cuenta";
        }
    }

    public JSONObject mostrarAlquileres() {
        List<JSONObject> lJSONS = new ArrayList<>();
        for (Alquiler unAlquiler : lAlquileres) {
            lJSONS.add(unAlquiler.mostrarAlquiler());
        }
        JSONObject json = new JSONObject();
        return json.put("alquileres", new JSONArray(lJSONS));
    }
}
