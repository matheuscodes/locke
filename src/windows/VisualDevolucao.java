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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import core.Sistema;
import database.Locacao;
import database.Usuario;

public class VisualDevolucao extends JDialog {

	private static final long serialVersionUID = 1L;
	private static final Dimension WINDOW_SIZE = new Dimension(560,240);
	private static final Dimension LONG_SIZE = new Dimension(540,50);
	private static final Dimension SHORT_SIZE = new Dimension(130,50);
	
	private JPanel tudo = null;
	private JPanel nome_usuario = null;
	private JPanel numero_marquinhas = null;
	private JPanel data_devolucao = null;
	private JPanel valor_cobrado = null;
	private JPanel novas_marquinhas = null;
	private JLabel _nome = null;
	private JLabel _marquinhas = null;
	private JTextField _valor = null;
	private JTextField _novas = null;
	private JLabel _data = null;
	private JButton botao_ok = null;
	private Locacao locacao = null;

	public VisualDevolucao(Locacao l) {
		super();
		
		locacao = l;
		
		inicializar();
		
		Usuario cara = Sistema.BiblioteCAASO.procuraUsuario(locacao.quem);
		
		_nome.setText(cara.Nome);
		_marquinhas.setText(cara.getMarquinhas() + "");
		_data.setText(locacao.paraquando);
		
	}

	private void inicializar() {
		this.setSize(WINDOW_SIZE);
		this.setContentPane(getTudo());
	}

	private JPanel getTudo() {
		if (tudo == null) {
			tudo = new JPanel();
			tudo.setLayout(new FlowLayout());
			tudo.setBackground(Constantes.BackgroundColor);
			tudo.setBorder(Constantes.makeTitledBorder("Devoluções"));
			tudo.add(getNomeUsuario());
			tudo.add(getNumeroMarquinhas());
			tudo.add(getDataDevolucao());
			tudo.add(getValorCobrado());
			tudo.add(getNovasMarquinhas());
			tudo.add(getBotaoOk());
		}
		return tudo;
	}

	private JPanel getNomeUsuario() {
		if (nome_usuario == null) {
			_nome = new JLabel();
			_nome.setText("");
			_nome.setForeground(Constantes.ContentColor);
			nome_usuario = new JPanel();
			nome_usuario.setLayout(new BorderLayout());
			nome_usuario.setBackground(Constantes.BackgroundColor);
			nome_usuario.setPreferredSize(LONG_SIZE);
			nome_usuario.setBorder(Constantes.makeTitledBorder("Nome do Usuário"));
			nome_usuario.add(_nome, BorderLayout.CENTER);
		}
		return nome_usuario;
	}

	private JPanel getNumeroMarquinhas() {
		if (numero_marquinhas == null) {
			_marquinhas = new JLabel();
			_marquinhas.setText("");
			_marquinhas.setHorizontalAlignment(SwingConstants.CENTER);
			_marquinhas.setForeground(Constantes.ContentColor);
			numero_marquinhas = new JPanel();
			numero_marquinhas.setLayout(new BorderLayout());
			numero_marquinhas.setPreferredSize(SHORT_SIZE);
			numero_marquinhas.setBorder(Constantes.makeTitledBorder("Marquinhas"));
			numero_marquinhas.setBackground(Constantes.BackgroundColor);
			numero_marquinhas.add(_marquinhas, BorderLayout.CENTER);
		}
		return numero_marquinhas;
	}

	private JPanel getDataDevolucao() {
		if (data_devolucao == null) {
			_data = new JLabel();
			_data.setText("");
			_data.setHorizontalAlignment(SwingConstants.CENTER);
			_data.setForeground(Constantes.ContentColor);
			data_devolucao = new JPanel();
			data_devolucao.setLayout(new BorderLayout());
			data_devolucao.setBackground(Constantes.BackgroundColor);
			data_devolucao.setPreferredSize(SHORT_SIZE);
			data_devolucao.setBorder(Constantes.makeTitledBorder("Data de Devolução"));
			data_devolucao.add(_data, BorderLayout.CENTER);
		}
		return data_devolucao;
	}

	private JPanel getValorCobrado() {
		if (valor_cobrado == null) {
			valor_cobrado = new JPanel();
			valor_cobrado.setLayout(new BorderLayout());
			valor_cobrado.setBackground(Constantes.BackgroundColor);
			valor_cobrado.setPreferredSize(SHORT_SIZE);
			valor_cobrado.setBorder(Constantes.makeTitledBorder("Valor Cobrado"));
			valor_cobrado.add(getValor(), BorderLayout.NORTH);
		}
		return valor_cobrado;
	}

	private JPanel getNovasMarquinhas() {
		if (novas_marquinhas == null) {
			novas_marquinhas = new JPanel();
			novas_marquinhas.setLayout(new BorderLayout());
			novas_marquinhas.setBackground(Constantes.BackgroundColor);
			novas_marquinhas.setPreferredSize(SHORT_SIZE);
			novas_marquinhas.setBorder(Constantes.makeTitledBorder("Novas Marquinhas"));
			novas_marquinhas.add(getNovas(), BorderLayout.NORTH);
		}
		return novas_marquinhas;
	}

	private JTextField getValor() {
		if (_valor == null) {
			_valor = new JTextField();
			_valor.setText("0.00");
		}
		return _valor;
	}

	private JTextField getNovas() {
		if (_novas == null) {
			_novas = new JTextField();
			_novas.setText("0");
		}
		return _novas;
	}

	private JButton getBotaoOk() {
		if (botao_ok == null) {
			botao_ok = new JButton();
			botao_ok.setBackground(Constantes.BackgroundColor);
			botao_ok.setPreferredSize(SHORT_SIZE);
			botao_ok.setText("Devolver");
			botao_ok.setIcon(new ImageIcon(getClass().getResource("/icons/accept.png")));
			botao_ok.setForeground(Constantes.DefaultColor);
			botao_ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					Usuario cara = Sistema.BiblioteCAASO.procuraUsuario(locacao.quem);
					if(cara !=  null) {
						Integer auxiliar;
					
						try {
							auxiliar = new Integer(getNovas().getText());
						}
						catch(Exception e) {
							System.out.println("[ERRO] Houve um problema convertendo o número dado em novas marquinhas.");
							e.printStackTrace();
							auxiliar = new Integer("0");
							getNovas().setText("0");
						}
					
						cara.incrementaMarquinhas(auxiliar.intValue());
					}
					for(int i = 0; i < locacao.oque.length ;i++) {
						locacao.oque[i].retirado = false;
						locacao.oque[i].numero_retiradas++;
						cara.addPassado(locacao.oque[i].toString());
					}
					core.Sistema.BiblioteCAASO.removeLocacao(locacao.ID);
					
					Float temporary;
					
					try {
					 temporary = new Float(getValor().getText());
					}
					catch(Exception e) {
						System.out.println("[ERRO] Houve um problema convertendo o valor da devolução.");
						e.printStackTrace();
						temporary = null;
						getValor().setText("0.00");
					}
					
					if(temporary != null) {
						Sistema.BiblioteCAASO.creditar(temporary.floatValue());
						Sistema.BiblioteCAASO.addMovimentacao(locacao + " Valor: " + _valor.getText());
						dispose();
					}
				}
			});
		}
		return botao_ok;
	}
}
