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
import com.geberl.winggcodedesigner.types.Project;
import com.geberl.winggcodedesigner.types.ProjectFactory;
import com.geberl.winggcodedesigner.utils.GUIHelpers;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;

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

public class ProjectPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField inputBaseProfileName;
	private JTextField inputBaseProfileNumberPoints;
	private JTextField inputTipProfileName;
	private JTextField inputTipProfileNumberPoints;
	private JPanel topSparePanel = new JPanel();
	private JPanel bottomSparePanel = new JPanel();
	
	private JFormattedTextField sMiddleCordLength;
	private JFormattedTextField sBaseCordWireBase;
	private JFormattedTextField sBaseCordWire;
	private JFormattedTextField sTipCordWireBase;
	private JFormattedTextField sTipCordWire;
	private JFormattedTextField sTipDeltaSweep;
	private JFormattedTextField sTipDeltaBase;
	private JFormattedTextField sTipDeltaAll;
	
	
	
	
	
	private WingCalculatorModel wingCalculatorModel;
	public Project project = null;

	public ProjectPanel(WingCalculatorModel anWingDesignerModel) {
		
		this.wingCalculatorModel = anWingDesignerModel;
		
		
		setForeground(Color.LIGHT_GRAY);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(972, 357));
		
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

		
		
		// ========== Profile laden ===================
		JButton loadBaseWingProfile = new JButton("Load Base Wing Profile");
		loadBaseWingProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.loadBaseProfileData(); }
		});
		loadBaseWingProfile.setBounds(774, 6, 170, 25);
		add(loadBaseWingProfile);
		
		inputBaseProfileName = new JTextField();
		inputBaseProfileName.setBounds(774, 31, 122, 25);
		add(inputBaseProfileName);
		inputBaseProfileName.setColumns(10);
		inputBaseProfileName.setEditable(false);
		inputBaseProfileName.setBackground(Color.LIGHT_GRAY);
		
		inputBaseProfileNumberPoints = new JTextField();
		inputBaseProfileNumberPoints.setBounds(895, 31, 49, 25);
		add(inputBaseProfileNumberPoints);
		inputBaseProfileNumberPoints.setColumns(10);
		inputBaseProfileNumberPoints.setEditable(false);
		inputBaseProfileNumberPoints.setBackground(Color.LIGHT_GRAY);
		
		JButton loadTipWingProfile = new JButton("Load Tip Wing Profile");
		loadTipWingProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.loadTipProfileData(); }
		});
		loadTipWingProfile.setBounds(774, 81, 170, 25);
		add(loadTipWingProfile);
		
		inputTipProfileName = new JTextField();
		inputTipProfileName.setBounds(774, 106, 122, 25);
		add(inputTipProfileName);
		inputTipProfileName.setColumns(10);
		inputTipProfileName.setEditable(false);
		inputTipProfileName.setBackground(Color.LIGHT_GRAY);
		
		inputTipProfileNumberPoints = new JTextField();
		inputTipProfileNumberPoints.setBounds(895, 106, 49, 25);
		add(inputTipProfileNumberPoints);
		inputTipProfileNumberPoints.setColumns(10);
		inputTipProfileNumberPoints.setEditable(false);
		inputTipProfileNumberPoints.setBackground(Color.LIGHT_GRAY);
		
		JCheckBox chckbxChangeBaseDirection = new JCheckBox("Change Direction (Base)");
		chckbxChangeBaseDirection.setSelected(true);
		chckbxChangeBaseDirection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.changeBaseProfileDirection(chckbxChangeBaseDirection.isSelected()); }
		});
		chckbxChangeBaseDirection.setBounds(774, 57, 175, 23);
		add(chckbxChangeBaseDirection);
		
		JCheckBox chckbxChangeTipDirection = new JCheckBox("Change Direction (Tip)");
		chckbxChangeTipDirection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.changeBaseProfileDirection(chckbxChangeTipDirection.isSelected()); }
		});
		chckbxChangeTipDirection.setSelected(true);
		chckbxChangeTipDirection.setBounds(774, 132, 170, 23);
		add(chckbxChangeTipDirection);
		// ================================================
		
		// ============= Daten erfassen ===================
		// ------------------------------------------
		// ============= Parameter ===================

		// ============= Parameter Ende ===================

		// ============= Manage Projects ===================
		JButton btnSaveProject = new JButton("Save Project");
		btnSaveProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wingCalculatorModel.saveProject();
				}
		});
		btnSaveProject.setBounds(774, 206, 170, 27);
		add(btnSaveProject);
		// ------------------------------------------
		JButton btnLoadProject = new JButton("Load Project");
		btnLoadProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wingCalculatorModel.loadProject();
				// inputProjectName.setText(wingCalculatorModel.project.getProjectName());
				}
		});
		btnLoadProject.setBounds(774, 180, 170, 27);
		add(btnLoadProject);

		
		
		JFormattedTextField sProjectName = new JFormattedTextField();
		sProjectName.setEditable(false);
		sProjectName.setBounds(47, 6, 200, 25);
		add(sProjectName);
		
		
		
		// ============= Manage Projects Ende ===================
		
		// ------------------------------------------
		JFormattedTextField inputHalfspanLength = new JFormattedTextField(doubleFormatter);
		inputHalfspanLength.setText("0.0");
		inputHalfspanLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputHalfspanLength.getValue() != null) { wingCalculatorModel.setHalfSpanLength( Double.parseDouble(inputHalfspanLength.getText()) ); }
			}
		});
		inputHalfspanLength.setBounds(172, 31, 75, 25);
		add(inputHalfspanLength);
		// ------------------------------------------
		JFormattedTextField inputBaseCordLength = new JFormattedTextField(doubleFormatter);
		inputBaseCordLength.setText("0.0");
		inputBaseCordLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputBaseCordLength.getValue() != null) { wingCalculatorModel.setBaseCordLength( Double.parseDouble(inputBaseCordLength.getText()) ); }
			}
		});
		inputBaseCordLength.setBounds(172, 55, 75, 25);
		add(inputBaseCordLength);
		// ------------------------------------------
		JFormattedTextField inputTipCordLength = new JFormattedTextField(doubleFormatter);
		inputTipCordLength.setText("0.0");
		inputTipCordLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputTipCordLength.getValue() != null) { wingCalculatorModel.setTipCordLength( Double.parseDouble(inputTipCordLength.getText()) ); }
			}
		});
		inputTipCordLength.setBounds(172, 81, 75, 25);
		add(inputTipCordLength);
		// ------------------------------------------
		JFormattedTextField inputBaseMeltingLoss = new JFormattedTextField(doubleFormatter);
		inputBaseMeltingLoss.setText("0.0");
		inputBaseMeltingLoss.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputBaseMeltingLoss.getValue() != null) { wingCalculatorModel.setBaseMeltingLoss( Double.parseDouble(inputBaseMeltingLoss.getText()) ); }
			}
		});
		inputBaseMeltingLoss.setBounds(172, 106, 75, 25);
		add(inputBaseMeltingLoss);
		// ------------------------------------------
		JFormattedTextField inputTipMeltingLoss = new JFormattedTextField(doubleFormatter);
		inputTipMeltingLoss.setText("0.0");
		inputTipMeltingLoss.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputTipMeltingLoss.getValue() != null) { wingCalculatorModel.setTipMeltingLoss( Double.parseDouble(inputTipMeltingLoss.getText()) ); }
			}
		});
		inputTipMeltingLoss.setBounds(172, 131, 75, 25);
		add(inputTipMeltingLoss);
		// ------------------------------------------
		JFormattedTextField inputWingSweep = new JFormattedTextField(degreeFormatter);
		inputWingSweep.setText("+0.0");
		inputWingSweep.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWingSweep.getValue() != null) { wingCalculatorModel.setWingSweep( Double.parseDouble(inputWingSweep.getText()) ); }
			}
		});
		inputWingSweep.setBounds(172, 156, 75, 25);
		add(inputWingSweep);
		// ------------------------------------------
		JFormattedTextField inputWingTipOffset = new JFormattedTextField(degreeFormatter);
		inputWingTipOffset.setText("+0.0");
		inputWingTipOffset.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWingTipOffset.getValue() != null) { wingCalculatorModel.setWingTipOffset( Double.parseDouble(inputWingTipOffset.getText()) ); }
			}
		});
		inputWingTipOffset.setBounds(172, 181, 75, 25);
		add(inputWingTipOffset);
		// ------------------------------------------
		JFormattedTextField inputWingTipYOffset = new JFormattedTextField(decimalFormater);
		inputWingTipYOffset.setText("+0.0");
		inputWingTipYOffset.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWingTipYOffset.getValue() != null) { wingCalculatorModel.setWingTipYOffset( Double.parseDouble(inputWingTipYOffset.getText()) ); }
			}
		});
		inputWingTipYOffset.setBounds(172, 207, 75, 25);
		add(inputWingTipYOffset);
		
		
		// ------------------------------------------
		
		JCheckBox chckbxSparWingTop = new JCheckBox("Spar wing top");
		chckbxSparWingTop.setSelected(false);
		chckbxSparWingTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wingCalculatorModel.setHasSparTop(chckbxSparWingTop.isSelected());
				setPanelEnabled(topSparePanel, chckbxSparWingTop.isSelected());
			}
		});
		chckbxSparWingTop.setBounds(282, 9, 193, 18);
		add(chckbxSparWingTop);
		
		// ===============================================================
		topSparePanel.setBounds(282, 30, 193, 76);
		add(topSparePanel);
		topSparePanel.setLayout(null);
		
			// ------------------------------------------
			JFormattedTextField inputSparOffsetTop = new JFormattedTextField(doubleFormatter);
			inputSparOffsetTop.setBounds(128, 1, 75, 25);
			topSparePanel.add(inputSparOffsetTop);
			inputSparOffsetTop.setText("0.0");
			inputSparOffsetTop.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if (inputSparOffsetTop.getValue() != null) { wingCalculatorModel.setSparOffsetTop( Double.parseDouble(inputSparOffsetTop.getText()) ); }
				}
			});
			// ------------------------------------------
			JFormattedTextField inputSparWidthTop = new JFormattedTextField(doubleFormatter);
			inputSparWidthTop.setBounds(128, 26, 75, 25);
			topSparePanel.add(inputSparWidthTop);
			inputSparWidthTop.setText("0.0");
			inputSparWidthTop.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if (inputSparWidthTop.getValue() != null) { wingCalculatorModel.setSparWidthTop( Double.parseDouble(inputSparWidthTop.getText()) ); }
				}
			});
			// ------------------------------------------
			JFormattedTextField inputSparHeightTop = new JFormattedTextField(doubleFormatter);
			inputSparHeightTop.setBounds(128, 51, 75, 25);
			topSparePanel.add(inputSparHeightTop);
			inputSparHeightTop.setText("0.0");
			inputSparHeightTop.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if (inputSparHeightTop.getValue() != null) { wingCalculatorModel.setSparHeightTop( Double.parseDouble(inputSparHeightTop.getText()) ); }
				}
			});
			// ------------------------------------------

			JLabel lblInputHalfspanLength_1_3_2_1 = new JLabel("Spar offset [%]");
			lblInputHalfspanLength_1_3_2_1.setBounds(0, 0, 122, 25);
			topSparePanel.add(lblInputHalfspanLength_1_3_2_1);
			// ------------------------------------------
			JLabel lblInputHalfspanLength_1_3_2_1_1 = new JLabel("Spar width [mm]");
			lblInputHalfspanLength_1_3_2_1_1.setBounds(0, 26, 122, 25);
			topSparePanel.add(lblInputHalfspanLength_1_3_2_1_1);
			// ------------------------------------------
			JLabel lblInputHalfspanLength_1_3_2_1_1_1 = new JLabel("Spar height [mm]");
			lblInputHalfspanLength_1_3_2_1_1_1.setBounds(0, 51, 122, 25);
			topSparePanel.add(lblInputHalfspanLength_1_3_2_1_1_1);
			// ------------------------------------------
		
		setPanelEnabled(topSparePanel, chckbxSparWingTop.isSelected());
		// ===============================================================

		// ===============================================================
		bottomSparePanel.setBounds(281, 131, 193, 76);
		add(bottomSparePanel);
		bottomSparePanel.setLayout(null);
		
			// ------------------------------------------
			JFormattedTextField inputSparOffsetBottom = new JFormattedTextField(doubleFormatter);
			inputSparOffsetBottom.setBounds(129, 25, 75, 25);
			bottomSparePanel.add(inputSparOffsetBottom);
			inputSparOffsetBottom.setText("0.0");
			inputSparOffsetBottom.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if (inputSparOffsetBottom.getValue() != null) { wingCalculatorModel.setSparOffsetBottom( Double.parseDouble(inputSparOffsetBottom.getText()) ); }
				}
			});
			// ------------------------------------------
			JFormattedTextField inputSparWidthBottom = new JFormattedTextField(doubleFormatter);
			inputSparWidthBottom.setBounds(129, 50, 75, 25);
			bottomSparePanel.add(inputSparWidthBottom);
			inputSparWidthBottom.setText("0.0");
			inputSparWidthBottom.addPropertyChangeListener(new PropertyChangeListener() {
				public void propertyChange(PropertyChangeEvent evt) {
					if (inputSparWidthBottom.getValue() != null) { wingCalculatorModel.setSparWidthBottom( Double.parseDouble(inputSparWidthBottom.getText()) ); }
				}
			});
		
			// ------------------------------------------
			JLabel lblInputHalfspanLength_1_3_2_1_1_1_1 = new JLabel("Spar offset [%]");
			lblInputHalfspanLength_1_3_2_1_1_1_1.setBounds(0, 25, 127, 25);
			bottomSparePanel.add(lblInputHalfspanLength_1_3_2_1_1_1_1);
			// ------------------------------------------
			JLabel lblInputHalfspanLength_1_3_2_1_1_1_1_1 = new JLabel("Spar width [mm]");
			lblInputHalfspanLength_1_3_2_1_1_1_1_1.setBounds(0, 50, 127, 25);
			bottomSparePanel.add(lblInputHalfspanLength_1_3_2_1_1_1_1_1);
		
		JCheckBox chckbxSparWingBottom = new JCheckBox("Spar wing bottom");
		chckbxSparWingBottom.setBounds(0, 0, 193, 26);
		bottomSparePanel.add(chckbxSparWingBottom);
		chckbxSparWingBottom.setSelected(false);
		chckbxSparWingBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wingCalculatorModel.setHasSparBottom(chckbxSparWingBottom.isSelected());
				setPanelEnabled(bottomSparePanel, chckbxSparWingBottom.isSelected());
			}
		});
		// ------------------------------------------
		
		setPanelEnabled(bottomSparePanel, chckbxSparWingBottom.isSelected());
		
		JLabel lblInputHalfspanLength = new JLabel("Half wingspan [mm]");
		lblInputHalfspanLength.setBounds(6, 31, 170, 25);
		add(lblInputHalfspanLength);
		
		JLabel lblInputHalfspanLength_1 = new JLabel("Base cord [mm]");
		lblInputHalfspanLength_1.setBounds(6, 55, 170, 25);
		add(lblInputHalfspanLength_1);
		
		JLabel lblInputHalfspanLength_1_1 = new JLabel("Tip cord [mm]");
		lblInputHalfspanLength_1_1.setBounds(6, 81, 170, 25);
		add(lblInputHalfspanLength_1_1);
		
		JLabel lblInputHalfspanLength_1_2 = new JLabel("Melting loss base [mm]");
		lblInputHalfspanLength_1_2.setBounds(6, 106, 170, 25);
		add(lblInputHalfspanLength_1_2);
		
		JLabel lblInputHalfspanLength_1_3 = new JLabel("Melting loss tip [mm]");
		lblInputHalfspanLength_1_3.setBounds(6, 131, 170, 25);
		add(lblInputHalfspanLength_1_3);
		
		JLabel lblInputHalfspanLength_1_3_1 = new JLabel("Wing sweep [deg]");
		lblInputHalfspanLength_1_3_1.setBounds(6, 156, 170, 25);
		add(lblInputHalfspanLength_1_3_1);
		
		JLabel lblInputHalfspanLength_1_3_2 = new JLabel("Wing tip offset [deg]");
		lblInputHalfspanLength_1_3_2.setBounds(6, 181, 170, 25);
		add(lblInputHalfspanLength_1_3_2);
		
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
		
		JLabel lblInputWingTipYOffset = new JLabel("Wing y offset [mm]");
		lblInputWingTipYOffset.setBounds(6, 207, 170, 25);
		add(lblInputWingTipYOffset);
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_1_1_2 = new JLabel("Spar height [mm]");
		lblInputHalfspanLength_1_3_2_1_1_1_1_2.setBounds(282, 207, 127, 25);
		add(lblInputHalfspanLength_1_3_2_1_1_1_1_2);
		// ------------------------------------------
		JFormattedTextField inputSparHeightBottom = new JFormattedTextField(doubleFormatter);
		inputSparHeightBottom.setBounds(410, 207, 65, 25);
		add(inputSparHeightBottom);
		inputSparHeightBottom.setText("0.0");
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(6, 6, 49, 25);
		add(lblName);
		inputSparHeightBottom.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSparHeightBottom.getValue() != null) { wingCalculatorModel.setSparHeightBottom( Double.parseDouble(inputSparHeightBottom.getText()) ); }
			}
		});
		// =======================================================
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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
