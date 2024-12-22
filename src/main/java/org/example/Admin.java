package org.example;

import java.util.ArrayList;

public class Admin extends Usuario {

    public Admin(String username, String contraseña, String nombre, String apellido, String correo, Admin aceptadoPor,
                 ArrayList<Alquiler> alquileres) {
        super(username, contraseña, nombre, apellido, correo, aceptadoPor, alquileres);
    }

    public String aceptarCuenta(Usuario unUsuario) {
        if (unUsuario.getAceptado_Por() != null) {
            String mensaje="La cuenta ya ha sido aceptada por " + unUsuario.getAceptado_Por().getUsername();
        	return mensaje;
        }
        unUsuario.setAceptadoPor(this);
        String mensaje="Ha sido aceptado por " + this.getUsername();
        return mensaje;
    }
}
