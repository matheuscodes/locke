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

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

public class Constantes {
	static public final Color BorderColor = new Color(0,153,153);
	static public final Color DefaultColor = new Color(0,153,153);
	static public final Color ContentColor = new Color(255,255,255);
	static public final Color BackgroundColor = new Color(0,0,0);
	static public final Border DefaultBorder = BorderFactory.createLineBorder(BorderColor,1);
	public static final short GAP = 5;
	
	
	static public Border makeTitledBorder(String s){
		return BorderFactory.createTitledBorder(DefaultBorder, s, TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, BorderColor);
	}
}
