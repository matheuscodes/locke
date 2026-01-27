package database.lists;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.Usuario;

class UsuariosTest {

    private File dbFile;

    @BeforeEach
    void setup() throws Exception {
        File dir = new File("DB");
        dir.mkdirs();

        dbFile = new File("DB/Usuarios.txt");

        try (FileWriter w = new FileWriter(dbFile)) {
            w.write("2\n");

            // User 1
            for (int i = 0; i < 17; i++) w.write("A" + i + "\n");
            w.write("1\n");
            w.write("ACERVO1\n");
            w.write("true\n");
            w.write("false\n");
            w.write("HIST1<SEPARA>\n");
            w.write("3\n");

            // User 2
            for (int i = 0; i < 17; i++) w.write("B" + i + "\n");
            w.write("2\n");
            w.write("ACERVO1\n");
            w.write("ACERVO2\n");
            w.write("false\n");
            w.write("true\n");
            w.write("HIST2<SEPARA>\n");
            w.write("7\n");
        }
    }

    @AfterEach
    void cleanup() {
        if (dbFile != null) dbFile.delete();
    }

    @Test
    void constructorLoadsUsers() throws IOException {
        Usuarios usuarios = new Usuarios();

        assertNotNull(usuarios.lista);
        assertEquals(2, usuarios.lista.length);
        assertEquals(2, usuarios.size());
    }

    @Test
    void novoUsuarioAddsAndSorts() throws IOException {
        Usuarios usuarios = new Usuarios();

        String[] dados = new String[17];
        for (int i = 0; i < 17; i++) dados[i] = "AAA";

        Usuario novo = new Usuario(dados);
        novo.setAtributos(true, false, new String[]{"X"}, "", 0);

        usuarios.novoUsuario(novo);

        assertEquals(3, usuarios.lista.length);
        assertEquals(3, usuarios.size());
        assertEquals(novo, usuarios.get(1));
    }

    @Test
    void removerRemovesCorrectUser() throws IOException {
        Usuarios usuarios = new Usuarios();

        Usuario removed = usuarios.get(0);
        usuarios.remover(0);

        assertEquals(1, usuarios.lista.length);
        assertEquals(1, usuarios.size());
        assertNotEquals(removed, usuarios.get(0));
    }

    @Test
    void toFileWritesAllUsers() throws IOException {
        Usuarios usuarios = new Usuarios();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(out);

        usuarios.toFile(ps);

        String content = out.toString();

        assertTrue(content.startsWith("2"));
        assertTrue(content.contains("A0"));
        assertTrue(content.contains("B0"));
    }
}
