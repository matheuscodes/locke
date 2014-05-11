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
package core;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.DefaultListModel;

import database.Item;
import database.Locacao;
import database.Usuario;
import database.lists.CDs;
import database.lists.Group;
import database.lists.JOGOs;
import database.lists.LIVROs;
import database.lists.Usuarios;
import database.lists.VIDEOs;

/**
 * The {@code Biblioteca} class models the library into the system. This is a
 * singleton class which holds all library information. It is instanced at the
 * {@linkplain main.Main#main(String[]) main method} The created instance is
 * made available on {@link core.Sistema#BiblioteCAASO Sistema.BiblioteCAASO}
 * All references to it will be made through that member.
 * 
 * @version 1.0
 * @author Matheus Borges Teixeira
 */
public class Biblioteca {
	/** For how many weeks CDs can be kept */
	private int semanaCD = 0;
	/** For how many weeks Films can be kept */
	private int semanaVIDEO = 0;
	/** For how many weeks Books can be kept */
	private int semanaLIVRO = 0;
	/** For how many weeks Games can be kept */
	private int semanaJOGO = 0;

	/** Next available ID for rental */
	private int nextLocacao = 0;

	/** User List */
	private Usuarios Pessoas = null;

	/** Model for all available item sources */
	private DefaultListModel<Group> TodosAcervos = null;
	/** Model for all available CD sources */
	private DefaultListModel<CDs> CDS = null;
	/** Model for all available Film sources */
	private DefaultListModel<VIDEOs> VIDEOS = null;
	/** Model for all available Book sources */
	private DefaultListModel<LIVROs> LIVROS = null;
	/** Model for all available Game sources */
	private DefaultListModel<JOGOs> JOGOS = null;

	/** Model for current open Rentals */
	private DefaultListModel<Locacao> LOCACOES = null;

	/** All available CD lists */
	private CDs[] cds = null;
	/** All Available Film lists */
	private VIDEOs[] videos = null;
	/** All Available Book lists */
	private LIVROs[] livros = null;
	/** All Available Game lists */
	private JOGOs[] jogos = null;

	/** Current open rentals */
	private Locacao[] locacoes = null;

	/** Monetary balance of the library */
	private float caixa = 0;
	/** Monetary transaction history of the library */
	private String movimentacao = null;

	/**
	 * This is the default constructor. Loads the library from the file system.
	 * 
	 * @throws IOException
	 *             whenever there are problems loading database text files.
	 * @throws NullPointerException
	 *             whenever {link core.Sistema#Load} is not defined.
	 */
	public Biblioteca() throws IOException {
		CDS = new DefaultListModel<CDs>();
		VIDEOS = new DefaultListModel<VIDEOs>();
		LIVROS = new DefaultListModel<LIVROs>();
		JOGOS = new DefaultListModel<JOGOs>();

		Sistema.Barra.getCarregando().setString("Iniciando");
		System.out.println("Iniciando...");
		/** Updating the progress bar, according to executed progress */
		Sistema.Barra.getCarregando().setValue(10);
		Sistema.Barra.getCarregando().setString("Iniciando CDs");
		System.out.println("Inicializando CDs...");
		inicializaCD();
		Sistema.Barra.getCarregando().setString("Iniciando Videos");
		Sistema.Barra.getCarregando().setValue(20);
		System.out.println("Inicializando Videos...");
		inicializaVIDEO();
		Sistema.Barra.getCarregando().setString("Iniciando Livros");
		Sistema.Barra.getCarregando().setValue(30);
		System.out.println("Inicializando Livros...");
		inicializaLIVRO();
		Sistema.Barra.getCarregando().setString("Iniciando Jogos");
		Sistema.Barra.getCarregando().setValue(40);
		System.out.println("Inicializando Jogos...");
		inicializaJOGO();
		Sistema.Barra.getCarregando().setString("Iniciando Acervos");
		Sistema.Barra.getCarregando().setValue(50);
		System.out.println("Inicializando Acervos...");
		inicializaTodosAcervos();
		Sistema.Barra.getCarregando().setString("Iniciando Usuarios");
		Sistema.Barra.getCarregando().setValue(60);
		System.out.println("Inicializando Usuarios...");
		inicializaUsuarios();
		Sistema.Barra.getCarregando().setString("Iniciando Caixa");
		Sistema.Barra.getCarregando().setValue(70);
		System.out.println("Inicializando Caixa...");
		inicializaBanco();
	}

	/**
	 * Loads monetary transactions and balance from the library.
	 * 
	 * @throws NumberFormatException
	 *             in case balance is not a proper number.
	 * @throws IOException
	 *             whenever there are problems reading the text file.
	 */
	private void inicializaBanco() throws NumberFormatException, IOException {
		Float tamanho = null;
		File auxiliar = null;
		RandomAccessFile file = null;
		auxiliar = new File("DB/Banco.txt");
		file = new RandomAccessFile(auxiliar, "rw");

		String aux = file.readLine();

		tamanho = new Float(aux);

		caixa = tamanho.floatValue();

		byte[] teste = new byte[(int) file.length() - (aux.length() * 2) - 2];

		try {
			file.readFully(teste);
		} catch (EOFException error) {
			System.out.println("[ERRO] Final inesperado de arquivo.");
			error.printStackTrace();
		}

		movimentacao = new String(teste);
		file.close();
	}

	/**
	 * Groups all loaded item sources into one model.
	 */
	private void inicializaTodosAcervos() {
		if (TodosAcervos == null) {
			TodosAcervos = new DefaultListModel<Group>();
		} else {
			TodosAcervos.removeAllElements();
		}

		for (int i = 0; i < CDS.size(); i++) {
			TodosAcervos.addElement(CDS.getElementAt(i));
		}
		for (int i = 0; i < VIDEOS.size(); i++) {
			TodosAcervos.addElement(VIDEOS.getElementAt(i));
		}
		for (int i = 0; i < LIVROS.size(); i++) {
			TodosAcervos.addElement(LIVROS.getElementAt(i));
		}
		for (int i = 0; i < LIVROS.size(); i++) {
			TodosAcervos.addElement(LIVROS.getElementAt(i));
		}
	}

	/**
	 * Loads user information.
	 * 
	 * @throws IOException
	 *             whenever there are problems reading the text file.
	 * @see database.lists.Usuarios#Usuarios() Usuarios's default constructor.
	 */
	private void inicializaUsuarios() throws IOException {
		Pessoas = new Usuarios();
	}

	/**
	 * Loads all child files for all CD sources.
	 * 
	 * @throws IOException
	 *             whenever there is a problem reading the control text file or
	 *             any of the child files specified there.
	 * @see database.lists.CDs#CDs(String) CDs's default constructor.
	 */
	private void inicializaCD() throws IOException {
		Integer tamanho = null;
		File auxiliar = null;
		RandomAccessFile file = null;
		auxiliar = new File("DB/CDs.txt");

		file = new RandomAccessFile(auxiliar, "rw");
		tamanho = new Integer(file.readLine());

		cds = new CDs[tamanho.intValue()];

		for (int i = 0; i < tamanho.intValue(); i++) {
			cds[i] = new CDs(file.readLine());
			CDS.addElement(cds[i]);
		}
		file.close();
	}

	/**
	 * Loads all child files for all Film sources.
	 * 
	 * @throws IOException
	 *             whenever there is a problem reading the control text file or
	 *             any of the child files specified there.
	 * @see database.lists.VIDEOs#VIDEOs(String) VIDEOs's default constructor.
	 */
	private void inicializaVIDEO() throws IOException {
		Integer tamanho = null;
		File auxiliar = null;
		RandomAccessFile file = null;

		auxiliar = new File("DB/VIDEOs.txt");
		file = new RandomAccessFile(auxiliar, "rw");
		tamanho = new Integer(file.readLine());

		videos = new VIDEOs[tamanho.intValue()];

		for (int i = 0; i < tamanho.intValue(); i++) {
			videos[i] = new VIDEOs(file.readLine());
			VIDEOS.addElement(videos[i]);
		}
		file.close();
	}

	/**
	 * Loads all child files for all Book sources.
	 * 
	 * @throws IOException
	 *             whenever there is a problem reading the control text file or
	 *             any of the child files specified there.
	 * @see database.lists.LIVROs#LIVROs(String) LIVROs's default constructor.
	 */
	private void inicializaLIVRO() throws IOException {
		Integer tamanho = null;
		File auxiliar = null;
		RandomAccessFile file = null;

		auxiliar = new File("DB/LIVROs.txt");
		file = new RandomAccessFile(auxiliar, "rw");
		tamanho = new Integer(file.readLine());

		livros = new LIVROs[tamanho.intValue()];

		for (int i = 0; i < tamanho.intValue(); i++) {
			livros[i] = new LIVROs(file.readLine());
			LIVROS.addElement(livros[i]);
		}
		file.close();
	}

	/**
	 * Loads all child files for all Game sources.
	 * 
	 * @throws IOException
	 *             whenever there is a problem reading the control text file or
	 *             any of the child files specified there.
	 * @see database.lists.JOGOs#JOGOs(String) JOGOs's default constructor.
	 */
	private void inicializaJOGO() throws IOException {
		Integer tamanho = null;
		File auxiliar = null;
		RandomAccessFile file = null;

		auxiliar = new File("DB/JOGOs.txt");
		file = new RandomAccessFile(auxiliar, "rw");
		tamanho = new Integer(file.readLine());

		jogos = new JOGOs[tamanho.intValue()];

		for (int i = 0; i < tamanho.intValue(); i++) {
			jogos[i] = new JOGOs(file.readLine());
			JOGOS.addElement(jogos[i]);
		}
		file.close();
	}

	/**
	 * Loads current rental information.
	 * 
	 * @throws IOException
	 *             whenever there are problems reading the text file.
	 */
	public void inicializaLOCACOES() throws IOException {
		Integer tamanho = null;
		File auxiliar = null;
		RandomAccessFile file = null;

		auxiliar = new File("DB/Locacoes.txt");
		file = new RandomAccessFile(auxiliar, "rw");
		tamanho = new Integer(file.readLine());

		locacoes = new Locacao[tamanho.intValue()];
		LOCACOES = new DefaultListModel<Locacao>();

		for (int i = 0; i < tamanho.intValue(); i++) {
			locacoes[i] = new Locacao(file);
			LOCACOES.addElement(locacoes[i]);
		}

		file.close();

		auxiliar = new File("DB/datas.txt");
		file = new RandomAccessFile(auxiliar, "rw");

		semanaCD = new Integer(file.readLine()).intValue();
		semanaVIDEO = new Integer(file.readLine()).intValue();
		semanaLIVRO = new Integer(file.readLine()).intValue();
		semanaJOGO = new Integer(file.readLine()).intValue();

		file.close();
	}

	/**
	 * Dumps all data from memory to text files.
	 * 
	 * @throws IOException
	 *             whenever there are problems writing information to the disk.
	 */
	public void filerize() throws IOException {
		PrintStream Usuarios = new PrintStream("DB/Usuarios.txt");
		Pessoas.toFile(Usuarios);

		int i = 0;
		PrintStream arquivo = null;
		System.out.println("Escrevendo CDs...");
		int tam = cds.length;
		arquivo = new PrintStream("DB/CDs.txt");
		arquivo.println(tam);
		for (i = 0; i < tam; i++) {
			arquivo.println(cds[i].Nome);
		}
		for (i = 0; i < tam; i++) {
			arquivo = new PrintStream("DB/CDS/" + cds[i].Nome + ".txt");
			cds[i].toFile(arquivo);
			arquivo.close();
		}

		System.out.println("Escrevendo Videos...");
		tam = videos.length;
		arquivo = new PrintStream("DB/VIDEOs.txt");
		arquivo.println(tam);
		for (i = 0; i < tam; i++) {
			arquivo.println(videos[i].Nome);
		}
		for (i = 0; i < tam; i++) {
			arquivo = new PrintStream("DB/VIDEOS/" + videos[i].Nome + ".txt");
			videos[i].toFile(arquivo);
			arquivo.close();
		}

		System.out.println("Escrevendo Livros...");
		tam = livros.length;
		arquivo = new PrintStream("DB/LIVROs.txt");
		arquivo.println(tam);
		for (i = 0; i < tam; i++) {
			arquivo.println(livros[i].Nome);
		}
		for (i = 0; i < tam; i++) {
			arquivo = new PrintStream("DB/LIVROS/" + livros[i].Nome + ".txt");
			livros[i].toFile(arquivo);
			arquivo.close();
		}

		System.out.println("Escrevendo Jogos...");
		tam = jogos.length;
		arquivo = new PrintStream("DB/JOGOs.txt");
		arquivo.println(tam);
		for (i = 0; i < tam; i++) {
			arquivo.println(jogos[i].Nome);
		}
		for (i = 0; i < tam; i++) {
			arquivo = new PrintStream("DB/JOGOS/" + jogos[i].Nome + ".txt");
			jogos[i].toFile(arquivo);
			arquivo.close();
		}

		System.out.println("Escrevendo Locações...");
		arquivo = new PrintStream("DB/Locacoes.txt");
		arquivo.println(locacoes.length);
		for (i = 0; i < locacoes.length; i++) {
			locacoes[i].toFile(arquivo);
		}
		arquivo.close();

		System.out.println("Escrevendo Caixa...");
		arquivo = new PrintStream("DB/Banco.txt");
		arquivo.println(caixa + "");
		arquivo.println(movimentacao);
		arquivo.println();
		arquivo.close();

		System.out.println("Escrevendo Configuração de Entrega...");
		arquivo = new PrintStream("DB/datas.txt");
		arquivo.println(semanaCD);
		arquivo.println(semanaVIDEO);
		arquivo.println(semanaLIVRO);
		arquivo.println(semanaJOGO);
		arquivo.close();
	}

	/**
	 * Selects the appropriate return date based on library's "business model".
	 * The reference date is the current day.
	 * 
	 * @param weeks
	 *            defines how many weeks to skip.
	 * @return the delivery date.
	 */
	static public GregorianCalendar calculaDevolucao(int weeks) {
		GregorianCalendar data = new GregorianCalendar();

		/**
		 * Current Logic: For rentals during the week, the same day of the week
		 * in X "weeks" For rentals during the weekend, the Monday in X "weeks"
		 */
		data.setTime(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000 * weeks));

		if (data.get(GregorianCalendar.DAY_OF_WEEK) < GregorianCalendar.MONDAY) {
			data.setTime(new Date(data.getTimeInMillis() + 24 * 60 * 60 * 1000));
		}
		if (data.get(GregorianCalendar.DAY_OF_WEEK) > GregorianCalendar.FRIDAY) {
			data.setTime(new Date(data.getTimeInMillis() + 2 * 24 * 60 * 60 * 1000));
		}
		data.set(GregorianCalendar.HOUR, 23);
		data.set(GregorianCalendar.MINUTE, 59);
		data.set(GregorianCalendar.SECOND, 59);

		return data;
	}

	/**
	 * Searches for a particular item in all sources.
	 * 
	 * @param termo
	 *            defines the search entry.
	 * @return all possible items that matched the search.
	 */
	public Item[] procuraItem(String termo) {
		Item[][][] auxiliar = new Item[4][][];

		auxiliar[0] = new Item[cds.length][];
		for (int i = 0; i < cds.length; i++) {
			auxiliar[0][i] = cds[i].busca(termo);
		}

		auxiliar[1] = new Item[videos.length][];
		for (int i = 0; i < videos.length; i++) {
			auxiliar[1][i] = videos[i].busca(termo);
		}

		auxiliar[2] = new Item[livros.length][];
		for (int i = 0; i < livros.length; i++) {
			auxiliar[2][i] = livros[i].busca(termo);
		}

		auxiliar[3] = new Item[jogos.length][];
		for (int i = 0; i < jogos.length; i++) {
			auxiliar[3][i] = jogos[i].busca(termo);
		}

		int count = 0;

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < auxiliar[i].length; j++)
				count += auxiliar[i][j].length;

		Item[] resultado = new Item[count];

		count = 0;

		for (int i = 0; i < 4; i++)
			for (int j = 0; j < auxiliar[i].length; j++)
				for (int k = 0; k < auxiliar[i][j].length; k++)
					resultado[count++] = auxiliar[i][j][k];

		return resultado;
	}

	/**
	 * Searches for a particular user.
	 * 
	 * @param quem
	 *            defines the user to be searched.
	 * @return the first matched user or null if not found.
	 */
	public Usuario procuraUsuario(String quem) {
		for (int i = 0; i < Pessoas.lista.length; i++) {
			if (Pessoas.lista[i].Nome.compareToIgnoreCase(quem) == 0)
				return Pessoas.lista[i];
		}

		return null;
	}

	/**
	 * Gets and increases an id for rental.
	 * 
	 * @return the rental id to be used.
	 */
	public int consomeLocacao() {
		return nextLocacao++;
	}

	/**
	 * Gets all users.
	 * 
	 * @return users.
	 */
	public Usuarios getPessoas() {
		return Pessoas;
	}

	/**
	 * Gets rental time allowance for CDs.
	 * 
	 * @return the number of weeks allowed for CDs.
	 */
	public int getSemanaCD() {
		return semanaCD;
	}

	/**
	 * Defines the time allowed for CD rental.
	 * 
	 * @param semanaCD
	 *            defines the number of weeks allowed.
	 */
	public void setSemanaCD(int semanaCD) {
		this.semanaCD = semanaCD;
	}

	/**
	 * Gets rental time allowance for Films.
	 * 
	 * @return the number of weeks allowed for Films.
	 */
	public int getSemanaVIDEO() {
		return semanaVIDEO;
	}

	/**
	 * Defines the time allowed for Film rental.
	 * 
	 * @param semanaVIDEO
	 *            defines the number of weeks allowed.
	 */
	public void setSemanaVIDEO(int semanaVIDEO) {
		this.semanaVIDEO = semanaVIDEO;
	}

	/**
	 * Gets rental time allowance for Books.
	 * 
	 * @return the number of weeks allowed for Books.
	 */
	public int getSemanaLIVRO() {
		return semanaLIVRO;
	}

	/**
	 * Defines the time allowed for Book rental.
	 * 
	 * @param semanaLIVRO
	 *            defines the number of weeks allowed.
	 */
	public void setSemanaLIVRO(int semanaLIVRO) {
		this.semanaLIVRO = semanaLIVRO;
	}

	/**
	 * Gets rental time allowance for Games.
	 * 
	 * @return the number of weeks allowed for Games.
	 */
	public int getSemanaJOGO() {
		return semanaJOGO;
	}

	/**
	 * Defines the time allowed for Game rental.
	 * 
	 * @param semanaJOGO
	 *            defines the number of weeks allowed.
	 */
	public void setSemanaJOGO(int semanaJOGO) {
		this.semanaJOGO = semanaJOGO;
	}

	/**
	 * Removes a CD source. Note 1: The old file won't be cleaned up from the
	 * disk. Note 2: A complete persistence layer write is performed.
	 * 
	 * @param Qual
	 *            selects which source shall be removed.
	 * @return whether the source could be found and removed.
	 * @throws IOException
	 *             if there are problems rewriting all system files.
	 */
	public boolean removeCD(String Qual) throws IOException {
		int counter = 0;
		CDs[] auxiliar = new CDs[cds.length - 1];
		for (counter = 0; (counter < cds.length) && (!cds[counter].Nome.equalsIgnoreCase(Qual)); counter++) {
			auxiliar[counter] = cds[counter];
		}

		if (counter == cds.length)
			return false;
		else {
			for (counter++; counter < cds.length; counter++) {
				auxiliar[counter - 1] = cds[counter];
			}
			cds = auxiliar;

			CDS.removeAllElements();
			for (int i = 0; i < cds.length; i++) {
				CDS.addElement(cds[i]);
			}
			inicializaTodosAcervos();
			filerize();
			return true;
		}
	}

	/**
	 * Removes a Film source. Note 1: The old file won't be cleaned up from the
	 * disk. Note 2: A complete persistence layer write is performed.
	 * 
	 * @param Qual
	 *            selects which source shall be removed.
	 * @return whether the source could be found and removed.
	 * @throws IOException
	 *             if there are problems rewriting all system files.
	 */
	public boolean removeVIDEO(String Qual) throws IOException {
		int counter = 0;
		VIDEOs[] auxiliar = new VIDEOs[videos.length - 1];
		for (counter = 0; (counter < videos.length) && (!videos[counter].Nome.equalsIgnoreCase(Qual)); counter++) {
			auxiliar[counter] = videos[counter];
		}

		if (counter == videos.length)
			return false;
		else {
			for (counter++; counter < videos.length; counter++) {
				auxiliar[counter - 1] = videos[counter];
			}
			videos = auxiliar;

			VIDEOS.removeAllElements();
			for (int i = 0; i < videos.length; i++) {
				VIDEOS.addElement(videos[i]);
			}
			inicializaTodosAcervos();
			filerize();
			return true;
		}
	}

	/**
	 * Removes a Book source. Note 1: The old file won't be cleaned up from the
	 * disk. Note 2: A complete persistence layer write is performed.
	 * 
	 * @param Qual
	 *            selects which source shall be removed.
	 * @return whether the source could be found and removed.
	 * @throws IOException
	 *             if there are problems rewriting all system files.
	 */
	public boolean removeLIVRO(String Qual) throws IOException {
		int counter = 0;
		LIVROs[] auxiliar = new LIVROs[livros.length - 1];
		for (counter = 0; (counter < livros.length) && (!livros[counter].Nome.equalsIgnoreCase(Qual)); counter++) {
			auxiliar[counter] = livros[counter];
		}

		if (counter == livros.length)
			return false;
		else {
			for (counter++; counter < livros.length; counter++) {
				auxiliar[counter - 1] = livros[counter];
			}
			livros = auxiliar;

			LIVROS.removeAllElements();
			for (int i = 0; i < livros.length; i++) {
				LIVROS.addElement(livros[i]);
			}
			inicializaTodosAcervos();
			filerize();
			return true;
		}
	}

	/**
	 * Removes a Game source. Note 1: The old file won't be cleaned up from the
	 * disk. Note 2: A complete persistence layer write is performed.
	 * 
	 * @param Qual
	 *            selects which source shall be removed.
	 * @return whether the source could be found and removed.
	 * @throws IOException
	 *             if there are problems rewriting all system files.
	 */
	public boolean removeJOGO(String Qual) throws IOException {
		int counter = 0;
		JOGOs[] auxiliar = new JOGOs[jogos.length - 1];
		for (counter = 0; (counter < jogos.length) && (!jogos[counter].Nome.equalsIgnoreCase(Qual)); counter++) {
			auxiliar[counter] = jogos[counter];
		}

		if (counter == jogos.length)
			return false;
		else {
			for (counter++; counter < jogos.length; counter++) {
				auxiliar[counter - 1] = jogos[counter];
			}
			jogos = auxiliar;

			JOGOS.removeAllElements();
			for (int i = 0; i < jogos.length; i++) {
				JOGOS.addElement(jogos[i]);
			}
			inicializaTodosAcervos();
			filerize();
			return true;
		}
	}

	/**
	 * Removes a rental from the system.
	 * 
	 * @param Identification
	 *            defines the id of the rental to be removed.
	 */
	public void removeLocacao(int Identification) {
		Locacao[] auxiliar = new Locacao[locacoes.length - 1];
		int i = 0;

		for (i = 0; (i < auxiliar.length) && (locacoes[i].ID != Identification); i++) {
			auxiliar[i] = locacoes[i];
		}
		i++;
		for (; i < locacoes.length; i++) {
			auxiliar[i - 1] = locacoes[i];
		}

		locacoes = auxiliar;

		LOCACOES.removeAllElements();

		for (int j = 0; j < locacoes.length; j++) {
			LOCACOES.addElement(locacoes[j]);
		}
	}

	/**
	 * Creates a new CD source database. Note: A complete persistence layer
	 * write is performed.
	 * 
	 * @param nova
	 *            defines name of the new source.
	 * @throws IOException
	 *             if there are problems creating or writing all files back.
	 */
	public void novoCD(String nova) throws IOException {
		PrintStream novo = new PrintStream("DB/CDS/" + nova + ".txt");
		novo.println("0");
		novo.close();
		CDs[] auxiliar = new CDs[cds.length + 1];
		for (int i = 0; i < cds.length; i++) {
			auxiliar[i] = cds[i];
		}
		auxiliar[cds.length] = new CDs(nova);
		cds = auxiliar;
		CDS.addElement(cds[cds.length - 1]);
		TodosAcervos.addElement(cds[cds.length - 1]);
		inicializaTodosAcervos();
		filerize();
	}

	/**
	 * Creates a new Film source database. Note: A complete persistence layer
	 * write is performed.
	 * 
	 * @param nova
	 *            defines name of the new source.
	 * @throws IOException
	 *             if there are problems creating or writing all files back.
	 */
	public void novoVIDEO(String nova) throws IOException {
		PrintStream novo = new PrintStream("DB/VIDEOS/" + nova + ".txt");
		novo.println("0");
		novo.close();
		VIDEOs[] auxiliar = new VIDEOs[videos.length + 1];
		for (int i = 0; i < videos.length; i++) {
			auxiliar[i] = videos[i];
		}
		auxiliar[videos.length] = new VIDEOs(nova);
		videos = auxiliar;
		VIDEOS.addElement(videos[videos.length - 1]);
		TodosAcervos.addElement(videos[videos.length - 1]);
		inicializaTodosAcervos();
		filerize();
	}

	/**
	 * Creates a new Book source database. Note: A complete persistence layer
	 * write is performed.
	 * 
	 * @param nova
	 *            defines name of the new source.
	 * @throws IOException
	 *             if there are problems creating or writing all files back.
	 */
	public void novoLIVRO(String nova) throws IOException {
		PrintStream novo = new PrintStream("DB/LIVROS/" + nova + ".txt");
		novo.println("0");
		novo.close();
		LIVROs[] auxiliar = new LIVROs[livros.length + 1];
		for (int i = 0; i < livros.length; i++) {
			auxiliar[i] = livros[i];
		}
		auxiliar[livros.length] = new LIVROs(nova);
		livros = auxiliar;
		LIVROS.addElement(livros[livros.length - 1]);
		TodosAcervos.addElement(livros[livros.length - 1]);
		inicializaTodosAcervos();
		filerize();
	}

	/**
	 * Creates a new Game source database. Note: A complete persistence layer
	 * write is performed.
	 * 
	 * @param nova
	 *            defines name of the new source.
	 * @throws IOException
	 *             if there are problems creating or writing all files back.
	 */
	public void novoJOGO(String nova) throws IOException {
		PrintStream novo = new PrintStream("DB/JOGOS/" + nova + ".txt");
		novo.println("0");
		novo.close();
		JOGOs[] auxiliar = new JOGOs[jogos.length + 1];
		for (int i = 0; i < jogos.length; i++) {
			auxiliar[i] = jogos[i];
		}
		auxiliar[jogos.length] = new JOGOs(nova);
		jogos = auxiliar;
		JOGOS.addElement(jogos[jogos.length - 1]);
		TodosAcervos.addElement(jogos[jogos.length - 1]);
		inicializaTodosAcervos();
		filerize();
	}

	/**
	 * Adds a new rental to the system.
	 * 
	 * @param nova
	 *            defines the rental to be added.
	 */
	public void novaLocacao(Locacao nova) {
		if (nova == null)
			return;

		Locacao[] auxiliar = new Locacao[locacoes.length + 1];

		for (int i = 0; i < locacoes.length; i++) {
			auxiliar[i] = locacoes[i];
		}

		auxiliar[locacoes.length] = nova;
		LOCACOES.addElement(nova);
		locacoes = auxiliar;
	}

	/**
	 * Gets all CD sources.
	 * 
	 * @return list of all CD sources.
	 */
	public CDs[] getCDs() {
		return cds;
	}

	/**
	 * Gets all Film sources.
	 * 
	 * @return list of all Film sources.
	 */
	public VIDEOs[] getVIDEOs() {
		return videos;
	}

	/**
	 * Gets all Book sources.
	 * 
	 * @return list of all Book sources.
	 */
	public LIVROs[] getLIVROs() {
		return livros;
	}

	/**
	 * Gets all Game sources.
	 * 
	 * @return list of all Game sources.
	 */
	public JOGOs[] getJOGOs() {
		return jogos;
	}

	/**
	 * Gets open rentals.
	 * 
	 * @return list of open rentals.
	 */
	public Locacao[] getLocacoes() {
		System.out.println("++" + locacoes.length);
		return locacoes;
	}

	/**
	 * Gets the source UI model for CD sources.
	 * 
	 * @return database.lists.CDs model with all CD sources.
	 */
	public DefaultListModel<CDs> getCDSModel() {
		return CDS;
	}

	/**
	 * Gets the source UI model for Film sources.
	 * 
	 * @return database.lists.VIDEOs model with all Film sources.
	 */
	public DefaultListModel<VIDEOs> getVIDEOSModel() {
		return VIDEOS;
	}

	/**
	 * Gets the source UI model for Book sources.
	 * 
	 * @return database.lists.LIVROs model with all Book sources.
	 */
	public DefaultListModel<LIVROs> getLIVROSModel() {
		return LIVROS;
	}

	/**
	 * Gets the source UI model for Game sources.
	 * 
	 * @return database.lists.JOGOs model with all Game sources.
	 */
	public DefaultListModel<JOGOs> getJOGOSModel() {
		return JOGOS;
	}

	/**
	 * Gets the source UI model for open rentals.
	 * 
	 * @return database.lists.Locacao model with all open rentals.
	 */
	public DefaultListModel<Locacao> getLOCACOESModel() {
		return LOCACOES;
	}

	/**
	 * Gets the source UI model for all sources.
	 * 
	 * @return database.lists.Group abstract model with all sources.
	 */
	public DefaultListModel<Group> getTodosAcervosModel() {
		return TodosAcervos;
	}

	/**
	 * Gets the monetary balance of the library.
	 * 
	 * @return the balance.
	 */
	public float getCaixa() {
		return caixa;
	}

	/**
	 * Takes money from the library.
	 * 
	 * @param valor
	 *            defines the amount of money to be taken.
	 */
	public void debitar(float valor) {
		caixa -= valor;
	}

	/**
	 * Adds money to the library.
	 * 
	 * @param valor
	 *            defines the amount of money to be added.
	 */
	public void creditar(float valor) {
		caixa += valor;
	}

	/**
	 * Gets monetary history of the library.
	 * 
	 * @return string with all transactions, one per line.
	 */
	public String getMovimentacao() {
		return movimentacao;
	}

	/**
	 * Adds a new transaction to the library monetary history.
	 * 
	 * @param movimentacao
	 *            defines the description of the transaction.
	 */
	public void addMovimentacao(String movimentacao) {
		GregorianCalendar data = new GregorianCalendar();
		this.movimentacao = "[" + data.get(GregorianCalendar.DATE) + "/" + data.get(GregorianCalendar.MONTH) + "/" + data.get(GregorianCalendar.YEAR) + "] " + movimentacao + "\n" + this.movimentacao;
	}
}
