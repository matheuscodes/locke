/**
 *  Copyright (C) 2005 Matheus Borges Teixeira
 *  
 *  This file is part of Locke, a tool for managing library rentals.
 *
 *  Locke is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  Locke is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with Locke.  If not, see <http://www.gnu.org/licenses/>.
 */
package windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import core.Sistema;
import database.Usuario;

public class NovoCadastro extends JDialog {
	
	private static final Dimension USUARIO_SIZE = new Dimension(550,1000);
	private static final Dimension WINDOW_SIZE = new Dimension(580,800);
	private static final Dimension BUTTON_SIZE = new Dimension(200,50);

	private static final long serialVersionUID = 1L;
	private JPanel conteudo = null;
	private JScrollPane scrollable_usuario = null;
	private JButton botao_salva = null;
	private JButton botao_limpa = null;
	
	private JPanel botoes = null;
	private VisualUsuarioEdit usuario = null;

	public NovoCadastro() {
		super();
		inicializar();
	}

	private void inicializar() {
		this.setContentPane(getConteudo());
		this.setTitle("Novo Cadastro");
		this.setSize(WINDOW_SIZE);
		this.setAlwaysOnTop(true);
		this.setVisible(true);
	}
	
	private JPanel getConteudo() {
		if (conteudo == null) {
			conteudo = new JPanel();
			conteudo.setLayout(new BorderLayout());
			conteudo.add(getBotoes(), BorderLayout.SOUTH);
			conteudo.add(getScrollableUsuario(), BorderLayout.CENTER);
		}
		return conteudo;
	}

	private JScrollPane getScrollableUsuario() {
		if (scrollable_usuario == null) {
			scrollable_usuario = new JScrollPane();
			scrollable_usuario.setViewportView(getUsuario());
		}
		return scrollable_usuario;
	}

	private JButton getBotaoSalva() {
		if (botao_salva == null) {
			botao_salva = new JButton();
			botao_salva.setText("Salva");
			botao_limpa.setPreferredSize(BUTTON_SIZE);
			botao_salva.setIcon(new ImageIcon(getClass().getResource("/icons/accept.png")));
			botao_salva.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Usuario maisum = getUsuario().cadastrar();
					if(maisum.getFicha()[0].compareToIgnoreCase("") != 0) Sistema.BiblioteCAASO.getPessoas().novoUsuario(maisum);
					dispose();
				}
			});
		}
		return botao_salva;
	}

	private JButton getBotaoLimpa() {
		if (botao_limpa == null) {
			botao_limpa = new JButton();
			botao_limpa.setText("Limpa");
			botao_limpa.setPreferredSize(BUTTON_SIZE);
			botao_limpa.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
			botao_limpa.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getUsuario().limpar();
				}
			});
		}
		return botao_limpa;
	}
	
	private JPanel getBotoes() {
		if (botoes == null) {
			botoes = new JPanel();
			botoes.setLayout(new BorderLayout());
			botoes.add(getBotaoLimpa(), BorderLayout.WEST);
			botoes.add(getBotaoSalva(), BorderLayout.CENTER);
		}
		return botoes;
	}

	private VisualUsuarioEdit getUsuario() {
		if (usuario == null) {
			usuario = new VisualUsuarioEdit(true);
			usuario.setPreferredSize(USUARIO_SIZE);
		}
		return usuario;
	}
}
