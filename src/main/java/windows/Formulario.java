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

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Formulario extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final Dimension size = new Dimension(100,25);
	
	private JPanel one = null;
	private JLabel onelabel = null;
	private JTextField fieldone = null;
	
	private JPanel two = null;
	private JLabel twolabel = null;
	private JTextField fieldtwo = null;
	
	private JPanel three = null;
	private JLabel threelabel = null;
	private JTextField fieldthree = null;
	
	private JPanel twoandthree = null;
	
	private String nome = null;
	private String first = null;
	private String second = null;
	private String third = null;
	
	public Formulario() {
		super();
		nome = "name";
		first = "A";
		second = "B";
		third = "C";
		inicializar();
	}
	
	public Formulario(String name, String A, String B, String C) {
		super();
		nome = name;
		first = A;
		second = B;
		third = C;
		inicializar();
	}

	private void inicializar() {
		BorderLayout borderLayout = new BorderLayout();
		borderLayout.setHgap(Constantes.GAP);
		borderLayout.setVgap(Constantes.GAP);
		setLayout(borderLayout);
		setBorder(Constantes.makeTitledBorder(nome));
		setBackground(Constantes.BackgroundColor);
		setPreferredSize(new Dimension(100,(size.height+10)*3+13));
		add(getPrimeiro(), BorderLayout.SOUTH);
		add(getCascata(), BorderLayout.NORTH);
	}

	private JPanel getPrimeiro() {
		if (one == null) {
			onelabel = new JLabel();
			onelabel.setText(third);
			onelabel.setForeground(Constantes.DefaultColor);
			onelabel.setBackground(Constantes.BackgroundColor);
			
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(Constantes.GAP);
			borderLayout.setVgap(Constantes.GAP);
			
			one = new JPanel();
			one.setPreferredSize(size);
			one.setBackground(Constantes.BackgroundColor);
			one.setLayout(borderLayout);
			one.add(onelabel, BorderLayout.WEST);
			one.add(getFieldone(), BorderLayout.CENTER);
		}
		return one;
	}

	private JPanel getCascata() {
		if (twoandthree == null) {
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setVgap(Constantes.GAP);
			borderLayout.setHgap(Constantes.GAP);
			twoandthree = new JPanel();
			twoandthree.setBackground(Constantes.BackgroundColor);
			twoandthree.setLayout(borderLayout);
			twoandthree.add(getSegundo(), BorderLayout.NORTH);
			twoandthree.add(getTerceiro(), BorderLayout.SOUTH);
		}
		return twoandthree;
	}

	private JPanel getSegundo() {
		if (two == null) {
			twolabel = new JLabel();
			twolabel.setText(first);
			twolabel.setForeground(Constantes.DefaultColor);
			
			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(Constantes.GAP);
			borderLayout.setVgap(Constantes.GAP);
			
			two = new JPanel();
			two.setPreferredSize(size);
			two.setBackground(Constantes.BackgroundColor);
			two.setLayout(borderLayout);
			two.add(twolabel, BorderLayout.WEST);
			two.add(getFieldtwo(), BorderLayout.CENTER);
		}
		return two;
	}

	private JPanel getTerceiro() {
		if (three == null) {
			threelabel = new JLabel();
			threelabel.setText(second);
			threelabel.setForeground(Constantes.DefaultColor);

			BorderLayout borderLayout = new BorderLayout();
			borderLayout.setHgap(Constantes.GAP);
			borderLayout.setVgap(Constantes.GAP);
			
			three = new JPanel();
			three.setPreferredSize(size);
			three.setBackground(Constantes.BackgroundColor);
			three.setLayout(borderLayout);
			three.add(threelabel, BorderLayout.WEST);
			three.add(getFieldthree(), BorderLayout.CENTER);
		}
		return three;
	}
	
	private JTextField getFieldone() {
		if (fieldone == null) {
			fieldone = new JTextField();
		}
		return fieldone;
	}

	private JTextField getFieldtwo() {
		if (fieldtwo == null) {
			fieldtwo = new JTextField();
		}
		return fieldtwo;
	}

	private JTextField getFieldthree() {
		if (fieldthree == null) {
			fieldthree = new JTextField();
		}
		return fieldthree;
	}
	
	public void limpa() {
		getFieldone().setText("");
		getFieldtwo().setText("");
		getFieldthree().setText("");
	}
	
	public String[] getConteudo() {
		String[] auxiliar = new String[3];
		auxiliar[0] = getFieldtwo().getText();
		auxiliar[1] = getFieldthree().getText();
		auxiliar[2] = getFieldone().getText();
		
		return auxiliar;
	}
}

