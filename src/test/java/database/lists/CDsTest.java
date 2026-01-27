package database.lists;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import database.CD;
import database.Item;

import java.io.File;
import java.io.PrintWriter;

class CDsTest {

    private void createDbFile(String name, String content) throws Exception {
        File dir = new File("DB/CDS");
        assertTrue(dir.mkdirs() || dir.exists());

        File f = new File(dir, name + ".txt");
        try (PrintWriter pw = new PrintWriter(f)) {
            pw.print(content);
        }
    }

    @Test
    void constructorLoadsItemsFromFile() throws Exception {
        createDbFile(
                "test",
                "1\n" +
                        "2\n" +
                        "false\n" +
                        "C1\n" +
                        "Album\n" +
                        "Artista\n"
        );

        CDs cds = new CDs("test");

        assertEquals(1, cds.getAcervo().length);
        assertEquals("C1", cds.getAcervo()[0].getIdentity());
        assertEquals(2, cds.getAcervo()[0].numero_retiradas);
        assertFalse(cds.getAcervo()[0].retirado);
        assertNotNull(cds.Lista);
    }

    @Test
    void adicionaItemIncreasesSize() throws Exception {
        createDbFile("add", "0\n");

        CDs cds = new CDs("add");
        cds.adicionaItem(new String[]{ "C2", "Alb", "Art" });

        assertEquals(1, cds.getAcervo().length);
        assertEquals("C2", cds.getAcervo()[0].getIdentity());
    }

    @Test
    void removeDeletesCorrectIndex() throws Exception {
        createDbFile(
                "rem",
                "2\n" +
                        "0\nfalse\nA\nA1\nX\n" +
                        "0\nfalse\nB\nB1\nY\n"
        );

        CDs cds = new CDs("rem");
        cds.remove(0);

        assertEquals(1, cds.getAcervo().length);
        assertEquals("B", cds.getAcervo()[0].getIdentity());
    }

    @Test
    void setAcervoReplacesArray() throws Exception {
        createDbFile("set", "0\n");

        CDs cds = new CDs("set");
        CD cd = new CD(0, false, "X", "T", "A");
        cds.setAcervo(new CD[]{ cd });

        assertEquals(1, cds.getAcervo().length);
        assertEquals("X", cds.getAcervo()[0].getIdentity());
    }
}
