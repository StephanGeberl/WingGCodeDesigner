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
//import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.JButton;


import com.geberl.winggcodedesigner.model.WingCalculatorModel;
import com.geberl.winggcodedesigner.types.ProjectFactory;
import com.geberl.winggcodedesigner.utils.GUIHelpers;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.NumberFormatter;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.beans.PropertyChangeEvent;
import javax.swing.JSeparator;

public class ParameterPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private WingCalculatorModel wingCalculatorModel;

	public ParameterPanel(WingCalculatorModel anWingDesignerModel) {
		
		this.wingCalculatorModel = anWingDesignerModel;
		
		
		setForeground(Color.LIGHT_GRAY);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(826, 367));
		
		this.createControls();
	}

	//	@PostConstruct
    public void createControls() {

		// ======= Formatter ============
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator('.');
		
		NumberFormat doubleFormat = new DecimalFormat("#.00", symbols);
		doubleFormat.setMaximumFractionDigits(2);
		
		DecimalFormat decimalFormat = new DecimalFormat("+#.00;-#", symbols);
		decimalFormat.setMaximumFractionDigits(2);
		
		NumberFormat integerFormat = new DecimalFormat("#");
		
		NumberFormat degreeFormat = new DecimalFormat("+#.0;-#", symbols);
		degreeFormat.setMaximumFractionDigits(1);
		
		NumberFormatter doubleFormatter = new NumberFormatter(doubleFormat);
		doubleFormatter.setMinimum(0.00);
		doubleFormatter.setMaximum(2000.00);
		doubleFormatter.setAllowsInvalid(false);
		
		NumberFormatter decimalFormater = new NumberFormatter(decimalFormat);
		decimalFormater.setMinimum(-2000.00);
		decimalFormater.setMaximum(2000.00);
		decimalFormater.setAllowsInvalid(false);

		NumberFormatter integerFormatter = new NumberFormatter(integerFormat);
		integerFormatter.setMinimum(0);
		integerFormatter.setMaximum(2000);
		integerFormatter.setAllowsInvalid(false);

		NumberFormatter degreeFormatter = new NumberFormatter(degreeFormat);
		degreeFormatter.setMinimum(-90.00);
		degreeFormatter.setMaximum(90.00);
		degreeFormatter.setAllowsInvalid(false);
		// ================================================
		
		// ============= Daten erfassen ===================
		// ------------------------------------------
		// ============= Parameter ===================

		JFormattedTextField inputWireLength = new JFormattedTextField(integerFormatter);
		inputWireLength.setText(String.valueOf(wingCalculatorModel.settings.getWireLength()));
		inputWireLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWireLength.getValue() != null) { wingCalculatorModel.settings.setWireLength( Double.parseDouble(inputWireLength.getText()) ); }
			}
		});
		inputWireLength.setBounds(195, 6, 98, 25);
		add(inputWireLength);
		// ------------------------------------------
		JFormattedTextField inputStartDistance = new JFormattedTextField(integerFormatter);
		inputStartDistance.setText(String.valueOf(wingCalculatorModel.settings.getStartDistance()));
		inputStartDistance.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputStartDistance.getValue() != null) { wingCalculatorModel.settings.setStartDistance( Double.parseDouble(inputStartDistance.getText()) ); }
			}
		});
		inputStartDistance.setBounds(195, 37, 98, 25);
		add(inputStartDistance);
		// ------------------------------------------
		JFormattedTextField inputSaveHeight = new JFormattedTextField(integerFormatter);
		inputSaveHeight.setText(String.valueOf(wingCalculatorModel.settings.getSaveHeight()));
		inputSaveHeight.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSaveHeight.getValue() != null) { wingCalculatorModel.settings.setSaveHeight( Double.parseDouble(inputSaveHeight.getText()) ); }
			}
		});
		inputSaveHeight.setBounds(195, 68, 98, 25);
		add(inputSaveHeight);
		// ------------------------------------------
		JFormattedTextField inputPause = new JFormattedTextField(integerFormatter);
		inputPause.setText(String.valueOf(wingCalculatorModel.settings.getPause()));
		inputPause.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputPause.getValue() != null) { wingCalculatorModel.settings.setPause( Double.parseDouble(inputPause.getText()) ); }
			}
		});
		inputPause.setBounds(195, 99, 98, 25);
		add(inputPause);
		// ------------------------------------------
		JFormattedTextField inputWireSpeed = new JFormattedTextField(integerFormatter);
		inputWireSpeed.setText(String.valueOf(wingCalculatorModel.settings.getWireSpeed()));
		inputWireSpeed.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWireSpeed.getValue() != null) { wingCalculatorModel.settings.setWireSpeed( Double.parseDouble(inputWireSpeed.getText()) ); }
			}
		});
		inputWireSpeed.setBounds(195, 130, 98, 25);
		add(inputWireSpeed);
		// ------------------------------------------
		JFormattedTextField inputTravelSpeed = new JFormattedTextField(integerFormatter);
		inputTravelSpeed.setText(String.valueOf(wingCalculatorModel.settings.getTravelSpeed()));
		inputTravelSpeed.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputTravelSpeed.getValue() != null) { wingCalculatorModel.settings.setTravelSpeed( Double.parseDouble(inputTravelSpeed.getText()) ); }
			}
		});
		inputTravelSpeed.setBounds(195, 161, 98, 25);
		add(inputTravelSpeed);
		// ------------------------------------------
		JCheckBox cbxCutBaseFirst = new JCheckBox("Cut base first");
		cbxCutBaseFirst.setSelected(wingCalculatorModel.settings.getCutBaseFirst());
		cbxCutBaseFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wingCalculatorModel.settings.setCutBaseFirst(cbxCutBaseFirst.isSelected());
			}
		});
		cbxCutBaseFirst.setBounds(16, 203, 167, 18);
		add(cbxCutBaseFirst);
		// ------------------------------------------
		JTextField inputDefaultProjectDirectory = new JTextField();
		inputDefaultProjectDirectory.setEditable(false);
		inputDefaultProjectDirectory.setText(wingCalculatorModel.settings.getProjectDefaultPath());
		inputDefaultProjectDirectory.setBounds(195, 240, 432, 25);
		add(inputDefaultProjectDirectory);
		// ------------------------------------------
		JTextField inputDefaultProfileDirectory = new JTextField();
		inputDefaultProfileDirectory.setEditable(false);
		inputDefaultProfileDirectory.setText(wingCalculatorModel.settings.getProfileDefaultPath());
		inputDefaultProfileDirectory.setBounds(195, 272, 432, 25);
		add(inputDefaultProfileDirectory);
		// ------------------------------------------
		// ------------------------------------------
		JButton btnChangeProjectDirectory = new JButton("Set ...");
		btnChangeProjectDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String aNewPath = getDefaultPath( inputDefaultProjectDirectory.getText() );
				inputDefaultProjectDirectory.setText(aNewPath);
				wingCalculatorModel.settings.setProjectDefaultPath(aNewPath);	
			}
		});
		btnChangeProjectDirectory.setBounds(639, 240, 65, 27);
		add(btnChangeProjectDirectory);
		// ------------------------------------------
		JButton btnChangeProfileDirectory = new JButton("Set ...");
		btnChangeProfileDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String aNewPath = getDefaultPath( inputDefaultProfileDirectory.getText() );
				inputDefaultProfileDirectory.setText(aNewPath);
				wingCalculatorModel.settings.setProfileDefaultPath(aNewPath);	
			}
		});
		btnChangeProfileDirectory.setBounds(639, 272, 65, 27);
		add(btnChangeProfileDirectory);
		// ------------------------------------------
		JButton btnSaveParameters = new JButton("Save Parameters");
		btnSaveParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wingCalculatorModel.saveSettings();
				}
		});
		btnSaveParameters.setBounds(16, 318, 208, 27);
		add(btnSaveParameters);
		// ===============================================================
		
		
		JLabel lblInputWireLength = new JLabel("Wire length [mm]");
		lblInputWireLength.setBounds(16, 6, 167, 25);
		add(lblInputWireLength);
		
		JLabel lblInputStartDistance = new JLabel("X-Start-Distance [mm]");
		lblInputStartDistance.setBounds(16, 68, 167, 25);
		add(lblInputStartDistance);
		
		JLabel lblInputSaveHeight = new JLabel("Save Height [mm]");
		lblInputSaveHeight.setBounds(16, 37, 167, 25);
		add(lblInputSaveHeight);
		
		JLabel lblInputCordOffset_1 = new JLabel("Cut speed [m/s]");
		lblInputCordOffset_1.setBounds(16, 130, 167, 25);
		add(lblInputCordOffset_1);
		
		JLabel lblInputCordOffset_2 = new JLabel("Cut-Pause [s]");
		lblInputCordOffset_2.setBounds(16, 99, 167, 25);
		add(lblInputCordOffset_2);

		JLabel lblInputCordOffset_1_1 = new JLabel("Travel speed [m/s]");
		lblInputCordOffset_1_1.setBounds(16, 161, 167, 25);
		add(lblInputCordOffset_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(16, 219, 1, 2);
		add(separator);
		
		JLabel lblInputCordOffset_1_1_1 = new JLabel("Default project directory");
		lblInputCordOffset_1_1_1.setBounds(16, 240, 167, 25);
		add(lblInputCordOffset_1_1_1);
		
		JLabel lblInputCordOffset_1_1_1_1 = new JLabel("Default profile directory");
		lblInputCordOffset_1_1_1_1.setBounds(16, 272, 167, 25);
		add(lblInputCordOffset_1_1_1_1);
		
	}
    
    
	private String getDefaultPath(String dirPath) {

		String newPath = "";
		JFileChooser projectFileChooser = new JFileChooser(dirPath);
		projectFileChooser.setDialogTitle("Select Directory");
		projectFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		projectFileChooser.setAcceptAllFileFilterUsed(false);

		
		int returnVal = projectFileChooser.showOpenDialog(this);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File fileReturn = projectFileChooser.getSelectedFile();
				newPath = fileReturn.getAbsolutePath();
			
			} catch (Exception ex) {
				GUIHelpers.displayErrorDialog("Problem saving project file: " + ex.getMessage());
				newPath = dirPath;
			}
		}
		else {
			newPath = dirPath;
		}

		return newPath;
		
	}    
    
    
    
    
}
