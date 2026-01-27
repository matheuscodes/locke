package windows;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Component;
import java.awt.Container;
import javax.swing.JPanel;
import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GeralTest {

    private Geral frame;

    @BeforeEach
    void setUp() {
        frame = new Geral();
        frame.setVisible(false); // prevent actual UI popup during tests
    }

    @Test
    void testFrameProperties() {
        assertNotNull(frame);
        assertEquals("Locke - Controle de Locadoras", frame.getTitle());
        assertEquals(JFrame.EXIT_ON_CLOSE, frame.getDefaultCloseOperation());
    }

    @Test
    void testContentPaneStructure() {
        Container content = frame.getContentPane();
        assertNotNull(content);
        assertEquals(2, content.getComponentCount()); // menu + ativo
        Component menu = content.getComponent(0);
        Component ativo = content.getComponent(1);
        assertTrue(menu.isVisible());
        assertTrue(ativo.isVisible());
        assertTrue(menu instanceof JPanel);
        assertTrue(ativo instanceof JPanel);
    }

    @Test
    void testAtivoPanelStartsEmpty() {
        JPanel ativo = (JPanel) frame.getContentPane().getComponent(1);
        assertEquals(0, ativo.getComponentCount()); // initially no active panel
    }

    @Test
    void testContentPaneBackground() {
        assertEquals(Constantes.BackgroundColor, ((JPanel) frame.getContentPane()).getBackground());
    }
}
