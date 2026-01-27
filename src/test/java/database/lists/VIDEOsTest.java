package database.lists;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.VIDEO;

class VIDEOsTest {

    private File dir;
    private File file;

    @BeforeEach
    void setup() throws Exception {
        dir = new File("DB/VIDEOS");
        dir.mkdirs();

        file = new File(dir, "test.txt");

        try (FileWriter w = new FileWriter(file)) {
            w.write("2\n");

            // VIDEO 1
            w.write("5\n");          // numero_retiradas
            w.write("false\n");      // retirado
            w.write("V001\n");       // codigo
            w.write("Titulo1\n");    // titulo
            w.write("Orig1\n");      // titulo_original

            // VIDEO 2
            w.write("1\n");
            w.write("true\n");
            w.write("V002\n");
            w.write("Titulo2\n");
            w.write("Orig2\n");
        }
    }

    @AfterEach
    void cleanup() {
        if (file != null) file.delete();
        if (dir != null) dir.delete();
    }

    @Test
    void constructorLoadsVideos() throws IOException {
        VIDEOs videos = new VIDEOs("test");

        assertNotNull(videos.Acervo);
        assertEquals(2, videos.Acervo.length);
        assertEquals("V001", videos.Acervo[0].getIdentity());
    }

    @Test
    void getAndSetAcervo() throws IOException {
        VIDEOs videos = new VIDEOs("test");

        VIDEO[] novo = new VIDEO[]{
                new VIDEO(0, false, "X", "A", "B")
        };

        videos.setAcervo(novo);

        assertEquals(1, videos.getAcervo().length);
        assertEquals("X", videos.getAcervo()[0].getIdentity());
    }

    @Test
    void adicionaItemAddsVideo() throws IOException {
        VIDEOs videos = new VIDEOs("test");

        int before = videos.getAcervo().length;

        videos.adicionaItem(new String[]{"V003", "Titulo3", "Orig3"});

        assertEquals(before + 1, videos.getAcervo().length);
        assertEquals("V003", videos.getAcervo()[before].getIdentity());
    }

    @Test
    void removeRemovesCorrectIndex() throws IOException {
        VIDEOs videos = new VIDEOs("test");

        VIDEO removed = videos.getAcervo()[0];

        videos.remove(0);

        assertEquals(1, videos.getAcervo().length);
        assertNotEquals(removed, videos.getAcervo()[0]);
    }

    @Test
    void listaIsKeptInSync() throws IOException {
        VIDEOs videos = new VIDEOs("test");

        int sizeBefore = videos.Lista.size();

        videos.adicionaItem(new String[]{"V004", "T4", "O4"});

        assertEquals(sizeBefore + 1, videos.Lista.size());

        videos.remove(0);

        assertEquals(sizeBefore, videos.Lista.size());
    }
}
