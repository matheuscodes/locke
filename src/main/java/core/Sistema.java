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


import windows.Carregando;

/**
 * The {@code Sistema} class just holds global variables.
 * 
 * @version 1.0
 * @author Matheus Borges Teixeira
 */
public class Sistema {
	/** Configures the order which items are displayed */
	public static int ModoOrdenacao = 1;
	/** Instance of the library singleton */
	public static Biblioteca BiblioteCAASO = null;
	/** Instance of the loading bar */
	public static Carregando Barra = null;
}
