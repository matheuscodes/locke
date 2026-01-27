package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import core.Sistema;

class LIVROTest {

    @AfterEach
    void resetModoOrdenacao() {
        Sistema.ModoOrdenacao = 1;
    }

    @Test
    void constructorInitializesFields() {
        LIVRO livro = new LIVRO(2, true, "Autor", "Titulo", "Assunto");
        assertEquals(2, livro.numero_retiradas);
        assertTrue(livro.retirado);
        assertEquals("Titulo", livro.getIdentity());
    }

    @Test
    void setUpdatesFields() {
        LIVRO livro = new LIVRO(0, false, "A", "B", "C");
        livro.set("X", "Y", "Z");
        assertEquals("Y", livro.getIdentity());
    }

    @Test
    void toStringModo0NotRetirado() {
        Sistema.ModoOrdenacao = 0;
        LIVRO livro = new LIVRO(1, false, "Autor", "Titulo", "Assunto");
        assertEquals(
                "Retirado 1 vezes, Autor - Titulo / Assunto",
                livro.toString()
        );
    }

    @Test
    void toStringModo0Retirado() {
        Sistema.ModoOrdenacao = 0;
        LIVRO livro = new LIVRO(1, true, "Autor", "Titulo", "Assunto");
        assertEquals(
                "Retirado 1 vezes, Autor - Titulo / Assunto(Retirado)",
                livro.toString()
        );
    }

    @Test
    void toStringModo1() {
        Sistema.ModoOrdenacao = 1;
        LIVRO livro = new LIVRO(3, false, "Autor", "Titulo", "Assunto");
        assertEquals(
                "Autor - Titulo / Assunto, Retirado 3 vezes",
                livro.toString()
        );
    }

    @Test
    void toStringModo2() {
        Sistema.ModoOrdenacao = 2;
        LIVRO livro = new LIVRO(4, false, "Autor", "Titulo", "Assunto");
        assertEquals(
                "Titulo / Assunto - Autor, Retirado 4 vezes",
                livro.toString()
        );
    }

    @Test
    void toStringModo3() {
        Sistema.ModoOrdenacao = 3;
        LIVRO livro = new LIVRO(5, false, "Autor", "Titulo", "Assunto");
        assertEquals(
                "Assunto / Titulo - Autor, Retirado 5 vezes",
                livro.toString()
        );
    }

    @Test
    void toFileWritesAllFieldsInOrder() {
        LIVRO livro = new LIVRO(7, true, "Autor7", "Titulo7", "Assunto7");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        livro.toFile(ps);

        String[] lines = baos.toString().split(System.lineSeparator());
        assertEquals("7", lines[0]);
        assertEquals("true", lines[1]);
        assertEquals("Autor7", lines[2]);
        assertEquals("Titulo7", lines[3]);
        assertEquals("Assunto7", lines[4]);
    }
}
