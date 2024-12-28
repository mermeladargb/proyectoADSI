package org.example;

import junit.framework.TestCase;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideoClubTest extends TestCase {

    Usuario u1,u2,u3,u4,usuario1,usuario2;
    Alquiler a1,a2,a3,a4;
    Pelicula p1,p2,p3,p4;

    public void setUp() throws Exception {
        super.setUp();
        GestorUsuarios.getGestorUsuarios().reset();
        GestorPeliculas.getGestorPeliculas().reset();
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

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetUnVideoClub() {
    }

    public void testMostrarPeliculasSimilares() {


        String esperado = "{\"peliculas\":[{\"titulo\":\"Inception\",\"id\":101}]}";
        assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("Inception").toString(),esperado);
        esperado = "{\"peliculas\":[{\"titulo\":\"Inception\",\"id\":101},{\"titulo\":\"Option\",\"id\":103}]}";
        assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("ption").toString(),esperado);
        esperado=  "{\"peliculas\":[{\"titulo\":\"Inception\",\"id\":101},{\"titulo\":\"Insertar\",\"id\":102}]}";
        assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("in").toString(),esperado);

        //Se muestran todas las peliculas sino se introduce valor
        //assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("").getJSONArray("peliculas").length(),0);
        assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("patata").getJSONArray("peliculas").length(),0);

    }

    public void testSeleccionarPelicula() {

        String esperado=  "{\"descrip\":\"Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.\",\"titulo\":\"Inception\",\"ID\":101,\"media\":\"NaN\"}";
      assertEquals(esperado,VideoClub.getUnVideoClub().seleccionarPelicula(101).toString());
      Valoracion v1 = new Valoracion((float)5.5,"Ta bien",usuario1);
      Valoracion v2= new Valoracion((float)9.8,"Excelente pelicula",usuario2);
      Valoracion v3= new Valoracion((float)3.3,"Ta mala",u1);
      p1.addValoracion(v1);
      p1.addValoracion(v2);
      p1.addValoracion(v3);

      esperado="{\"descrip\":\"Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.\",\"titulo\":\"Inception\",\"ID\":101,\"media\":\"6,20\"}";
      assertEquals(esperado, VideoClub.getUnVideoClub().seleccionarPelicula(101).toString());

      assertNull(VideoClub.getUnVideoClub().seleccionarPelicula(300));

    }

    public void testAlquilarPeli() {

        VideoClub.getUnVideoClub().alquilarPeli("mlopez",103);
        JSONArray json1=usuario2.mostrarAlquileres().getJSONArray("alquileres");
        assertTrue (json1.getJSONObject(0).get("titulo")=="Option" && json1.length()==1) ;
        VideoClub.getUnVideoClub().alquilarPeli("mlopez",101);
        json1=usuario2.mostrarAlquileres().getJSONArray("alquileres");
        assertTrue (json1.getJSONObject(1).get("titulo")=="Inception" && json1.length()==2) ;

        VideoClub.getUnVideoClub().alquilarPeli("mlopez",300);
        json1=usuario2.mostrarAlquileres().getJSONArray("alquileres");
        assertTrue (json1.getJSONObject(1).get("titulo")=="Inception" && json1.length()==2) ;





    }

    public void testVerAlquileres() {


        String esperado = "{\"alquileres\":[{\"titulo\":\"Inception\",\"fechaInic\":\"2024-12-26 15:56:23\",\"peliID\":101,\"fechaFin\":\"2025-01-10 15:56:23\"}]}";
        assertEquals(VideoClub.getUnVideoClub().verAlquileres("pancho").toString(),esperado) ;
        assertNull(VideoClub.getUnVideoClub().verAlquileres(""));
        assertEquals(VideoClub.getUnVideoClub().verAlquileres("mlopez").getJSONArray("alquileres").length(),0);
    }

    public void testMostrarValoracionesAntiguas() {
    }

    public void testPuntuarPelicula() {
    }

    public void testMostrarReseñas() {
    }

    public void testVerificarRegistro() {
    }

    public void testVerificarInicioDeSesion() {
    }

    public void testActualizarDatos() {
    }

    public void testMostrarSolicitudes() {
    }

    public void testModificarCuentaSeleccionada() {
    }

    public void testEliminarCuentaSeleccionada() {
    }

    public void testCrearLista() {
    }

    public void testGetListasUsuario() {
    }

    public void testGetListaUsuario() {
    }

    public void testAñadirPeliculaALista() {
    }

    public void testBuscarLista() {
    }

    public void testCambiarVisibilidadLista() {
    }
}