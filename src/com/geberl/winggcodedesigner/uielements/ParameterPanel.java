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

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JSeparator;

public class ParameterPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private WingCalculatorModel wingCalculatorModel;
	private JTextField inputProjectName;

	public ParameterPanel(WingCalculatorModel anWingDesignerModel) {
		
		this.wingCalculatorModel = anWingDesignerModel;
		
		
		setForeground(Color.LIGHT_GRAY);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(700, 600));
		
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
		inputWireLength.setBounds(195, 12, 75, 25);
		add(inputWireLength);
		// ------------------------------------------
		JFormattedTextField inputStartDistance = new JFormattedTextField(integerFormatter);
		inputStartDistance.setText(String.valueOf(wingCalculatorModel.settings.getStartDistance()));
		inputStartDistance.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputStartDistance.getValue() != null) { wingCalculatorModel.settings.setStartDistance( Double.parseDouble(inputStartDistance.getText()) ); }
			}
		});
		inputStartDistance.setBounds(195, 38, 75, 25);
		add(inputStartDistance);
		// ------------------------------------------
		JFormattedTextField inputSaveHeight = new JFormattedTextField(integerFormatter);
		inputSaveHeight.setText(String.valueOf(wingCalculatorModel.settings.getSaveHeight()));
		inputSaveHeight.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSaveHeight.getValue() != null) { wingCalculatorModel.settings.setSaveHeight( Double.parseDouble(inputSaveHeight.getText()) ); }
			}
		});
		inputSaveHeight.setBounds(195, 64, 75, 25);
		add(inputSaveHeight);
		// ------------------------------------------
		JFormattedTextField inputPause = new JFormattedTextField(integerFormatter);
		inputPause.setText(String.valueOf(wingCalculatorModel.settings.getPause()));
		inputPause.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputPause.getValue() != null) { wingCalculatorModel.settings.setPause( Double.parseDouble(inputPause.getText()) ); }
			}
		});
		inputPause.setBounds(195, 95, 75, 25);
		add(inputPause);
		// ------------------------------------------
		JFormattedTextField inputWireSpeed = new JFormattedTextField(integerFormatter);
		inputWireSpeed.setText(String.valueOf(wingCalculatorModel.settings.getWireSpeed()));
		inputWireSpeed.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWireSpeed.getValue() != null) { wingCalculatorModel.settings.setWireSpeed( Double.parseDouble(inputWireSpeed.getText()) ); }
			}
		});
		inputWireSpeed.setBounds(195, 135, 75, 25);
		add(inputWireSpeed);
		// ------------------------------------------
		JFormattedTextField inputTravelSpeed = new JFormattedTextField(integerFormatter);
		inputTravelSpeed.setText(String.valueOf(wingCalculatorModel.settings.getTravelSpeed()));
		inputTravelSpeed.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputTravelSpeed.getValue() != null) { wingCalculatorModel.settings.setTravelSpeed( Double.parseDouble(inputTravelSpeed.getText()) ); }
			}
		});
		inputTravelSpeed.setBounds(195, 172, 75, 25);
		add(inputTravelSpeed);
		// ------------------------------------------
		JCheckBox cbxCutBaseFirst = new JCheckBox("Cut base first");
		cbxCutBaseFirst.setSelected(wingCalculatorModel.settings.getCutBaseFirst());
		cbxCutBaseFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wingCalculatorModel.settings.setCutBaseFirst(cbxCutBaseFirst.isSelected());
			}
		});
		cbxCutBaseFirst.setBounds(43, 203, 208, 18);
		add(cbxCutBaseFirst);
		// ------------------------------------------
		JButton btnSaveParameters = new JButton("Save Parameters");
		btnSaveParameters.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wingCalculatorModel.saveSettings();
				}
		});
		btnSaveParameters.setBounds(20, 229, 208, 27);
		add(btnSaveParameters);
		
		inputProjectName = new JTextField();
		inputProjectName.setBounds(70, 574, 229, 25);
		add(inputProjectName);
		inputProjectName.setColumns(10);
		inputProjectName.setEditable(false);
		inputProjectName.setBackground(Color.LIGHT_GRAY);
		// ===============================================================
		
		
		JLabel lblInputWireLength = new JLabel("Wire length [mm]");
		lblInputWireLength.setBounds(6, 12, 128, 25);
		add(lblInputWireLength);
		
		JLabel lblInputStartDistance = new JLabel("X-Distance [mm]");
		lblInputStartDistance.setBounds(6, 49, 128, 25);
		add(lblInputStartDistance);
		
		JLabel lblInputSaveHeight = new JLabel("Save Height [mm]");
		lblInputSaveHeight.setBounds(6, 38, 128, 25);
		add(lblInputSaveHeight);
		
		JLabel lblInputCordOffset_1 = new JLabel("Cut speed [m/s]");
		lblInputCordOffset_1.setBounds(16, 120, 115, 25);
		add(lblInputCordOffset_1);
		
		JLabel lblInputCordOffset_2 = new JLabel("Pause [s]");
		lblInputCordOffset_2.setBounds(6, 75, 115, 25);
		add(lblInputCordOffset_2);

		JLabel lblInputCordOffset_1_1 = new JLabel("Travel speed [m/s]");
		lblInputCordOffset_1_1.setBounds(20, 157, 115, 25);
		add(lblInputCordOffset_1_1);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(16, 219, 1, 2);
		add(separator);
		
		

		
		
		
		

		
		
		

		
		
	}
	
}
