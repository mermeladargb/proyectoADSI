package org.example;

import junit.framework.TestCase;

public class GestorPeliculasTest extends TestCase {
    Pelicula p1,p2,p3,p4;
    public void setUp() throws Exception {
        super.setUp();
        p1= new Pelicula(  101, "Frozen", "Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.", null, null);
        p2= new Pelicula(  102, "Freeze", "pepe", null, null);
        p3= new Pelicula(  103, "Fury", "pepe", null, null);
        GestorPeliculas.getGestorPeliculas().addPelicula(p1);
        GestorPeliculas.getGestorPeliculas().addPelicula(p2);
        GestorPeliculas.getGestorPeliculas().addPelicula(p3);
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetGestorPeliculas() {
    }

    public void testBuscarPeliSeleccionada() {
    }

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

    public void testAddPelicula() {
    }
}