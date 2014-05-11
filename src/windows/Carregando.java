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

import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;

public class Carregando extends JWindow {

	private static final long serialVersionUID = 1L;
	private JPanel tudo = null;
	private JProgressBar carregando = null;
	private JLabel fundo = null;
	
	static public final int WIDTH = 520;
	static public final int HEIGHT = 190;

	public Carregando() {
		super();
		initialize();
	}

	private void initialize() {
		Rectangle r = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		this.setBounds(new Rectangle((r.width/2)-(WIDTH/2),(r.height/2)-(HEIGHT/2),WIDTH,HEIGHT));
        this.setContentPane(getTudo());
	}

	private JPanel getTudo() {
		if (tudo == null) {
			fundo = new JLabel();
			fundo.setBounds(new Rectangle(0,0,WIDTH,HEIGHT));
			fundo.setIcon(new ImageIcon(getClass().getResource("/logo-carregando.png")));
			
			tudo = new JPanel();
			tudo.setLayout(null);
			tudo.add(getCarregando());
			tudo.add(fundo);			
		}
		return tudo;
	}

	public JProgressBar getCarregando() {
		if (carregando == null) {
			carregando = new JProgressBar();
			carregando.setBounds(new Rectangle(265,115,205,18));
			carregando.setStringPainted(true);
		}
		return carregando;
	}
}
