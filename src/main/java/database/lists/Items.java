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
import java.io.RandomAccessFile;

import javax.swing.DefaultListModel;

import core.Sistema;
import database.Item;

/**
 * The {@code Items} class defines a UI usable ListModel for system items.
 * Even though this is not an abstract class, it implements generic handling for all item types. 
 * 
 * @author Matheus Borges Teixeira
 * @version 1.0
 */
public class Items extends DefaultListModel<Item> 
{
	/** Mandatory version number **/
	private static final long serialVersionUID = 1L;

	/**
	 * Fills the ListModel based on information from a given item list.
	 * 
	 * @param nome specifies the name of the item list.
	 * @param acervo defines the list of items to add in the model.
	 * @throws NumberFormatException whenever the count data written on the file is not correct.
	 * @throws IOException whenever there are problems reading the written data.
	 */
	public void fromFile(String nome, Item[] acervo) throws NumberFormatException, IOException {
		File auxiliar = new File(nome);
		RandomAccessFile file = new RandomAccessFile(auxiliar,"rw");

		int tamanho = 0;
		Integer tam = Integer.valueOf(file.readLine());

		tamanho = tam.intValue();

		for(int i = 0; i < tamanho; i++) {
			Integer ret = new Integer(file.readLine());
			acervo[i].numero_retiradas = ret.intValue();

			Boolean inout = Boolean.parseBoolean(file.readLine());
			acervo[i].retirado = inout.booleanValue();
			
			acervo[i].set(file.readLine(),file.readLine(),file.readLine());
			
			this.addElement(acervo[i]);
		}
		
		file.close();
	}
	
	/**
	 * Replaces the item list and orders it.
	 * @param acervo defines the list of items.
	 */
	public void substituirOrdenando(Item[] acervo) {
		if(Sistema.ModoOrdenacao == 0) {
			sortRetirada(acervo);
		}
		else {
			sortString(acervo);
		}
		
		this.removeAllElements();
		if(acervo != null) {
			for(int i = 0; i < acervo.length; i++) {
				this.addElement(acervo[i]);
			}
		}
	}

	/**
	 * Simple bubble sort implementation using string data.
	 * @param acervo defines the item list.
	 */
	private void sortString(Item[] acervo) {
		if(acervo != null){
			for(int i = acervo.length; i > 0; i--)
				for(int j = 0; j < i - 1; j++) {
					if(acervo[j].toString().compareToIgnoreCase(acervo[j+1].toString()) > 0) {
						Item auxiliar = acervo[j];
						acervo[j] = acervo[j+1];
						acervo[j+1] = auxiliar;
					}
				}
		}
	}

	/**
	 * Simple bubble sort implementation using item rental count.
	 * @param acervo defines the item list.
	 */
	private void sortRetirada(Item[] acervo) {
		for(int i = acervo.length; i > 0; i--) {
			for(int j = 0; j < i - 1; j++) {
				if(acervo[j].numero_retiradas < acervo[j+1].numero_retiradas) {
					Item auxiliar = acervo[j];
					acervo[j] = acervo[j+1];
					acervo[j+1] = auxiliar;
				}
			}
		}
	}
}
