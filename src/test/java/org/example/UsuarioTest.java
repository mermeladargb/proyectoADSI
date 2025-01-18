package org.example;

import java.util.ArrayList;
import junit.framework.TestCase;

public class UsuarioTest extends TestCase {

    Usuario u1, usuario1, usuario2;
    Pelicula p1;
    Alquiler a1;

    public void setUp() throws Exception {
        super.setUp();
        usuario1 = new Usuario(
                "jperez",
                "password-123",
                "Juan",
                "Pérez",
                "juan.perez@example.com",
                u1,
                new ArrayList<Alquiler>(),
                false
        );

        usuario2 = new Usuario(
                "mlopez",
                "secure-Pass1",
                "María",
                "López",
                "maria.lopez@example.com",
                u1,
                null,
                false
        );
        p1 = new Pelicula(101,
               "Inception",
               "Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.",
               usuario1, usuario2
        );
        a1 = new Alquiler("2024-12-26 15:56:23", p1);
        ArrayList<Alquiler> lista = new ArrayList<Alquiler>();
        lista.add(a1);
        u1 = new Usuario("pancho", "12345678", "Pancho", "Colate", "pepe@gmail.com", u1, lista, true);
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
        boolean actualizado = u1.actualizarCuenta("liam", "contraseña-123", "liam", "lawson", "liam@gmail.com");
        assertTrue(actualizado);
        assertEquals("contraseña-123", u1.getContraseña());
        assertEquals("lawson", u1.getApellido());
        assertEquals("liam@gmail.com", u1.getCorreo());
    }

    public void testEnviarSolicitud() {
        Pelicula pelicula = new Pelicula(104, "Nueva Pelicula", "Descripción de la nueva película.", usuario1, usuario2);
        String resultado = usuario1.enviarSolicitud(pelicula);
        assertEquals("Enviado solicitud de la Pelicula al GestorSolicitudesPeliculas Nueva Pelicula\n", resultado);
    }

    public void testValidarDatos() {
        Usuario usuarioPrueba = new Usuario(
            "testuser",
            "password-123",
            "Test",
            "User",
            "test.user@example.com",
            null,
            new ArrayList<Alquiler>(),
            false
        );
    
        usuarioPrueba.setContraseña("password-123");
        usuarioPrueba.setCorreo("test.user@example.com");
        boolean datosValidos = usuarioPrueba.validarDatos();
        assertTrue(datosValidos);
    

    
        usuarioPrueba.setContraseña("password-123");
        usuarioPrueba.setCorreo("wrongemail.com");
        boolean datosInvalidos = usuarioPrueba.validarDatos();
        assertFalse(datosInvalidos);
    }
    

    public void testAceptarCuenta() {
        u1.aceptarCuenta(usuario1);
        assertTrue(usuario1.getAceptado_Por() != null);
    }

    public void testGetAceptado_Por() {
    }

    public void testGetAlquileres() {
    }

    public void testGetCorreo() {
    }

    public void testMostrarAlquileres() {
        String esperado = "{\"alquileres\":[{\"titulo\":\"Inception\",\"fechaInic\":\"2024-12-26 15:56:23\",\"peliID\":101,\"fechaFin\":\"2024-12-28 15:56:23\"}]}";
        assertEquals(u1.mostrarAlquileres().toString(),esperado);
        assertEquals(usuario1.mostrarAlquileres().getJSONArray("alquileres").length(),0);
    }
}
