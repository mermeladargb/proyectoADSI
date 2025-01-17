package org.example;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class GestorPeliculasTest extends TestCase {
    Pelicula p1,p2,p3,p4,p5,p6,p7;
    Usuario u1,usuario1,usuario2;
    Alquiler a1;
    @Before
    public void setUp() throws Exception {
        super.setUp();
        GestorUsuarios.getGestorUsuarios().reset();
        GestorPeliculas.getGestorPeliculas().reset();
        GestorPeliculas.getGestorPeliculas().reset();
        p5= new Pelicula(  110, "Frozen", "Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.", null, null);
        p6= new Pelicula(  112, "Freeze", "pepe", null, null);
        p7= new Pelicula(  114, "Fury", "pepe", null, null);
        GestorPeliculas.getGestorPeliculas().addPelicula(p5);
        GestorPeliculas.getGestorPeliculas().addPelicula(p6);
        GestorPeliculas.getGestorPeliculas().addPelicula(p7);
        usuario1 = new Usuario(
                "jperez",
                "password123",
                "Juan",
                "Pérez",
                "juan.perez@example.com",
                u1, // Aceptado por el administrador
                null,
                false  // No es administrador
        );

        usuario2 = new Usuario(
                "mlopez",
                "securePass",
                "María",
                "López",
                "maria.lopez@example.com",
                u1, // Aceptado por el administrador
                new ArrayList<Alquiler>(),
                false  // No es administrador
        );
        p1= new Pelicula(  101, "Inception", "Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.", usuario1, usuario2);
        p2= new Pelicula(  102, "Insertar", "pepe", usuario1, usuario2);
        p3= new Pelicula(  103, "Option", "pepe", usuario1, usuario2);
        a1= new Alquiler("2024-12-26 15:56:23",p1);
        ArrayList<Alquiler> lista=new ArrayList<Alquiler>();
        lista.add(a1);
        u1= new Usuario("pancho","12345678","Pancho","Colate","pepe@gmail.com",u1,lista,true);
        GestorUsuarios.getGestorUsuarios().addUsuario(usuario1);
        GestorUsuarios.getGestorUsuarios().addUsuario(usuario2);
        GestorUsuarios.getGestorUsuarios().addUsuario(u1);
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
    public void testmostrarPelicula() {
        String esperado=  "{\"descrip\":\"Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.\",\"titulo\":\"Inception\",\"ID\":101,\"media\":\"NaN\"}";
        assertEquals(esperado,VideoClub.getUnVideoClub().seleccionarPelicula(101).toString());
        Valoracion v1 = new Valoracion((float)5.5,"Ta bien",usuario1);
        Valoracion v2= new Valoracion((float)9.8,"Excelente pelicula",usuario2);
        Valoracion v3= new Valoracion((float)3.3,"Ta mala",u1);
        p1.addValoracion(v1);
        p1.addValoracion(v2);
        p1.addValoracion(v3);

        esperado="{\"descrip\":\"Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.\",\"titulo\":\"Inception\",\"ID\":101,\"media\":\"6,20\"}";
        assertEquals(esperado, GestorPeliculas.getGestorPeliculas().verDetallesPelicula(101).toString());

        assertNull(GestorPeliculas.getGestorPeliculas().verDetallesPelicula(300));
    }

    @Test
    public void testBuscarPeliSeleccionada() {


        assertTrue(GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(101).getID()==101);

        assertNull(GestorPeliculas.getGestorPeliculas().buscarPeliSeleccionada(300));
    }

    @Test
    public void testMostrarPeliculas() {


        String esperado = "{\"peliculas\":[{\"titulo\":\"Frozen\",\"id\":110,\"media\":\"NaN\"}]}";;
        assertEquals(GestorPeliculas.getGestorPeliculas().mostrarPeliculas("Frozen").toString(),esperado);
        esperado = "{\"peliculas\":[{\"titulo\":\"Frozen\",\"id\":110,\"media\":\"NaN\"},{\"titulo\":\"Freeze\",\"id\":112,\"media\":\"NaN\"}]}";
        assertEquals(GestorPeliculas.getGestorPeliculas().mostrarPeliculas("fr").toString(),esperado);
        p4= new Pelicula(123,"Frozen Ice","",null,null);
        GestorPeliculas.getGestorPeliculas().addPelicula(p4);
        esperado="{\"peliculas\":[{\"titulo\":\"Frozen\",\"id\":110,\"media\":\"NaN\"},{\"titulo\":\"Frozen Ice\",\"id\":123,\"media\":\"NaN\"}]}";
        assertEquals(GestorPeliculas.getGestorPeliculas().mostrarPeliculas("frozen").toString(),esperado);

        //Se muestran todas las peliculas sino se introduce valor
        //assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("").getJSONArray("peliculas").length(),0);
        assertEquals(GestorPeliculas.getGestorPeliculas().mostrarPeliculas("Fwererowesfn").getJSONArray("peliculas").length(),0);

    }

    @Test
    public void testAddPelicula() {
    }
}