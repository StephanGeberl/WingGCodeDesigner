/*
    Copyright 2020 Stephan Geberl

    This file is part of WingGCodeDesigner.

    WingGCodeDesigner is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    WingGCodeDesigner is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with WingGCodeDesigner.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.geberl.winggcodedesigner.uielements;

import java.awt.Dimension;
import javax.swing.JPanel;
import java.awt.FlowLayout;

public class HelpPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public HelpPanel() {
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(947, 638));
		
		this.createControls();
	}

	//	@PostConstruct
    public void createControls() {

		// ======= Formatter ============

		
		JPanelBackground helpPicturePanel = new JPanelBackground();
		FlowLayout flowLayout = (FlowLayout) helpPicturePanel.getLayout();
		helpPicturePanel.setBorder(null);
		helpPicturePanel.setBounds(0, 0, 637, 626);
		helpPicturePanel.setBackgroundImagePath("/resources/icons/HelpPicture.gif");
		add(helpPicturePanel);
		
	}

}
