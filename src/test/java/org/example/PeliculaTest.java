package org.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.json.JSONObject;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class PeliculaTest {
    private Pelicula pelicula;
    private Usuario user1, user2;


    @Before
    public void setUp() {
        pelicula = new Pelicula(1, "Batman", "Hombre Murcielago", null, null);
        user1 = new Usuario("u1", "12345678", null, null, "user1@user1.com", null, new ArrayList<>(), false);
        user2 = new Usuario("u2", "87654321", null, null, "user2@user2.com", null, new ArrayList<>(), false);
    }

    @Test
    public void testTieneNombreParecido1() {
        JSONObject resultado = pelicula.tieneNombreParecido("Batman");
        assertEquals(1, resultado.getInt("id"));
        assertEquals("Batman", resultado.getString("titulo"));
    }

    @Test
    public void testTieneNombreParecido2() {
        JSONObject resultado = pelicula.tieneNombreParecido("bAtMan");
        assertEquals(1, resultado.getInt("id"));
        assertEquals("Batman", resultado.getString("titulo"));
    }
    @Test
    public void testNOTieneNombreParecido() {
        JSONObject resultado = pelicula.tieneNombreParecido("Spiderman");
        assertNull(resultado);
    }
    @Test
    public void testGuardarValoracionNuevoUsuario() {
        pelicula.guardarValoracion(user1, "Buena pelicula", 5);
        double puntuacionEsperada = 5.0;
        double puntuacionActual = pelicula.verValoraciones(user1.getUsername()).get(0).getPuntuacion();
        assertEquals(puntuacionEsperada, puntuacionActual, 0.001);
    }

    @Test
    public void testGuardarValoracionActualizarUsuario() {
        pelicula.guardarValoracion(user1, "Buena", 5);
        pelicula.guardarValoracion(user1, "Mala peli", 2);

        ArrayList<Valoracion> valoraciones = pelicula.verValoraciones(user1.getUsername());
        assertEquals(1, valoraciones.size());
        assertEquals("Mala peli", valoraciones.get(0).getReseña());
        assertEquals(2, valoraciones.get(0).getPuntuacion(), 0.001);
    }

    @Test
    public void testVerValoracionesUsuario() {
        pelicula.guardarValoracion(user1, "Buena", 5);
        pelicula.guardarValoracion(user2, "Mala", 3);

        ArrayList<Valoracion> valoraciones = pelicula.verValoraciones(user1.getUsername());
        assertEquals("u1", valoraciones.get(0).getUser());
    }
}


