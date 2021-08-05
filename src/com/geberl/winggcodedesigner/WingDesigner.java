/*
    Copyright 2021 Stephan Geberl

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

package com.geberl.winggcodedesigner;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.*;


import java.awt.BorderLayout;
import java.awt.Toolkit;


import com.geberl.winggcodedesigner.model.WingCalculatorModel;
import com.geberl.winggcodedesigner.uielements.WingCalculatorPanel;
import com.geberl.winggcodedesigner.utils.Settings;
import com.geberl.winggcodedesigner.utils.SettingsFactory;


/**
 */
public class WingDesigner extends JFrame {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private Settings settings;

	private javax.swing.JScrollPane wingCalculatorScrollPanel;
	private WingCalculatorPanel wingCalculatorPanel;
	private WingCalculatorModel wingCalculatorModel;

	/** starts MainWindow */
	public static void main(String[] args) { new WingDesigner(); }

	
    /** Creates new form MainWindow */
    public WingDesigner() {

        super("GCode Designer for Foam Wings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.settings = SettingsFactory.loadSettings();
        
        this.wingCalculatorModel = new WingCalculatorModel(this.settings);
        this.wingCalculatorPanel = new WingCalculatorPanel(wingCalculatorModel, this.settings);
        
        this.wingCalculatorModel.addWingCalculatorEventListener(this.wingCalculatorPanel);

        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (d.width - getSize().width) / 2;
        int y = (d.height - getSize().height) / 2 ;
        int frameWidth = d.width/2 - 60;
        int frameHeight = d.height/2;
        frameWidth = 1000;
        frameHeight = 900;
        setSize(frameWidth, frameHeight);
        setLocation(20, 60);

        wingCalculatorScrollPanel = new javax.swing.JScrollPane(wingCalculatorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        cp.add(wingCalculatorScrollPanel, BorderLayout.CENTER);
        

        /** look and feel */
        try {
           UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
           SwingUtilities.updateComponentTreeUI(this.getContentPane());
           } catch (Exception e) { System.out.println("Fehler bei der Oberflächenänderung"); }
        // ----------------------------------------------------------------------

        this.setVisible(true);
        
        Runtime.getRuntime().addShutdownHook(
        		new Thread(() -> {
        			System.out.println("Shutting down...");
         			SettingsFactory.saveSettings(settings);
        		}));

    }


}
