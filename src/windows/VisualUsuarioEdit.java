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

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import core.Sistema;
import database.Usuario;
import database.lists.Group;

public class VisualUsuarioEdit extends JPanel {

	private static final Dimension SIX_SIZE = new Dimension(475,50);
	private static final Dimension ONE_SIZE = new Dimension(75,50);
	private static final Dimension TWO_SIZE = new Dimension(155,50);
	private static final Dimension THREE_SIZE = new Dimension(235,50);
	private static final Dimension FOUR_SIZE = new Dimension(315,50);
	private static final Dimension FIVE_SIZE = new Dimension(395,50);
	
	private static final Dimension ENDERECO_SANCA_SIZE = new Dimension(500,143);
	private static final Dimension ENDERECO_SIZE = new Dimension(500,143);
	private static final Dimension CONTATO_SIZE = new Dimension(500,143);
	private static final Dimension PESSOAL_SIZE = new Dimension(500,193);
	private static final Dimension HISTORICO_SIZE = new Dimension(500,193);
	private static final Dimension PERMISSOES_SIZE = new Dimension(500,143);
	private static final Dimension LISTA_SIZE = new Dimension(210,100);
	private static final Dimension BOTAO_SIZE = new Dimension(50,25);
	
	private static final long serialVersionUID = 1L;
	
	private boolean editable = false;
	
	private JCheckBox CAASO = null;
	private JLabel caaso = null;
	private JCheckBox ALOJAMENTO = null;
	private JLabel alojamento = null;
	
	private JPanel pessoal = null;
	private JPanel pessoal_nome = null;
	private JPanel pessoal_aniversario = null;
	private JPanel pessoal_curso = null;
	private JPanel pessoal_ano_ingresso = null;
	private JPanel pessoal_marquinhas;
	private JTextField nome = null;
	private JTextField aniversario = null;
	private JTextField curso = null;
	private JTextField ano_ingresso = null;
	private JTextField marquinhas;
	
	private JPanel enderecoSC = null;
	private JPanel enderecoSC_rua = null;
	private JPanel enderecoSC_numero = null;
	private JPanel enderecoSC_complemento = null;
	private JPanel enderecoSC_bairro = null;
	private JTextField ruaSC = null;
	private JTextField complementoSC = null;
	private JTextField numeroSC = null;
	private JTextField bairroSC = null;
	
	private JPanel endereco = null;
	private JPanel endereco_rua = null;
	private JPanel endereco_numero = null;
	private JPanel endereco_complemento = null;
	private JPanel endereco_bairro = null;
	private JPanel endereco_cidade = null;
	private JTextField rua = null;
	private JTextField numero = null;
	private JTextField complemento = null;
	private JTextField bairro = null;
	private JTextField cidade = null;
	
	private JPanel contato = null;
	private JPanel contato_email = null;
	private JPanel contato_telefoneSC = null;
	private JPanel contato_telefone = null;
	private JPanel contato_celular = null;
	private JTextField email = null;
	private JTextField telefoneSC = null;
	private JTextField telefone = null;
	private JTextField celular = null;
	
	private JPanel permissoes = null;
	private JScrollPane scrollable_todos = null;
	private JScrollPane scrollable_autorizados = null;
	private JButton botao_adiciona = null;
	private JButton botao_remove = null;
	private JList<Group> lista_todos = null;
	private JList<Group> lista_autorizados = null;
	private DefaultListModel<Group> autorizados = null;
	
	private JTextPane passado;
	private JPanel historico;
	
	
	public VisualUsuarioEdit(boolean editable) {
		super();
		this.editable = editable;
		initialize();
	}

	private void initialize() {
		this.setBorder(Constantes.makeTitledBorder("Cadastro de Usuários"));
		this.setForeground(Constantes.DefaultColor);
		this.setBackground(Constantes.BackgroundColor);
		this.add(getPessoal());
		this.add(getContato());
		this.add(getEnderecoSC());
		this.add(getEndereco());
		if(editable){
			this.add(getPermissoes());
		}
		else{
			this.add(getHistorico());
		}
	}

	private JPanel getPessoal() {
		if (pessoal == null) {
			alojamento = new JLabel();
			alojamento.setBackground(Constantes.BackgroundColor);
			alojamento.setForeground(Constantes.DefaultColor);
			alojamento.setText("Alojamento");
			caaso = new JLabel();
			caaso.setBackground(Constantes.BackgroundColor);
			caaso.setForeground(Constantes.DefaultColor);
			caaso.setText("Sócio CAASO");
			
			pessoal = new JPanel();
			((FlowLayout)pessoal.getLayout()).setAlignment(FlowLayout.LEFT);
			pessoal.setPreferredSize(PESSOAL_SIZE);
			pessoal.setForeground(Constantes.DefaultColor);
			pessoal.setBorder(Constantes.makeTitledBorder("Informações Pessoais"));
			pessoal.setBackground(Constantes.BackgroundColor);
			pessoal.add(getPessoalNome());
			pessoal.add(getPessoalAniversario());
			pessoal.add(getPessoalAnoIngresso());
			pessoal.add(getALOJAMENTO());
			pessoal.add(alojamento);
			pessoal.add(getPessoalCurso());
			if(!editable) {
				pessoal.add(getPessoalMarquinhas());
			}
			pessoal.add(getCAASO());
			pessoal.add(caaso);
			
		}
		return pessoal;
	}

	private JPanel getContato() {
		if (contato == null) {
			contato = new JPanel();
			contato.setPreferredSize(CONTATO_SIZE);
			contato.setForeground(Constantes.DefaultColor);
			contato.setBorder(Constantes.makeTitledBorder("Contato"));
			contato.setBackground(Constantes.BackgroundColor);
			((FlowLayout)contato.getLayout()).setAlignment(FlowLayout.LEFT);
			contato.add(getContatoEmail());
			contato.add(getContatoTelefoneSC());
			contato.add(getContatoTelefone());
			contato.add(getContatoCelular());
		}
		return contato;
	}

	private JPanel getEnderecoSC() {
		if (enderecoSC == null) {
			enderecoSC = new JPanel();
			enderecoSC.setPreferredSize(ENDERECO_SANCA_SIZE);
			enderecoSC.setBorder(Constantes.makeTitledBorder("Endereço em São Carlos"));
			enderecoSC.setBackground(Constantes.BackgroundColor);
			((FlowLayout)enderecoSC.getLayout()).setAlignment(FlowLayout.LEFT);
			enderecoSC.add(getEnderecoSCRua());
			enderecoSC.add(getEnderecoSCNumero());
			enderecoSC.add(getEnderecoSCComplemento());
			enderecoSC.add(getEnderecoSCBairro());
		}
		return enderecoSC;
	}

	private JPanel getEndereco() {
		if (endereco == null) {
			endereco = new JPanel();
			endereco.setPreferredSize(ENDERECO_SIZE);
			endereco.setForeground(Constantes.DefaultColor);
			endereco.setBorder(Constantes.makeTitledBorder("Endereço de Fora"));
			endereco.setBackground(Constantes.BackgroundColor);
			((FlowLayout)endereco.getLayout()).setAlignment(FlowLayout.LEFT);
			endereco.add(getEnderecoRua());
			endereco.add(getEnderecoNumero());
			endereco.add(getEnderecoComplemento());
			endereco.add(getEnderecoBairro());
			endereco.add(getEnderecoCidade());
		}
		return endereco;
	}

	private JPanel getPessoalNome() {
		if (pessoal_nome == null) {
			pessoal_nome = new JPanel();
			pessoal_nome.setPreferredSize(SIX_SIZE);
			pessoal_nome.setLayout(new BorderLayout());
			pessoal_nome.setForeground(Constantes.DefaultColor);
			pessoal_nome.setBorder(Constantes.makeTitledBorder("Nome Completo"));
			pessoal_nome.setBackground(Constantes.BackgroundColor);
			pessoal_nome.add(getNome());
		}
		return pessoal_nome;
	}

	private JTextField getNome() {
		if (nome == null) {
			nome = new JTextField();
			if(!editable) {
				nome.setBackground(Constantes.BackgroundColor);
				nome.setForeground(Constantes.ContentColor);
				nome.setEditable(false);
			}
		}
		return nome;
	}

	private JPanel getPessoalAniversario() {
		if (pessoal_aniversario == null) {
			pessoal_aniversario = new JPanel();
			pessoal_aniversario.setPreferredSize(TWO_SIZE);
			pessoal_aniversario.setLayout(new BorderLayout());
			pessoal_aniversario.setForeground(Constantes.DefaultColor);
			pessoal_aniversario.setBorder(Constantes.makeTitledBorder("Data de Nascimento"));
			pessoal_aniversario.setBackground(Constantes.BackgroundColor);
			pessoal_aniversario.add(getAniversario());
		}
		return pessoal_aniversario;
	}

	private JTextField getAniversario() {
		if (aniversario == null) {
			aniversario = new JTextField();
			if(!editable) {
				aniversario.setBackground(Constantes.BackgroundColor);
				aniversario.setForeground(Constantes.ContentColor);
				aniversario.setEditable(false);
			}
		}
		return aniversario;
	}

	private JPanel getPessoalCurso() {
		if (pessoal_curso == null) {
			pessoal_curso = new JPanel();
			if(!editable){
				pessoal_curso.setPreferredSize(THREE_SIZE);
			}
			else {
				pessoal_curso.setPreferredSize(FOUR_SIZE);
			}
			pessoal_curso.setLayout(new BorderLayout());
			pessoal_curso.setForeground(Constantes.DefaultColor);
			pessoal_curso.setBorder(Constantes.makeTitledBorder("Curso"));
			pessoal_curso.setBackground(Constantes.BackgroundColor);
			pessoal_curso.add(getCurso());
		}
		return pessoal_curso;
	}

	private JTextField getCurso() {
		if (curso == null) {
			curso = new JTextField();
			if(!editable) {
				curso.setBackground(Constantes.BackgroundColor);
				curso.setForeground(Constantes.ContentColor);
				curso.setEditable(false);
			}
		}
		return curso;
	}

	private JPanel getPessoalAnoIngresso() {
		if (pessoal_ano_ingresso == null) {
			pessoal_ano_ingresso = new JPanel();
			pessoal_ano_ingresso.setPreferredSize(TWO_SIZE);
			pessoal_ano_ingresso.setLayout(new BorderLayout());
			pessoal_ano_ingresso.setForeground(Constantes.DefaultColor);
			pessoal_ano_ingresso.setBorder(Constantes.makeTitledBorder("Ano de Ingresso"));
			pessoal_ano_ingresso.setBackground(Constantes.BackgroundColor);
			pessoal_ano_ingresso.add(getAnoIngresso());
		}
		return pessoal_ano_ingresso;
	}

	private JTextField getAnoIngresso() {
		if (ano_ingresso == null) {
			ano_ingresso = new JTextField();
			if(!editable) {
				ano_ingresso.setBackground(Constantes.BackgroundColor);
				ano_ingresso.setForeground(Constantes.ContentColor);
				ano_ingresso.setEditable(false);
			}
		}
		return ano_ingresso;
	}
	
	private JPanel getPessoalMarquinhas() {
		if (pessoal_marquinhas == null) {
			pessoal_marquinhas = new JPanel();
			pessoal_marquinhas.setPreferredSize(ONE_SIZE);
			pessoal_marquinhas.setLayout(new BorderLayout());
			pessoal_marquinhas.setForeground(Constantes.DefaultColor);
			pessoal_marquinhas.setBorder(Constantes.makeTitledBorder("Marcas"));
			pessoal_marquinhas.setBackground(Constantes.BackgroundColor);
			pessoal_marquinhas.add(getMarquinhas());
		}
		return pessoal_marquinhas;
	}

	private JTextField getMarquinhas() {
		if (marquinhas == null) {
			marquinhas = new JTextField();
			if(!editable) {
				marquinhas.setBackground(Constantes.BackgroundColor);
				marquinhas.setForeground(Constantes.ContentColor);
				marquinhas.setEditable(false);
			}
		}
		return marquinhas;
	}

	private JCheckBox getCAASO() {
		if (CAASO == null) {
			CAASO = new JCheckBox();
			CAASO.setBackground(Constantes.BackgroundColor);
			if(!editable) {
				CAASO.setEnabled(false);
			}
		}
		return CAASO;
	}

	private JCheckBox getALOJAMENTO() {
		if (ALOJAMENTO == null) {
			ALOJAMENTO = new JCheckBox();
			ALOJAMENTO.setBackground(Constantes.BackgroundColor);
			if(!editable) {
				ALOJAMENTO.setEnabled(false);
			}
		}
		return ALOJAMENTO;
	}

	private JPanel getEnderecoSCRua() {
		if (enderecoSC_rua == null) {
			enderecoSC_rua = new JPanel();
			enderecoSC_rua.setPreferredSize(FIVE_SIZE);
			enderecoSC_rua.setLayout(new BorderLayout());
			enderecoSC_rua.setBackground(Constantes.BackgroundColor);
			enderecoSC_rua.setBorder(Constantes.makeTitledBorder("Rua, Travessa, Avenida"));
			enderecoSC_rua.add(getRuaSC());
		}
		return enderecoSC_rua;
	}

	private JTextField getRuaSC() {
		if (ruaSC == null) {
			ruaSC = new JTextField();
			if(!editable) {
				ruaSC.setBackground(Constantes.BackgroundColor);
				ruaSC.setForeground(Constantes.ContentColor);
				ruaSC.setEditable(false);
			}
		}
		return ruaSC;
	}

	private JPanel getEnderecoSCNumero() {
		if (enderecoSC_numero == null) {
			enderecoSC_numero = new JPanel();
			enderecoSC_numero.setPreferredSize(ONE_SIZE);
			enderecoSC_numero.setLayout(new BorderLayout());
			enderecoSC_numero.setBackground(Constantes.BackgroundColor);
			enderecoSC_numero.setBorder(Constantes.makeTitledBorder("Número"));
			enderecoSC_numero.add(getNumeroSC());
		}
		return enderecoSC_numero;
	}

	private JTextField getNumeroSC() {
		if (numeroSC == null) {
			numeroSC = new JTextField();
			if(!editable) {
				numeroSC.setBackground(Constantes.BackgroundColor);
				numeroSC.setForeground(Constantes.ContentColor);
				numeroSC.setEditable(false);
			}
		}
		return numeroSC;
	}

	private JPanel getEnderecoSCComplemento() {
		if (enderecoSC_complemento == null) {
			enderecoSC_complemento = new JPanel();
			enderecoSC_complemento.setPreferredSize(THREE_SIZE);
			enderecoSC_complemento.setLayout(new BorderLayout());
			enderecoSC_complemento.setBackground(Constantes.BackgroundColor);
			enderecoSC_complemento.setBorder(Constantes.makeTitledBorder("Complemento"));
			enderecoSC_complemento.add(getComplementoSC());
		}
		return enderecoSC_complemento;
	}

	private JTextField getComplementoSC() {
		if (complementoSC == null) {
			complementoSC = new JTextField();
			if(!editable) {
				complementoSC.setBackground(Constantes.BackgroundColor);
				complementoSC.setForeground(Constantes.ContentColor);
				complementoSC.setEditable(false);
			}
		}
		return complementoSC;
	}

	private JPanel getEnderecoSCBairro() {
		if (enderecoSC_bairro == null) {
			enderecoSC_bairro = new JPanel();
			enderecoSC_bairro.setPreferredSize(THREE_SIZE);
			enderecoSC_bairro.setLayout(new BorderLayout());
			enderecoSC_bairro.setBorder(Constantes.makeTitledBorder("Bairro"));
			enderecoSC_bairro.setBackground(Constantes.BackgroundColor);
			enderecoSC_bairro.add(getBairroSC());
		}
		return enderecoSC_bairro;
	}

	private JTextField getBairroSC() {
		if (bairroSC == null) {
			bairroSC = new JTextField();
			if(!editable) {
				bairroSC.setBackground(Constantes.BackgroundColor);
				bairroSC.setForeground(Constantes.ContentColor);
				bairroSC.setEditable(false);
			}
		}
		return bairroSC;
	}

	private JPanel getEnderecoRua() {
		if (endereco_rua == null) {
			endereco_rua = new JPanel();
			endereco_rua.setPreferredSize(FIVE_SIZE);
			endereco_rua.setLayout(new BorderLayout());
			endereco_rua.setBackground(Constantes.BackgroundColor);
			endereco_rua.setBorder(Constantes.makeTitledBorder("Rua, Travessa, Avenida"));
			endereco_rua.setForeground(Constantes.DefaultColor);
			endereco_rua.add(getRua());
		}
		return endereco_rua;
	}

	private JTextField getRua() {
		if (rua == null) {
			rua = new JTextField();
			if(!editable) {
				rua.setBackground(Constantes.BackgroundColor);
				rua.setForeground(Constantes.ContentColor);
				rua.setEditable(false);
			}
		}
		return rua;
	}

	private JPanel getEnderecoNumero() {
		if (endereco_numero == null) {
			endereco_numero = new JPanel();
			endereco_numero.setPreferredSize(ONE_SIZE);
			endereco_numero.setLayout(new BorderLayout());
			endereco_numero.setBackground(Constantes.BackgroundColor);
			endereco_numero.setBorder(Constantes.makeTitledBorder("Número"));
			endereco_numero.setForeground(Constantes.DefaultColor);
			endereco_numero.add(getNumero());
		}
		return endereco_numero;
	}

	private JTextField getNumero() {
		if (numero == null) {
			numero = new JTextField();
			if(!editable) {
				numero.setBackground(Constantes.BackgroundColor);
				numero.setForeground(Constantes.ContentColor);
				numero.setEditable(false);
			}
		}
		return numero;
	}

	private JPanel getEnderecoComplemento() {
		if (endereco_complemento == null) {
			endereco_complemento = new JPanel();
			endereco_complemento.setPreferredSize(TWO_SIZE);
			endereco_complemento.setLayout(new BorderLayout());
			endereco_complemento.setBackground(Constantes.BackgroundColor);
			endereco_complemento.setBorder(Constantes.makeTitledBorder("Complemento"));
			endereco_complemento.setForeground(Constantes.DefaultColor);
			endereco_complemento.add(getComplemento());
		}
		return endereco_complemento;
	}

	private JTextField getComplemento() {
		if (complemento == null) {
			complemento = new JTextField();
			if(!editable) {
				complemento.setBackground(Constantes.BackgroundColor);
				complemento.setForeground(Constantes.ContentColor);
				complemento.setEditable(false);
			}
		}
		return complemento;
	}

	private JPanel getEnderecoBairro() {
		if (endereco_bairro == null) {
			endereco_bairro = new JPanel();
			endereco_bairro.setPreferredSize(TWO_SIZE);
			endereco_bairro.setLayout(new BorderLayout());
			endereco_bairro.setBorder(Constantes.makeTitledBorder("Bairro"));
			endereco_bairro.setBackground(Constantes.BackgroundColor);
			endereco_bairro.add(getBairro());
		}
		return endereco_bairro;
	}

	private JTextField getBairro() {
		if (bairro == null) {
			bairro = new JTextField();
			if(!editable) {
				bairro.setBackground(Constantes.BackgroundColor);
				bairro.setForeground(Constantes.ContentColor);
				bairro.setEditable(false);
			}
		}
		return bairro;
	}

	private JPanel getEnderecoCidade() {
		if (endereco_cidade == null) {
			endereco_cidade = new JPanel();
			endereco_cidade.setPreferredSize(TWO_SIZE);
			endereco_cidade.setLayout(new BorderLayout());
			endereco_cidade.setBorder(Constantes.makeTitledBorder("Cidade"));
			endereco_cidade.setBackground(Constantes.BackgroundColor);
			endereco_cidade.add(getCidade());
		}
		return endereco_cidade;
	}

	private JTextField getCidade() {
		if (cidade == null) {
			cidade = new JTextField();
			if(!editable) {
				cidade.setBackground(Constantes.BackgroundColor);
				cidade.setForeground(Constantes.ContentColor);
				cidade.setEditable(false);
			}
		}
		return cidade;
	}

	private JPanel getContatoEmail() {
		if (contato_email == null) {
			contato_email = new JPanel();
			contato_email.setPreferredSize(SIX_SIZE);
			contato_email.setLayout(new BorderLayout());
			contato_email.setBorder(Constantes.makeTitledBorder("E-mail"));
			contato_email.setBackground(Constantes.BackgroundColor);
			contato_email.add(getEmail());
		}
		return contato_email;
	}

	private JTextField getEmail() {
		if (email == null) {
			email = new JTextField();
			if(!editable) {
				email.setBackground(Constantes.BackgroundColor);
				email.setForeground(Constantes.ContentColor);
				email.setEditable(false);
			}
		}
		return email;
	}

	private JPanel getContatoTelefoneSC() {
		if (contato_telefoneSC == null) {
			contato_telefoneSC = new JPanel();
			contato_telefoneSC.setPreferredSize(TWO_SIZE);
			contato_telefoneSC.setLayout(new BorderLayout());
			contato_telefoneSC.setBorder(Constantes.makeTitledBorder("Telefone em São Carlos"));
			contato_telefoneSC.setBackground(Constantes.BackgroundColor);
			contato_telefoneSC.add(getTelefoneSC());
		}
		return contato_telefoneSC;
	}

	private JTextField getTelefoneSC() {
		if (telefoneSC == null) {
			telefoneSC = new JTextField();
			if(!editable) {
				telefoneSC.setBackground(Constantes.BackgroundColor);
				telefoneSC.setForeground(Constantes.ContentColor);
				telefoneSC.setEditable(false);
			}
		}
		return telefoneSC;
	}

	private JPanel getContatoTelefone() {
		if (contato_telefone == null) {
			contato_telefone = new JPanel();
			contato_telefone.setPreferredSize(TWO_SIZE);
			contato_telefone.setLayout(new BorderLayout());
			contato_telefone.setBorder(Constantes.makeTitledBorder("Telefone de Fora"));
			contato_telefone.setBackground(Constantes.BackgroundColor);
			contato_telefone.add(getTelefone());
		}
		return contato_telefone;
	}

	private JTextField getTelefone() {
		if (telefone == null) {
			telefone = new JTextField();
			if(!editable) {
				telefone.setBackground(Constantes.BackgroundColor);
				telefone.setForeground(Constantes.ContentColor);
				telefone.setEditable(false);
			}
		}
		return telefone;
	}

	private JPanel getContatoCelular() {
		if (contato_celular == null) {
			contato_celular = new JPanel();
			contato_celular.setPreferredSize(TWO_SIZE);
			contato_celular.setLayout(new BorderLayout());
			contato_celular.setBorder(Constantes.makeTitledBorder("Celular"));
			contato_celular.setBackground(Constantes.BackgroundColor);
			contato_celular.add(getCelular());
		}
		return contato_celular;
	}

	private JTextField getCelular() {
		if (celular == null) {
			celular = new JTextField();
			if(!editable) {
				celular.setBackground(Constantes.BackgroundColor);
				celular.setForeground(Constantes.ContentColor);
				celular.setEditable(false);
			}
		}
		return celular;
	}

	private JPanel getPermissoes() {
		if (permissoes == null) {
			permissoes = new JPanel();
			permissoes.setPreferredSize(PERMISSOES_SIZE);
			permissoes.setLayout(new BorderLayout());
			permissoes.setBorder(Constantes.makeTitledBorder("Acervos Permitidos"));
			permissoes.setBackground(Constantes.BackgroundColor);
			permissoes.add(getScrollableTodos(),BorderLayout.WEST);
			permissoes.add(getScrollableAutorizados(),BorderLayout.EAST);
			JPanel helper = new JPanel();
			helper.setPreferredSize(BOTAO_SIZE);
			helper.setBackground(Constantes.BackgroundColor);
			helper.add(getBotaoAdiciona());
			helper.add(getBotaoRemove());
			permissoes.add(helper,BorderLayout.CENTER);
		}
		return permissoes;
	}

	private JScrollPane getScrollableTodos() {
		if (scrollable_todos == null) {
			scrollable_todos = new JScrollPane();
			scrollable_todos.setPreferredSize(LISTA_SIZE);
			scrollable_todos.setViewportView(getListaTodos());
		}
		return scrollable_todos;
	}

	private JScrollPane getScrollableAutorizados() {
		if (scrollable_autorizados == null) {
			scrollable_autorizados = new JScrollPane();
			scrollable_autorizados.setPreferredSize(LISTA_SIZE);
			scrollable_autorizados.setViewportView(getListaAutorizados());
		}
		return scrollable_autorizados;
	}

	private JButton getBotaoAdiciona() {
		if (botao_adiciona == null) {
			botao_adiciona = new JButton();
			botao_adiciona.setPreferredSize(BOTAO_SIZE);
			botao_adiciona.setText(">>");
			botao_adiciona.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					int i = lista_todos.getSelectedIndex();
					if(i != -1) getAutorizados().addElement(Sistema.BiblioteCAASO.getTodosAcervosModel().getElementAt(i));
				}
			});
		}
		return botao_adiciona;
	}

	private JButton getBotaoRemove() {
		if (botao_remove == null) {
			botao_remove = new JButton();
			botao_remove.setPreferredSize(BOTAO_SIZE);
			botao_remove.setText("<<");
			botao_remove.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) 
				{
					int i = getListaAutorizados().getSelectedIndex();
					if(i != -1) getAutorizados().removeElementAt(i);
				}
			});
		}
		return botao_remove;
	}

	private JList<Group> getListaTodos() {
		if (lista_todos == null) {
			lista_todos = new JList<Group>();
			lista_todos.setModel(Sistema.BiblioteCAASO.getTodosAcervosModel());
		}
		return lista_todos;
	}

	private JList<Group> getListaAutorizados() {
		if (lista_autorizados == null) {
			lista_autorizados = new JList<Group>();
			lista_autorizados.setModel(getAutorizados());
		}
		return lista_autorizados;
	}

	private DefaultListModel<Group> getAutorizados() {
		if(autorizados == null) {
			autorizados = new DefaultListModel<Group>();
		}
		return autorizados;
	}
	

	private JPanel getHistorico() {
		if (historico == null) {
			historico = new JPanel();
			historico.setPreferredSize(HISTORICO_SIZE);
			historico.setLayout(new BorderLayout());
			historico.setForeground(Constantes.DefaultColor);
			historico.setBorder(Constantes.makeTitledBorder("Histórico"));
			historico.setBackground(Constantes.BackgroundColor);
			historico.add(getPassado(),BorderLayout.CENTER);
		}
		return historico;
	}

	private JTextPane getPassado() {
		if (passado == null) {
			passado = new JTextPane();
			passado.setForeground(Constantes.ContentColor);
			passado.setBackground(Constantes.BackgroundColor);
		}
		return passado;
	}

	public void preencher(String[] parametros,boolean pos,boolean aloja, String passad, int marqs) {
		getNome().setText(parametros[0]);
		getCurso().setText(parametros[1]);
		getAnoIngresso().setText(parametros[2]);
		getAniversario().setText(parametros[3]);
		getRuaSC().setText(parametros[4]);
		getComplementoSC().setText(parametros[5]);
		getNumeroSC().setText(parametros[6]);
		getBairroSC().setText(parametros[7]);
		getRua().setText(parametros[8]);
		getNumero().setText(parametros[9]);
		getComplemento().setText(parametros[10]);
		getBairro().setText(parametros[11]);
		getTelefoneSC().setText(parametros[12]);
		getTelefone().setText(parametros[13]);
		getCelular().setText(parametros[14]);
		getEmail().setText(parametros[15]);
		getCidade().setText(parametros[16]);
		getCAASO().setSelected(pos);
		getALOJAMENTO().setSelected(aloja);
		getPassado().setText(passad);
		getMarquinhas().setText(""+marqs);
	}
	
	public void limpar() {
		getNome().setText("");
		getCurso().setText("");
		getAnoIngresso().setText("");
		getAniversario().setText("");
		getRuaSC().setText("");
		getComplementoSC().setText("");
		getNumeroSC().setText("");
		getBairroSC().setText("");
		getRua().setText("");
		getNumero().setText("");
		getComplemento().setText("");
		getBairro().setText("");
		getTelefoneSC().setText("");
		getTelefone().setText("");
		getCelular().setText("");
		getEmail().setText("");
		getCidade().setText("");
		getCAASO().setSelected(false);
		getALOJAMENTO().setSelected(false);
		getAutorizados().removeAllElements();
	}
	
	public Usuario cadastrar() {
		String[] auxiliar = new String[17];
		auxiliar[0] = getNome().getText();
		auxiliar[1] = getCurso().getText();
		auxiliar[2] = getAnoIngresso().getText();
		auxiliar[3] = getAniversario().getText();
		auxiliar[4] = getRuaSC().getText();
		auxiliar[5] = getComplementoSC().getText();
		auxiliar[6] = getNumeroSC().getText();
		auxiliar[7] = getBairroSC().getText();
		auxiliar[8] = getRua().getText();
		auxiliar[9] = getNumero().getText();
		auxiliar[10] = getComplemento().getText();
		auxiliar[11] = getBairro().getText();
		auxiliar[12] = getTelefoneSC().getText();
		auxiliar[13] = getTelefone().getText();
		auxiliar[14] = getCelular().getText();
		auxiliar[15] = getEmail().getText();
		auxiliar[16] = getCidade().getText();

		Usuario novo = new Usuario(auxiliar);
		String[] temporaria = new String[getAutorizados().getSize()];
		for(int i = 0; i < temporaria.length;i++) {
			temporaria[i] = getAutorizados().getElementAt(i).toString();
		}
		novo.setAtributos(getCAASO().isSelected(), getALOJAMENTO().isSelected(), temporaria, "", 0);
		return novo;	
	}

}
