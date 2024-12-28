package org.example;

import junit.framework.TestCase;

import java.util.ArrayList;

public class GestorUsuariosTest extends TestCase {
    Usuario u1,u2,u3,u4,usuario1,usuario2;
    Alquiler a1,a2,a3,a4;
    Pelicula p1,p2,p3,p4;
    public void setUp() throws Exception {
        super.setUp();
        GestorUsuarios.getGestorUsuarios().reset();
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
                null,
                false  // No es administrador
        );
        p1= new Pelicula(  101,
                "Inception",
                "Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.",
                usuario1,
                usuario2
        );
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
        //System.out.println(u1.verAlquileres().getJSONObject(0).get("titulo"));
        //System.out.println(VideoClub.getUnVideoClub().verAlquileres("pancho"));



        assertEquals(GestorUsuarios.getGestorUsuarios().getUsuario("pancho"),u1);
        assertNull(GestorUsuarios.getGestorUsuarios().getUsuario(""));

    }

    public void testCuentaExistente() {
    }

    public void testRegistrarUsuario() {
    }

    public void testCuentaValida() {
    }

    public void testEliminarCuenta() {
    }

    public void testModificarCuenta() {
    }
}