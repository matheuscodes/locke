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
package database;

import java.io.PrintStream;
import java.util.GregorianCalendar;

/**
 * The {@code Usuario} class models a user.
 * 
 * @author Matheus Borges Teixeira
 * @version 1.0
 */
public class Usuario {
	/** Name of the user **/
	public String Nome = null;
	/** Course of the user **/
	private String Curso = null;
	/** Joining year **/
	private String AnoIngresso = null;
	/** Birth date **/
	private String Aniversario = null;
	/** Local Address Road **/
	private String Rua_sc = null;
	/** Local Address Complement **/
	private String Complemento_sc = null;
	/** Local Address House Number **/
	private String Numero_sc = null;
	/** Local Address Neighborhood **/
	private String Bairro_sc = null;
	/** Home Address Road **/
	private String Rua = null;
	/** Home Address House Number **/
	private String Numero = null;
	/** Home Address Complement **/
	private String Complemento = null;
	/** Home Address Neighborhood **/
	private String Bairro = null;
	/** Home Address City **/
	private String Cidade = null;
	/** Contact home town telephone **/
	private String Telefone_sc = null;
	/** Contact local telephone **/
	private String Telefone = null;
	/** Contact cellphone **/
	private String Celular = null;
	/** Contact email **/
	private String Email = null;
	/** Whether user is CAASO member **/
	private boolean CAASO = true;
	/** Whether user is Student House member **/
	private boolean Aloja = false;
	/** List of user's permissions **/
	private String[] AcervosPermitidos = null;
	/** Renting history of the user **/
	private String Passado = "";
	/** Collection points **/
	private int Marquinhas = 0;

	/**
	 * Gets whether user is CAASO member.
	 * 
	 * @return whether user is CAASO member.
	 */
	public boolean getCAASO() {
		return CAASO;
	}

	/**
	 * Gets whether user lives in student house.
	 * 
	 * @return whether user lives in student house.
	 */
	public boolean getAloja() {
		return Aloja;
	}

	/**
	 * Gets user permissions.
	 * 
	 * @return user permissions.
	 */
	public String[] getAcervosPermitidos() {
		return AcervosPermitidos;
	}

	/**
	 * Gets user history.
	 * 
	 * @return user history.
	 */
	public String getPassado() {
		return Passado;
	}

	/**
	 * Adds a new entry to user history.
	 * 
	 * @param entrada
	 *            specifies the new entry.
	 */
	public void addPassado(String entrada) {
		GregorianCalendar data = new GregorianCalendar();
		Passado = "[" + data.get(GregorianCalendar.DATE) + "/" + data.get(GregorianCalendar.MONTH) + "/" + data.get(GregorianCalendar.YEAR) + "] " + entrada + "\n" + Passado;
	}

	/**
	 * Gets collection points.
	 * 
	 * @return collection points.
	 */
	public int getMarquinhas() {
		return Marquinhas;
	}

	/**
	 * Increases collection count.
	 * 
	 * @param valor
	 *            defines amount to increase.
	 */
	public void incrementaMarquinhas(int valor) {
		Marquinhas += valor;
	}

	/**
	 * Sets user attributes.
	 * 
	 * @param caaso
	 *            defines if user is member of CAASO.
	 * @param aloja
	 *            defines if user lives in the student house.
	 * @param acervos
	 *            specifies permissions of the user.
	 * @param passado
	 *            defines user rental history.
	 * @param marcas
	 *            informs the collection count of the user.
	 */
	public void setAtributos(boolean caaso, boolean aloja, String[] acervos, String passado, int marcas) {
		this.CAASO = caaso;
		this.Aloja = aloja;
		this.AcervosPermitidos = acervos;
		this.Passado = passado;
		this.Marquinhas = marcas;
	}

	/**
	 * Creates a user based on a list of informations about the user.
	 * 
	 * @param parametros
	 *            string list of informations.
	 */
	public Usuario(String[] parametros) {
		Nome = parametros[0];
		Curso = parametros[1];
		AnoIngresso = parametros[2];
		Aniversario = parametros[3];
		Rua_sc = parametros[4];
		Complemento_sc = parametros[5];
		Numero_sc = parametros[6];
		Bairro_sc = parametros[7];
		Rua = parametros[8];
		Numero = parametros[9];
		Complemento = parametros[10];
		Bairro = parametros[11];
		Telefone_sc = parametros[12];
		Telefone = parametros[13];
		Celular = parametros[14];
		Email = parametros[15];
		Cidade = parametros[16];
	}

	public String toString() {
		return (Nome + ", " + Email);
	}

	/**
	 * Gets a list with all information about the user.
	 * 
	 * @return string list of informations.
	 */
	public String[] getFicha() {
		String[] parametros = new String[17];
		parametros[0] = Nome;
		parametros[1] = Curso;
		parametros[2] = AnoIngresso;
		parametros[3] = Aniversario;
		parametros[4] = Rua_sc;
		parametros[5] = Complemento_sc;
		parametros[6] = Numero_sc;
		parametros[7] = Bairro_sc;
		parametros[8] = Rua;
		parametros[9] = Numero;
		parametros[10] = Complemento;
		parametros[11] = Bairro;
		parametros[12] = Telefone_sc;
		parametros[13] = Telefone;
		parametros[14] = Celular;
		parametros[15] = Email;
		parametros[16] = Cidade;

		return parametros;
	}

	/**
	 * Writes the user to a file.
	 * 
	 * @param arquivo
	 *            specifies which file to write to.
	 */
	public void toFile(PrintStream arquivo) {
		arquivo.println(Nome);
		arquivo.println(Curso);
		arquivo.println(AnoIngresso);
		arquivo.println(Aniversario);
		arquivo.println(Rua_sc);
		arquivo.println(Complemento_sc);
		arquivo.println(Numero_sc);
		arquivo.println(Bairro_sc);
		arquivo.println(Rua);
		arquivo.println(Numero);
		arquivo.println(Complemento);
		arquivo.println(Bairro);
		arquivo.println(Telefone_sc);
		arquivo.println(Telefone);
		arquivo.println(Celular);
		arquivo.println(Email);
		arquivo.println(Cidade);
		arquivo.println(AcervosPermitidos.length);

		for (int i = 0; i < AcervosPermitidos.length; i++) {
			arquivo.println(AcervosPermitidos[i]);
		}

		arquivo.println(CAASO);
		arquivo.println(Aloja);
		arquivo.println(Passado.replaceAll("\n", "<SEPARA>"));

		arquivo.println(Marquinhas);
	}
}
