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
import com.geberl.winggcodedesigner.model.SettingsFactory;
import com.geberl.winggcodedesigner.model.WingCalculatorModel;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.text.NumberFormatter;


public class WingCalculatorPanel extends JPanel implements WingCalculatorEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(SettingsFactory.class.getName());
 
	private WingCalculatorModel wingCalculatorModel;
//	private JLabel statusMessage = new JLabel("<html><b>Idle</b></html>");
	
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

		this.setLayout(null);
		this.setPreferredSize(new Dimension(940, 460));
		
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
		
		
		// =========== Action Buttons ============================
		JButton btnSaveRightGcode = new JButton("Save right GCode");
		btnSaveRightGcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.saveRightGcodeList(); }
		});
		btnSaveRightGcode.setBounds(6, 225, 240, 35);
		add(btnSaveRightGcode);

		JButton btnSaveLeftGcode = new JButton("Save left GCode");
		btnSaveLeftGcode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.saveLeftGcodeList(); }
		});
		btnSaveLeftGcode.setBounds(6, 265, 240, 35);
		add(btnSaveLeftGcode);
		

		// ------------------
		// Output
		
		JLabel lblInputHalfspanLength_1_3_2_2 = new JLabel("Mid cord length [mm]");
		lblInputHalfspanLength_1_3_2_2.setBounds(6, 5, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2);

		sMiddleCordLength = new JFormattedTextField(doubleFormatter);
		sMiddleCordLength.setBackground(Color.LIGHT_GRAY);
		sMiddleCordLength.setEditable(false);
		sMiddleCordLength.setText("0.0");
		sMiddleCordLength.setBounds(172, 5, 75, 25);
		add(sMiddleCordLength);

		// ------------------
		
		JLabel lblInputHalfspanLength_1_3_2_2_1 = new JLabel("Wire cord base [mm]");
		lblInputHalfspanLength_1_3_2_2_1.setBounds(6, 29, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1);
		
		sBaseCordWireBase = new JFormattedTextField(doubleFormatter);
		sBaseCordWireBase.setBackground(Color.LIGHT_GRAY);
		sBaseCordWireBase.setEditable(false);
		sBaseCordWireBase.setText("0.0");
		sBaseCordWireBase.setBounds(172, 29, 75, 25);
		add(sBaseCordWireBase);

		// ------------------
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_1 = new JLabel("Wire cord base + [mm]");
		lblInputHalfspanLength_1_3_2_2_1_1.setBounds(6, 53, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_1);
		
		sBaseCordWire = new JFormattedTextField(doubleFormatter);
		sBaseCordWire.setBackground(Color.LIGHT_GRAY);
		sBaseCordWire.setEditable(false);
		sBaseCordWire.setText("0.0");
		sBaseCordWire.setBounds(172, 53, 75, 25);
		add(sBaseCordWire);

		
		JLabel lblInputHalfspanLength_1_3_2_2_1_2 = new JLabel("Wire cord tip [mm]");
		lblInputHalfspanLength_1_3_2_2_1_2.setBounds(6, 77, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_2);
		
		sTipCordWireBase = new JFormattedTextField(doubleFormatter);
		sTipCordWireBase.setBackground(Color.LIGHT_GRAY);
		sTipCordWireBase.setEditable(false);
		sTipCordWireBase.setText("0.0");
		sTipCordWireBase.setBounds(172, 77, 75, 25);
		add(sTipCordWireBase);

		// ------------------
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_3 = new JLabel("Wire cord tip + [mm]");
		lblInputHalfspanLength_1_3_2_2_1_3.setBounds(6, 101, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_3);
		
		sTipCordWire = new JFormattedTextField(doubleFormatter);
		sTipCordWire.setBackground(Color.LIGHT_GRAY);
		sTipCordWire.setEditable(false);
		sTipCordWire.setText("0.0");
		sTipCordWire.setBounds(172, 101, 75, 25);
		add(sTipCordWire);
		
		// JSeparator separator = new JSeparator();
		// separator.setBounds(16, 219, 1, 2);
		// add(separator);

		// ------------------
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_3_1 = new JLabel("Tip delta [mm]");
		lblInputHalfspanLength_1_3_2_2_1_3_1.setBounds(6, 125, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_3_1);
		
		sTipDeltaBase = new JFormattedTextField(doubleFormatter);
		sTipDeltaBase.setText("0.0");
		sTipDeltaBase.setEditable(false);
		sTipDeltaBase.setBackground(Color.LIGHT_GRAY);
		sTipDeltaBase.setBounds(172, 125, 75, 25);
		add(sTipDeltaBase);

		// ------------------
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_3_2 = new JLabel("Tip Sweep delta [mm]");
		lblInputHalfspanLength_1_3_2_2_1_3_2.setBounds(6, 149, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_3_2);
		
		sTipDeltaSweep = new JFormattedTextField(doubleFormatter);
		sTipDeltaSweep.setText("0.0");
		sTipDeltaSweep.setEditable(false);
		sTipDeltaSweep.setBackground(Color.LIGHT_GRAY);
		sTipDeltaSweep.setBounds(172, 149, 75, 25);
		add(sTipDeltaSweep);

		// ------------------
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_3_3 = new JLabel("Tip Sum Delta [mm]");
		lblInputHalfspanLength_1_3_2_2_1_3_3.setBounds(6, 173, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_3_3);
		
		sTipDeltaAll = new JFormattedTextField(doubleFormatter);
		sTipDeltaAll.setText("0.0");
		sTipDeltaAll.setEditable(false);
		sTipDeltaAll.setBackground(Color.LIGHT_GRAY);
		sTipDeltaAll.setBounds(172, 173, 75, 25);
		add(sTipDeltaAll);
		
		// =======================================================
		
		
	}

	@Override
	public void WingCalculatorEvent(WingCalculatorEvent evt) {
		logger.info("CALC DONE EVENT");
		switch(evt.getEventType()) {
			case CALCULATION_DONE_EVENT:
				// statusMessage.setText(wingCalculatorModel.getStatusMessage());
				sMiddleCordLength.setValue(wingCalculatorModel.getMiddleCordLength());
				sBaseCordWireBase.setValue(wingCalculatorModel.getBaseCordWireBase());
				sBaseCordWire.setValue(wingCalculatorModel.getBaseCordWire());
				sTipCordWireBase.setValue(wingCalculatorModel.getTipCordWireBase());
				sTipCordWire.setValue(wingCalculatorModel.getTipCordWire());
				sTipDeltaBase.setValue(wingCalculatorModel.getTipDeltaBase());
				sTipDeltaSweep.setValue(wingCalculatorModel.getTipDeltaSweep());
				sTipDeltaAll.setValue(wingCalculatorModel.getTipDeltaAll());

				break;

			default:
				break;
		}

	}

}
