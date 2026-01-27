package database.lists;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import database.Item;

class GroupTest {

    static class ItemStub extends Item {
        String value;

        ItemStub(String value) {
            super(0, false);
            this.value = value;
        }

        @Override
        public void toFile(PrintStream arquivo) {
            arquivo.println(value);
        }

        @Override
        public void set(String a, String b, String c) {
            // dummy implementation for test
        }

        @Override
        public String getIdentity() {
            return value;
        }

        @Override
        public String toString() {
            return value;
        }
    }

    static class GroupStub extends Group {
        Item[] acervo;

        GroupStub(String nome, Item[] acervo) {
            super(nome);
            this.acervo = acervo;
        }

        @Override
        public Item[] getAcervo() {
            return acervo;
        }

        @Override
        public void setAcervo(Item[] novo) {
            acervo = novo;
        }

        @Override
        public void remove(int onde) {
            Item[] n = new Item[acervo.length - 1];
            for (int i = 0, j = 0; i < acervo.length; i++) {
                if (i != onde) n[j++] = acervo[i];
            }
            acervo = n;
        }

        @Override
        public void adicionaItem(String[] parametros) {
            Item[] n = new Item[acervo.length + 1];
            System.arraycopy(acervo, 0, n, 0, acervo.length);
            n[acervo.length] = new ItemStub(parametros[0]);
            acervo = n;
        }
    }

    @Test
    void constructorAndToString() {
        Group g = new GroupStub("Grupo", new Item[0]);
        assertEquals("Grupo", g.toString());
        assertEquals("Grupo", g.Nome);
    }

    @Test
    void toFileWritesAllItems() {
        Item[] items = {
                new ItemStub("A"),
                new ItemStub("B")
        };
        Group g = new GroupStub("G", items);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        g.toFile(ps);

        String[] lines = baos.toString().split(System.lineSeparator());
        assertEquals("2", lines[0]);
        assertEquals("A", lines[1]);
        assertEquals("B", lines[2]);
    }

    @Test
    void buscaReturnsMatchingItems() {
        Item[] items = {
                new ItemStub("ABC"),
                new ItemStub("DEF"),
                new ItemStub("ABX")
        };
        Group g = new GroupStub("G", items);

        Item[] result = g.busca("AB");

        assertEquals(2, result.length);
        assertEquals("ABC", result[0].toString());
        assertEquals("ABX", result[1].toString());
    }

    @Test
    void buscaReturnsEmptyWhenNoMatch() {
        Item[] items = {
                new ItemStub("AAA"),
                new ItemStub("BBB")
        };
        Group g = new GroupStub("G", items);

        Item[] result = g.busca("ZZ");

        assertEquals(0, result.length);
    }
}
