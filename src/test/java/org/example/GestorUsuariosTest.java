package org.example;

import junit.framework.TestCase;
import org.json.JSONArray;

import java.util.ArrayList;

public class GestorUsuariosTest extends TestCase {
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
        p1= new Pelicula(  101,
                "Inception",
                "Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.",
                usuario1,
                usuario2
        );
        p2= new Pelicula(  102, "Insertar", "pepe", usuario1, usuario2);
        p3= new Pelicula(  103, "Option", "pepe", usuario1, usuario2);
        a1= new Alquiler("2024-12-26 15:56:23",p1);
        ArrayList<Alquiler>lista=new ArrayList<Alquiler>();
        lista.add(a1);
        u1= new Usuario("pancho","12345678","Pancho","Colate","pepe@gmail.com",u1,lista,true);
        GestorUsuarios.getGestorUsuarios().addUsuario(usuario1);
        GestorUsuarios.getGestorUsuarios().addUsuario(usuario2);
        GestorUsuarios.getGestorUsuarios().addUsuario(u1);

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetGestorUsuarios() {

    }

    public void testGetUsuario() {



        assertEquals(GestorUsuarios.getGestorUsuarios().getUsuario("pancho"),u1);
        assertNull(GestorUsuarios.getGestorUsuarios().getUsuario(""));

    }

    public void testCuentaExistente() {
    }
    public void testVerAlquileres() {
        String esperado = "{\"alquileres\":[{\"titulo\":\"Inception\",\"fechaInic\":\"2024-12-26 15:56:23\",\"peliID\":101,\"fechaFin\":\"2024-12-28 15:56:23\"}]}";
        assertEquals(GestorUsuarios.getGestorUsuarios().verAlquileres("pancho").toString(),esperado) ;
        assertNull(VideoClub.getUnVideoClub().verAlquileres(""));
        assertEquals(GestorUsuarios.getGestorUsuarios().verAlquileres("mlopez").getJSONArray("alquileres").length(),0);
    }


    public void testRegistrarUsuario() {
    }

    public void testCuentaValida() {
    }

    public void testEliminarCuenta() {
    }

    public void testModificarCuenta() {
    }

    public void testAlquilarPeli() {
        GestorUsuarios.getGestorUsuarios().alquilarPeli("mlopez",p3);
        JSONArray json1=usuario2.mostrarAlquileres().getJSONArray("alquileres");
        assertTrue (json1.getJSONObject(0).get("titulo")=="Option" && json1.length()==1) ;
        GestorUsuarios.getGestorUsuarios().alquilarPeli("mlopez",p1);
        json1=usuario2.mostrarAlquileres().getJSONArray("alquileres");
        assertTrue (json1.getJSONObject(1).get("titulo")=="Inception" && json1.length()==2) ;

        GestorUsuarios.getGestorUsuarios().alquilarPeli("mlopez",null);
        json1=usuario2.mostrarAlquileres().getJSONArray("alquileres");
        assertTrue (json1.getJSONObject(1).get("titulo")=="Inception" && json1.length()==2) ;
    }
}