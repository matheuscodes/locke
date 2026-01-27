package database.lists;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.Item;
import database.LIVRO;

class LIVROsTest {

    private static final String NOME = "teste";
    private File dbFile;

    @BeforeEach
    void setup() throws Exception {
        File dir = new File("DB/LIVROS");
        dir.mkdirs();

        dbFile = new File(dir, NOME + ".txt");

        try (FileWriter w = new FileWriter(dbFile)) {
            w.write("2\n");
            w.write("5\n");
            w.write("false\n");
            w.write("ISBN1\n");
            w.write("Titulo1\n");
            w.write("Autor1\n");
            w.write("2\n");
            w.write("true\n");
            w.write("ISBN2\n");
            w.write("Titulo2\n");
            w.write("Autor2\n");
        }
    }

    @AfterEach
    void cleanup() {
        if (dbFile != null) dbFile.delete();
    }

    @Test
    void constructorLoadsFromFile() throws IOException {
        LIVROs livros = new LIVROs(NOME);

        assertNotNull(livros.Acervo);
        assertEquals(2, livros.Acervo.length);
        assertNotNull(livros.Lista);
        assertEquals(2, livros.Lista.size());
    }

    @Test
    void getAcervoReturnsInternalArray() throws IOException {
        LIVROs livros = new LIVROs(NOME);
        assertSame(livros.Acervo, livros.getAcervo());
    }

    @Test
    void setAcervoReplacesArray() throws IOException {
        LIVROs livros = new LIVROs(NOME);

        LIVRO[] novo = {
                new LIVRO(0, false, "X", "Y", "Z")
        };

        livros.setAcervo(novo);

        assertEquals(1, livros.Acervo.length);
        assertEquals("Y", livros.Acervo[0].getIdentity());
    }

    @Test
    void removeRemovesCorrectIndex() throws IOException {
        LIVROs livros = new LIVROs(NOME);

        livros.remove(0);

        assertEquals(1, livros.Acervo.length);
        assertEquals(1, livros.Lista.size());
    }

    @Test
    void adicionaItemAppendsNewBook() throws IOException {
        LIVROs livros = new LIVROs(NOME);

        String[] params = { "ISBN3", "Titulo3", "Autor3" };
        livros.adicionaItem(params);

        assertEquals(3, livros.Acervo.length);
        assertEquals(3, livros.Lista.size());
    }
}
