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
        boolean actualizado = u1.actualizarCuenta("pancho", "newpassword", "Pancho", "NuevoApellido", "nuevoemail@gmail.com");
        assertTrue(actualizado);
        assertEquals("newpassword", u1.getContraseña());
        assertEquals("NuevoApellido", u1.getApellido());
        assertEquals("nuevoemail@gmail.com", u1.getCorreo());
    }
    


    public void testEnviarSolicitud() {
        Pelicula pelicula = new Pelicula(104, "Nueva Pelicula", "Descripción de la nueva película.", usuario1, usuario2);
        String resultado = usuario1.enviarSolicitud(pelicula);
        assertEquals("Enviado solicitud de la Pelicula al GestorSolicitudesPeliculas Nueva Pelicula\n", resultado);
    }
    
    public void testValidarDatos() {
        // Configuración inicial con datos válidos
        usuario1.setContraseña("password-123");
        usuario1.setCorreo("charlesleclerc@example.com");
        boolean datosValidos = usuario1.validarDatos();
        assertTrue(datosValidos);
        
        // Configuración con datos inválidos
        usuario1.setContraseña("pass");  // Contraseña corta
        boolean datosInvalidos = usuario1.validarDatos();
        assertFalse(datosInvalidos);
    
        // Restauración de datos válidos para siguiente prueba
        usuario1.setContraseña("password-123");
    
        // Configuración con correo inválido
        usuario1.setCorreo("pierregasly@gmail");  // Correo con formato incorrecto
        datosInvalidos = usuario1.validarDatos();
        assertFalse(datosInvalidos);
    }
    
    public void testAceptarCuenta() {
        usuario1.aceptarCuenta(u1);
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
        assertEquals(u1.mostrarAlquileres().toString(),esperado) ;
        assertEquals(usuario1.mostrarAlquileres().getJSONArray("alquileres").length(),0);
    }
}