package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import core.Sistema;

class JOGOTest {

    @AfterEach
    void resetModoOrdenacao() {
        Sistema.ModoOrdenacao = 1;
    }

    @Test
    void constructorInitializesAllFields() {
        JOGO jogo = new JOGO(2, true, "C1", "Titulo", "Fab");
        assertEquals(2, jogo.numero_retiradas);
        assertTrue(jogo.retirado);
        assertEquals("C1", jogo.getIdentity());
    }

    @Test
    void setUpdatesFields() {
        JOGO jogo = new JOGO(0, false, "A", "B", "C");
        jogo.set("X", "Y", "Z");
        assertEquals("X", jogo.getIdentity());
    }

    @Test
    void toStringModo0NotRetirado() {
        Sistema.ModoOrdenacao = 0;
        JOGO jogo = new JOGO(1, false, "C", "T", "F");
        assertEquals("Retirado 1 vezes, C - T / F", jogo.toString());
    }

    @Test
    void toStringModo0Retirado() {
        Sistema.ModoOrdenacao = 0;
        JOGO jogo = new JOGO(1, true, "C", "T", "F");
        assertEquals("Retirado 1 vezes, C - T / F(Retirado)", jogo.toString());
    }

    @Test
    void toStringModo1() {
        Sistema.ModoOrdenacao = 1;
        JOGO jogo = new JOGO(3, false, "C", "T", "F");
        assertEquals("C - T / F, Retirado 3 vezes", jogo.toString());
    }

    @Test
    void toStringModo2() {
        Sistema.ModoOrdenacao = 2;
        JOGO jogo = new JOGO(4, false, "C", "T", "F");
        assertEquals("T / F - C, Retirado 4 vezes", jogo.toString());
    }

    @Test
    void toStringModo3() {
        Sistema.ModoOrdenacao = 3;
        JOGO jogo = new JOGO(5, false, "C", "T", "F");
        assertEquals("F / T - C, Retirado 5 vezes", jogo.toString());
    }

    @Test
    void toFileWritesAllFieldsInOrder() {
        JOGO jogo = new JOGO(7, true, "C7", "Titulo7", "Fab7");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        jogo.toFile(ps);

        String[] lines = baos.toString().split(System.lineSeparator());
        assertEquals("7", lines[0]);
        assertEquals("true", lines[1]);
        assertEquals("C7", lines[2]);
        assertEquals("Titulo7", lines[3]);
        assertEquals("Fab7", lines[4]);
    }
}
