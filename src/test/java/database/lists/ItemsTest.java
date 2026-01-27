package database.lists;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;

import core.Sistema;
import database.Item;

class ItemsTest {

    static class ItemStub extends Item {
        String a, b, c;

        ItemStub() {
            super(0, false);
        }

        @Override
        public void toFile(java.io.PrintStream arquivo) {
            // dummy implementation for test
        }

        @Override
        public void set(String a, String b, String c) {
            this.a = a;
            this.b = b;
            this.c = c;
        }

        @Override
        public String getIdentity() {
            return a;
        }

        @Override
        public String toString() {
            return a;
        }
    }

    private File tempFile;

    @AfterEach
    void cleanup() {
        if (tempFile != null && tempFile.exists()) {
            tempFile.delete();
        }
    }

    @Test
    void fromFileLoadsItemsCorrectly() throws Exception {
        tempFile = File.createTempFile("items", ".txt");

        try (FileWriter fw = new FileWriter(tempFile)) {
            fw.write("2\n");
            fw.write("5\n");
            fw.write("true\n");
            fw.write("ID1\n");
            fw.write("B\n");
            fw.write("C\n");
            fw.write("2\n");
            fw.write("false\n");
            fw.write("ID2\n");
            fw.write("D\n");
            fw.write("E\n");
        }

        ItemStub[] acervo = { new ItemStub(), new ItemStub() };
        Items items = new Items();

        items.fromFile(tempFile.getAbsolutePath(), acervo);

        assertEquals(2, items.size());
        assertEquals("ID1", items.get(0).getIdentity());
        assertEquals(5, items.get(0).numero_retiradas);
        assertTrue(items.get(0).retirado);

        assertEquals("ID2", items.get(1).getIdentity());
        assertEquals(2, items.get(1).numero_retiradas);
        assertFalse(items.get(1).retirado);
    }

    @Test
    void substituirOrdenandoByRentalCount() {
        Sistema.ModoOrdenacao = 0;

        ItemStub a = new ItemStub();
        a.numero_retiradas = 1;
        a.set("A", "", "");

        ItemStub b = new ItemStub();
        b.numero_retiradas = 5;
        b.set("B", "", "");

        ItemStub c = new ItemStub();
        c.numero_retiradas = 3;
        c.set("C", "", "");

        Item[] acervo = { a, b, c };
        Items items = new Items();

        items.substituirOrdenando(acervo);

        assertEquals("B", items.get(0).getIdentity());
        assertEquals("C", items.get(1).getIdentity());
        assertEquals("A", items.get(2).getIdentity());
    }

    @Test
    void substituirOrdenandoByString() {
        Sistema.ModoOrdenacao = 1;

        ItemStub a = new ItemStub();
        a.set("Charlie", "", "");

        ItemStub b = new ItemStub();
        b.set("alpha", "", "");

        ItemStub c = new ItemStub();
        c.set("Bravo", "", "");

        Item[] acervo = { a, b, c };
        Items items = new Items();

        items.substituirOrdenando(acervo);

        assertEquals("alpha", items.get(0).getIdentity());
        assertEquals("Bravo", items.get(1).getIdentity());
        assertEquals("Charlie", items.get(2).getIdentity());
    }
}
