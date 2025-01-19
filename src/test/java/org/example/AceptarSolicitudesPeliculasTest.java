package org.example;

import junit.framework.TestCase;

import java.util.ArrayList;

import org.json.JSONObject;

public class AceptarSolicitudesPeliculasTest extends TestCase {
    private Usuario user1, user2;

    public void setUp() throws Exception {
        user1 = new Usuario("u1", "12345678", null, null, "user1@user1.com", null, new ArrayList<>(), false);
        user2 = new Usuario("u2", "12345678", null, null, "user2@user2.com", null, new ArrayList<>(), true);

        GestorUsuarios.getGestorUsuarios().addUsuario(user1);
        VideoClub.getUnVideoClub().pedirPelicula("Ratatouille", "u1");

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAceptarSolicitudPelicula() {
        
        String titulo = null;
        Usuario user = null;

        GestorSolicitudesPeliculas.getmGestorSolicutudesPeliculas().aceptarSolicitudPelicula("Ratatouille",user2.getUsername());

        ArrayList<SolicitudPelicula> solicitudes = VideoClub.getUnVideoClub().pedirSolicitudesPeliculas();

        for (SolicitudPelicula solicitud : solicitudes){
            if (solicitud.getTitulo().equals("Ratatouille")){
                titulo = solicitud.getTitulo();
                user = solicitud.solicitadaPor();
            }
        }

        assertTrue(titulo == null);
        assertTrue(user == null);

        Pelicula peli = GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(382932);

        String title = peli.getTitulo();

        assertEquals(title, "Ratatouille");
    }

    public void testRechazarSolicitudPelicula() {
       
        String titulo = null;
        Usuario user = null;

        GestorSolicitudesPeliculas.getmGestorSolicutudesPeliculas().rechazarSolicitudPelicula(titulo);

        ArrayList<SolicitudPelicula> solicitudes = VideoClub.getUnVideoClub().pedirSolicitudesPeliculas();

        for (SolicitudPelicula solicitud : solicitudes){
            if (solicitud.getTitulo().equals("Ratatouille")){
                titulo = solicitud.getTitulo();
                user = solicitud.solicitadaPor();
            }
        }

        assertTrue(titulo == null);
        assertTrue(user == null);

    }
}