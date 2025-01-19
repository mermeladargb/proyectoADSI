package org.example;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import junit.framework.TestCase;

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
                "password-123",
                "Juan",
                "Pérez",
                "juan.perez@example.com",
                u1, // Aceptado por el administrador
                null,
                false  // No es administrador
        );

        usuario2 = new Usuario(
                "mlopez",
                "secure-Pass1",
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
        p4= new Pelicula(  124, "SuperLopez", "pepe", usuario1, usuario2);
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
        GestorPeliculas.getGestorPeliculas().addPelicula(p4);

    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testGetUnVideoClub() {
    }

    public void testMostrarPeliculasSimilares() {

        // Se busca una pelicula en concreto que no tenga peliculas que se le parezcan, si tuviera alguna pelicula que se parezca tambien aparecería 
        String esperado = "{\"peliculas\":[{\"titulo\":\"SuperLopez\",\"id\":124,\"media\":\"NaN\"}]}";
        assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("SuperLopez").toString(),esperado);
        // Se busca por una cadena las pelicualas que contengan esa cadena
        esperado = "{\"peliculas\":[{\"titulo\":\"Inception\",\"id\":101,\"media\":\"NaN\"},{\"titulo\":\"Option\",\"id\":103,\"media\":\"NaN\"}]}";
        assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("ption").toString(),esperado);
        esperado=  "{\"peliculas\":[{\"titulo\":\"Inception\",\"id\":101,\"media\":\"NaN\"},{\"titulo\":\"Insertar\",\"id\":102,\"media\":\"NaN\"}]}";
        assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("in").toString(),esperado);

        //Si se buscará un valor nulo todas las peliculas que están en el sistema aparecerán
        // Se busca una pelicula que no se parece a ninguna de las almacenadas en el sistema
        assertEquals(VideoClub.getUnVideoClub().mostrarPeliculasSimilares("patata").getJSONArray("peliculas").length(),0);

    }

    public void testSeleccionarPelicula() {

     // Se selecciona una pelicula dentro del sistema que no tiene valoraciones
      String esperado=  "{\"descrip\":\"Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.\",\"titulo\":\"Inception\",\"ID\":101,\"media\":\"NaN\"}";
      assertEquals(esperado,VideoClub.getUnVideoClub().seleccionarPelicula(101).toString());
      Valoracion v1 = new Valoracion((float)5.5,"Ta bien",usuario1);
      Valoracion v2= new Valoracion((float)9.8,"Excelente pelicula",usuario2);
      Valoracion v3= new Valoracion((float)3.3,"Ta mala",u1);
      p1.addValoracion(v1);
      p1.addValoracion(v2);
      p1.addValoracion(v3);

      // Se selecciona una pelicula dentro del sistema que  tiene valoraciones
      esperado="{\"descrip\":\"Un ladrón que roba secretos corporativos a través del uso de tecnología para compartir sueños.\",\"titulo\":\"Inception\",\"ID\":101,\"media\":\"6,20\"}";
      assertEquals(esperado, VideoClub.getUnVideoClub().seleccionarPelicula(101).toString());

     // Se selecciona una pelicula que no esta en el sistema
      assertNull(VideoClub.getUnVideoClub().seleccionarPelicula(300));

    }

    public void testAlquilarPeli() {

        //Alquila una pelicula existente en el sistema un Usuario
        VideoClub.getUnVideoClub().alquilarPeli("mlopez",103);
        JSONArray json1=usuario2.mostrarAlquileres().getJSONArray("alquileres");
        assertTrue (json1.getJSONObject(0).get("titulo")=="Option" && json1.length()==1) ;
        //Alquila una pelicula existente en el sistema el mismo Usuario
        VideoClub.getUnVideoClub().alquilarPeli("mlopez",101);
        json1=usuario2.mostrarAlquileres().getJSONArray("alquileres");
        assertTrue (json1.getJSONObject(1).get("titulo")=="Inception" && json1.length()==2) ;

        //Alquila una pelicula que no existe por lo que mo se añade la pelicula
        VideoClub.getUnVideoClub().alquilarPeli("mlopez",300);
        json1=usuario2.mostrarAlquileres().getJSONArray("alquileres");
        assertTrue (json1.getJSONObject(1).get("titulo")=="Inception" && json1.length()==2) ;


    }

    public void testVerAlquileres() {

        // Alquileres de un usuario que ha alquilado varias veces
        String esperado = "{\"alquileres\":[{\"titulo\":\"Inception\",\"fechaInic\":\"2024-12-26 15:56:23\",\"peliID\":101,\"fechaFin\":\"2024-12-28 15:56:23\"}]}";
        assertEquals(VideoClub.getUnVideoClub().verAlquileres("pancho").toString(),esperado) ;
        // Alquileres de un usuario que no se encuentra en el sistema
        assertNull(VideoClub.getUnVideoClub().verAlquileres(""));
        // Alquileres de un usuario que se encuentra en el sistema pero que no ha alquilado con anterioridad
        assertEquals(VideoClub.getUnVideoClub().verAlquileres("mlopez").getJSONArray("alquileres").length(),0);
    }


    public void testObtenerDatosUsuario() {
        JSONObject datos = VideoClub.getUnVideoClub().obtenerDatosUsuario("jperez");
        assertEquals("jperez", datos.getString("username"));
        assertEquals("Juan", datos.getString("nombre"));
        assertEquals("Pérez", datos.getString("apellido"));
        assertEquals("juan.perez@example.com", datos.getString("correo"));
    }
    
    public void testModificarCuenta() {
        GestorUsuarios gestorUsuarios = GestorUsuarios.getGestorUsuarios();
        VideoClub videoClub = VideoClub.getUnVideoClub();
        
        gestorUsuarios.addUsuario(new Usuario("Michael", "contraseña-123", "Michael", "Schumacher", "schumi@gmail.com", null, new ArrayList<Alquiler>(), true));
        gestorUsuarios.addUsuario(new Usuario("Esteban", "contraseña-123", "Esteban", "Ocon", "estebanviejo@gmail.com", null, new ArrayList<Alquiler>(), false));
        
        JSONObject resultado = videoClub.modificarCuenta("Michael", "Piastri", "Ocon", "Esteban", "Contraseña-123", "esteban@gmail.com");
        assertEquals("exitoso", resultado.getString("estado"));
        
        JSONObject datos = videoClub.obtenerDatosUsuario("Esteban");
        assertEquals("Piastri", datos.getString("nombre"));
        assertEquals("Ocon", datos.getString("apellido"));
        assertEquals("esteban@gmail.com", datos.getString("correo"));
    }
    
    
    public void testVerificarRegistro() {
        JSONObject resultado = VideoClub.getUnVideoClub().verificarRegistro("Michael", "Massi", "Massi", "contraseña-123", "michael@gmail.com");
        assertEquals("exitoso", resultado.getString("estado"));
        assertEquals("Registro exitoso. Esperando aprobación del administrador.", resultado.getString("mensaje"));
    }
    
    
    public void testVerificarInicioDeSesion() {
        GestorUsuarios gestorUsuarios = GestorUsuarios.getGestorUsuarios();
        gestorUsuarios.addUsuario(new Usuario("alex", "password123", "Alex", "Perez", "alex@example.com", null, new ArrayList<Alquiler>(), false));
        
        JSONObject resultado = VideoClub.getUnVideoClub().verificarInicioDeSesion("alex", "password123");
        assertEquals("exitoso", resultado.getString("estado"));
        assertEquals("Inicio de sesión exitoso", resultado.getString("mensaje"));
        assertFalse(resultado.getBoolean("esAdmin"));
    }
    
    
    
    
    public void testAceptarSolicitud() {
        VideoClub.getUnVideoClub().verificarRegistro("nico", "hulkenberg", "nico", "contraseña-123", "nico@gmail.com");
        
        JSONObject resultado = VideoClub.getUnVideoClub().aceptarSolicitud("pancho", "nico");
        assertEquals("exitoso", resultado.getString("estado"));
        assertEquals("Solicitud aceptada", resultado.getString("mensaje"));
    }
    
    public void testRechazarSolicitud() {
        VideoClub.getUnVideoClub().verificarRegistro("kevin", "magnussen", "kevin", "contraseña-123", "kevin@gmail.com");
        
        JSONObject resultado = VideoClub.getUnVideoClub().rechazarSolicitud("pancho", "kevin");
        assertEquals("exitoso", resultado.getString("estado"));
        assertEquals("Solicitud rechazada", resultado.getString("mensaje"));
    }
    
    

    public void testMostrarValoracionesAntiguas() {
        Valoracion valoracion = new Valoracion(8.5f, "Buena pelicula", usuario1);
        p1.addValoracion(valoracion);

        // Ejecutar el método a probar
        JSONObject resultado = VideoClub.getUnVideoClub().mostrarValoracionesAntiguas("jperez", 101);

        assertNotNull(resultado);
        assertEquals(101, resultado.getInt("idPelicula"));
        assertEquals(8.5f, resultado.getFloat("puntuacion"), 0.001);
        assertEquals("Buena pelicula", resultado.getString("descripcion"));

        resultado = VideoClub.getUnVideoClub().mostrarValoracionesAntiguas("mlopez", 101);
        assertNotNull(resultado);
        assertEquals(101, resultado.getInt("idPelicula"));
        assertEquals(JSONObject.NULL, resultado.get("puntuacion"));
        assertEquals("", resultado.getString("descripcion"));
    }

    public void testPuntuarPelicula() {
        VideoClub.getUnVideoClub().puntuarPelicula("mlopez", 101, "Buena pelicula", 7);

        Valoracion valoracion = p1.getValoracion(usuario2);
        assertEquals(7.0, valoracion.getPuntuacion(), 0.001);
        assertEquals("Buena pelicula", valoracion.getReseña());
    }

    public void testMostrarReseñas() {
        Valoracion v1 = new Valoracion(9.0f, "Buena pelicula", usuario1);
        Valoracion v2 = new Valoracion(6.0f, "meh", usuario2);
        p1.addValoracion(v1);
        p1.addValoracion(v2);

        //Metodo a probar
        JSONObject resultado = VideoClub.getUnVideoClub().mostrarReseñas("mlopez", p1.getID());

        //Extraer el JSON
        JSONArray reseñas = resultado.getJSONArray("reseñas");
        assertNotNull(reseñas);
        assertEquals(2, reseñas.length());

        //Verificar los datos
        JSONObject reseña1 = reseñas.getJSONObject(0);
        assertEquals("jperez", reseña1.getString("username"));
        assertEquals(9.0f, reseña1.getFloat("puntuacion"), 0.001);
        assertEquals("Buena pelicula", reseña1.getString("reseña"));

        //Verificar los datos
        JSONObject reseña2 = reseñas.getJSONObject(1);
        assertEquals("mlopez", reseña2.getString("username"));
        assertEquals(6.0f, reseña2.getFloat("puntuacion"), 0.001);
        assertEquals("meh", reseña2.getString("reseña"));
    }


    public void testActualizarDatos() {
        Usuario usuario = new Usuario("ayrton", "contraseña-123", "Ayrton", "Senna", "ayrton@gmail.com", null, new ArrayList<>(), false);
        GestorUsuarios.getGestorUsuarios().addUsuario(usuario);
    
        // Actualizar los datos del usuario
        JSONObject resultado = VideoClub.getUnVideoClub().actualizarDatos("Ayrton", "Senna", "ayrton", "contraseña-123", "ayrton.senna@gmail.com");
        assertEquals("exitoso", resultado.getString("estado"));
        assertEquals("Datos actualizados correctamente", resultado.getString("mensaje"));
    
        // Verificar que los datos se hayan actualizado correctamente
        Usuario usuarioModificado = GestorUsuarios.getGestorUsuarios().getUsuario("ayrton");
        assertEquals("Ayrton", usuarioModificado.getNombre());
        assertEquals("Senna", usuarioModificado.getApellido());
        assertEquals("ayrton.senna@gmail.com", usuarioModificado.getCorreo());
    }
    
    public void testMostrarSolicitudes() {
        // Añadir solicitudes de usuarios históricos de F1
        Usuario solicitud1 = new Usuario("nelson", "contraseña-123", "Nelson", "Piquet", "nelson.piquet@gmail.com", null, new ArrayList<>(), false);
        Usuario solicitud2 = new Usuario("nigel", "contraseña-123", "Nigel", "Mansell", "nigel.mansell@gmail.com", null, new ArrayList<>(), false);
        GestorUsuarios.getGestorUsuarios().addSolicitud(solicitud1);
        GestorUsuarios.getGestorUsuarios().addSolicitud(solicitud2);
    
        // Mostrar solicitudes
        JSONObject resultado = VideoClub.getUnVideoClub().mostrarSolicitudes();
        JSONArray solicitudes = resultado.getJSONArray("solicitudes");
        assertEquals(2, solicitudes.length());
    
        // Verificar detalles de las solicitudes
        assertEquals("nelson", solicitudes.getJSONObject(0).getString("username"));
        assertEquals("nigel", solicitudes.getJSONObject(1).getString("username"));
    }
    
    public void testEliminarCuentaSeleccionada() {
        // Añadir un usuario histórico de F1 que será eliminado
        Usuario usuario = new Usuario("alain", "contraseña-123", "Alain", "Prost", "alain.prost@gmail.com", null, new ArrayList<>(), false);
        GestorUsuarios.getGestorUsuarios().addUsuario(usuario);
    
        // Eliminar la cuenta del usuario
        VideoClub.getUnVideoClub().eliminarCuentaSeleccionada("alain");
    
        // Verificar que el usuario ya no exista
        Usuario usuarioEliminado = GestorUsuarios.getGestorUsuarios().getUsuario("alain");
        assertNull(usuarioEliminado);
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
