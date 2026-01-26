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
import database.Item;
import database.Locacao;
import database.lists.Items;

public class VisualLocacoes extends JPanel {

	private static final Dimension BUTTON_SIZE = new Dimension(150,150);
	private static final Dimension LIST_SIZE = new Dimension(300,300);
	
	private static final long serialVersionUID = 1L;
	
	private JPanel locacoes_feitas = null;
	private JScrollPane scrollable_locacoes = null;
	private JList<Locacao> locacoes = null;
	
	private JPanel items_locacados = null;
	private JScrollPane scrollable_itens = null;
	private JList<Item> itens = null;
	
	private JButton botao_devolve = null;
	
	
	public VisualLocacoes() {
		super();
		inicializar();
		
		getLocacoes().setModel(Sistema.BiblioteCAASO.getLOCACOESModel());
	}

	private void inicializar() {
		this.setLayout(new BorderLayout());
		this.add(getLocacoesFeitas(), BorderLayout.NORTH);
		this.add(getItemsLocacados(), BorderLayout.CENTER);
		this.add(getBotaoDevolve(), BorderLayout.EAST);
	}

	private JPanel getLocacoesFeitas() {
		if (locacoes_feitas == null) {
			locacoes_feitas = new JPanel();
			locacoes_feitas.setLayout(new BorderLayout());
			locacoes_feitas.setPreferredSize(LIST_SIZE);
			locacoes_feitas.setBorder(Constantes.makeTitledBorder("Locações"));
			locacoes_feitas.setBackground(Constantes.BackgroundColor);
			locacoes_feitas.add(getScrollableLocacoes(), BorderLayout.CENTER);
		}
		return locacoes_feitas;
	}

	private JPanel getItemsLocacados() {
		if (items_locacados == null) {
			items_locacados = new JPanel();
			items_locacados.setLayout(new BorderLayout());
			items_locacados.setBorder(Constantes.makeTitledBorder("Itens Locados"));
			items_locacados.setBackground(Constantes.BackgroundColor);
			items_locacados.add(getScrollableItens(), BorderLayout.CENTER);
		}
		return items_locacados;
	}

	private JButton getBotaoDevolve() {
		if (botao_devolve == null) {
			botao_devolve = new JButton();
			botao_devolve.setPreferredSize(BUTTON_SIZE);
			botao_devolve.setText("Devolução");
			botao_devolve.setForeground(Constantes.DefaultColor);
			botao_devolve.setIcon(new ImageIcon(getClass().getResource("/icons/down.png")));
			botao_devolve.setBackground(Constantes.BackgroundColor);
			botao_devolve.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(getLocacoes().getSelectedIndex() >= 0 ) {
						VisualDevolucao devolve = new VisualDevolucao((Locacao)getLocacoes().getSelectedValue());
						getItens().setModel(new Items());
						getLocacoes().setSelectedIndices(new int[]{});
						devolve.setVisible(true);
					}
				}
			});
		}
		return botao_devolve;
	}

	private JList<Locacao> getLocacoes() {
		if (locacoes == null) {
			locacoes = new JList<Locacao>();
			locacoes.addListSelectionListener(new ListSelectionListener() {
						public void valueChanged(ListSelectionEvent e) {
							if(getLocacoes().getSelectedIndex() >= 0) {
								Locacao escolhida = (Locacao)getLocacoes().getSelectedValue();
								
								Sistema.ModoOrdenacao = 1;
								
								escolhida.lista.substituirOrdenando(escolhida.oque);
								
								getItens().setModel(escolhida.lista);
							}
						}
					});
		}
		return locacoes;
	}

	private JScrollPane getScrollableItens() {
		if (scrollable_itens == null) {
			scrollable_itens = new JScrollPane();
			scrollable_itens.setViewportView(getItens());
		}
		return scrollable_itens;
	}

	private JList<Item> getItens() {
		if (itens == null) {
			itens = new JList<Item>();
		}
		return itens;
	}

	private JScrollPane getScrollableLocacoes() {
		if (scrollable_locacoes == null) {
			scrollable_locacoes = new JScrollPane();
			scrollable_locacoes.setViewportView(getLocacoes());
		}
		return scrollable_locacoes;
	}
}
