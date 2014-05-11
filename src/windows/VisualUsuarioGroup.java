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
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.Sistema;
import database.Usuario;

public class VisualUsuarioGroup extends JPanel {
	
	private static final Dimension BUTTON_SIZE = new Dimension(400,50);
	private static final Dimension USUARIO_SIZE = new Dimension(550,1000);

	private static final long serialVersionUID = 1L;
	
	private JPanel todos_botoes = null;
	
	private JButton botao_novo = null;
	private JButton botao_exclui = null;
	private JButton botao_locacao = null;
		
	private Usuario quem = null;
	private JScrollPane scrollable_usuario = null;
	private VisualUsuarioEdit usuario = null;
	
	private JPanel todos_usuarios = null;
	private JScrollPane scrollable_lista = null;
	private JList<Usuario> lista = null;
	

	public VisualUsuarioGroup() {
		super();
		initialize();
	}

	private void initialize() {
		this.setLayout(new BorderLayout());
		this.add(getScrollableUsuario(), BorderLayout.CENTER);
		this.add(getTodosUsuarios(), BorderLayout.EAST);
		this.add(getTodosBotoes(), BorderLayout.SOUTH);
	}
	
	private JPanel getTodosBotoes() {
		if(todos_botoes == null) {
			todos_botoes = new JPanel();
			todos_botoes.setBackground(Constantes.BackgroundColor);
			todos_botoes.add(getBotaoExclui());
			todos_botoes.add(getBotaoLocacao());
		}
		return todos_botoes;
	}

	private JScrollPane getScrollableUsuario() {
		if (scrollable_usuario == null) {
			scrollable_usuario = new JScrollPane();
			scrollable_usuario.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			//scrollable_usuario.setPreferredSize(USUARIO_SIZE);
			scrollable_usuario.setViewportView(getUsuario());
		}
		return scrollable_usuario;
	}

	private VisualUsuarioEdit getUsuario() {
		if (usuario == null) {
			usuario = new VisualUsuarioEdit(false);
			usuario.setPreferredSize(USUARIO_SIZE);
		}
		return usuario;
	}

	private JScrollPane getScrollableLista() {
		if (scrollable_lista == null) {
			scrollable_lista = new JScrollPane();
			scrollable_lista.setViewportView(getLista());
		}
		return scrollable_lista;
	}

	private JList<Usuario> getLista() {
		if (lista == null) {
			lista = new JList<Usuario>();
			lista.setModel(Sistema.BiblioteCAASO.getPessoas());
			lista.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if(getLista().getSelectedIndex() != -1) quem = Sistema.BiblioteCAASO.getPessoas().lista[getLista().getSelectedIndex()];
					if(quem != null) getUsuario().preencher(quem.getFicha(),quem.getCAASO(),quem.getAloja(),quem.getPassado(),quem.getMarquinhas());
				}
			});
		}
		return lista;
	}

	private JButton getBotaoNovo() {
		if (botao_novo == null) {
			botao_novo = new JButton();
			botao_novo.setText("Novo Cadastro");
			botao_novo.setPreferredSize(BUTTON_SIZE);
			botao_novo.setBackground(Constantes.BackgroundColor);
			botao_novo.setForeground(Constantes.DefaultColor);
			botao_novo.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
			botao_novo.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					NovoCadastro newone = new NovoCadastro();
					newone.setVisible(true);
				}
			});
		}
		return botao_novo;
	}

	private JButton getBotaoExclui() {
		if (botao_exclui == null) {
			botao_exclui = new JButton();
			botao_exclui.setText("Exclui Cadastro");
			botao_exclui.setPreferredSize(BUTTON_SIZE);
			botao_exclui.setBackground(Constantes.BackgroundColor);
			botao_exclui.setForeground(Constantes.DefaultColor);
			botao_exclui.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
			botao_exclui.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int i = getLista().getSelectedIndex();
					if((i != -1) && (quem == Sistema.BiblioteCAASO.getPessoas().lista[i])) {
						Sistema.BiblioteCAASO.getPessoas().remover(i);
					}
					getUsuario().limpar();
				}
			});
		}
		return botao_exclui;
	}

	private JButton getBotaoLocacao() {
		if (botao_locacao == null) {
			botao_locacao = new JButton();
			botao_locacao.setBackground(Constantes.BackgroundColor);
			botao_locacao.setText("Nova Locação");
			botao_locacao.setIcon(new ImageIcon(getClass().getResource("/icons/up.png")));
			botao_locacao.setForeground(Constantes.DefaultColor);
			botao_locacao.setPreferredSize(BUTTON_SIZE);
			botao_locacao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					int i = getLista().getSelectedIndex();
					if (i >= 0) {
						NovaLocacao newone = new NovaLocacao((Usuario)getLista().getSelectedValue());
						newone.setVisible(true);
					}
				}
			});
		}
		return botao_locacao;
	}
	
	private JPanel getTodosUsuarios() {
		if (todos_usuarios == null) {
			todos_usuarios = new JPanel();
			todos_usuarios.setLayout(new BorderLayout());
			todos_usuarios.setBackground(Constantes.BackgroundColor);
			todos_usuarios.setBorder(Constantes.makeTitledBorder("Lista de Usuários"));
			todos_usuarios.add(getScrollableLista(), BorderLayout.CENTER);
			todos_usuarios.add(getBotaoNovo(),BorderLayout.NORTH);
		}
		return todos_usuarios;
	}
}
