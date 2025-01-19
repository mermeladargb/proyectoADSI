package org.example;

import junit.framework.TestCase;

import java.util.ArrayList;

public class PedirPeliculaTest extends TestCase {
    private Usuario user1;

    public void setUp() throws Exception {
        user1 = new Usuario("u1", "12345678", null, null, "user1@user1.com", null, new ArrayList<>(), false);
        GestorUsuarios.getGestorUsuarios().addUsuario(user1);

        VideoClub.getUnVideoClub().pedirPelicula("Ratatouille", "u1");
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMostrarSolicitudPelicula() {
        ArrayList<SolicitudPelicula> solicitudes = VideoClub.getUnVideoClub().pedirSolicitudesPeliculas();
        String titulo = null;
        String user = null;

        for (SolicitudPelicula solicitud : solicitudes){
            if (solicitud.getTitulo().equals("Ratatouille")){
                titulo = solicitud.getTitulo();
                user = solicitud.solicitadaPor().getUsername();
            }
        }
        String tituloEsperado = "Ratatouille";
        String userEsperado = "u1";

        assertEquals(tituloEsperado,titulo);
        assertEquals(userEsperado,user);

    }
}