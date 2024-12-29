package org.gui;

import org.example.*;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Usuario u1 = new Usuario("u1", "12345678", null, null, "a@a", null, new ArrayList<Alquiler>(), false);

        GestorListas.getGestorListas().crearLista(u1, "lista1");
        GestorListas.getGestorListas().crearLista(u1, "lista2");
        GestorUsuarios.getGestorUsuarios().addUsuario(u1);
        Pelicula p1 = new Pelicula(1, "P1", "p1", null, null);
        Pelicula p2 = new Pelicula(1, "P2", "p2", null, null);
        GestorListas.getGestorListas().añadirPeliculaALista("u1", "lista1", p1);
        GestorListas.getGestorListas().añadirPeliculaALista("u1", "lista1", p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        u1.añadirAlquiler(p1);
        u1.añadirAlquiler(p2);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuPrincipal menuPrincipal = new MenuPrincipal();
                menuPrincipal.setVisible(true);
            }
        });
    }
}
