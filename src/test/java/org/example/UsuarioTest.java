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


    public void testAñadirAlquiler() {
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
        Usuario usuarioPrueba = new Usuario("Niki", "contraseña-123", "Niki", "Lauda","niki@gmail.com", null, new ArrayList<Alquiler>(), false);
    
        usuarioPrueba.setContraseña("contraseña-123");
        usuarioPrueba.setCorreo("niki@gmail.com");
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

    public void testGetUsername() {
        // Configura un usuario de prueba
        Usuario usuarioPrueba = new Usuario("Fittipaldi", "contraseña-123", "Emmerson", "Fittipaldi", "fittipaldi@gmail.com", null, new ArrayList<Alquiler>(), false);
    
        // Verificar si getUsername devuelve el nombre de usuario correcto
        assertEquals("Fittipaldi", usuarioPrueba.getUsername());
    }
    
    public void testSetAceptadoPor() {
        // Configura dos usuarios de prueba, uno siendo el administrador
        Usuario admin = new Usuario("admin", "contraseña-123", "Oscar","Piastri", "admin@gmail.com", null, new ArrayList<Alquiler>(), true);
        Usuario usuarioPrueba = new Usuario("testuser", "contraseña-123", "Yuki", "Tsunoda", "yuki@gmail.com", null, new ArrayList<Alquiler>(), false);
    
        // Configuración del usuario usando el administrador
        usuarioPrueba.setAceptadoPor(admin);
        // Verificar si getAceptado_Por devuelve el administrador correcto
        assertEquals(admin, usuarioPrueba.getAceptado_Por());
    }
    
    public void testGetContraseña() {
        // Configura un usuario de prueba
        Usuario usuarioPrueba = new Usuario("Nick", "contraseña-123", "Nick", "DeVries", "nick@gmail.com", null, new ArrayList<Alquiler>(),false);
    
        // Verificar si getContraseña devuelve la contraseña correcta
        assertEquals("contraseña-123", usuarioPrueba.getContraseña());
    }
    
    public void testGetAceptado_Por() {
        // Configurar dos usuarios, uno siendo el administrador
        Usuario admin = new Usuario("admin", "contraseña-123", "Valtery", "Bottas", "valtery@gmail.com", null, new ArrayList<Alquiler>(), true);
        Usuario usuarioPrueba = new Usuario("testuser", "contraseña-123", "Logan", "Sargeant", "logan@gmail.com", null, new ArrayList<Alquiler>(), false);
    
        // Establecer el administrador con setAceptadoPor
        usuarioPrueba.setAceptadoPor(admin);
        
        // Verificar que getAceptado_Por devuelve el administrador correctamente
        assertEquals(admin, usuarioPrueba.getAceptado_Por());
    }
    
    public void testGetAlquileres() {

    }
    
    public void testGetCorreo() {
        // Configurar un usuario de prueba
        Usuario usuarioPrueba = new Usuario("Nikita", "password-123", "Nikita", "Mazepin", "nikita@gmail.com", null, new ArrayList<Alquiler>(), false);
    
        // Verificar que getCorreo devuelve el correo correctamente
        assertEquals("nikita@gmail.com", usuarioPrueba.getCorreo());
    }
    

    public void testMostrarAlquileres() {
        // Un usuario ha alquilado alguna pelicula
        String esperado = "{\"alquileres\":[{\"titulo\":\"Inception\",\"fechaInic\":\"2024-12-26 15:56:23\",\"peliID\":101,\"fechaFin\":\"2024-12-28 15:56:23\"}]}";
        assertEquals(u1.mostrarAlquileres().toString(),esperado);
        //Un usuario que no ha alquilado ninguna pelicula
        assertEquals(usuario1.mostrarAlquileres().getJSONArray("alquileres").length(),0);
    }
}
