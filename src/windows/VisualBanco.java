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
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import core.Sistema;

public class VisualBanco extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final Dimension BUTTON_SIZE = new Dimension(120,50);
	
	private JScrollPane scrollable_movimentacoes = null;
	private JTextArea movimentacoes = null;
	private JPanel nova_movimentacao = null;
	
	private JPanel mudanca = null;
	private JPanel caixa = null;
	private JPanel discriminacao = null;
	private JPanel valor = null;
	
	private JButton credito = null;
	private JButton debito = null;
	
	private JTextField _discriminacao = null;
	private JTextField _valor = null;
	
	private JLabel _caixa = null;

	public VisualBanco() {
		super();
		inicializar();
	}

	private void inicializar() {
		this.setLayout(new BorderLayout());
		this.setBorder(Constantes.makeTitledBorder("Movimentações Monetárias"));
		this.setBackground(Constantes.BackgroundColor);
		this.add(getScrollableMovimentacoes(), BorderLayout.CENTER);
		this.add(getMudanca(), BorderLayout.NORTH);
	}

	private JScrollPane getScrollableMovimentacoes() {
		if (scrollable_movimentacoes == null) {
			scrollable_movimentacoes = new JScrollPane();
			scrollable_movimentacoes.setViewportView(getMovimentacoes());
		}
		return scrollable_movimentacoes;
	}

	private JTextArea getMovimentacoes() {
		if (movimentacoes == null) {
			movimentacoes = new JTextArea();
			movimentacoes.setEditable(false);
			movimentacoes.setEnabled(true);
			movimentacoes.setText(Sistema.BiblioteCAASO.getMovimentacao());		
		}
		return movimentacoes;
	}

	private JPanel getMudanca() {
		if (mudanca == null) {
			mudanca = new JPanel();
			mudanca.setBackground(Constantes.BackgroundColor);
			mudanca.add(getNovaMovimentacao());
			mudanca.add(getCaixa());
		}
		return mudanca;
	}

	private JPanel getNovaMovimentacao() {
		if (nova_movimentacao == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setHgap(Constantes.GAP);
			flowLayout.setVgap(Constantes.GAP);
			
			nova_movimentacao = new JPanel();
			nova_movimentacao.setLayout(flowLayout);
			nova_movimentacao.setBackground(Constantes.BackgroundColor);
			nova_movimentacao.setPreferredSize(new Dimension((BUTTON_SIZE.width+Constantes.GAP)*6,BUTTON_SIZE.height+Constantes.GAP*2+23));
			nova_movimentacao.setBorder(Constantes.makeTitledBorder("Nova Movimentação"));
			nova_movimentacao.add(getDiscriminacao());
			nova_movimentacao.add(getValor());
			nova_movimentacao.add(getBotaoCredito());
			nova_movimentacao.add(getBotaoDebito());
		}
		return nova_movimentacao;
	}

	private JPanel getCaixa() {
		if (caixa == null) {
			_caixa = new JLabel();
			_caixa.setText(Sistema.BiblioteCAASO.getCaixa() + "");
			_caixa.setForeground(Constantes.ContentColor);
			_caixa.setHorizontalAlignment(JLabel.CENTER);
			caixa = new JPanel();
			caixa.setLayout(new BorderLayout());
			caixa.setBackground(Constantes.BackgroundColor);
			caixa.setPreferredSize(BUTTON_SIZE);
			caixa.setBorder(Constantes.makeTitledBorder("Caixa"));
			caixa.add(_caixa, BorderLayout.CENTER);
		}
		return caixa;
	}

	private JPanel getDiscriminacao() {
		if (discriminacao == null) {
			discriminacao = new JPanel();
			discriminacao.setLayout(new BorderLayout());
			discriminacao.setBackground(Constantes.BackgroundColor);
			discriminacao.setPreferredSize(new Dimension(BUTTON_SIZE.width*3,BUTTON_SIZE.height));
			discriminacao.setBorder(Constantes.makeTitledBorder("Discriminação"));
			discriminacao.add(get_discriminacao(), BorderLayout.CENTER);
		}
		return discriminacao;
	}

	private JPanel getValor() {
		if (valor == null) {
			valor = new JPanel();
			valor.setLayout(new BorderLayout());
			valor.setBackground(Constantes.BackgroundColor);
			valor.setPreferredSize(BUTTON_SIZE);
			valor.setBorder(Constantes.makeTitledBorder("Valor"));
			valor.add(get_valor(), BorderLayout.CENTER);
		}
		return valor;
	}

	private JButton getBotaoCredito() {
		if (credito == null) {
			credito = new JButton();
			credito.setText("Creditar");
			credito.setPreferredSize(BUTTON_SIZE);
			credito.setForeground(Constantes.DefaultColor);
			credito.setIcon(new ImageIcon(getClass().getResource("/icons/accept.png")));
			credito.setBackground(Constantes.BackgroundColor);
			credito.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Float temporario;
					try {
						temporario = new Float(get_valor().getText());
					}
					catch(Exception error) {
						temporario = null;
						get_valor().setText("0.00");
					}
					
					if(temporario != null) {
						Sistema.BiblioteCAASO.addMovimentacao(get_discriminacao().getText() + ", +" + temporario.floatValue()); 
						Sistema.BiblioteCAASO.creditar(temporario.floatValue());
						getMovimentacoes().setText(Sistema.BiblioteCAASO.getMovimentacao());
						_caixa.setText(Sistema.BiblioteCAASO.getCaixa() + "");
					}
				}
			});
		}
		return credito;
	}

	private JButton getBotaoDebito() {
		if (debito == null) {
			debito = new JButton();
			debito.setText("Debitar");
			debito.setPreferredSize(BUTTON_SIZE);
			debito.setForeground(Constantes.DefaultColor);
			debito.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
			debito.setBackground(Constantes.BackgroundColor);
			debito.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Float temporario;
					
					try {
					 temporario = new Float(get_valor().getText());
					}
					catch(Exception error) {
						temporario = null;
						get_valor().setText("0.00");
					}
					
					if(temporario != null) {
						Sistema.BiblioteCAASO.addMovimentacao(get_discriminacao().getText() + ", -" + temporario.floatValue()); 
						Sistema.BiblioteCAASO.debitar(temporario.floatValue());
						getMovimentacoes().setText(Sistema.BiblioteCAASO.getMovimentacao());
						_caixa.setText(Sistema.BiblioteCAASO.getCaixa() + "");
					}
				}
			});
		}
		return debito;
	}

	private JTextField get_discriminacao() {
		if (_discriminacao == null) {
			_discriminacao = new JTextField();
		}
		return _discriminacao;
	}

	private JTextField get_valor() {
		if (_valor == null) {
			_valor = new JTextField();
			_valor.setText("0.00");
		}
		return _valor;
	}

	public void setVisivel(boolean b) {
		_caixa.setText(Sistema.BiblioteCAASO.getCaixa() + "");
		getMovimentacoes().setText(Sistema.BiblioteCAASO.getMovimentacao());
		setVisible(b);
	}
}
