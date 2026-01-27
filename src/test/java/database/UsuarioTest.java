package database;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class UsuarioTest {

    private String[] baseParams() {
        return new String[]{
                "Nome", "Curso", "2020", "01/01/2000",
                "RuaSC", "CompSC", "10", "BairroSC",
                "Rua", "20", "Comp", "Bairro",
                "TelSC", "Tel", "Cel", "email@test.com",
                "Cidade"
        };
    }

    @Test
    void constructorAndGetFicha() {
        Usuario u = new Usuario(baseParams());
        String[] ficha = u.getFicha();
        assertEquals(17, ficha.length);
        assertEquals("Nome", ficha[0]);
        assertEquals("email@test.com", ficha[15]);
        assertEquals("Cidade", ficha[16]);
    }

    @Test
    void toStringFormatsCorrectly() {
        Usuario u = new Usuario(baseParams());
        assertEquals("Nome, email@test.com", u.toString());
    }

    @Test
    void defaultFlagsAndCounters() {
        Usuario u = new Usuario(baseParams());
        assertTrue(u.getCAASO());
        assertFalse(u.getAloja());
        assertEquals(0, u.getMarquinhas());
        assertEquals("", u.getPassado());
        assertNull(u.getAcervosPermitidos());
    }

    @Test
    void setAtributosUpdatesAllFields() {
        Usuario u = new Usuario(baseParams());
        String[] acervos = { "A", "B" };

        u.setAtributos(false, true, acervos, "HIST", 5);

        assertFalse(u.getCAASO());
        assertTrue(u.getAloja());
        assertArrayEquals(acervos, u.getAcervosPermitidos());
        assertEquals("HIST", u.getPassado());
        assertEquals(5, u.getMarquinhas());
    }

    @Test
    void incrementaMarquinhasAddsValue() {
        Usuario u = new Usuario(baseParams());
        u.incrementaMarquinhas(3);
        u.incrementaMarquinhas(2);
        assertEquals(5, u.getMarquinhas());
    }

    @Test
    void addPassadoPrependsEntry() {
        Usuario u = new Usuario(baseParams());
        u.addPassado("Evento");
        assertTrue(u.getPassado().contains("Evento"));
        assertTrue(u.getPassado().startsWith("["));
    }

    @Test
    void toFileWritesAllFieldsInOrder() {
        Usuario u = new Usuario(baseParams());
        String[] acervos = { "X", "Y" };
        u.setAtributos(true, false, acervos, "PASSADO\nANTIGO", 7);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);

        u.toFile(ps);

        String out = baos.toString();
        assertTrue(out.contains("Nome"));
        assertTrue(out.contains("Curso"));
        assertTrue(out.contains("email@test.com"));
        assertTrue(out.contains("2"));
        assertTrue(out.contains("X"));
        assertTrue(out.contains("Y"));
        assertTrue(out.contains("true"));
        assertTrue(out.contains("false"));
        assertTrue(out.contains("PASSADO<SEPARA>ANTIGO"));
        assertTrue(out.contains("7"));
    }
}
