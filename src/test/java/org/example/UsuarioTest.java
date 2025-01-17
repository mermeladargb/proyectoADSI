package org.example;

import junit.framework.TestCase;

import java.util.ArrayList;

public class UsuarioTest extends TestCase {

    Usuario u1,usuario1,usuario2;
    Pelicula p1;
    Alquiler a1;
    public void setUp() throws Exception {
        super.setUp();
        usuario1 = new Usuario(
                "jperez",
                "password123",
                "Juan",
                "Pérez",
                "juan.perez@example.com",
                u1, // Aceptado por el administrador
                new ArrayList<Alquiler>(),
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
        ArrayList<Alquiler> lista=new ArrayList<Alquiler>();
        lista.add(a1);
        u1= new Usuario("pancho","12345678","Pancho","Colate","pepe@gmail.com",u1,lista,true);
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetUsername() {
    }

    public void testAñadirAlquiler() {
    }

    public void testSetAceptadoPor() {
    }

    public void testGetContraseña() {
    }

    public void testActualizarCuenta() {
    }

    public void testEnviarSolicitud() {
    }

    public void testGetAceptado_Por() {
    }

    public void testValidarDatos() {
    }

    public void testGetAlquileres() {
    }

    public void testGetCorreo() {
    }

    public void testAceptarCuenta() {
    }

    public void testMostrarAlquileres() {
        String esperado = "{\"alquileres\":[{\"titulo\":\"Inception\",\"fechaInic\":\"2024-12-26 15:56:23\",\"peliID\":101,\"fechaFin\":\"2024-12-28 15:56:23\"}]}";
        assertEquals(u1.mostrarAlquileres().toString(),esperado) ;
        assertEquals(usuario1.mostrarAlquileres().getJSONArray("alquileres").length(),0);
    }
}