package org.example;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.json.JSONObject;

public class PeliculaTest {
    private Pelicula pelicula;

    @Before
    public void setUp() {
        pelicula = new Pelicula(1, "Batman", "Hombre Murcielago", null, null);
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


}
