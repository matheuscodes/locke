package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import core.Sistema;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class CDTest {

    @AfterEach
    void resetModoOrdenacao() {
        Sistema.ModoOrdenacao = 1;
    }

    @Test
    void constructorAndGetIdentity() {
        CD cd = new CD(3, false, "C001", "Album", "Artist");
        assertEquals("C001", cd.getIdentity());
    }

    @Test
    void toStringModoOrdenacao0NotRetirado() {
        Sistema.ModoOrdenacao = 0;
        CD cd = new CD(2, false, "C1", "A1", "B1");
        assertEquals(
                "Retirado 2 vezes, C1 - A1 / B1",
                cd.toString()
        );
    }

    @Test
    void toStringModoOrdenacao0Retirado() {
        Sistema.ModoOrdenacao = 0;
        CD cd = new CD(1, true, "C1", "A1", "B1");
        assertEquals(
                "Retirado 1 vezes, C1 - A1 / B1(Retirado)",
                cd.toString()
        );
    }

    @Test
    void toStringModoOrdenacao1() {
        Sistema.ModoOrdenacao = 1;
        CD cd = new CD(5, false, "C2", "Album2", "Artist2");
        assertEquals(
                "C2 - Album2 / Artist2, Retirado 5 vezes",
                cd.toString()
        );
    }

    @Test
    void toStringModoOrdenacao2() {
        Sistema.ModoOrdenacao = 2;
        CD cd = new CD(0, false, "C3", "Album3", "Artist3");
        assertEquals(
                "Album3 / Artist3 - C3, Retirado 0 vezes",
                cd.toString()
        );
    }

    @Test
    void toStringModoOrdenacao3() {
        Sistema.ModoOrdenacao = 3;
        CD cd = new CD(7, false, "C4", "Album4", "Artist4");
        assertEquals(
                "Artist4 / Album4 - C4, Retirado 7 vezes",
                cd.toString()
        );
    }

    @Test
    void toFileWritesAllFieldsInOrder() {
        CD cd = new CD(4, true, "CX", "ALB", "ART");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        cd.toFile(ps);

        String[] lines = baos.toString().split(System.lineSeparator());
        assertEquals("4", lines[0]);
        assertEquals("true", lines[1]);
        assertEquals("CX", lines[2]);
        assertEquals("ALB", lines[3]);
        assertEquals("ART", lines[4]);
    }
}
