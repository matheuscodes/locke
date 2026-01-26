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

import java.io.PrintStream;

import database.Item;

/**
 * The {@code Group} class models a group of items.
 * 
 * @author Matheus Borges Teixeira
 * @version 1.0
 */
public abstract class Group {
	/** Name of the group **/
	public String Nome = null;
	/** List of items usable for UI **/
	public Items Lista = null;
	
	/**
	 * Abstract method for getting the group contents.
	 * @return a list of items.
	 */
	abstract public Item[] getAcervo();
	/**
	 * Abstract method for setting the list of items.
	 * @param novo define the list of items to be used by the group.
	 */
	abstract public void setAcervo(Item[] novo);
	/**
	 * Remove an item based on position.
	 * @param onde specifies the position of the item to be removed. 
	 */
	abstract public void remove(int onde);
	/**
	 * Add a new item to the group.
	 * @param parametros three strings containing item information.
	 */
	abstract public void adicionaItem(String[] parametros);
	/**
	 * Constructs a new Group with given name.
	 * @param nome defines the name of the Group.
	 */
	public Group(String nome) {
		Nome = nome;
	}
	
	public String toString() {
		return Nome;
	}
	
	public void toFile(PrintStream arquivo) {
		arquivo.println(getAcervo().length);
		for(int i = 0; i < getAcervo().length; i++) {
			getAcervo()[i].toFile(arquivo);
		}
	}
	/**
	 * Simple linear search with partial "exact" match.
	 * Searches all matches and re-copy to a result list.
	 * @param oque specifies the string to be searched.
	 * @return a list with all found matches.
	 */
	public Item[] busca(String oque) {
		Item[] auxiliar = new Item[getAcervo().length];
		int i = 0;
		for(int j = 0; j < auxiliar.length; j++) {
			if(getAcervo()[j].toString().contains(oque.subSequence(0,oque.length()))) {
				auxiliar[i] = getAcervo()[j];
				i++;
			}
		}
		
		Item[] resultado = new Item[i];
		
		for(int j = 0; j < resultado.length; j++) {
			resultado[j] = auxiliar[j];
		}
		
		return resultado;
	}
}
