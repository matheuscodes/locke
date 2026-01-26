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
package windows;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JRadioButton;

import core.Sistema;

public class Ordenacoes extends JPanel {

	private static final Dimension SIZE = new Dimension(130,20);
	private static final long serialVersionUID = 1L;
	private JRadioButton one = null;
	private JRadioButton zero = null;
	private JRadioButton two = null;
	private JRadioButton three = null;
	
	private String first = null;
	private String second = null;
	private String third = null;

	public Ordenacoes(String A, String B, String C) {
		super();
		first = A;
		second = B;
		third = C;
		
		inicializar();
	}

	private void inicializar() {
		this.setBackground(Constantes.BackgroundColor);
		this.setBorder(Constantes.makeTitledBorder("Ordenar Por: "));
		this.add(getZero());
		this.add(getOne());
		this.add(getTwo());
		this.add(getThree());
		
		switch(Sistema.ModoOrdenacao) {
			case 0: {
				getZero().setSelected(true);
				break;
			}
			case 1: {
				getOne().setSelected(true);
				break;
			}
			case 2: {
				getTwo().setSelected(true);
				break;
			}
			case 3: {
				getThree().setSelected(true);
				break;
			}
		}
	}

	private JRadioButton getOne() {
		if (one == null) {
			one = new JRadioButton();
			one.setPreferredSize(SIZE);
			one.setBackground(Constantes.BackgroundColor);
			one.setForeground(Constantes.DefaultColor);
			one.setText(first);
			one.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					unsetAllbut(getOne());
					getOne().setSelected(true);
					
					Sistema.ModoOrdenacao = 1;
					
					Acervos auxiliar = (Acervos)getParent();
					if (auxiliar.getAcervoOn() != null)	auxiliar.getAcervoOn().Lista.substituirOrdenando(auxiliar.getAcervoOn().getAcervo());
				}
			});
		}
		return one;
	}

	private JRadioButton getZero() {
		if (zero == null) {
			zero = new JRadioButton();
			zero.setPreferredSize(SIZE);
			zero.setBackground(Constantes.BackgroundColor);
			zero.setForeground(Constantes.DefaultColor);
			zero.setText("Retiradas");
			zero.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					unsetAllbut(getZero());
					getZero().setSelected(true);
					
					Sistema.ModoOrdenacao = 0;
					
					Acervos auxiliar = (Acervos)getParent();
					if (auxiliar.getAcervoOn() != null)	auxiliar.getAcervoOn().Lista.substituirOrdenando(auxiliar.getAcervoOn().getAcervo());
				}
			});
		}
		return zero;
	}

	private JRadioButton getTwo() {
		if (two == null) {
			two = new JRadioButton();
			two.setPreferredSize(SIZE);
			two.setBackground(Constantes.BackgroundColor);
			two.setForeground(Constantes.DefaultColor);
			two.setText(second);
			two.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					unsetAllbut(getTwo());
					getTwo().setSelected(true);
					
					Sistema.ModoOrdenacao = 2;
					
					Acervos auxiliar = (Acervos)getParent();
					if (auxiliar.getAcervoOn() != null)	auxiliar.getAcervoOn().Lista.substituirOrdenando(auxiliar.getAcervoOn().getAcervo());
				}
			});
		}
		return two;
	}

	private JRadioButton getThree() {
		if (three == null) {
			three = new JRadioButton();
			three.setPreferredSize(SIZE);
			three.setBackground(Constantes.BackgroundColor);
			three.setForeground(Constantes.DefaultColor);
			three.setText(third);
			three.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					unsetAllbut(getThree());
					getThree().setSelected(true);
					
					Sistema.ModoOrdenacao = 3;
					
					Acervos auxiliar = (Acervos)getParent();
					if (auxiliar.getAcervoOn() != null)	auxiliar.getAcervoOn().Lista.substituirOrdenando(auxiliar.getAcervoOn().getAcervo());
				}
			});
		}
		return three;
	}
	
	private void unsetAllbut(JRadioButton butthis) {
		if(butthis != getZero()) getZero().setSelected(false);
		if(butthis != getOne()) getOne().setSelected(false);
		if(butthis != getTwo()) getTwo().setSelected(false);
		if(butthis != getThree()) getThree().setSelected(false);
	}
	
	public void setNames(String A, String B, String C) {
		first = A;
		second = B;
		third = C;
		
		getOne().setText(A);
		getTwo().setText(B);
		getThree().setText(C);
	}
	
	public void unsetAll() {
		getZero().setSelected(false);
		getOne().setSelected(false);
		getTwo().setSelected(false);
		getThree().setSelected(false);
	}
	
	public void setOrdem(int i) {
		switch(i) {
		   case 0: 
			   	getZero().setSelected(true);
		   		break;
		   case 1: 
		   		getOne().setSelected(true);
	   			break;
		   case 2: 
		   		getTwo().setSelected(true);
	   			break;
		   case 3: 
		   		getThree().setSelected(true);
	   			break;
		}
	}
}

