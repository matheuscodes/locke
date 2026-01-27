package database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.io.File;
import java.util.GregorianCalendar;

import core.Biblioteca;
import core.Sistema;
import database.lists.Items;
import windows.Carregando;

class LocacaoTest {

    // Flexible BibliotecaStub to avoid NPEs
    static class BibliotecaStub extends Biblioteca {
        Item[] itens;

        BibliotecaStub(Item... itens) throws IOException {
            this.itens = itens;
        }

        @Override
        public Item[] procuraItem(String id) {
            for (Item i : itens) {
                if (i.getIdentity().equals(id)) return new Item[]{ i };
            }
            return new Item[0]; // return empty array if not found
        }

        @Override
        public int consomeLocacao() {
            return 42;
        }
    }

    static class ItemStub extends Item {
        String id;

        ItemStub(String id) {
            super(0, false);
            this.id = id;
        }

        @Override
        public void toFile(PrintStream arquivo) {}

        @Override
        public void set(String A, String B, String C) {}

        @Override
        public String getIdentity() {
            return id;
        }
    }

    @AfterEach
    void cleanup() {
        Sistema.BiblioteCAASO = null;
    }

    @BeforeEach
    void writeFile() throws Exception {
        File dir = new File("DB");
        dir.mkdirs();
        File dbFile = new File("DB/Usuarios.txt");

        try (FileWriter w = new FileWriter(dbFile)) {
            w.write("0\n");
        }
    }

    @Test
    void constructorWithParametersSetsFieldsAndMarksItemsRetirado() throws IOException {
        ItemStub item = new ItemStub("X1");
        Sistema.Barra = new Carregando();
        Sistema.BiblioteCAASO = new BibliotecaStub(item);

        GregorianCalendar cal = new GregorianCalendar(2025, 1, 3);
        Locacao loc = new Locacao("User", new Item[]{ item }, cal);

        assertEquals(42, loc.ID);
        assertEquals("User", loc.quem);
        assertEquals(1, loc.oque.length);
        assertTrue(loc.oque[0].retirado);
        assertEquals("03/01/2025", loc.paraquando);
        assertNotNull(loc.lista);
    }

    @Test
    void toStringFormatsCorrectly() throws IOException {
        ItemStub item = new ItemStub("A");
        Sistema.Barra = new Carregando();
        Sistema.BiblioteCAASO = new BibliotecaStub(item);

        GregorianCalendar cal = new GregorianCalendar(2024, 9, 10);
        Locacao loc = new Locacao("Maria", new Item[]{ item }, cal);

        assertTrue(loc.toString().startsWith("Maria, 1 items."));
        assertTrue(loc.toString().endsWith("10/09/2024"));
    }

    @Test
    void toFileWritesAllFieldsInOrder() throws IOException {
        ItemStub item = new ItemStub("ID1");
        Sistema.Barra = new Carregando();
        Sistema.BiblioteCAASO = new BibliotecaStub(item);

        GregorianCalendar cal = new GregorianCalendar(2023, 1, 1);
        Locacao loc = new Locacao("Joao", new Item[]{ item }, cal);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        loc.toFile(ps);

        String[] lines = baos.toString().split(System.lineSeparator());
        assertEquals("42", lines[0]);
        assertEquals("Joao", lines[1]);
        assertEquals("1", lines[2]);
        assertEquals("ID1", lines[3]);
        assertEquals("01/01/2023", lines[4]);
    }

    @Test
    void constructorFromFileReadsAllFields() throws Exception {
        ItemStub item = new ItemStub("IT");
        Sistema.Barra = new Carregando();
        Sistema.BiblioteCAASO = new BibliotecaStub(item);

        File temp = File.createTempFile("locacao", ".txt");
        try (PrintWriter pw = new PrintWriter(temp)) {
            pw.println("5");
            pw.println("Ana");
            pw.println("1");
            pw.println("IT");
            pw.println("12/12/2025");
        }

        RandomAccessFile raf = new RandomAccessFile(temp, "r");
        Locacao loc = new Locacao(raf);

        assertEquals(5, loc.ID);
        assertEquals("Ana", loc.quem);
        assertEquals(1, loc.oque.length);
        assertEquals("IT", loc.oque[0].getIdentity());
        assertEquals("12/12/2025", loc.paraquando);
        assertNotNull(loc.lista);

        raf.close();
        temp.delete();
    }

    @Test
    void multipleItemsHandledCorrectly() throws IOException {
        ItemStub item1 = new ItemStub("X");
        ItemStub item2 = new ItemStub("Y");
        Sistema.Barra = new Carregando();
        Sistema.BiblioteCAASO = new BibliotecaStub(item1, item2);

        Item[] itens = new Item[]{ item1, item2 };
        GregorianCalendar cal = new GregorianCalendar(2026, 1, 15);
        Locacao loc = new Locacao("Alice", itens, cal);

        assertEquals(2, loc.oque.length);
        assertTrue(loc.oque[0].retirado);
        assertTrue(loc.oque[1].retirado);
        assertTrue(loc.toString().startsWith("Alice, 2 items."));
        assertTrue(loc.toString().endsWith("15/01/2026"));
    }
}
