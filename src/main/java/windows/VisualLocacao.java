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
import java.util.GregorianCalendar;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import core.Biblioteca;
import core.Sistema;
import database.CD;
import database.Item;
import database.JOGO;
import database.LIVRO;
import database.Locacao;
import database.Usuario;
import database.VIDEO;
import database.lists.Items;

public class VisualLocacao extends JPanel {

	private static final Dimension SIZE_BUTTON = new Dimension(32, 32);
	private static final Dimension SIZE_LIST = new Dimension(250, 160);
	private static final Dimension SIZE_LONG_FIELD = new Dimension(490, 50);
	private static final Dimension SIZE_SHORT_FIELD = new Dimension(90, 50);

	private static final long serialVersionUID = 1L;

	private JPanel locacoes = null;
	private JScrollPane scrollable_locados = null;
	private JList<Item> locados = null;

	private JPanel busca = null;
	private JTextField field_busca = null;
	private JButton botao_busca = null;
	private JScrollPane scrollable_encontrados = null;
	private JList<Item> encontrados = null;
	private JButton botao_adiciona = null;

	private JPanel info_usuario = null;
	private JPanel acervos = null;
	private JLabel _acervos = null;
	private JPanel marquinhas = null;
	private JLabel _marquinhas = null;
	private JPanel nome = null;
	private JLabel _nome = null;

	private JPanel ordens = null;
	private JRadioButton ordem_one = null;
	private JRadioButton ordem_two = null;
	private JRadioButton ordem_three = null;

	private Items achados = null;
	private Item[] _achados = null;

	private Items alocar = null;
	private Item[] _alocar = null;

	private JPanel data = null;
	private JLabel _data = null;

	private int semanas = 99999;
	private GregorianCalendar data_devolucao = null;
	private Usuario quem = null;

	public VisualLocacao(Usuario quem) {
		super();
		inicializar();

		if (quem != null) {
			_nome.setText(quem.Nome);

			String auxiliar = "";
			String[] temporario = quem.getAcervosPermitidos();
			if (temporario.length > 0) {
				auxiliar += temporario[0];
			}
			for (int i = 1; i < temporario.length; i++)
				auxiliar += ", " + temporario[i];

			auxiliar += ".";

			_acervos.setText(auxiliar);

			_marquinhas.setText(quem.getMarquinhas() + "");
		}

		this.quem = quem;
	}

	private void inicializar() {
		achados = new Items();
		alocar = new Items();

		this.setLayout(new BorderLayout());
		this.setSize(600, 460);
		this.add(getInfoUsuario(), BorderLayout.NORTH);
		this.add(getBusca(), BorderLayout.WEST);
		this.add(getLocacoes(), BorderLayout.CENTER);
		this.add(getOrdens(), BorderLayout.SOUTH);
	}

	private JPanel getInfoUsuario() {
		if (info_usuario == null) {
			info_usuario = new JPanel();
			info_usuario.setPreferredSize(new Dimension(SIZE_LONG_FIELD.width, SIZE_LONG_FIELD.height * 2 + 43));
			info_usuario.setForeground(Constantes.DefaultColor);
			info_usuario.setBorder(Constantes.makeTitledBorder("Usuário"));
			info_usuario.setBackground(Constantes.BackgroundColor);
			info_usuario.add(getNome());
			info_usuario.add(getData());
			info_usuario.add(getAcervos());
			info_usuario.add(getMarquinhas());
		}
		return info_usuario;
	}

	private JPanel getLocacoes() {
		if (locacoes == null) {
			locacoes = new JPanel();
			locacoes.setLayout(new BorderLayout());
			locacoes.setBorder(Constantes.makeTitledBorder("Itens Locados"));
			locacoes.setBackground(Constantes.BackgroundColor);
			locacoes.add(getScrollableLocados(), BorderLayout.CENTER);
		}
		return locacoes;
	}

	private JScrollPane getScrollableLocados() {
		if (scrollable_locados == null) {
			scrollable_locados = new JScrollPane();
			scrollable_locados.setViewportView(getLocados());
		}
		return scrollable_locados;
	}

	private JList<Item> getLocados() {
		if (locados == null) {
			locados = new JList<Item>();
			locados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			encontrados.setModel(alocar);
		}
		return locados;
	}

	private JPanel getBusca() {
		if (busca == null) {
			busca = new JPanel();
			busca.setPreferredSize(new Dimension(SIZE_LIST.width + SIZE_BUTTON.width + 20, SIZE_LIST.height + SIZE_BUTTON.height + 33));
			busca.setBorder(Constantes.makeTitledBorder("Busca"));
			busca.setBackground(Constantes.BackgroundColor);
			busca.add(getFieldBusca());
			busca.add(getBotaoBusca());
			busca.add(getScrollableEncontrados());
			busca.add(getBotao_adiciona());
		}
		return busca;
	}

	private JTextField getFieldBusca() {
		if (field_busca == null) {
			field_busca = new JTextField();
			field_busca.setPreferredSize(new Dimension(SIZE_LIST.width, SIZE_BUTTON.height));
		}
		return field_busca;
	}

	private JButton getBotaoBusca() {
		if (botao_busca == null) {
			botao_busca = new JButton();
			botao_busca.setPreferredSize(SIZE_BUTTON);
			botao_busca.setBackground(Constantes.BackgroundColor);
			botao_busca.setIcon(new ImageIcon(getClass().getResource("/icons/search.png")));
			botao_busca.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Item[] auxiliar = Sistema.BiblioteCAASO.procuraItem(getFieldBusca().getText());
					_achados = auxiliar;

					if (_achados != null)
						achados.substituirOrdenando(_achados);

					getEncontrados().setModel(achados);
				}
			});
		}
		return botao_busca;
	}

	private JScrollPane getScrollableEncontrados() {
		if (scrollable_encontrados == null) {
			scrollable_encontrados = new JScrollPane();
			scrollable_encontrados.setPreferredSize(SIZE_LIST);
			scrollable_encontrados.setViewportView(getEncontrados());
		}
		return scrollable_encontrados;
	}

	private JList<Item> getEncontrados() {
		if (encontrados == null) {
			encontrados = new JList<Item>();
			encontrados.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		}
		return encontrados;
	}

	private JButton getBotao_adiciona() {
		if (botao_adiciona == null) {
			botao_adiciona = new JButton();
			botao_adiciona.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
			botao_adiciona.setPreferredSize(SIZE_BUTTON);
			botao_adiciona.setBackground(Constantes.BackgroundColor);
			botao_adiciona.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if (getEncontrados().getSelectedIndex() >= 0) {
						Item qual = (Item) getEncontrados().getSelectedValue();

						if (qual instanceof CD)
							if (Sistema.BiblioteCAASO.getSemanaCD() < semanas)
								semanas = Sistema.BiblioteCAASO.getSemanaCD();

						if (qual instanceof VIDEO)
							if (Sistema.BiblioteCAASO.getSemanaVIDEO() < semanas)
								semanas = Sistema.BiblioteCAASO.getSemanaVIDEO();

						if (qual instanceof LIVRO)
							if (Sistema.BiblioteCAASO.getSemanaLIVRO() < semanas)
								semanas = Sistema.BiblioteCAASO.getSemanaLIVRO();

						if (qual instanceof JOGO)
							if (Sistema.BiblioteCAASO.getSemanaJOGO() < semanas)
								semanas = Sistema.BiblioteCAASO.getSemanaJOGO();

						Item[] auxiliar = null;

						if (_alocar != null) {

							auxiliar = new Item[_alocar.length + 1];

							for (int i = 0; i < _alocar.length; i++) {
								auxiliar[i] = _alocar[i];
							}
							auxiliar[_alocar.length] = qual;
						} else {
							auxiliar = new Item[1];
							auxiliar[0] = qual;
						}

						setNovaData();

						_alocar = auxiliar;
						if (_alocar != null)
							alocar.substituirOrdenando(_alocar);

						getLocados().setModel(alocar);
					}
				}
			});
		}
		return botao_adiciona;
	}

	private JPanel getNome() {
		if (nome == null) {
			_nome = new JLabel();
			_nome.setText("");
			_nome.setForeground(Constantes.ContentColor);
			nome = new JPanel();
			nome.setForeground(Constantes.ContentColor);
			nome.setBorder(Constantes.makeTitledBorder("Nome Completo"));
			nome.setPreferredSize(SIZE_LONG_FIELD);
			nome.setBackground(Constantes.BackgroundColor);
			nome.add(_nome);
		}
		return nome;
	}

	private JPanel getAcervos() {
		if (acervos == null) {
			_acervos = new JLabel();
			_acervos.setText("");
			_acervos.setForeground(Constantes.ContentColor);
			acervos = new JPanel();
			acervos.setBackground(Constantes.BackgroundColor);
			acervos.setForeground(Constantes.ContentColor);
			acervos.setPreferredSize(SIZE_LONG_FIELD);
			acervos.setBorder(Constantes.makeTitledBorder("Acervos Permitidos"));
			acervos.add(_acervos);
		}
		return acervos;
	}

	private JPanel getMarquinhas() {
		if (marquinhas == null) {
			_marquinhas = new JLabel();
			_marquinhas.setText("");
			_marquinhas.setForeground(Constantes.ContentColor);
			marquinhas = new JPanel();
			marquinhas.setPreferredSize(SIZE_SHORT_FIELD);
			marquinhas.setBorder(Constantes.makeTitledBorder("Marquinhas"));
			marquinhas.setBackground(Constantes.BackgroundColor);
			marquinhas.add(_marquinhas);
		}
		return marquinhas;
	}

	private JPanel getOrdens() {
		if (ordens == null) {
			ordens = new JPanel();
			ordens.setBackground(Constantes.BackgroundColor);
			ordens.setBorder(Constantes.makeTitledBorder("Ordenação"));
			ordens.add(getOrdemOne());
			ordens.add(getOrdemTwo());
			ordens.add(getOrdemThree());
		}
		return ordens;
	}

	private JRadioButton getOrdemOne() {
		if (ordem_one == null) {
			ordem_one = new JRadioButton();
			ordem_one.setBackground(Constantes.BackgroundColor);
			ordem_one.setForeground(Constantes.DefaultColor);
			ordem_one.setText("Código, Autor");
			ordem_one.setSelected(true);
			ordem_one.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Sistema.ModoOrdenacao = 1;
					getOrdemOne().setSelected(true);
					getOrdemTwo().setSelected(false);
					getOrdemThree().setSelected(false);
					alocar.substituirOrdenando(_alocar);
					achados.substituirOrdenando(_achados);
				}
			});
		}
		return ordem_one;
	}

	private JRadioButton getOrdemTwo() {
		if (ordem_two == null) {
			ordem_two = new JRadioButton();
			ordem_two.setBackground(Constantes.BackgroundColor);
			ordem_two.setText("Título, Álbum");
			ordem_two.setForeground(Constantes.DefaultColor);
			ordem_two.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Sistema.ModoOrdenacao = 2;
					getOrdemOne().setSelected(false);
					getOrdemTwo().setSelected(true);
					getOrdemThree().setSelected(false);
					alocar.substituirOrdenando(_alocar);
					achados.substituirOrdenando(_achados);
				}
			});
		}
		return ordem_two;
	}

	private JRadioButton getOrdemThree() {
		if (ordem_three == null) {
			ordem_three = new JRadioButton();
			ordem_three.setBackground(Constantes.BackgroundColor);
			ordem_three.setText("Classificação, Original, Artista, Assunto");
			ordem_three.setForeground(Constantes.DefaultColor);
			ordem_three.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Sistema.ModoOrdenacao = 3;
					getOrdemOne().setSelected(false);
					getOrdemTwo().setSelected(false);
					getOrdemThree().setSelected(true);
					alocar.substituirOrdenando(_alocar);
					achados.substituirOrdenando(_achados);
				}
			});
		}
		return ordem_three;
	}

	private JPanel getData() {
		if (data == null) {
			_data = new JLabel();
			_data.setText("");
			_data.setForeground(Constantes.ContentColor);
			data = new JPanel();
			data.setBackground(Constantes.BackgroundColor);
			data.setPreferredSize(SIZE_SHORT_FIELD);
			data.setBorder(Constantes.makeTitledBorder("Devolução"));
			data.add(_data);
		}
		return data;
	}

	public void setNovaData() {
		GregorianCalendar quando = Biblioteca.calculaDevolucao(semanas);
		String auxiliar = "";

		if (quando.get(GregorianCalendar.DATE) < 10) {
			auxiliar += "0" + quando.get(GregorianCalendar.DATE) + "/";
		} else {
			auxiliar += quando.get(GregorianCalendar.DATE) + "/";
		}

		if (quando.get(GregorianCalendar.MONTH) < 9) {
			auxiliar += "0" + (quando.get(GregorianCalendar.MONTH) + 1) + "/";
		} else {
			auxiliar += (quando.get(GregorianCalendar.MONTH) + 1) + "/";
		}
		auxiliar += "" + (quando.get(GregorianCalendar.YEAR));

		_data.setText(auxiliar);

		data_devolucao = quando;
	}

	public Locacao getConteudo() {
		if (_alocar != null)
			return new Locacao(quem.Nome, _alocar, data_devolucao);
		else
			return null;
	}
}
