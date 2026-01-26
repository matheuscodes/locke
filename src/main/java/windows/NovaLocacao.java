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

import core.Sistema;
import database.Locacao;
import database.Usuario;

public class NovaLocacao extends JDialog {
	private static final Dimension BUTTON_SIZE = new Dimension(50,50);
	private static final Dimension WINDOW_SIZE = new Dimension(610,530);

	private static final long serialVersionUID = 1L;
	
	private JPanel conteudo = null;
	
	private VisualLocacao locacao = null;
	
	private JButton botao_salvar = null;
	
	private Usuario quem = null; 

	public NovaLocacao() {
		super();
		initialize();
	}
	
	public NovaLocacao(Usuario quem) {
		super();
		this.quem = quem;
		
		initialize();
	}

	private void initialize() {
		this.setSize(WINDOW_SIZE);
		this.setContentPane(getConteudo());
		this.setAlwaysOnTop(true);
	}

	private JPanel getConteudo() {
		if (conteudo == null) {
			conteudo = new JPanel();
			conteudo.setLayout(new BorderLayout());
			conteudo.add(getLocacao(), BorderLayout.CENTER);
			conteudo.add(getBotaoSalvar(), BorderLayout.SOUTH);
		}
		return conteudo;
	}

	private VisualLocacao getLocacao() {
		if (locacao == null) {
			locacao = new VisualLocacao(quem);
		}
		return locacao;
	}

	private JButton getBotaoSalvar() {
		if (botao_salvar == null) {
			botao_salvar = new JButton();
			botao_salvar.setPreferredSize(BUTTON_SIZE);
			botao_salvar.setIcon(new ImageIcon(getClass().getResource("/icons/accept.png")));
			botao_salvar.setText("Salva");
			botao_salvar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					
					Locacao nova = getLocacao().getConteudo();										
					Sistema.BiblioteCAASO.novaLocacao(nova);
										
					dispose();
				}
			});
		}
		return botao_salvar;
	}
}
