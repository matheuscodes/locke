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
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;

import core.Sistema;
import database.Item;
import database.lists.Group;
import database.lists.Items;

public class Acervos extends JPanel {
	private static final Dimension LISTA_SIZE = new Dimension(150, 150);
	private static final Dimension BUTTON_SIZE = new Dimension(150, 50);

	private static final long serialVersionUID = 1L;

	private JScrollPane lista_acervos = null;
	private JPanel insercao = null;
	private Ordenacoes ordens = null;

	private Formulario formulario = null;

	private String what = null;

	private String first = null;
	private String second = null;
	private String third = null;

	private JButton adiciona = null;
	private JPanel remocao = null;
	private JScrollPane lista_itens = null;
	private JButton remover = null;
	private JList<Item> itens = null;
	private JList<Group> acervos = null;
	private DefaultListModel<Group> acervos_model = null;

	private Group acervo_ativo = null;

	@SuppressWarnings("unchecked")
	/* Required to fix a bug on updating lists on add/deletion */
	public Acervos(DefaultListModel<?> lista, String name, String A, String B, String C) {
		super();

		what = name;
		first = A;
		second = B;
		third = C;

		acervos_model = ((DefaultListModel<Group>) lista);

		inicializar();
	}

	private void inicializar() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(Constantes.GAP);
		borderLayout.setVgap(Constantes.GAP);
		this.setLayout(borderLayout);
		this.setBorder(Constantes.makeTitledBorder(what + "s"));
		this.setBackground(Constantes.BackgroundColor);
		this.add(getOrdens(), BorderLayout.NORTH);
		this.add(getInsercao(), BorderLayout.SOUTH);
		this.add(getRemocao(), BorderLayout.CENTER);
		this.add(getListaAcervos(), BorderLayout.WEST);
	}

	private Ordenacoes getOrdens() {
		if (ordens == null) {
			ordens = new Ordenacoes(first, second, third);
		}
		return ordens;
	}

	private JScrollPane getListaAcervos() {
		if (lista_acervos == null) {
			lista_acervos = new JScrollPane();
			lista_acervos.setPreferredSize(LISTA_SIZE);
			lista_acervos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			lista_acervos.setViewportView(getAcervos());
			lista_acervos.setBackground(Constantes.BackgroundColor);
		}
		return lista_acervos;
	}

	private Formulario getFormulario() {
		if (formulario == null) {
			formulario = new Formulario(("Novo " + what), first, second, third);
		}
		return formulario;
	}

	private JPanel getInsercao() {
		if (insercao == null) {
			insercao = new JPanel();
			insercao.setLayout(new BorderLayout());
			insercao.setBackground(Constantes.BackgroundColor);
			insercao.add(getFormulario(), BorderLayout.CENTER);
			insercao.add(getBotaoAdiciona(), BorderLayout.EAST);
		}
		return insercao;
	}

	private JButton getBotaoAdiciona() {
		if (adiciona == null) {
			adiciona = new JButton();
			adiciona.setText("Adiciona");
			adiciona.setForeground(Constantes.DefaultColor);
			adiciona.setBackground(Constantes.BackgroundColor);
			adiciona.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
			adiciona.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					String[] params = getFormulario().getConteudo();
					if (getAcervoOn() != null) {
						getAcervoOn().adicionaItem(params);
						try {
							core.Sistema.BiblioteCAASO.filerize();
						} catch (IOException e) {
							System.err.println("[ERRO] Problema salvando Acervo no Disco.");
							e.printStackTrace();
						}
					}
					getFormulario().limpa();
				}
			});
		}
		return adiciona;
	}

	private JPanel getRemocao() {
		if (remocao == null) {
			remocao = new JPanel();
			remocao.setLayout(new BorderLayout());
			remocao.setBackground(Constantes.BackgroundColor);
			remocao.add(getListaItens(), BorderLayout.CENTER);
			remocao.add(getRemover(), BorderLayout.SOUTH);
		}
		return remocao;
	}

	private JScrollPane getListaItens() {
		if (lista_itens == null) {
			lista_itens = new JScrollPane();
			lista_itens.setViewportView(getItens());
		}
		return lista_itens;
	}

	private JButton getRemover() {
		if (remover == null) {
			remover = new JButton();
			remover.setText("Remover");
			remover.setForeground(Constantes.DefaultColor);
			remover.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
			remover.setPreferredSize(BUTTON_SIZE);
			remover.setBackground(Constantes.BackgroundColor);
			remover.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					if (getItens().getSelectedIndex() != -1) {
						getAcervoOn().remove(getItens().getSelectedIndex());
						try {
							core.Sistema.BiblioteCAASO.filerize();
						} catch (IOException e) {
							System.err.println("[ERRO] Problema alterando Acervo no Disco.");
							e.printStackTrace();
						}
					}
				}
			});
		}
		return remover;
	}

	private JList<Item> getItens() {
		if (itens == null) {
			itens = new JList<Item>();
			itens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return itens;
	}

	private JList<Group> getAcervos() {
		if (acervos == null) {
			acervos = new JList<Group>();
			acervos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			acervos.setModel(acervos_model);
			acervos.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(javax.swing.event.ListSelectionEvent e) {
					Items selecionado = null;
					setAcervoOn((Group) acervos_model.getElementAt(getAcervos().getSelectedIndex()));
					selecionado = getAcervoOn().Lista;
					if (selecionado != null)
						getItens().setModel(selecionado);
				}
			});
		}
		return acervos;
	}

	public void setAcervoOn(Group acervo) {
		acervo_ativo = acervo;
	}

	public Group getAcervoOn() {
		return acervo_ativo;
	}

	public void setVisivel(boolean simounao) {
		this.setVisible(simounao);
		this.getOrdens().unsetAll();
		this.getOrdens().setOrdem(Sistema.ModoOrdenacao);
	}
}
