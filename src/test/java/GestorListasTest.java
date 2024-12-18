import org.example.GestorListas;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;


public class GestorListasTest {

    @Test
    public void testGetListasUsuario() {
        GestorListas gs = GestorListas.getGestorListas();
        assertNotNull(gs);
    }
}
