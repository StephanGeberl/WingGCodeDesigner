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

// init
import com.geberl.winggcodedesigner.eventing.WingCalculatorEvent;
import com.geberl.winggcodedesigner.eventing.WingCalculatorEventListener;
import com.geberl.winggcodedesigner.model.Settings;
import com.geberl.winggcodedesigner.model.SettingsFactory;
import com.geberl.winggcodedesigner.model.WingCalculatorModel;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.NumberFormatter;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JSeparator;

public class WingCalculatorPanel extends JPanel implements WingCalculatorEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
 
	private WingCalculatorModel wingCalculatorModel;
	private JLabel statusMessage = new JLabel("<html><b>Idle</b></html>");
	
	private JFormattedTextField sMiddleCordLength;
	private JFormattedTextField sBaseCordWireBase;
	private JFormattedTextField sBaseCordWire;
	private JFormattedTextField sTipCordWireBase;
	private JFormattedTextField sTipCordWire;
	private JFormattedTextField sTipDeltaSweep;
	private JFormattedTextField sTipDeltaBase;
	private JFormattedTextField sTipDeltaAll;

	public WingCalculatorPanel(WingCalculatorModel anWingDesignerModel) {
		
		this.wingCalculatorModel = anWingDesignerModel;
		
		// Test
		
		
		setForeground(Color.LIGHT_GRAY);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(946, 900));
		
		this.createControls();
		
//		wingCalculatorModel.setBaseDirection(true);
//		wingCalculatorModel.setTipDirection(true);
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
		
		// ===============================================================

		ProfilesDrawPanel profilePanel = new ProfilesDrawPanel(wingCalculatorModel);
		profilePanel.setBounds(247, 374, 660, 210);
		add(profilePanel);
		wingCalculatorModel.addWingCalculatorEventListener(profilePanel);
		
		// ===============================================================
		
		WingDrawPanel wingPanel = new WingDrawPanel(wingCalculatorModel);
		wingPanel.setBounds(252, 583, 660, 210);
		add(wingPanel);
		wingCalculatorModel.addWingCalculatorEventListener(wingPanel);
		
		// ===============================================================

		ProjectPanel projectPanel = new ProjectPanel(wingCalculatorModel);
		projectPanel.setBounds(5, 5, 937, 283);
		add(projectPanel);
		
		// ===============================================================
		
		
		
		
		
		
		
		// =========== Status Message ===========================
		statusMessage.setBounds(244, 337, 403, 25);
		add(statusMessage);
		// =======================================================
		
		// =========== Action Buttons ============================
		JButton btnSaveRightGcode = new JButton("Save right GCode");
		btnSaveRightGcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.saveRightGcodeList(); }
		});
		btnSaveRightGcode.setBounds(39, 404, 170, 27);
		add(btnSaveRightGcode);

		JButton btnSaveLeftGcode = new JButton("Save left GCode");
		btnSaveLeftGcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.saveLeftGcodeList(); }
		});
		btnSaveLeftGcode.setBounds(39, 374, 170, 27);
		add(btnSaveLeftGcode);
		
		JButton btnDrawDraft = new JButton("Draw draft");
		btnDrawDraft.addActionListener(new ActionListener() { 
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.drawDraft(); }
		});
		btnDrawDraft.setBounds(39, 480, 170, 27);
		add(btnDrawDraft);
		
		JLabel lblInputHalfspanLength_1_3_2_2 = new JLabel("Mid cord length [mm]");
		lblInputHalfspanLength_1_3_2_2.setBounds(6, 533, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2);
		
		// Output
		
		sMiddleCordLength = new JFormattedTextField(doubleFormatter);
		sMiddleCordLength.setBackground(Color.LIGHT_GRAY);
		sMiddleCordLength.setEditable(false);
		sMiddleCordLength.setText("0.0");
		sMiddleCordLength.setBounds(160, 533, 75, 25);
		add(sMiddleCordLength);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1 = new JLabel("Wire cord base [mm]");
		lblInputHalfspanLength_1_3_2_2_1.setBounds(6, 559, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1);
		
		sBaseCordWireBase = new JFormattedTextField(doubleFormatter);
		sBaseCordWireBase.setBackground(Color.LIGHT_GRAY);
		sBaseCordWireBase.setEditable(false);
		sBaseCordWireBase.setText("0.0");
		sBaseCordWireBase.setBounds(160, 559, 75, 25);
		add(sBaseCordWireBase);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_1 = new JLabel("Wire cord base + [mm]");
		lblInputHalfspanLength_1_3_2_2_1_1.setBounds(6, 585, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_1);
		
		sBaseCordWire = new JFormattedTextField(doubleFormatter);
		sBaseCordWire.setBackground(Color.LIGHT_GRAY);
		sBaseCordWire.setEditable(false);
		sBaseCordWire.setText("0.0");
		sBaseCordWire.setBounds(160, 585, 75, 25);
		add(sBaseCordWire);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_2 = new JLabel("Wire cord tip [mm]");
		lblInputHalfspanLength_1_3_2_2_1_2.setBounds(6, 610, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_2);
		
		sTipCordWireBase = new JFormattedTextField(doubleFormatter);
		sTipCordWireBase.setBackground(Color.LIGHT_GRAY);
		sTipCordWireBase.setEditable(false);
		sTipCordWireBase.setText("0.0");
		sTipCordWireBase.setBounds(160, 610, 75, 25);
		add(sTipCordWireBase);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_3 = new JLabel("Wire cord tip + [mm]");
		lblInputHalfspanLength_1_3_2_2_1_3.setBounds(6, 635, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_3);
		
		sTipCordWire = new JFormattedTextField(doubleFormatter);
		sTipCordWire.setBackground(Color.LIGHT_GRAY);
		sTipCordWire.setEditable(false);
		sTipCordWire.setText("0.0");
		sTipCordWire.setBounds(160, 635, 75, 25);
		add(sTipCordWire);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(16, 219, 1, 2);
		add(separator);
		
		sTipDeltaBase = new JFormattedTextField(doubleFormatter);
		sTipDeltaBase.setText("0.0");
		sTipDeltaBase.setEditable(false);
		sTipDeltaBase.setBackground(Color.LIGHT_GRAY);
		sTipDeltaBase.setBounds(160, 659, 75, 25);
		add(sTipDeltaBase);
		
		sTipDeltaSweep = new JFormattedTextField(doubleFormatter);
		sTipDeltaSweep.setText("0.0");
		sTipDeltaSweep.setEditable(false);
		sTipDeltaSweep.setBackground(Color.LIGHT_GRAY);
		sTipDeltaSweep.setBounds(160, 684, 75, 25);
		add(sTipDeltaSweep);
		
		sTipDeltaAll = new JFormattedTextField(doubleFormatter);
		sTipDeltaAll.setText("0.0");
		sTipDeltaAll.setEditable(false);
		sTipDeltaAll.setBackground(Color.LIGHT_GRAY);
		sTipDeltaAll.setBounds(160, 707, 75, 25);
		add(sTipDeltaAll);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_3_1 = new JLabel("Tip delta [mm]");
		lblInputHalfspanLength_1_3_2_2_1_3_1.setBounds(6, 659, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_3_1);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_3_2 = new JLabel("Tip Sweep delta [mm]");
		lblInputHalfspanLength_1_3_2_2_1_3_2.setBounds(6, 684, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_3_2);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_3_3 = new JLabel("Tip Sum Delta [mm]");
		lblInputHalfspanLength_1_3_2_2_1_3_3.setBounds(6, 707, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_3_3);
		// =======================================================
		
		
	}

	@Override
	public void WingCalculatorEvent(WingCalculatorEvent evt) {
		switch(evt.getEventType()) {
			case BASE_PROFILE_CHANGED_EVENT:
//				this.setInputBaseProfileName(wingCalculatorModel.getBaseProfileName());
//				this.setInputBaseProfileNumberPoints(wingCalculatorModel.getBaseProfilePointNumber());
				break;
			case TIP_PROFILE_CHANGED_EVENT:
//				this.setInputTipProfileName(wingCalculatorModel.getTipProfileName());
//				this.setInputTipProfileNumberPoints(wingCalculatorModel.getTipProfilePointNumber());
				break;
			case CALCULATOR_STATUS_CHANGED_EVENT:
				statusMessage.setText(wingCalculatorModel.getStatusMessage());
				sMiddleCordLength.setValue(wingCalculatorModel.getMiddleCordLength());
				sBaseCordWireBase.setValue(wingCalculatorModel.getBaseCordWireBase());
				sBaseCordWire.setValue(wingCalculatorModel.getBaseCordWire());
				sTipCordWireBase.setValue(wingCalculatorModel.getTipCordWireBase());
				sTipCordWire.setValue(wingCalculatorModel.getTipCordWire());
				sTipDeltaBase.setValue(wingCalculatorModel.getTipDeltaBase());
				sTipDeltaSweep.setValue(wingCalculatorModel.getTipDeltaSweep());
				sTipDeltaAll.setValue(wingCalculatorModel.getTipDeltaAll());

				if ((wingCalculatorModel.getTipProfilePointNumber()).intValue() != (wingCalculatorModel.getBaseProfilePointNumber()).intValue()) {
//					this.inputBaseProfileNumberPoints.setBackground(Color.RED);;
//					this.inputTipProfileNumberPoints.setBackground(Color.RED);;
				} else {
//					this.inputBaseProfileNumberPoints.setBackground(Color.LIGHT_GRAY);;
//					this.inputTipProfileNumberPoints.setBackground(Color.LIGHT_GRAY);;
				}
				break;
			case GCODE_CHANGED_EVENT:
				statusMessage.setText("------");
				// statusMessage.setText(wingDesignerModel.getStatusMessage());
				break;
			default:
				break;
		}

	}
	
	
	
	// Hilfsfunktion
	private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
	    panel.setEnabled(isEnabled);

	    Component[] components = panel.getComponents();

	    for (Component component : components) {
	        if (component instanceof JPanel) {
	            setPanelEnabled((JPanel) component, isEnabled);
	        }
	        component.setEnabled(isEnabled);
	    }
	}
}
