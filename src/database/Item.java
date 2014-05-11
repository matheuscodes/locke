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

/**
 * The {@code Item} class models an abstract item from the system.
 * 
 * @author Matheus Borges Teixeira
 * @version 1.0
 */
public abstract class Item {
	/** Counter for all rentals */
	public int numero_retiradas = 0;
	/** Whether it is in the library or out in a rental */
	public boolean retirado = false;
	
	/**
	 * This is the default constructor.
	 * 
	 * @param historico specifies the initial amount of time is has been rented.
	 * @param ausencia defines whether the item is in the library or not.
	 */
	public Item(int historico, boolean ausencia) {
		numero_retiradas = historico;
		retirado = ausencia;
	}
	/**
	 * Abstract method to record item in a file.
	 * 
	 * @param arquivo specifies which file to write to.
	 */
	public abstract void toFile(PrintStream arquivo);

	/**
	 * All derived items will be defined by three strings.
	 * This abstract method makes the setter of all three common.
	 * Actual meanings of the parameters are defined in the implementing classes.
	 * 
	 * @param A First definition String.
	 * @param B Second definition String.
	 * @param C Third definition String.
	 */
	public abstract void set(String A, String B, String C);
	
	/**
	 * All derived items will be defined by three strings.
	 * One of those strings actually refers to the item uniquely.
	 * 
	 * @return java.lang.String which identifies the item.
	 */
	public abstract String getIdentity();
}
