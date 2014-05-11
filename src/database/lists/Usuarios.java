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
package database.lists;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;

import javax.swing.DefaultListModel;

import database.Usuario;

/**
 * The {@code Usuarios} class defines a UI usable ListModel with all system users.
 * 
 * @author Matheus Borges Teixeira
 * @version 1.0
 */
public class Usuarios extends DefaultListModel<Usuario>
{
	/** Mandatory serial version **/
	private static final long serialVersionUID = 1L;
	/** User list **/
	public Usuario[] lista = null;
	
	/**
	 * Constructs a user list, based on the file written on disk.
	 * @throws IOException whenever there is a problem reading the file.
	 */
	public Usuarios() throws IOException {
		super();
		
		File auxiliar = new File("DB/Usuarios.txt");
		RandomAccessFile file = new RandomAccessFile(auxiliar,"rw");
		
		Integer tamanho = new Integer(file.readLine());
		
		lista = new Usuario[tamanho.intValue()];
		
		for(int i = 0; i < lista.length;i++) {
			String[] dados = new String[17];
			
			for(int j = 0; j < 17; j++) {
				dados[j] = file.readLine();
			}
			
			Integer acervos = new Integer(file.readLine());
			
			String[] acervospermitidos = new String[acervos.intValue()];
			
			for(int j = 0; j < acervospermitidos.length;j++) {
				acervospermitidos[j] = file.readLine();
			}
			
			Boolean caaso = new Boolean(file.readLine());
			Boolean aloja = new Boolean(file.readLine());
			
			String past = file.readLine();
			past = past.replaceAll("<SEPARA>","\n");
			Integer marcas = new Integer(file.readLine());
			
			lista[i] = new Usuario(dados);
			lista[i].setAtributos(caaso.booleanValue(), aloja.booleanValue(), acervospermitidos, past, marcas.intValue());
		}
		
		file.close();
		
		int x = 0;
		for(x = 0; x < lista.length; x++ ) {
			this.addElement(lista[x]);
		}
	}
	
	/**
	 * Adds a new user to the list.
	 * @param novo informs which user is to be added.
	 */
	public void novoUsuario(Usuario novo) {
		Usuario[] auxiliar = new Usuario[lista.length + 1];
		for(int i = 0; i < lista.length; i++) {
			auxiliar[i] = lista[i];
		}
		auxiliar[lista.length] = novo;
		
		lista = auxiliar;
		
		this.ordena(this.lista);
	}
	
	/**
	 * Orders all user alphabetically using complete names.
	 * @param usuarios defines the list of users.
	 */
	private void ordena(Usuario[] usuarios) {
		sortString(usuarios);
		
		this.removeAllElements();
		
		for(int i = 0; i < usuarios.length; i++) {
			this.addElement(usuarios[i]);
		}
	}

	/**
	 * Simple bubble sort implementation.
	 * @param usuarios defines the list of users.
	 */
	private void sortString(Usuario[] usuarios) {
		for(int i = usuarios.length; i > 0; i--)
			for(int j = 0; j < i - 1; j++) {
				if(usuarios[j].toString().compareToIgnoreCase(usuarios[j+1].toString()) > 0) {
					Usuario auxiliar = usuarios[j];
					usuarios[j] = usuarios[j+1];
					usuarios[j+1] = auxiliar;
				}
			}
	}
	
	/**
	 * Remove a user based on position.
	 * @param onde specifies the position of the user to be removed.
	 */
	public void remover(int onde) {
		Usuario[] reserva = lista;
		Usuario[] auxiliar = new Usuario[reserva.length - 1];
		int i = 0;
		
		for(i = 0; i < onde; i++) {
			auxiliar[i] = reserva[i];
		}
		
		for(i = onde; i < auxiliar.length; i++) {
			auxiliar[i] = reserva[i+1];
		}
		
		lista = auxiliar;
		
		this.ordena(this.lista);
	}

	/**
	 * Writes all users to the disk.
	 * @param usuarios opened file to write to.
	 */
	public void toFile(PrintStream usuarios) {
		usuarios.println(lista.length);
		for(int i = 0; i < lista.length; i++) {
			lista[i].toFile(usuarios);
		}
	}
}
