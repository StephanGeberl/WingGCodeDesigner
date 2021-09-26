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
import java.awt.Font;

import javax.swing.*;


import java.awt.BorderLayout;
import java.awt.Toolkit;

import com.geberl.winggcodedesigner.model.Settings;
import com.geberl.winggcodedesigner.model.SettingsFactory;
import com.geberl.winggcodedesigner.model.WingCalculatorModel;
import com.geberl.winggcodedesigner.uielements.ParameterPanel;
import com.geberl.winggcodedesigner.uielements.WingCalculatorPanel;
import com.geberl.winggcodedesigner.utils.GUIHelpers;


/**
 */
public class WingDesigner extends JFrame {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/** starts MainWindow */
	public static void main(String[] args) { new WingDesigner(); }

	
    /** Creates new form MainWindow */
    public WingDesigner() {

        super("GCode Designer for Foam Wings");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int frameWidth = 1200;
        int frameHeight = 1000;
        setSize(frameWidth, frameHeight);
        setMinimumSize(new java.awt.Dimension(500, 200));
        setLocation(60, 60);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        WingCalculatorModel wingCalculatorModel = new WingCalculatorModel();
        WingCalculatorPanel wingCalculatorPanel = new WingCalculatorPanel(wingCalculatorModel);
        ParameterPanel parameterPanel = new ParameterPanel(wingCalculatorModel);
       
        wingCalculatorModel.addWingCalculatorEventListener(wingCalculatorPanel);
        
        
        JTabbedPane tabbedPanelMain = new JTabbedPane();
        tabbedPanelMain.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
        // tabbedPanelMain.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
        // tabbedPanelMain.setBounds(190,0,610,480);
        javax.swing.JScrollPane wingCalculatorScrollPanel = new javax.swing.JScrollPane(wingCalculatorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        javax.swing.JScrollPane parameterPanelScrollPanel = new javax.swing.JScrollPane(parameterPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        tabbedPanelMain.addTab("Project",wingCalculatorScrollPanel);
        tabbedPanelMain.addTab("Settings",parameterPanelScrollPanel);
        
        cp.add(tabbedPanelMain, BorderLayout.CENTER);
        

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
        		}));

    }


}
