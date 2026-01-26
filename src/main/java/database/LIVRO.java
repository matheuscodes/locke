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
 * The {@code LIVRO} class models a Book, deriving from {@link database.Item Item}
 * 
 * @author Matheus Borges Teixeira
 * @version 1.0
 */
public class LIVRO extends Item {

	/** Author of the book */
	String Autor = null;
	/** Title of the book */
	String Titulo = null;
	/** Genre of the book */
	String Assunto = null;

	/**
	 * This is the default constructor.
	 * 
	 * @param historico specifies the initial amount of time is has been rented.
	 * @param ausencia defines whether the item is in the library or not.
	 * @param autor defines the Book's author.
	 * @param titulo specifies the unique Book's title.
	 * @param assunto defines the Book's genre.
	 */
	public LIVRO(int historico, boolean ausencia, String autor, String titulo, String assunto) 
	{
		super(historico,ausencia);
		this.set(autor,titulo,assunto);
	}

	/**
	 * Implements the abstract method from inherited class.
	 * Defines the three string parameters of the item.
	 * 
	 * @param autor defines the Book's author.
	 * @param titulo specifies the unique Book's title.
	 * @param assunto defines the Book's genre.
	 */
	public void set(String autor, String titulo, String assunto) 
	{
		Autor = autor;
		Titulo = titulo;
		Assunto = assunto;
	}
	
	/**
	 * This returns a String representation of the book.
	 * Note that it uses the {@link core.Sistema Sistema} class to read the ordering parameter.
	 * 
	 * @return java.lang.String with all definition strings ordered and formatted properly.
	 * @see core.Sistema#ModoOrdenacao
	 */
	public String toString()
	{
		String um = ("Retirado " + this.numero_retiradas + " vezes");
		String dois = Autor;
		String tres = Titulo;
		String quatro = Assunto;
		
		switch(Sistema.ModoOrdenacao)
		{
			case 0: /** Order by Rental count */
			{
				if(this.retirado) return(um + ", " + dois + " - " + tres + " / " + quatro + "(Retirado)");
				return(um + ", " + dois + " - " + tres + " / " + quatro);
			}
			case 1: /** Order by Author */
			{
				if(this.retirado) return(dois + " - " + tres + " / " + quatro + ", " + um + "(Retirado)");
				return(dois + " - " + tres + " / " + quatro + ", " + um);
			}
			case 2: /** Order by Title */
			{
				if(this.retirado) return(tres + " / " + quatro + " - " + dois + ", " + um + "(Retirado)");
				return(tres + " / " + quatro + " - " + dois + ", " + um);
			}
			case 3: /** Order by Genre */
			{
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
	public void toFile(PrintStream arquivo)
	{
		arquivo.println(this.numero_retiradas);
		arquivo.println(this.retirado);
		arquivo.println(Autor);
		arquivo.println(Titulo);
		arquivo.println(Assunto);
	}


	/**
	 * Implements the abstract method from inherited class.
	 */
	public String getIdentity() 
	{
		return Titulo;
	}	



}
