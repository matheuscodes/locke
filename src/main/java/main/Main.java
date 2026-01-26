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
package main;

import java.io.IOException;
import java.io.PrintStream;
import java.util.GregorianCalendar;

import windows.Carregando;
import windows.Geral;
import core.Biblioteca;
import core.Sistema;


public class Main {

	public static void main(String[] args) throws IOException {
		GregorianCalendar data = new GregorianCalendar();
		String dia = data.get(GregorianCalendar.YEAR)+"-";
		if(data.get(GregorianCalendar.MONTH) < 10)
			dia += "0"+data.get(GregorianCalendar.MONTH)+"-";
		else
			dia += data.get(GregorianCalendar.MONTH)+"-";
		if(data.get(GregorianCalendar.DATE) < 10)
			dia += "0"+data.get(GregorianCalendar.DATE)+" ";
		else
			dia += data.get(GregorianCalendar.DATE)+" ";
		if(data.get(GregorianCalendar.HOUR) < 10)
			dia += "0"+data.get(GregorianCalendar.HOUR)+"h";
		else
			dia += data.get(GregorianCalendar.HOUR)+"h";
		if(data.get(GregorianCalendar.MINUTE) < 10)
			dia += "0"+data.get(GregorianCalendar.MINUTE)+"m";
		else
			dia += data.get(GregorianCalendar.MINUTE)+"m";
		if(data.get(GregorianCalendar.SECOND) < 10)
			dia += "0"+data.get(GregorianCalendar.SECOND)+"s";
		else
			dia += data.get(GregorianCalendar.SECOND)+"s";
		System.setErr(new PrintStream("ErrorLog["+ dia + "].txt"));
		System.setOut(new PrintStream("Log["+ dia + "].txt"));
		
		Sistema.Barra = new Carregando();
		Sistema.Barra.setVisible(true);
		
		
		Sistema.BiblioteCAASO = new Biblioteca();
		
		Sistema.Barra.getCarregando().setString("Finalizando...");
		Sistema.Barra.getCarregando().setValue(80);
		/** This needs to remain here **/
		/** Due to dependency on library entirely set up **/
		System.out.println("Inicializando Locações...");
		Sistema.BiblioteCAASO.inicializaLOCACOES();
		Sistema.Barra.getCarregando().setValue(100);
		System.out.println("Biblioteca Carregada...");
		
		/**Wait a second**/
		long i = System.currentTimeMillis();
		while(System.currentTimeMillis() - i < 1000);
		
		Geral janelaPrincipal = new Geral();
		Sistema.Barra.setVisible(false);
		janelaPrincipal.setVisible(true);
		
		Sistema.BiblioteCAASO.filerize();
	}

}
