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

import database.Item;
import database.JOGO;

/**
 * The {@code JOGOs} class models a Group type which holds Games.
 * 
 * @author Matheus Borges Teixeira
 * @version 1.0
 */
public class JOGOs extends Group 
{
	/** Games list **/
	public JOGO[] Acervo = null;

	/**
	 * Constructs a Game Group with a given name.
	 * @param nome defines the name of the game group.
	 * @throws IOException whenever there are problems creating the group into the disk.
	 */
	public JOGOs(String nome) throws IOException {
		super(nome);
		
		File auxiliar = new File("DB/JOGOS/" + nome + ".txt");
		RandomAccessFile file = new RandomAccessFile(auxiliar,"rw");
		Integer tamanho = new Integer(file.readLine());
		
		Acervo = new JOGO[tamanho.intValue()];
		
		for(int i = 0; i < tamanho.intValue(); i++) {
			Acervo[i] = new JOGO(new Integer(file.readLine()).intValue(),new Boolean(file.readLine()).booleanValue(),file.readLine(),file.readLine(),file.readLine());
		}
		
		this.Lista = new Items();
		
		this.Lista.substituirOrdenando(Acervo);
		
		file.close();
	}
	
	/**
	 * @see database.lists.Group
	 */
	public JOGO[] getAcervo() {
		return Acervo;
	}

	/**
	 * @see database.lists.Group
	 */
	public void setAcervo(Item[] novo) {
		Acervo = (JOGO[])novo;
	}
	
	/**
	 * @see database.lists.Group
	 */
	public void remove(int onde) {
		Item[] reserva = this.getAcervo();
		JOGO[] auxiliar = new JOGO[reserva.length - 1];
		int i = 0;
		
		for(i = 0; i < onde; i++) {
			auxiliar[i] = (JOGO) reserva [i];
		}
		
		for(i = onde; i < auxiliar.length; i++) {
			auxiliar[i] = (JOGO) reserva[i+1];
		}
		
		this.setAcervo(auxiliar);
		this.Lista.substituirOrdenando(this.getAcervo());
	}
	
	/**
	 * @see database.lists.Group
	 */
	public void adicionaItem(String[] parametros) {
		int i = 0;
		JOGO novo = new JOGO(0,false,parametros[0],parametros[1],parametros[2]);
		JOGO[] auxiliar = new JOGO[getAcervo().length + 1];
		
		for(i = 0; i < getAcervo().length; i++) {
			auxiliar[i] = getAcervo()[i];
		}
		auxiliar[i] = novo;
		this.setAcervo(auxiliar);
		this.Lista.substituirOrdenando(this.getAcervo());
	}
}
