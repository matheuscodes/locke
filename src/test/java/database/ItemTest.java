package database;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class ItemTest {

    private static class TestItem extends Item {

        String a;
        String b;
        String c;

        TestItem(int historico, boolean ausencia) {
            super(historico, ausencia);
        }

        @Override
        public void toFile(PrintStream arquivo) {
            arquivo.println(numero_retiradas);
            arquivo.println(retirado);
            arquivo.println(a);
            arquivo.println(b);
            arquivo.println(c);
        }

        @Override
        public void set(String A, String B, String C) {
            this.a = A;
            this.b = B;
            this.c = C;
        }

        @Override
        public String getIdentity() {
            return a;
        }
    }

    @Test
    void constructorInitializesFields() {
        TestItem item = new TestItem(5, true);
        assertEquals(5, item.numero_retiradas);
        assertTrue(item.retirado);
    }

    @Test
    void setStoresAllValues() {
        TestItem item = new TestItem(0, false);
        item.set("A", "B", "C");
        assertEquals("A", item.a);
        assertEquals("B", item.b);
        assertEquals("C", item.c);
    }

    @Test
    void getIdentityReturnsFirstField() {
        TestItem item = new TestItem(0, false);
        item.set("ID", "X", "Y");
        assertEquals("ID", item.getIdentity());
    }

    @Test
    void toFileWritesAllFieldsInOrder() {
        TestItem item = new TestItem(3, true);
        item.set("A1", "B1", "C1");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        item.toFile(ps);

        String[] lines = baos.toString().split(System.lineSeparator());
        assertEquals("3", lines[0]);
        assertEquals("true", lines[1]);
        assertEquals("A1", lines[2]);
        assertEquals("B1", lines[3]);
        assertEquals("C1", lines[4]);
    }
}
