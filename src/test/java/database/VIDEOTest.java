package database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import core.Sistema;

class VIDEOTest {

    @AfterEach
    void resetModoOrdenacao() {
        Sistema.ModoOrdenacao = 1;
    }

    @Test
    void constructorInitializesFields() {
        VIDEO v = new VIDEO(2, true, "C1", "Titulo", "Original");
        assertEquals(2, v.numero_retiradas);
        assertTrue(v.retirado);
        assertEquals("C1", v.getIdentity());
    }

    @Test
    void setUpdatesFields() {
        VIDEO v = new VIDEO(0, false, "A", "B", "C");
        v.set("X", "Y", "Z");
        assertEquals("X", v.getIdentity());
    }

    @Test
    void toStringModo0NotRetirado() {
        Sistema.ModoOrdenacao = 0;
        VIDEO v = new VIDEO(1, false, "C", "T", "O");
        assertEquals(
                "Retirado 1 vezes, C - T / O",
                v.toString()
        );
    }

    @Test
    void toStringModo0Retirado() {
        Sistema.ModoOrdenacao = 0;
        VIDEO v = new VIDEO(1, true, "C", "T", "O");
        assertEquals(
                "Retirado 1 vezes, C - T / O(Retirado)",
                v.toString()
        );
    }

    @Test
    void toStringModo1() {
        Sistema.ModoOrdenacao = 1;
        VIDEO v = new VIDEO(3, false, "C", "T", "O");
        assertEquals(
                "C - T / O, Retirado 3 vezes",
                v.toString()
        );
    }

    @Test
    void toStringModo2() {
        Sistema.ModoOrdenacao = 2;
        VIDEO v = new VIDEO(4, false, "C", "T", "O");
        assertEquals(
                "T / O - C, Retirado 4 vezes",
                v.toString()
        );
    }

    @Test
    void toStringModo3() {
        Sistema.ModoOrdenacao = 3;
        VIDEO v = new VIDEO(5, false, "C", "T", "O");
        assertEquals(
                "O / T - C, Retirado 5 vezes",
                v.toString()
        );
    }

    @Test
    void toFileWritesAllFieldsInOrder() {
        VIDEO v = new VIDEO(7, true, "C7", "Titulo7", "Orig7");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        v.toFile(ps);

        String[] lines = baos.toString().split(System.lineSeparator());
        assertEquals("7", lines[0]);
        assertEquals("true", lines[1]);
        assertEquals("C7", lines[2]);
        assertEquals("Titulo7", lines[3]);
        assertEquals("Orig7", lines[4]);
    }
}
