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

import com.geberl.winggcodedesigner.model.Settings;
import com.geberl.winggcodedesigner.model.SettingsFactory;
import com.geberl.winggcodedesigner.utils.GUIHelpers;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.beans.PropertyChangeEvent;

public class SettingsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private Settings settings;
	
	JButton btnSaveParameters = null;

	public SettingsPanel(Settings aSettings) {
		
		this.settings = aSettings;
		
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
		inputWireLength.setText(String.valueOf(settings.getWireLength()));
		inputWireLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWireLength.getValue() != null) { settings.setWireLength( Double.parseDouble(inputWireLength.getText()) ); }
			}
		});
		inputWireLength.setBounds(195, 6, 98, 25);
		add(inputWireLength);
		// ------------------------------------------
		JFormattedTextField inputXAxisMax = new JFormattedTextField(integerFormatter);
		inputXAxisMax.setText(String.valueOf(settings.getXAxisMax()));
		inputXAxisMax.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputXAxisMax.getValue() != null) { settings.setXAxisMax( Double.parseDouble(inputXAxisMax.getText()) ); }
			}
		});
		inputXAxisMax.setBounds(195, 31, 98, 25);
		add(inputXAxisMax);
		// ------------------------------------------
		JFormattedTextField inputYAxisMax = new JFormattedTextField(integerFormatter);
		inputYAxisMax.setText(String.valueOf(settings.getYAxisMax()));
		inputYAxisMax.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputYAxisMax.getValue() != null) { settings.setYAxisMax( Double.parseDouble(inputYAxisMax.getText()) ); }
			}
		});
		inputYAxisMax.setBounds(195, 57, 98, 25);
		add(inputYAxisMax);
		// ------------------------------------------
		JFormattedTextField inputStartDistance = new JFormattedTextField(integerFormatter);
		inputStartDistance.setText(String.valueOf(settings.getStartDistance()));
		inputStartDistance.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputStartDistance.getValue() != null) { settings.setStartDistance( Double.parseDouble(inputStartDistance.getText()) ); }
			}
		});
		inputStartDistance.setBounds(195, 82, 98, 25);
		add(inputStartDistance);
		// ------------------------------------------
		JFormattedTextField inputSaveHeight = new JFormattedTextField(integerFormatter);
		inputSaveHeight.setText(String.valueOf(settings.getSaveHeight()));
		inputSaveHeight.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSaveHeight.getValue() != null) { settings.setSaveHeight( Double.parseDouble(inputSaveHeight.getText()) ); }
			}
		});
		inputSaveHeight.setBounds(195, 108, 98, 25);
		add(inputSaveHeight);
		// ------------------------------------------
		JFormattedTextField inputPause = new JFormattedTextField(integerFormatter);
		inputPause.setText(String.valueOf(settings.getPause()));
		inputPause.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputPause.getValue() != null) { settings.setPause( Double.parseDouble(inputPause.getText()) ); }
			}
		});
		inputPause.setBounds(195, 133, 98, 25);
		add(inputPause);
		// ------------------------------------------
		JFormattedTextField inputWireSpeed = new JFormattedTextField(integerFormatter);
		inputWireSpeed.setText(String.valueOf(settings.getWireSpeed()));
		inputWireSpeed.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWireSpeed.getValue() != null) { settings.setWireSpeed( Double.parseDouble(inputWireSpeed.getText()) ); }
			}
		});
		inputWireSpeed.setBounds(195, 158, 98, 25);
		add(inputWireSpeed);
		// ------------------------------------------
		JFormattedTextField inputTravelSpeed = new JFormattedTextField(integerFormatter);
		inputTravelSpeed.setText(String.valueOf(settings.getTravelSpeed()));
		inputTravelSpeed.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputTravelSpeed.getValue() != null) { settings.setTravelSpeed( Double.parseDouble(inputTravelSpeed.getText()) ); }
			}
		});
		inputTravelSpeed.setBounds(195, 182, 98, 25);
		add(inputTravelSpeed);
		// ------------------------------------------

		// ------------------------------------------
		JTextField inputDefaultProjectDirectory = new JTextField();
		inputDefaultProjectDirectory.setEditable(false);
		inputDefaultProjectDirectory.setText(settings.getProjectDefaultPath());
		inputDefaultProjectDirectory.setBounds(195, 209, 432, 25);
		add(inputDefaultProjectDirectory);
		// ------------------------------------------
		JTextField inputDefaultProfileDirectory = new JTextField();
		inputDefaultProfileDirectory.setEditable(false);
		inputDefaultProfileDirectory.setText(settings.getProfileDefaultPath());
		inputDefaultProfileDirectory.setBounds(195, 233, 432, 25);
		add(inputDefaultProfileDirectory);
		// ------------------------------------------
		// ------------------------------------------
		JButton btnChangeProjectDirectory = new JButton("Change ...");
		btnChangeProjectDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String aNewPath = getDefaultPath( inputDefaultProjectDirectory.getText() );
				inputDefaultProjectDirectory.setText(aNewPath);
				settings.setProjectDefaultPath(aNewPath);	
			}
		});
		btnChangeProjectDirectory.setBounds(639, 209, 100, 27);
		add(btnChangeProjectDirectory);
		// ------------------------------------------
		JButton btnChangeProfileDirectory = new JButton("Change ...");
		btnChangeProfileDirectory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String aNewPath = getDefaultPath( inputDefaultProfileDirectory.getText() );
				inputDefaultProfileDirectory.setText(aNewPath);
				settings.setProfileDefaultPath(aNewPath);	
			}
		});
		btnChangeProfileDirectory.setBounds(639, 233, 100, 27);
		add(btnChangeProfileDirectory);
		// ------------------------------------------
		
		btnSaveParameters = new JButton("Save Parameters");
		btnSaveParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SettingsFactory.saveSettings();
				}
		});
		btnSaveParameters.setBounds(16, 270, 208, 27);
		add(btnSaveParameters);
		btnSaveParameters.setEnabled(true);
		// ===============================================================
		
		JLabel lblInputWireLength = new JLabel("Wire length [mm]");
		lblInputWireLength.setBounds(16, 6, 167, 25);
		add(lblInputWireLength);
		
		JLabel lblXaxisMaxmm = new JLabel("X-Axis max [mm]");
		lblXaxisMaxmm.setBounds(16, 31, 167, 25);
		add(lblXaxisMaxmm);
		
		JLabel lblInputWireLength_1_1 = new JLabel("Y-Axis max [mm]");
		lblInputWireLength_1_1.setBounds(16, 57, 167, 25);
		add(lblInputWireLength_1_1);
		
		JLabel lblInputStartDistance = new JLabel("X-Start-Distance [mm]");
		lblInputStartDistance.setBounds(16, 108, 167, 25);
		add(lblInputStartDistance);
		
		JLabel lblInputSaveHeight = new JLabel("Save Height [mm]");
		lblInputSaveHeight.setBounds(16, 82, 167, 25);
		add(lblInputSaveHeight);
		
		JLabel lblInputCordOffset_1 = new JLabel("Cut speed [m/s]");
		lblInputCordOffset_1.setBounds(16, 158, 167, 25);
		add(lblInputCordOffset_1);
		
		JLabel lblInputCordOffset_2 = new JLabel("Cut-Pause [s]");
		lblInputCordOffset_2.setBounds(16, 133, 167, 25);
		add(lblInputCordOffset_2);

		JLabel lblInputCordOffset_1_1 = new JLabel("Travel speed [m/s]");
		lblInputCordOffset_1_1.setBounds(16, 182, 167, 25);
		add(lblInputCordOffset_1_1);

		JLabel lblInputCordOffset_1_1_1 = new JLabel("Default project directory");
		lblInputCordOffset_1_1_1.setBounds(16, 209, 167, 25);
		add(lblInputCordOffset_1_1_1);
		
		JLabel lblInputCordOffset_1_1_1_1 = new JLabel("Default profile directory");
		lblInputCordOffset_1_1_1_1.setBounds(16, 233, 167, 25);
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
