package org.gui;

import java.util.ArrayList;

import javax.swing.SwingUtilities;

import org.example.*;

public class Main {

    public static void main(String[] args) {
        Usuario u1 = new Usuario("u1", "12345678", null, null, "a@a", null, new ArrayList<Alquiler>(), false);
        Usuario u2 = new Usuario("u2", "12345678", null, null, "a@a", null, new ArrayList<Alquiler>(), true);
        Usuario u3 = new Usuario("u3", "12345678", null, null, "a@a", null, new ArrayList<Alquiler>(), false);
        Usuario u4 = new Usuario("u4", "12345678", null, null, "a@a", null, new ArrayList<Alquiler>(), false);
        Usuario u5 = new Usuario("u5", "12345678", null, null, "a@a", null, new ArrayList<Alquiler>(), false);
        Usuario u6 = new Usuario("u6", "12345678", null, null, "a@a", null, new ArrayList<Alquiler>(), false);

        GestorListas.getGestorListas().crearLista(u1, "lista1");
        GestorListas.getGestorListas().crearLista(u1, "lista2");
        GestorUsuarios.getGestorUsuarios().addUsuario(u1);
        GestorUsuarios.getGestorUsuarios().addUsuario(u2);
        GestorUsuarios.getGestorUsuarios().addUsuario(u3);
        GestorUsuarios.getGestorUsuarios().addUsuario(u4);
        GestorUsuarios.getGestorUsuarios().addUsuario(u5);
        GestorUsuarios.getGestorUsuarios().addUsuario(u6);
        Pelicula p1 = new Pelicula(1, "P1", "p1", null, null);
        Pelicula p2 = new Pelicula(2, "P2", "p2", null, null);
        GestorListas.getGestorListas().añadirPeliculaALista("u1", "lista1", p1);
        for (int i = 0; i < 20; i++)
            GestorListas.getGestorListas().añadirPeliculaALista("u1", "lista1", p2);

       /* u1.añadirAlquiler(p1);
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
        */

        GestorPeliculas.getGestorPeliculas().addPelicula(p1);
        GestorPeliculas.getGestorPeliculas().addPelicula(p2);

        DBGestor.getDBGestor().cargarUsuarios();
        Pelicula peli = new Pelicula(1, "SW", "bla", null, null);
        GestorPeliculas.getGestorPeliculas().addPelicula(peli);
        DBGestor.getDBGestor().cargarListas();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuPrincipal menuPrincipal = new MenuPrincipal();
                menuPrincipal.setVisible(true);
            }
        });
    }
}
