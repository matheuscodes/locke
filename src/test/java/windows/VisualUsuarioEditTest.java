package windows;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.DefaultListModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Sistema;
import core.Biblioteca;
import database.Item;
import database.Usuario;
import database.lists.Group;

/**
 * Unit tests for VisualUsuarioEdit with stubs for Biblioteca and Group.
 */
class VisualUsuarioEditTest {

    private VisualUsuarioEdit panelEditable;

    // Concrete Group subclass for testing (implements all abstract methods)
    static class TestGroup extends Group {
        public TestGroup(String name) {
            super(name);
        }

        @Override
        public void adicionaItem(String[] itens) {
            // dummy implementation
        }

        @Override
        public void remove(int i) {
            // dummy implementation
        }

        @Override
        public String toString() {
            return super.toString();
        }

        @Override
        public void setAcervo(Item[] novo) {

        }

        @Override
        public Item[] getAcervo() {
            return null;
        }

        // If Group has more abstract methods, implement them as no-ops here
    }

    // Stub Biblioteca subclass to avoid file loading
    static class StubBiblioteCAASO extends Biblioteca {

        private final DefaultListModel<Group> todos = new DefaultListModel<>();

        public StubBiblioteCAASO() throws java.io.IOException {
            super(); // Biblioteca constructor may throw IOException
        }

        @Override
        public DefaultListModel<Group> getTodosAcervosModel() {
            return todos;
        }

    }

    @AfterEach
    void close() {
        Sistema.Barra =  null;
        Sistema.BiblioteCAASO = null;
    }
    @BeforeEach
    void setUp() throws java.io.IOException {
        // Stub the static Sistema.BiblioteCAASO dependency
        Sistema.Barra = new Carregando();
        Sistema.BiblioteCAASO = new StubBiblioteCAASO();

        // Populate the todos list with sample groups
        Sistema.BiblioteCAASO.getTodosAcervosModel().addElement(new TestGroup("Group1"));
        Sistema.BiblioteCAASO.getTodosAcervosModel().addElement(new TestGroup("Group2"));

        // Create the editable panel
        panelEditable = new VisualUsuarioEdit(true);
    }

    @Test
    void testPreencherDoesNotThrow() {
        String[] dados = {
                "Joao Silva", "Engenharia", "2020", "01/01/2000",
                "Rua A", "Comp A", "10", "Centro",
                "Rua B", "20", "Comp B", "Bairro B",
                "12345678", "87654321", "999888777", "joao@ex.com",
                "Sao Paulo"
        };

        assertDoesNotThrow(() -> {
            panelEditable.preencher(dados, true, false, "Historico de teste", 5);
        });
    }

    @Test
    void testLimparDoesNotThrow() {
        String[] dados = new String[17];
        for (int i = 0; i < dados.length; i++) dados[i] = "Teste" + i;

        panelEditable.preencher(dados, true, true, "Passado", 3);

        assertDoesNotThrow(() -> panelEditable.limpar());
    }

    @Test
    void testCadastrarReturnsUsuario() {
        String[] dados = {
                "Joao Silva", "Engenharia", "2020", "01/01/2000",
                "Rua A", "Comp A", "10", "Centro",
                "Rua B", "20", "Comp B", "Bairro B",
                "12345678", "87654321", "999888777", "joao@ex.com",
                "Sao Paulo"
        };

        panelEditable.preencher(dados, true, false, "Historico", 5);

        // Create Usuario using public API
        Usuario user = panelEditable.cadastrar();

        assertNotNull(user, "Usuario should not be null");

        // Further field verification requires getters in Usuario or reflection
    }
}
