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
import java.awt.Font;

import javax.swing.*;


import java.awt.BorderLayout;

import com.geberl.winggcodedesigner.model.Project;
import com.geberl.winggcodedesigner.model.ProjectFactory;
import com.geberl.winggcodedesigner.model.Settings;
import com.geberl.winggcodedesigner.model.SettingsFactory;
import com.geberl.winggcodedesigner.model.WingCalculatorModel;
import com.geberl.winggcodedesigner.uielements.SettingsPanel;
import com.geberl.winggcodedesigner.uielements.ProfilesDrawPanel;
import com.geberl.winggcodedesigner.uielements.ProjectPanel;
import com.geberl.winggcodedesigner.uielements.WingCalculatorPanel;
import com.geberl.winggcodedesigner.uielements.WingDrawPanel;


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
        
        Settings settings;
        Project project;

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int frameWidth = 970;
        int frameHeight = 880;
        setSize(frameWidth, frameHeight);
        // setMinimumSize(new java.awt.Dimension(955, 850));
        setMinimumSize(new java.awt.Dimension(200, 200));
        setPreferredSize(new java.awt.Dimension(940, 800));
        setLocation(60, 60);

        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());
        
        settings = SettingsFactory.loadSettings();
        ProjectFactory.setSettings(settings);
        project = ProjectFactory.newProject();
        
        WingCalculatorModel wingCalculatorModel = new WingCalculatorModel(project,settings);
        
        // --------------------
        
        SettingsPanel settingsPanel = new SettingsPanel(settings);
 
        // --------------------
       
        JPanel projectPanelContainer = new JPanel();
        projectPanelContainer.setLayout(null);
        projectPanelContainer.setPreferredSize(new java.awt.Dimension(940, 800));

        
        ProjectPanel projectDataPanel = new ProjectPanel(wingCalculatorModel);
        projectDataPanel.setBounds(5, 5, 940, 320);
        projectDataPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        projectPanelContainer.add(projectDataPanel);
        
        WingCalculatorPanel calculatorPanel = new WingCalculatorPanel(wingCalculatorModel);
        calculatorPanel.setBounds(5, 330, 265, 440);
        calculatorPanel.setBorder(BorderFactory.createLoweredBevelBorder());
        projectPanelContainer.add(calculatorPanel);
        
        // Draw Panels
        ProfilesDrawPanel profileDrawPanel = new ProfilesDrawPanel(wingCalculatorModel, 620);
		WingDrawPanel wingDrawPanel = new WingDrawPanel(wingCalculatorModel, 620);

        javax.swing.JScrollPane profileDrawScrollPanel = new javax.swing.JScrollPane(profileDrawPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        javax.swing.JScrollPane wingDrawScrollPanel = new javax.swing.JScrollPane(wingDrawPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		
        JTabbedPane tabbedPanelDrawings = new JTabbedPane();
        tabbedPanelDrawings.setFont(new Font("DejaVu Sans", Font.BOLD, 12));
        tabbedPanelDrawings.setBounds(285,335,660,440);
        tabbedPanelDrawings.addTab("Profile Schematics",profileDrawScrollPanel);
        tabbedPanelDrawings.addTab("Wing Schematics",wingDrawScrollPanel);
 
        projectPanelContainer.add(tabbedPanelDrawings, BorderLayout.CENTER);
		
		ProjectFactory.addProjectChangeListener(wingCalculatorModel);
        
        wingCalculatorModel.addWingCalculatorEventListener(calculatorPanel);
        wingCalculatorModel.addWingCalculatorEventListener(profileDrawPanel);
		wingCalculatorModel.addWingCalculatorEventListener(wingDrawPanel);
		
        // --------------------
        
        JTabbedPane tabbedPanelMain = new JTabbedPane();
        tabbedPanelMain.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
        // tabbedPanelMain.setBorder(javax.swing.BorderFactory.createLoweredBevelBorder());
        // tabbedPanelMain.setBounds(190,0,610,480);
        // javax.swing.JScrollPane wingCalculatorScrollPanel = new javax.swing.JScrollPane(wingCalculatorPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        javax.swing.JScrollPane wingCalculatorScrollPanel = new javax.swing.JScrollPane(projectPanelContainer, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        javax.swing.JScrollPane parameterPanelScrollPanel = new javax.swing.JScrollPane(settingsPanel, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
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
