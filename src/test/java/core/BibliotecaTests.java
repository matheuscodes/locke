package core;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import database.Item;
import database.Locacao;
import database.Usuario;
import database.lists.CDs;
import database.lists.JOGOs;
import database.lists.LIVROs;
import database.lists.VIDEOs;
import database.lists.Usuarios;
import windows.Carregando;

class BibliotecaTests {

    private Biblioteca biblioteca;

    @AfterEach
    void close() {
        Sistema.Barra =  null;
    }
    @BeforeEach
    void setUp() throws IOException {
        Sistema.Barra = new Carregando();
        biblioteca = new Biblioteca();
        biblioteca.inicializaLOCACOES();
    }

    void writeFile(String path, String content) throws IOException {
        FileWriter fw = new FileWriter(path);
        fw.write(content);
        fw.close();
    }

    @Test
    void testSemanaSettersGetters() {
        biblioteca.setSemanaCD(5);
        biblioteca.setSemanaVIDEO(4);
        biblioteca.setSemanaLIVRO(3);
        biblioteca.setSemanaJOGO(2);
        assertEquals(5, biblioteca.getSemanaCD());
        assertEquals(4, biblioteca.getSemanaVIDEO());
        assertEquals(3, biblioteca.getSemanaLIVRO());
        assertEquals(2, biblioteca.getSemanaJOGO());
    }

    @Test
    void testConsomeLocacao() {
        int id1 = biblioteca.consomeLocacao();
        int id2 = biblioteca.consomeLocacao();
        assertEquals(id1 + 1, id2);
    }

    @Test
    void testProcuraUsuarioAndPessoas() {
        Usuarios users = biblioteca.getPessoas();
        assertNotNull(users);
        Usuario u = biblioteca.procuraUsuario("nonexistent");
        assertNull(u);
    }

    @Test
    void testProcuraItem() {
        Item[] items = biblioteca.procuraItem("nonexistent");
        assertNotNull(items);
        assertEquals(0, items.length);
    }

    @Test
    void testCalculaDevolucao() {
        GregorianCalendar cal = Biblioteca.calculaDevolucao(1);
        assertNotNull(cal);
    }

    @Test
    void testCaixaOperations() {
        float initial = biblioteca.getCaixa();
        biblioteca.creditar(100);
        assertEquals(initial + 100, biblioteca.getCaixa());
        biblioteca.debitar(50);
        assertEquals(initial + 50, biblioteca.getCaixa());
    }

    @Test
    void testMovimentacao() {
        biblioteca.addMovimentacao("Test Transaction");
        String mov = biblioteca.getMovimentacao();
        assertTrue(mov.contains("Test Transaction"));
    }

    @Test
    void testCDsCRUD() throws IOException {
        biblioteca.novoCD("TestCD");
        assertEquals(2, biblioteca.getCDs().length);
        assertTrue(biblioteca.removeCD("TestCD"));
        assertEquals(1, biblioteca.getCDs().length);
    }

    @Test
    void testVIDEOSCRUD() throws IOException {
        biblioteca.novoVIDEO("TestVideo");
        assertEquals(3, biblioteca.getVIDEOs().length);
        assertTrue(biblioteca.removeVIDEO("TestVideo"));
        assertEquals(2, biblioteca.getVIDEOs().length);
    }

    @Test
    void testLIVROsCRUD() throws IOException {
        biblioteca.novoLIVRO("TestLivro");
        assertEquals(4, biblioteca.getLIVROs().length);
        assertTrue(biblioteca.removeLIVRO("TestLivro"));
        assertEquals(3, biblioteca.getLIVROs().length);
    }

    @Test
    void testJOGOsCRUD() throws IOException {
        biblioteca.novoJOGO("TestJogo");
        assertEquals(5, biblioteca.getJOGOs().length);
        assertTrue(biblioteca.removeJOGO("TestJogo"));
        assertEquals(4, biblioteca.getJOGOs().length);
    }

//    @Test
//    void testLocacaoCRUD() {
//        Locacao loc = new Locacao(0); // Assuming constructor exists for test
//        biblioteca.novaLocacao(loc);
//        assertEquals(1, biblioteca.getLocacoes().length);
//        biblioteca.removeLocacao(loc.ID);
//        assertEquals(0, biblioteca.getLocacoes().length);
//    }

    @Test
    void testGetModels() {
        DefaultListModel<CDs> cdsModel = biblioteca.getCDSModel();
        DefaultListModel<VIDEOs> videosModel = biblioteca.getVIDEOSModel();
        DefaultListModel<LIVROs> livrosModel = biblioteca.getLIVROSModel();
        DefaultListModel<JOGOs> jogosModel = biblioteca.getJOGOSModel();
        DefaultListModel<Locacao> locacoesModel = biblioteca.getLOCACOESModel();
        DefaultListModel<?> todosAcervos = biblioteca.getTodosAcervosModel();

        assertNotNull(cdsModel);
        assertNotNull(videosModel);
        assertNotNull(livrosModel);
        assertNotNull(jogosModel);
        assertNotNull(locacoesModel);
        assertNotNull(todosAcervos);
    }

    @Test
    void testFilerize() throws IOException {
        biblioteca.filerize(); // Should run without exceptions
    }
}
