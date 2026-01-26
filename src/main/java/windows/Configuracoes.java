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

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import core.Sistema;
import database.lists.CDs;
import database.lists.JOGOs;
import database.lists.LIVROs;
import database.lists.VIDEOs;

public class Configuracoes extends JPanel {

	private static final Dimension LIST_SIZE = new Dimension(150, 100);
	private static final Dimension SMALL_SIZE = new Dimension(150, 32);
	private static final Dimension BUTTON_SIZE = new Dimension(50, 32);
	private static final Dimension TEXT_BUTTON_SIZE = new Dimension(150, 32);
	private static final long serialVersionUID = 1L;

	private JPanel lista_acervos = null;

	private JTextField field_cds = null;
	private JScrollPane cds = null;
	private JList<CDs> CDS = null;

	private JScrollPane videos = null;
	private JList<VIDEOs> VIDEOS = null;
	private JTextField field_videos = null;

	private JScrollPane livros = null;
	private JList<LIVROs> LIVROS = null;
	private JTextField field_livros = null;

	private JScrollPane jogos = null;
	private JList<JOGOs> JOGOS = null;
	private JTextField field_jogos = null;

	private JPanel entrega = null;

	private JButton botao_entrega = null;

	private JPanel alteracoes = null;

	private JPanel adicao = null;
	private JTextField novo_acervo = null;
	private JButton botao_adicionar = null;
	private JComboBox<String> tipo = null;
	private DefaultComboBoxModel<String> todos_tipos = null;

	private JPanel remocao = null;
	private JLabel selecionado = null;
	private JButton botao_remover = null;

	int grupo = 0;
	boolean secondtime = false;

	public Configuracoes() {
		super();
		inicializar();
	}

	private void inicializar() {
		this.setLayout(new FlowLayout());
		this.setBorder(Constantes.makeTitledBorder("Configurações"));
		this.setBackground(Constantes.BackgroundColor);
		this.add(getListaAcervos());
		this.add(getAlteracoes());
		this.add(getEntrega());
	}

	private JPanel getListaAcervos() {
		if (lista_acervos == null) {
			FlowLayout flowLayout = new FlowLayout();
			flowLayout.setAlignment(FlowLayout.CENTER);

			JLabel label_jogos = new JLabel();
			label_jogos.setText("Jogos: ");
			label_jogos.setForeground(Constantes.DefaultColor);

			JLabel label_livros = new JLabel();
			label_livros.setText("Livros: ");
			label_livros.setForeground(Constantes.DefaultColor);

			JLabel label_videos = new JLabel();
			label_videos.setText("Vídeos: ");
			label_videos.setForeground(Constantes.DefaultColor);

			JLabel label_cds = new JLabel();
			label_cds.setText("CDs: ");
			label_cds.setForeground(Constantes.DefaultColor);

			lista_acervos = new JPanel();
			lista_acervos.setBorder(Constantes.makeTitledBorder("Acervos"));
			lista_acervos.setBackground(Constantes.BackgroundColor);
			lista_acervos.setLayout(flowLayout);
			lista_acervos.add(label_cds);
			lista_acervos.add(getCds());
			lista_acervos.add(label_videos);
			lista_acervos.add(getVideos());
			lista_acervos.add(label_livros);
			lista_acervos.add(getLivros());
			lista_acervos.add(label_jogos);
			lista_acervos.add(getJogos());
		}
		return lista_acervos;
	}

	private JScrollPane getCds() {
		if (cds == null) {
			CDS = new JList<CDs>();
			CDS.setModel(Sistema.BiblioteCAASO.getCDSModel());
			CDS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			CDS.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (CDS.getSelectedIndex() >= 0)
						selecionado.setText(CDS.getSelectedValue().toString());
					grupo = 1;
					unSelectAllBut(CDS);
				}
			});

			cds = new JScrollPane();
			cds.setPreferredSize(LIST_SIZE);
			cds.setViewportView(CDS);
		}
		return cds;
	}

	private JScrollPane getVideos() {
		if (videos == null) {
			VIDEOS = new JList<VIDEOs>();
			VIDEOS.setModel(Sistema.BiblioteCAASO.getVIDEOSModel());
			VIDEOS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			VIDEOS.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (VIDEOS.getSelectedIndex() >= 0)
						selecionado.setText(VIDEOS.getSelectedValue().toString());
					grupo = 2;
					unSelectAllBut(VIDEOS);
				}
			});

			videos = new JScrollPane();
			videos.setPreferredSize(LIST_SIZE);
			videos.setViewportView(VIDEOS);
		}
		return videos;
	}

	private JScrollPane getJogos() {
		if (jogos == null) {
			JOGOS = new JList<JOGOs>();
			JOGOS.setModel(Sistema.BiblioteCAASO.getJOGOSModel());
			JOGOS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			JOGOS.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (JOGOS.getSelectedIndex() >= 0)
						selecionado.setText(JOGOS.getSelectedValue().toString());
					grupo = 4;
					unSelectAllBut(JOGOS);
				}
			});

			jogos = new JScrollPane();
			jogos.setPreferredSize(LIST_SIZE);
			jogos.setViewportView(JOGOS);
		}
		return jogos;
	}

	private JScrollPane getLivros() {
		if (livros == null) {
			LIVROS = new JList<LIVROs>();
			LIVROS.setModel(Sistema.BiblioteCAASO.getLIVROSModel());
			LIVROS.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			LIVROS.addListSelectionListener(new ListSelectionListener() {
				public void valueChanged(ListSelectionEvent e) {
					if (LIVROS.getSelectedIndex() >= 0)
						selecionado.setText(LIVROS.getSelectedValue().toString());
					grupo = 3;
					unSelectAllBut(LIVROS);
				}
			});

			livros = new JScrollPane();
			livros.setPreferredSize(LIST_SIZE);
			livros.setViewportView(LIVROS);
		}
		return livros;
	}

	private JPanel getEntrega() {
		if (entrega == null) {
			JLabel label_jogos = new JLabel();
			label_jogos.setText("Jogos: ");
			label_jogos.setForeground(Constantes.DefaultColor);
			JLabel label_livros = new JLabel();
			label_livros.setText("Livros: ");
			label_livros.setForeground(Constantes.DefaultColor);
			JLabel label_videos = new JLabel();
			label_videos.setText("Vídeos: ");
			label_videos.setForeground(Constantes.DefaultColor);
			JLabel label_cds = new JLabel();
			label_cds.setText("CDs: ");
			label_cds.setForeground(Constantes.DefaultColor);
			entrega = new JPanel();
			entrega.setBackground(Constantes.BackgroundColor);
			entrega.setBorder(Constantes.makeTitledBorder("Dados de Entrega (Em semanas)"));
			entrega.setForeground(Constantes.DefaultColor);
			entrega.add(label_cds);
			entrega.add(getFieldCds());
			entrega.add(label_videos);
			entrega.add(getFieldVideos());
			entrega.add(label_livros);
			entrega.add(getFieldLivros());
			entrega.add(label_jogos);
			entrega.add(getFieldJogos());
			entrega.add(getBotaoEntrega());
		}
		return entrega;
	}

	private JTextField getFieldCds() {
		if (field_cds == null) {
			field_cds = new JTextField();
			field_cds.setText(Sistema.BiblioteCAASO.getSemanaCD() + "");
			field_cds.setPreferredSize(BUTTON_SIZE);
			field_cds.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					getBotaoEntrega().setEnabled(true);
				}
			});
		}
		return field_cds;
	}

	private JTextField getFieldVideos() {
		if (field_videos == null) {
			field_videos = new JTextField();
			field_videos.setText(Sistema.BiblioteCAASO.getSemanaVIDEO() + "");
			field_videos.setPreferredSize(BUTTON_SIZE);
			field_videos.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					getBotaoEntrega().setEnabled(true);
				}
			});
		}
		return field_videos;
	}

	private JTextField getFieldLivros() {
		if (field_livros == null) {
			field_livros = new JTextField();
			field_livros.setText(Sistema.BiblioteCAASO.getSemanaLIVRO() + "");
			field_livros.setPreferredSize(BUTTON_SIZE);
			field_livros.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					getBotaoEntrega().setEnabled(true);
				}
			});
		}
		return field_livros;
	}

	private JTextField getFieldJogos() {
		if (field_jogos == null) {
			field_jogos = new JTextField();
			field_jogos.setText(Sistema.BiblioteCAASO.getSemanaJOGO() + "");
			field_jogos.setPreferredSize(BUTTON_SIZE);
			field_jogos.addKeyListener(new KeyAdapter() {
				public void keyTyped(KeyEvent e) {
					getBotaoEntrega().setEnabled(true);
				}
			});
		}
		return field_jogos;
	}

	private JPanel getAdicao() {
		if (adicao == null) {
			adicao = new JPanel();
			adicao.setBackground(Constantes.BackgroundColor);
			adicao.setPreferredSize(new Dimension(SMALL_SIZE.width + BUTTON_SIZE.width + 2 * Constantes.GAP + 10, (SMALL_SIZE.height + Constantes.GAP) * 2 + 33));
			adicao.setBorder(Constantes.makeTitledBorder("Novo"));
			((FlowLayout) adicao.getLayout()).setAlignment(FlowLayout.LEFT);
			adicao.add(getNovoAcervo());
			adicao.add(getBotaoAdicionar());
			adicao.add(getTipo());
		}
		return adicao;
	}

	private JTextField getNovoAcervo() {
		if (novo_acervo == null) {
			novo_acervo = new JTextField();
			novo_acervo.setPreferredSize(SMALL_SIZE);
		}
		return novo_acervo;
	}

	private JButton getBotaoAdicionar() {
		if (botao_adicionar == null) {
			botao_adicionar = new JButton();
			botao_adicionar.setIcon(new ImageIcon(getClass().getResource("/icons/add.png")));
			botao_adicionar.setBackground(Constantes.BackgroundColor);
			botao_adicionar.setPreferredSize(BUTTON_SIZE);
			botao_adicionar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					try {
						switch (getTipo().getSelectedIndex()) {
						case 0:
							Sistema.BiblioteCAASO.novoCD(getNovoAcervo().getText());
							getNovoAcervo().setText("");
							break;
						case 1:
							Sistema.BiblioteCAASO.novoVIDEO(getNovoAcervo().getText());
							getNovoAcervo().setText("");
							break;
						case 2:
							Sistema.BiblioteCAASO.novoLIVRO(getNovoAcervo().getText());
							getNovoAcervo().setText("");
							break;
						case 3:
							Sistema.BiblioteCAASO.novoJOGO(getNovoAcervo().getText());
							getNovoAcervo().setText("");
							break;
						}
					} catch (IOException erro) {
						System.err.println("[ERRO] Houve um erro na criação dos arquivos do novo Acervo.");
						erro.printStackTrace();
					}
				}
			});
		}
		return botao_adicionar;
	}

	private JComboBox<String> getTipo() {
		if (tipo == null) {
			todos_tipos = new DefaultComboBoxModel<String>();
			todos_tipos.addElement("CDs");
			todos_tipos.addElement("Vídeos");
			todos_tipos.addElement("Livros");
			todos_tipos.addElement("Jogos");

			tipo = new JComboBox<String>();
			tipo.setPreferredSize(SMALL_SIZE);
			tipo.setModel(todos_tipos);
		}
		return tipo;
	}

	private JButton getBotaoEntrega() {
		if (botao_entrega == null) {
			botao_entrega = new JButton();
			botao_entrega.setText("Muda");
			botao_entrega.setPreferredSize(TEXT_BUTTON_SIZE);
			botao_entrega.setBackground(Constantes.BackgroundColor);
			botao_entrega.setForeground(Constantes.DefaultColor);
			botao_entrega.setEnabled(false);
			botao_entrega.setIcon(new ImageIcon(getClass().getResource("/icons/accept.png")));
			botao_entrega.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					getBotaoEntrega().setEnabled(false);
					Sistema.BiblioteCAASO.setSemanaCD(new Integer(getFieldCds().getText()).intValue());
					Sistema.BiblioteCAASO.setSemanaVIDEO(new Integer(getFieldVideos().getText()).intValue());
					Sistema.BiblioteCAASO.setSemanaJOGO(new Integer(getFieldJogos().getText()).intValue());
					Sistema.BiblioteCAASO.setSemanaLIVRO(new Integer(getFieldLivros().getText()).intValue());
				}
			});
		}
		return botao_entrega;
	}

	private JPanel getAlteracoes() {
		if (alteracoes == null) {
			alteracoes = new JPanel();
			alteracoes.setBackground(Constantes.BackgroundColor);
			alteracoes.setBorder(Constantes.makeTitledBorder("Alterações"));
			alteracoes.setForeground(Constantes.DefaultColor);
			alteracoes.add(getAdicao());
			alteracoes.add(getRemocao());
		}
		return alteracoes;
	}

	private JPanel getRemocao() {
		if (remocao == null) {
			selecionado = new JLabel();
			selecionado.setText("Nenhum Selecionado");
			selecionado.setForeground(Constantes.ContentColor);

			remocao = new JPanel();
			remocao.setBackground(Constantes.BackgroundColor);
			remocao.setBorder(Constantes.makeTitledBorder("Acervo a ser removido"));
			remocao.setForeground(Constantes.ContentColor);
			remocao.setPreferredSize(new Dimension(SMALL_SIZE.width + BUTTON_SIZE.width + 2 * Constantes.GAP + 10, (SMALL_SIZE.height + Constantes.GAP) * 2 + 33));
			remocao.add(selecionado);
			remocao.add(getBotaoRemover());
		}
		return remocao;
	}

	private JButton getBotaoRemover() {
		if (botao_remover == null) {
			botao_remover = new JButton();
			botao_remover.setPreferredSize(BUTTON_SIZE);
			botao_remover.setIcon(new ImageIcon(getClass().getResource("/icons/delete.png")));
			botao_remover.setBackground(Constantes.BackgroundColor);
			botao_remover.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent ae) {
					try {
						switch (grupo) {
						case 1:
							if (Sistema.BiblioteCAASO.removeCD(selecionado.getText())) {
								selecionado.setText("Removido!");
							} else {
								selecionado.setText("Acervo não existe!");
							}
							break;

						case 2:
							if (Sistema.BiblioteCAASO.removeVIDEO(selecionado.getText())) {
								selecionado.setText("Removido!");
							} else {
								selecionado.setText("Acervo não existe!");
							}
							break;

						case 3:
							if (Sistema.BiblioteCAASO.removeLIVRO(selecionado.getText())) {
								selecionado.setText("Removido!");
							} else {
								selecionado.setText("Acervo não existe!");
							}
							break;

						case 4:
							if (Sistema.BiblioteCAASO.removeJOGO(selecionado.getText())) {
								selecionado.setText("Removido!");
							} else {
								selecionado.setText("Acervo não existe!");
							}
							break;
						}
					} catch (IOException erro) {
						System.err.println("[ERRO] Houve um erro removendo o Acervo.");
						erro.printStackTrace();
					}
				}
			});
		}
		return botao_remover;
	}

	public void unSelectAllBut(JList<?> butthis) {
		if (secondtime) {
			secondtime = !secondtime;
			return;
		} else {
			secondtime = !secondtime;
		}
		if (butthis != CDS)
			CDS.clearSelection();
		if (butthis != VIDEOS)
			VIDEOS.clearSelection();
		if (butthis != LIVROS)
			LIVROS.clearSelection();
		if (butthis != JOGOS)
			JOGOS.clearSelection();
	}
}
