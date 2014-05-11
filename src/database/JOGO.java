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

import core.Sistema;


/**
 * The {@code JOGO} class models a Game, deriving from {@link database.Item Item}
 * 
 * @author Matheus Borges Teixeira
 * @version 1.0
 */
public class JOGO extends Item {

	/** Library's internal Game code */
	String Codigo = null;
	/** Game title */
	String Titulo = null;
	/** Game manufacturer */
	String Fabricante = null;

	/**
	 * This is the default constructor.
	 * 
	 * @param historico specifies the initial amount of time is has been rented.
	 * @param ausencia defines whether the item is in the library or not.
	 * @param codigo specifies a unique code for the Game.
	 * @param titulo defines the Game title.
	 * @param fabricante defines the Game manufacturer.
	 */
	public JOGO(int historico, boolean ausencia, String codigo, String titulo, String fabricante) {
		super(historico,ausencia);
		this.set(codigo,titulo,fabricante);
	}
	
	/**
	 * Implements the abstract method from inherited class.
	 * Defines the three string parameters of the item.
	 * 
	 * @param codigo specifies a unique code for the Game.
	 * @param titulo defines the Game title.
	 * @param fabricante defines the Game manufacturer.
	 */
	public void set(String codigo, String titulo, String fabricante) {
		Codigo = codigo;
		Titulo = titulo;
		Fabricante = fabricante;
	}

	/**
	 * This returns a String representation of the game.
	 * Note that it uses the {@link core.Sistema Sistema} class to read the ordering parameter.
	 * 
	 * @return java.lang.String with all definition strings ordered and formatted properly.
	 * @see core.Sistema#ModoOrdenacao
	 */
	public String toString() {
		String um = ("Retirado " + this.numero_retiradas + " vezes");
		String dois = Codigo;
		String tres = Titulo;
		String quatro = Fabricante;
		
		switch(Sistema.ModoOrdenacao) {
			case 0: /** Order by Rental count */ {
				if(this.retirado) return(um + ", " + dois + " - " + tres + " / " + quatro + "(Retirado)");
				return(um + ", " + dois + " - " + tres + " / " + quatro);
			}
			case 1: /** Order by unique ID */ {
				if(this.retirado) return(dois + " - " + tres + " / " + quatro + ", " + um + "(Retirado)");
				return(dois + " - " + tres + " / " + quatro + ", " + um);
			}
			case 2: /** Order by Game title */ {
				if(this.retirado) return(tres + " / " + quatro + " - " + dois + ", " + um + "(Retirado)");
				return(tres + " / " + quatro + " - " + dois + ", " + um);
			}
			case 3: /** Order by Game manufacturer */ {
				if(this.retirado) return(quatro + " / " + tres + " - " + dois + ", " + um + "(Retirado)");
				return(quatro + " / " + tres + " - " + dois + ", " + um);
			}
		}
		return null;
	}
	
	/**
	 * Implements the abstract method from inherited class. 
	 * 
	 * @param arquivo specifies which file to write to. 
	 */
	public void toFile(PrintStream arquivo) {
		arquivo.println(this.numero_retiradas);
		arquivo.println(this.retirado);
		arquivo.println(Codigo);
		arquivo.println(Titulo);
		arquivo.println(Fabricante);
	}

	/**
	 * Implements the abstract method from inherited class.
	 */
	public String getIdentity() {
		return Codigo;
	}
}
