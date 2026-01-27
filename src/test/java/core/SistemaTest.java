package core;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SistemaTest {

    @Test
    void defaultStaticValues() {
        assertEquals(1, Sistema.ModoOrdenacao);
        assertNull(Sistema.BiblioteCAASO);
        assertNull(Sistema.Barra);
    }

    @Test
    void canModifyModoOrdenacao() {
        int original = Sistema.ModoOrdenacao;
        Sistema.ModoOrdenacao = 42;
        assertEquals(42, Sistema.ModoOrdenacao);
        Sistema.ModoOrdenacao = original;
    }

    @Test
    void canAssignAndClearBibliotecaReference() {
        Sistema.BiblioteCAASO = null;
        assertNull(Sistema.BiblioteCAASO);
    }

    @Test
    void canAssignAndClearBarraReference() {
        Sistema.Barra = null;
        assertNull(Sistema.Barra);
    }
}