package org.example;

import junit.framework.TestCase;

import java.util.ArrayList;

public class AlquilerTest extends TestCase {
    Pelicula p1;
    Alquiler a1;
    public void setUp() throws Exception {
        super.setUp();
        p1= new Pelicula(  101,
                "Inception",
                "Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.",
                null,
               null
        );
        a1= new Alquiler("2024-12-26 15:56:23",p1);

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testMostrarAlquiler() {
        String esperado = "{\"titulo\":\"Inception\",\"fechaInic\":\"2024-12-26 15:56:23\",\"peliID\":101,\"fechaFin\":\"2025-01-10 15:56:23\"}";
        assertEquals(a1.mostrarAlquiler().toString(),esperado) ;
    }

    public void testGetPelicula() {
    }
}