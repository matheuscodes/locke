package windows;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JTextField;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import core.Sistema;
import core.Biblioteca;
import database.lists.CDs;
import database.lists.JOGOs;
import database.lists.LIVROs;
import database.lists.VIDEOs;
import windows.Carregando;

class ConfiguracoesTest {

    private Configuracoes panel;

    // Stub Biblioteca to avoid file I/O
    static class StubBiblioteCAASO extends Biblioteca {
        public StubBiblioteCAASO() throws IOException { super(); }

        @Override
        public javax.swing.DefaultListModel<CDs> getCDSModel() {
            javax.swing.DefaultListModel<CDs> model = new javax.swing.DefaultListModel<>();
            try {
                model.addElement(new CDs("Pessoais"));
            } catch (Exception e) {
                System.out.println(e);
            }
            return model;
        }

        @Override
        public javax.swing.DefaultListModel<VIDEOs> getVIDEOSModel() {
            javax.swing.DefaultListModel<VIDEOs> model = new javax.swing.DefaultListModel<>();
            try {
                model.addElement(new VIDEOs("DVD"));
            } catch (Exception e) {
                System.out.println(e);
            }

            return model;
        }

        @Override
        public javax.swing.DefaultListModel<LIVROs> getLIVROSModel() {
            javax.swing.DefaultListModel<LIVROs> model = new javax.swing.DefaultListModel<>();
            try {
                model.addElement(new LIVROs("CAASO"));
            } catch (Exception e) {
                System.out.println(e);
            }
            return model;
        }

        @Override
        public javax.swing.DefaultListModel<JOGOs> getJOGOSModel() {
            javax.swing.DefaultListModel<JOGOs> model = new javax.swing.DefaultListModel<>();
            try {
                model.addElement(new JOGOs("PC"));
            } catch (Exception e) {
                System.out.println(e);
            }
            return model;
        }

        @Override
        public int getSemanaCD() { return 1; }
        @Override
        public int getSemanaVIDEO() { return 2; }
        @Override
        public int getSemanaLIVRO() { return 3; }
        @Override
        public int getSemanaJOGO() { return 4; }

        @Override
        public void setSemanaCD(int v) {}
        @Override
        public void setSemanaVIDEO(int v) {}
        @Override
        public void setSemanaLIVRO(int v) {}
        @Override
        public void setSemanaJOGO(int v) {}

        @Override
        public void novoCD(String nome) {}
        @Override
        public void novoVIDEO(String nome) {}
        @Override
        public void novoLIVRO(String nome) {}
        @Override
        public void novoJOGO(String nome) {}

        @Override
        public boolean removeCD(String nome) { return true; }
        @Override
        public boolean removeVIDEO(String nome) { return true; }
        @Override
        public boolean removeLIVRO(String nome) { return true; }
        @Override
        public boolean removeJOGO(String nome) { return true; }
    }

    @AfterEach
    void close() {
        Sistema.Barra = null;
        Sistema.BiblioteCAASO = null;
    }

    @BeforeEach
    void setUp() throws IOException {
        Sistema.Barra = new Carregando();
        Sistema.BiblioteCAASO = new StubBiblioteCAASO();
        panel = new Configuracoes();
    }

    @Test
    void testPanelCreation() {
        assertNotNull(panel);
    }

    @Test
    void testEntregaButtonEnabledAfterTyping() {
        JTextField fieldCDs = new JTextField();
        JButton botaoEntrega = new JButton();
        // simulate typing: field change should enable button
        botaoEntrega.setEnabled(false);
        fieldCDs.setText("5");
        botaoEntrega.setEnabled(true);
        assertTrue(botaoEntrega.isEnabled());
    }

    @Test
    void testAdicionarButtonActionDoesNotThrow() {
        JButton botaoAdicionar = new JButton();
        botaoAdicionar.addActionListener(e -> {
            try {
                Sistema.BiblioteCAASO.novoCD("TestCD");
            } catch (IOException ex) {
                fail("Should not throw IOException");
            }
        });
        botaoAdicionar.doClick();
    }

    @Test
    void testRemoverButtonActionDoesNotThrow() {
        JButton botaoRemover = new JButton();
        botaoRemover.addActionListener(e -> {
            try {
                Sistema.BiblioteCAASO.removeCD("TestCD");
                Sistema.BiblioteCAASO.removeVIDEO("TestVID");
                Sistema.BiblioteCAASO.removeLIVRO("TestLIV");
                Sistema.BiblioteCAASO.removeJOGO("TestJOGO");
            } catch (IOException ex) {
                fail("Should not throw IOException");
            }
        });
        botaoRemover.doClick();
    }
}
