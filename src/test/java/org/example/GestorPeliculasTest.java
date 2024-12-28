package org.example;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class GestorPeliculasTest extends TestCase {
    Pelicula p1,p2,p3,p4;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        GestorPeliculas.getGestorPeliculas().reset();
        p1= new Pelicula(  101, "Frozen", "Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.", null, null);
        p2= new Pelicula(  102, "Freeze", "pepe", null, null);
        p3= new Pelicula(  103, "Fury", "pepe", null, null);
        GestorPeliculas.getGestorPeliculas().addPelicula(p1);
        GestorPeliculas.getGestorPeliculas().addPelicula(p2);
        GestorPeliculas.getGestorPeliculas().addPelicula(p3);


    }

    @After
    public void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void testGetGestorPeliculas() {
    }

    @Test
    public void testBuscarPeliSeleccionada() {


        assertTrue(GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(101).getID()==101);

        assertNull(GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(300));
    }

    @Test
    public void testMostrarPeliculas() {


        String esperado = "{\"peliculas\":[{\"titulo\":\"Frozen\",\"id\":101}]}";
        assertEquals(GestorPeliculas.getGestorPeliculas().mostrarPeliculas("Frozen").toString(),esperado);
        esperado = "{\"peliculas\":[{\"titulo\":\"Frozen\",\"id\":101},{\"titulo\":\"Freeze\",\"id\":102}]}";
        assertEquals(GestorPeliculas.getGestorPeliculas().mostrarPeliculas("fr").toString(),esperado);
        p4= new Pelicula(123,"Frozen Ice","",null,null);
        GestorPeliculas.getGestorPeliculas().addPelicula(p4);
        esperado="{\"peliculas\":[{\"titulo\":\"Frozen\",\"id\":101},{\"titulo\":\"Frozen Ice\",\"id\":123}]}";
        assertEquals(GestorPeliculas.getGestorPeliculas().mostrarPeliculas("frozen").toString(),esperado);

        //Se muestran todas las peliculas sino se introduce valor
        //assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("").getJSONArray("peliculas").length(),0);
        assertEquals(GestorPeliculas.getGestorPeliculas().mostrarPeliculas("Fwererowesfn").getJSONArray("peliculas").length(),0);

    }

    @Test
    public void testAddPelicula() {
    }
}