package database.lists;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.Item;
import database.JOGO;

class JOGOsTest {

    private static final String NOME = "teste";
    private File dbFile;

    @BeforeEach
    void setup() throws Exception {
        File dir = new File("DB/JOGOS");
        dir.mkdirs();

        dbFile = new File(dir, NOME + ".txt");

        try (FileWriter w = new FileWriter(dbFile)) {
            w.write("2\n");
            w.write("3\n");          // numero_retiradas
            w.write("false\n");      // retirado
            w.write("COD1\n");
            w.write("Titulo1\n");
            w.write("Orig1\n");
            w.write("1\n");
            w.write("true\n");
            w.write("COD2\n");
            w.write("Titulo2\n");
            w.write("Orig2\n");
        }
    }

    @AfterEach
    void cleanup() {
        if (dbFile != null) dbFile.delete();
    }

    @Test
    void constructorLoadsFromFile() throws IOException {
        JOGOs jogos = new JOGOs(NOME);

        assertNotNull(jogos.Acervo);
        assertEquals(2, jogos.Acervo.length);

        assertEquals("COD1", jogos.Acervo[0].getIdentity());
        assertEquals("COD2", jogos.Acervo[1].getIdentity());

        assertNotNull(jogos.Lista);
        assertEquals(2, jogos.Lista.size());
    }

    @Test
    void getAcervoReturnsInternalArray() throws IOException {
        JOGOs jogos = new JOGOs(NOME);
        JOGO[] acervo = jogos.getAcervo();

        assertSame(jogos.Acervo, acervo);
    }

    @Test
    void setAcervoReplacesArray() throws IOException {
        JOGOs jogos = new JOGOs(NOME);

        JOGO[] novo = new JOGO[] {
                new JOGO(0, false, "X", "Y", "Z")
        };

        jogos.setAcervo(novo);

        assertEquals(1, jogos.Acervo.length);
        assertEquals("X", jogos.Acervo[0].getIdentity());
    }

    @Test
    void removeRemovesCorrectIndex() throws IOException {
        JOGOs jogos = new JOGOs(NOME);

        jogos.remove(0);

        assertEquals(1, jogos.Acervo.length);
        assertEquals("COD2", jogos.Acervo[0].getIdentity());
        assertEquals(1, jogos.Lista.size());
    }

    @Test
    void adicionaItemAppendsNewGame() throws IOException {
        JOGOs jogos = new JOGOs(NOME);

        String[] params = { "COD3", "Titulo3", "Orig3" };
        jogos.adicionaItem(params);

        assertEquals(3, jogos.Acervo.length);

        JOGO added = jogos.Acervo[2];
        assertEquals("COD3", added.getIdentity());
        assertEquals(3, jogos.Lista.size());
    }
}
