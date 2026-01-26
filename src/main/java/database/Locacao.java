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

import java.io.IOException;
import java.io.PrintStream;
import java.io.RandomAccessFile;
import java.util.GregorianCalendar;

import core.Sistema;
import database.lists.Items;

/**
 * The {@code Locacao} class models a Rental.
 * 
 * @author Matheus Borges Teixeira
 * @version 1.0
 */
public class Locacao 
{
	public int ID = 0;
	
	public String quem = null;
	public Item[] oque = null;
	public String paraquando = null;
	
	public Items lista = null;
	
	/**
	 * This constructor builds a rental based on an open file.
	 * 
	 * @param file defines a source to read information from.
	 * @throws IOException whenever there are problems reading the file.
	 */
	public Locacao(RandomAccessFile file) throws IOException {
		Integer a = new Integer(file.readLine());
		
		ID = a.intValue();
		
		quem = file.readLine();
		
		Integer tam = new Integer(file.readLine());
		
		oque = new Item[tam.intValue()];
		
		for(int i = 0; i < tam.intValue(); i++) {
			String b = file.readLine();
			oque[i] = Sistema.BiblioteCAASO.procuraItem(b)[0];
		}
		
		lista = new Items();
		
		if(oque != null) lista.substituirOrdenando(oque);
		
		paraquando = file.readLine();
	}
	
	/**
	 * Constructs a rental based on the given information.
	 * @param usuario specifies name of the user renting.
	 * @param itens defines the list of rented items.
	 * @param quando specifies the latest delivery date.
	 */
	public Locacao(String usuario, Item[] itens, GregorianCalendar quando) {
		ID = core.Sistema.BiblioteCAASO.consomeLocacao();
		
		quem = usuario;
		
		oque = itens;
		
		for(int i = 0; i < oque.length; i++) {
			oque[i].retirado = true;
		}
		
		lista = new Items();
		
		if(oque != null)lista.substituirOrdenando(oque);
		
		String auxiliar = "";
		
		if(quando.get(GregorianCalendar.DATE) < 10) {
			auxiliar += "0" + quando.get(GregorianCalendar.DATE) + "/"; 
		}
		else {
			auxiliar += quando.get(GregorianCalendar.DATE) + "/";
		}
		
		if(quando.get(GregorianCalendar.MONTH) < 10) {
			auxiliar += "0" + quando.get(GregorianCalendar.MONTH) + "/"; 
		}
		else {
			auxiliar += quando.get(GregorianCalendar.MONTH) + "/";
		}
		auxiliar += "" + (quando.get(GregorianCalendar.YEAR));
		
		paraquando = auxiliar;
	}
	/**
	 * This returns a String representation of the rental.
	 * 
	 * @return a string describing briefly the pending rental.
	 */
	public String toString() {
		return quem + ", " + oque.length + " items. Devolver até "+ paraquando;
	}
	
	/**
	 * Writes the rental to a file.
	 * 
	 * @param file specifies which file to write to.
	 */
	public void toFile(PrintStream file) {
		file.println(ID);
		file.println(quem);
		file.println(oque.length);
		for(int i = 0; i < oque.length;i++) {
			file.println(oque[i].getIdentity());
		}
		file.println(paraquando);
	}
}
