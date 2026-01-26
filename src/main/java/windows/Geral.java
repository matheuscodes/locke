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
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import core.Sistema;

public class Geral extends JFrame {

	private static final Dimension SMALL_BUTTON_SIZE = new Dimension(110, 32);
	private static final Dimension NORMAL_BUTTON_SIZE = new Dimension((SMALL_BUTTON_SIZE.width) * 2 + Constantes.GAP, 32);

	private static final long serialVersionUID = 1L;

	private JPanel fundo = null;
	private JPanel menu_lateral = null;

	private JButton abre_CD = null;
	private JButton abre_VIDEO = null;
	private JButton abre_LIVRO = null;
	private JButton abre_JOGO = null;
	private JButton abre_Usuarios = null;
	private JButton abre_Configuracao = null;
	private JButton abre_Locacoes = null;
	private JButton abre_Movimentacoes = null;

	private JPanel ativo = null;

	private Acervos CDS = null;
	private Acervos VIDEOS = null;
	private Acervos JOGOS = null;
	private Acervos LIVROS = null;
	private VisualUsuarioGroup USUARIOS = null;
	private Configuracoes CONFIGURACOES = null;
	private VisualLocacoes LOCACOES = null;
	private VisualBanco MOVIMENTACOES = null;

	private JLabel logo = null;

	public Geral() {
		super();
		inicializar();
	}

	private void inicializar() {
		this.setSize(1024, 768);
		this.setExtendedState(MAXIMIZED_BOTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getFundo());
		this.setTitle("Locke - Controle de Locadoras");
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				try {
					Sistema.BiblioteCAASO.filerize();
				} catch (IOException e) {
					System.err.println("[ERRO] Problemas ao salvar as alterações no Disco.\n Informação foi perdida no fechamento do programa.");
					e.printStackTrace();
				}
				System.out.println("Fechando...");
			}
		});
	}

	private JPanel getFundo() {
		if (fundo == null) {
			fundo = new JPanel();
			fundo.setLayout(new BorderLayout());
			fundo.setBackground(Constantes.BackgroundColor);
			fundo.add(getMenuLateral(), BorderLayout.WEST);
			fundo.add(getAtivo(), BorderLayout.CENTER);
		}
		return fundo;
	}

	private JPanel getMenuLateral() {
		if (menu_lateral == null) {
			logo = new JLabel();
			logo.setText("2005 © Matheus");
			logo.setIcon(new ImageIcon(getClass().getResource("/logo.png")));
			logo.setForeground(Color.white);

			JLabel copyright = new JLabel("Icon Design by www.dryicons.com");
			copyright.setForeground(Color.white);

			menu_lateral = new JPanel();
			menu_lateral.setPreferredSize(new Dimension(NORMAL_BUTTON_SIZE.width + 2 * Constantes.GAP, 10));
			menu_lateral.setBackground(Constantes.BackgroundColor);

			menu_lateral.add(getAbreCD());
			menu_lateral.add(getAbreVIDEO());
			menu_lateral.add(getAbreLIVRO());
			menu_lateral.add(getAbreJOGO());
			menu_lateral.add(getAbreUsuarios());
			menu_lateral.add(getAbreConfiguracao());
			menu_lateral.add(getAbreLocacoes());
			menu_lateral.add(getAbreMovimentacoes());
			menu_lateral.add(logo);
			menu_lateral.add(copyright);
		}
		return menu_lateral;
	}

	private JButton getAbreCD() {
		if (abre_CD == null) {
			abre_CD = new JButton();
			abre_CD.setText("CDs");
			abre_CD.setPreferredSize(SMALL_BUTTON_SIZE);
			abre_CD.setIcon(new ImageIcon(getClass().getResource("/icons/folder_open.png")));
			abre_CD.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAllInvisible();
					getAtivo().removeAll();
					getAtivo().add(getCDS(), BorderLayout.CENTER);
					getCDS().setVisivel(true);
				}
			});
		}
		return abre_CD;
	}

	private JButton getAbreVIDEO() {
		if (abre_VIDEO == null) {
			abre_VIDEO = new JButton();
			abre_VIDEO.setText("Vídeos");
			abre_VIDEO.setPreferredSize(SMALL_BUTTON_SIZE);
			abre_VIDEO.setIcon(new ImageIcon(getClass().getResource("/icons/folder_open.png")));
			abre_VIDEO.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAllInvisible();
					getAtivo().removeAll();
					getAtivo().add(getVIDEOS(), BorderLayout.CENTER);
					getVIDEOS().setVisivel(true);
				}
			});
		}
		return abre_VIDEO;
	}

	private JButton getAbreLIVRO() {
		if (abre_LIVRO == null) {
			abre_LIVRO = new JButton();
			abre_LIVRO.setText("Livros");
			abre_LIVRO.setPreferredSize(SMALL_BUTTON_SIZE);
			abre_LIVRO.setIcon(new ImageIcon(getClass().getResource("/icons/folder_open.png")));
			abre_LIVRO.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAllInvisible();
					getAtivo().removeAll();
					getAtivo().add(getLIVROS(), BorderLayout.CENTER);
					getLIVROS().setVisivel(true);
				}
			});
		}
		return abre_LIVRO;
	}

	private JButton getAbreJOGO() {
		if (abre_JOGO == null) {
			abre_JOGO = new JButton();
			abre_JOGO.setText("Jogos");
			abre_JOGO.setPreferredSize(SMALL_BUTTON_SIZE);
			abre_JOGO.setIcon(new ImageIcon(getClass().getResource("/icons/folder_open.png")));
			abre_JOGO.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAllInvisible();
					getAtivo().removeAll();
					getAtivo().add(getJOGOS(), BorderLayout.CENTER);
					getJOGOS().setVisivel(true);
				}
			});
		}
		return abre_JOGO;
	}

	private JPanel getAtivo() {
		if (ativo == null) {
			ativo = new JPanel();
			ativo.setLayout(new BorderLayout());
			ativo.setBackground(Constantes.BackgroundColor);
		}
		return ativo;
	}

	private Acervos getCDS() {
		if (CDS == null) {
			CDS = new Acervos(Sistema.BiblioteCAASO.getCDSModel(), "CD", "Código", "Álbum", "Artista");
			CDS.setVisible(false);
		}
		return CDS;
	}

	private Acervos getVIDEOS() {
		if (VIDEOS == null) {
			VIDEOS = new Acervos(Sistema.BiblioteCAASO.getVIDEOSModel(), "Vídeo", "Código", "Título", "Original");
			VIDEOS.setVisible(false);
		}
		return VIDEOS;
	}

	private Acervos getLIVROS() {
		if (LIVROS == null) {
			LIVROS = new Acervos(Sistema.BiblioteCAASO.getLIVROSModel(), "Livro", "Autor", "Título", "Assunto");
			LIVROS.setVisible(false);
		}
		return LIVROS;
	}

	private Acervos getJOGOS() {
		if (JOGOS == null) {
			JOGOS = new Acervos(Sistema.BiblioteCAASO.getJOGOSModel(), "Jogo", "Código", "Título", "Classificação");
			JOGOS.setVisible(false);
		}
		return JOGOS;
	}

	private void setAllInvisible() {
		getCDS().setVisible(false);
		getVIDEOS().setVisible(false);
		getLIVROS().setVisible(false);
		getJOGOS().setVisible(false);
		getUSUARIOS().setVisible(false);
		getCONFIGURACOES().setVisible(false);
		getLOCACOES().setVisible(false);
	}

	private JButton getAbreUsuarios() {
		if (abre_Usuarios == null) {
			abre_Usuarios = new JButton();
			abre_Usuarios.setText("Usuarios");
			abre_Usuarios.setPreferredSize(NORMAL_BUTTON_SIZE);
			abre_Usuarios.setIcon(new ImageIcon(getClass().getResource("/icons/user.png")));
			abre_Usuarios.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAllInvisible();
					getAtivo().removeAll();
					getAtivo().add(getUSUARIOS(), BorderLayout.CENTER);
					getUSUARIOS().setVisible(true);
				}
			});
		}
		return abre_Usuarios;
	}

	private VisualUsuarioGroup getUSUARIOS() {
		if (USUARIOS == null) {
			USUARIOS = new VisualUsuarioGroup();
			USUARIOS.setVisible(false);
		}
		return USUARIOS;
	}

	private JButton getAbreConfiguracao() {
		if (abre_Configuracao == null) {
			abre_Configuracao = new JButton();
			abre_Configuracao.setText("Configurações");
			abre_Configuracao.setPreferredSize(NORMAL_BUTTON_SIZE);
			abre_Configuracao.setIcon(new ImageIcon(getClass().getResource("/icons/tools.png")));
			abre_Configuracao.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAllInvisible();
					getAtivo().removeAll();
					getAtivo().add(getCONFIGURACOES(), BorderLayout.CENTER);
					getCONFIGURACOES().setVisible(true);
				}
			});
		}
		return abre_Configuracao;
	}

	private Configuracoes getCONFIGURACOES() {
		if (CONFIGURACOES == null) {
			CONFIGURACOES = new Configuracoes();
			CONFIGURACOES.setVisible(false);
		}
		return CONFIGURACOES;
	}

	private JButton getAbreLocacoes() {
		if (abre_Locacoes == null) {
			abre_Locacoes = new JButton();
			abre_Locacoes.setText("Locações");
			abre_Locacoes.setPreferredSize(NORMAL_BUTTON_SIZE);
			abre_Locacoes.setIcon(new ImageIcon(getClass().getResource("/icons/page_full.png")));
			abre_Locacoes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAllInvisible();
					getAtivo().removeAll();
					getAtivo().add(getLOCACOES(), BorderLayout.CENTER);
					getLOCACOES().setVisible(true);
				}
			});
		}
		return abre_Locacoes;
	}

	private VisualLocacoes getLOCACOES() {
		if (LOCACOES == null) {
			LOCACOES = new VisualLocacoes();
			LOCACOES.setVisible(false);
		}
		return LOCACOES;
	}

	private JButton getAbreMovimentacoes() {
		if (abre_Movimentacoes == null) {
			abre_Movimentacoes = new JButton();
			abre_Movimentacoes.setText("Movimentações");
			abre_Movimentacoes.setPreferredSize(NORMAL_BUTTON_SIZE);
			abre_Movimentacoes.setIcon(new ImageIcon(getClass().getResource("/icons/dollar.png")));
			abre_Movimentacoes.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					setAllInvisible();
					getAtivo().removeAll();
					getAtivo().add(getMOVIMENTACOES(), BorderLayout.CENTER);
					getMOVIMENTACOES().setVisivel(true);
				}
			});
		}
		return abre_Movimentacoes;
	}

	private VisualBanco getMOVIMENTACOES() {
		if (MOVIMENTACOES == null) {
			MOVIMENTACOES = new VisualBanco();
			MOVIMENTACOES.setVisible(false);
		}
		return MOVIMENTACOES;
	}
}
