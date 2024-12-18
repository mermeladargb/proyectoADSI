import org.example.GestorListas;
import org.example.Pelicula;
import org.example.Usuario;
import org.junit.Before;
import org.junit.Test;

import java.util.List;


public class GestorListasTest {

    @Before
    public void setup() {
        GestorListas.getGestorListas().reset();
    }

    @Test
    public void testGetGestorListas() {
        GestorListas gs = GestorListas.getGestorListas();
        assert(gs != null);
    }

    @Test
    public void testCrearLista() {
        GestorListas gs = GestorListas.getGestorListas();
        Usuario u1 = new Usuario("u1");
        Usuario u2 = new Usuario("u2");

        // la lista no existe
        gs.crearLista(u1, "lista1");
        assert(gs.getListaUsuario("u1", "lista1") != null);

        // la lista ya existe, no se crea
        gs.crearLista(u1, "lista1");
        assert(gs.getListasUsuario("u1").size() == 1);

        // existe una lista con ese nombre, pero de otro usuario
        gs.reset();
        gs.crearLista(u2, "lista1");
        gs.crearLista(u1, "lista1");
        assert(gs.getListasUsuario("u1").size() == 1);
    }

    @Test
    public void testGetListasUsuario() {
        GestorListas gs = GestorListas.getGestorListas();
        Usuario u1 = new Usuario("u1");

        // el usuario no tiene listas
        assert(gs.getListasUsuario("u1").isEmpty());

        // el usuario tiene listas
        gs.crearLista(u1, "lista1");
        gs.crearLista(u1, "lista2");
        List<String> listas = gs.getListasUsuario("u1");
        assert(listas.size() == 2);

        // no existe un usuario
        listas = gs.getListasUsuario("u2");
        assert(listas.isEmpty());
    }

    @Test
    public void testAñadirPeliculaALista() {
        GestorListas gs = GestorListas.getGestorListas();
        Usuario u1 = new Usuario("u1");
        gs.crearLista(u1, "lista1");

        Pelicula p1 = new Pelicula(1, "p1", null, null, null);
        gs.añadirPeliculaALista("u1", "lista1", p1);
        assert(gs.getListaUsuario("u1", "lista1").size() == 1);

        // no existe el usuario
        gs.reset();
        gs.añadirPeliculaALista("u2", "lista1", p1);
        assert(gs.getListaUsuario("u2", "lista1") == null);
    }
}
