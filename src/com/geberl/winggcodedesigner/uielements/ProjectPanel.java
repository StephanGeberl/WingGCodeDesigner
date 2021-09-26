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
import javax.swing.BorderFactory;
import javax.swing.JButton;

import com.geberl.winggcodedesigner.model.Project;
import com.geberl.winggcodedesigner.model.ProjectFactory;
import com.geberl.winggcodedesigner.model.WingCalculatorModel;
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

	private WingCalculatorModel wingCalculatorModel;
	public Project project = null;

	private JPanel topSparePanel = new JPanel();
	private JPanel bottomSparePanel = new JPanel();
	private JPanel hollowProfilePanel = new JPanel();

	private JTextField inputProjectName;

	private JCheckBox inputCutBaseFirst;
	private JTextField inputProjectPath;

	private JTextField inputBaseProfileName;
	private JTextField inputBaseProfileNumberPoints;
	private JCheckBox inputBaseDirection;
	private JTextField inputTipProfileName;
	private JTextField inputTipProfileNumberPoints;
	private JCheckBox inputTipDirection;

	private JFormattedTextField inputHalfSpanLength;
	private JFormattedTextField inputBaseCordLength;
	private JFormattedTextField inputTipCordLength;
	private JFormattedTextField inputBaseMeltingLoss;
	private JFormattedTextField inputTipMeltingLoss;
	private JFormattedTextField inputWingSweep;
	private JFormattedTextField inputWingTipOffset;
	private JFormattedTextField inputWingTipYOffset;
	
	private JCheckBox inputHasSparTop;
	private JCheckBox inputHasSparBottom;
	private JFormattedTextField inputSparOffsetTop;
	private JFormattedTextField inputSparWidthTop;
	private JFormattedTextField inputSparHeightTop;
	private JFormattedTextField inputSparOffsetBottom;
	private JFormattedTextField inputSparWidthBottom;
	private JFormattedTextField inputSparHeightBottom;
	
	private JCheckBox inputIsHollowed;
	private JCheckBox inputIsHollowedFrontOnly;
	private JFormattedTextField inputWallThickness;
	private JFormattedTextField inputCrosspieceWidth;
	private JFormattedTextField inputCrosspieceOffset;
	private JFormattedTextField inputFrontHollowOffset;
	private JFormattedTextField inputBackHollowOffset;
	
	

	public ProjectPanel(WingCalculatorModel anWingDesignerModel) {
		
		this.wingCalculatorModel = anWingDesignerModel;
		
		
		setForeground(Color.LIGHT_GRAY);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(937, 283));
		
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
		// ========== Profile laden ===================
		inputBaseProfileName = new JTextField();
		inputBaseProfileName.setBounds(755, 44, 122, 25);
		add(inputBaseProfileName);
		inputBaseProfileName.setColumns(10);
		inputBaseProfileName.setEditable(false);
		inputBaseProfileName.setBackground(Color.LIGHT_GRAY);
		
		inputBaseProfileNumberPoints = new JTextField();
		inputBaseProfileNumberPoints.setBounds(876, 44, 49, 25);
		add(inputBaseProfileNumberPoints);
		inputBaseProfileNumberPoints.setColumns(10);
		inputBaseProfileNumberPoints.setEditable(false);
		inputBaseProfileNumberPoints.setBackground(Color.LIGHT_GRAY);
		
		inputTipProfileName = new JTextField();
		inputTipProfileName.setBounds(755, 143, 122, 25);
		add(inputTipProfileName);
		inputTipProfileName.setColumns(10);
		inputTipProfileName.setEditable(false);
		inputTipProfileName.setBackground(Color.LIGHT_GRAY);
		
		inputTipProfileNumberPoints = new JTextField();
		inputTipProfileNumberPoints.setBounds(876, 143, 49, 25);
		add(inputTipProfileNumberPoints);
		inputTipProfileNumberPoints.setColumns(10);
		inputTipProfileNumberPoints.setEditable(false);
		inputTipProfileNumberPoints.setBackground(Color.LIGHT_GRAY);
		
		inputBaseDirection = new JCheckBox("Change Direction (Base)");
		inputBaseDirection.setSelected(true);
		inputBaseDirection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { project.setBaseDirection(inputBaseDirection.isSelected()); }
		});
		inputBaseDirection.setBounds(755, 70, 175, 23);
		add(inputBaseDirection);
		
		inputTipDirection = new JCheckBox("Change Direction (Tip)");
		inputTipDirection.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { project.setTipDirection(inputTipDirection.isSelected()); }
		});
		inputTipDirection.setSelected(true);
		inputTipDirection.setBounds(755, 169, 170, 23);
		add(inputTipDirection);
		// ================================================
		JButton loadTipWingProfile = new JButton("Load Tip Wing Profile");
		loadTipWingProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.loadTipProfileData(); }
		});
		loadTipWingProfile.setBounds(755, 108, 170, 27);
		add(loadTipWingProfile);
		
		JButton loadBaseWingProfile = new JButton("Load Base Wing Profile");
		loadBaseWingProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) { wingCalculatorModel.loadBaseProfileData(); }
		});
		loadBaseWingProfile.setBounds(755, 9, 170, 27);
		add(loadBaseWingProfile);
		
		
		// ============= Daten erfassen ===================

		// ============= Manage Projects ===================
		JButton btnSaveProject = new JButton("Save Project");
		btnSaveProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				wingCalculatorModel.saveProject();
				}
		});
		btnSaveProject.setBounds(755, 246, 170, 27);
		add(btnSaveProject);
		// ------------------------------------------
		JButton btnLoadProject = new JButton("Load Project");
		btnLoadProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project = wingCalculatorModel.loadProject();
				
				// inputProjectName.setText(wingCalculatorModel.project.getProjectName());
				setPanelValues();
				}
		});
		btnLoadProject.setBounds(755, 217, 170, 27);
		add(btnLoadProject);
		// ------------------------------------------
		inputProjectPath = new JFormattedTextField();
		inputProjectPath.setEditable(false);
		inputProjectPath.setBounds(47, 6, 200, 25);
		add(inputProjectPath);
		// ============= Manage Projects Ende ===================
		
		// ------------------------------------------
		inputHalfSpanLength = new JFormattedTextField(doubleFormatter);
		inputHalfSpanLength.setText("0.0");
		inputHalfSpanLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputHalfSpanLength.getValue() != null) { project.setHalfSpanLength( Double.parseDouble(inputHalfSpanLength.getText()) ); }
			}
		});
		inputHalfSpanLength.setBounds(172, 47, 75, 25);
		add(inputHalfSpanLength);
		// ------------------------------------------
		inputBaseCordLength = new JFormattedTextField(doubleFormatter);
		inputBaseCordLength.setText("0.0");
		inputBaseCordLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputBaseCordLength.getValue() != null) { project.setBaseCordLength( Double.parseDouble(inputBaseCordLength.getText()) ); }
			}
		});
		inputBaseCordLength.setBounds(172, 73, 75, 25);
		add(inputBaseCordLength);
		// ------------------------------------------
		inputTipCordLength = new JFormattedTextField(doubleFormatter);
		inputTipCordLength.setText("0.0");
		inputTipCordLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputTipCordLength.getValue() != null) { project.setTipCordLength( Double.parseDouble(inputTipCordLength.getText()) ); }
			}
		});
		inputTipCordLength.setBounds(172, 97, 75, 25);
		add(inputTipCordLength);
		// ------------------------------------------
		
		inputBaseMeltingLoss = new JFormattedTextField(doubleFormatter);
		inputBaseMeltingLoss.setText("0.0");
		inputBaseMeltingLoss.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputBaseMeltingLoss.getValue() != null) { project.setBaseMeltingLoss( Double.parseDouble(inputBaseMeltingLoss.getText()) ); }
			}
		});
		inputBaseMeltingLoss.setBounds(172, 122, 75, 25);
		add(inputBaseMeltingLoss);
		// ------------------------------------------
		inputTipMeltingLoss = new JFormattedTextField(doubleFormatter);
		inputTipMeltingLoss.setText("0.0");
		inputTipMeltingLoss.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputTipMeltingLoss.getValue() != null) { project.setTipMeltingLoss( Double.parseDouble(inputTipMeltingLoss.getText()) ); }
			}
		});
		inputTipMeltingLoss.setBounds(172, 146, 75, 25);
		add(inputTipMeltingLoss);
		// ------------------------------------------
		inputWingSweep = new JFormattedTextField(degreeFormatter);
		inputWingSweep.setText("+0.0");
		inputWingSweep.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWingSweep.getValue() != null) { project.setWingSweep( Double.parseDouble(inputWingSweep.getText()) ); }
			}
		});
		inputWingSweep.setBounds(172, 172, 75, 25);
		add(inputWingSweep);
		// ------------------------------------------
		inputWingTipOffset = new JFormattedTextField(degreeFormatter);
		inputWingTipOffset.setText("+0.0");
		inputWingTipOffset.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWingTipOffset.getValue() != null) { project.setWingTipOffset( Double.parseDouble(inputWingTipOffset.getText()) ); }
			}
		});
		inputWingTipOffset.setBounds(172, 197, 75, 25);
		add(inputWingTipOffset);
		// ------------------------------------------
		inputWingTipYOffset = new JFormattedTextField(decimalFormater);
		inputWingTipYOffset.setText("+0.0");
		inputWingTipYOffset.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputWingTipYOffset.getValue() != null) { project.setWingTipYOffset( Double.parseDouble(inputWingTipYOffset.getText()) ); }
			}
		});
		inputWingTipYOffset.setBounds(172, 223, 75, 25);
		add(inputWingTipYOffset);
		
		
		inputCutBaseFirst = new JCheckBox("Cut Profile base first");
		inputCutBaseFirst.setSelected(false);
		inputCutBaseFirst.setBounds(6, 255, 193, 18);
		add(inputCutBaseFirst);
		inputCutBaseFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project.setCutBaseFirst(inputCutBaseFirst.isSelected());
			}
		});

		JFormattedTextField sProjectName_1 = new JFormattedTextField();
		sProjectName_1.setBackground(Color.LIGHT_GRAY);
		sProjectName_1.setEditable(false);
		sProjectName_1.setBounds(274, 252, 469, 25);
		add(sProjectName_1);
		
		
		// ===============================================================
		// Spar Data
		// ===============================================================
		
		inputHasSparTop = new JCheckBox("Spar wing top");
		inputHasSparTop.setSelected(false);
		inputHasSparTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project.setHasSparTop(inputHasSparTop.isSelected());
				setPanelEnabled(topSparePanel, inputHasSparTop.isSelected());
			}
		});
		inputHasSparTop.setBounds(281, 9, 193, 18);
		add(inputHasSparTop);
		
		// ------------------------------------------
		topSparePanel.setBounds(275, 30, 216, 89);
		add(topSparePanel);
		topSparePanel.setLayout(null);
		topSparePanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		
		// ------------------------------------------
		inputSparOffsetTop = new JFormattedTextField(doubleFormatter);
		inputSparOffsetTop.setBounds(134, 7, 75, 25);
		topSparePanel.add(inputSparOffsetTop);
		inputSparOffsetTop.setText("0.0");
		inputSparOffsetTop.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSparOffsetTop.getValue() != null) { project.setSparOffsetTop( Double.parseDouble(inputSparOffsetTop.getText()) ); }
			}
		});
		// ------------------------------------------
		inputSparWidthTop = new JFormattedTextField(doubleFormatter);
		inputSparWidthTop.setBounds(134, 32, 75, 25);
		topSparePanel.add(inputSparWidthTop);
		inputSparWidthTop.setText("0.0");
		inputSparWidthTop.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSparWidthTop.getValue() != null) { project.setSparWidthTop( Double.parseDouble(inputSparWidthTop.getText()) ); }
			}
		});
		// ------------------------------------------
		inputSparHeightTop = new JFormattedTextField(doubleFormatter);
		inputSparHeightTop.setBounds(134, 57, 75, 25);
		topSparePanel.add(inputSparHeightTop);
		inputSparHeightTop.setText("0.0");
		inputSparHeightTop.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSparHeightTop.getValue() != null) { project.setSparHeightTop( Double.parseDouble(inputSparHeightTop.getText()) ); }
			}
		});
		// ------------------------------------------

		JLabel lblInputHalfspanLength_1_3_2_1 = new JLabel("Spar offset [%]");
		lblInputHalfspanLength_1_3_2_1.setBounds(6, 6, 122, 25);
		topSparePanel.add(lblInputHalfspanLength_1_3_2_1);
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1 = new JLabel("Spar width [mm]");
		lblInputHalfspanLength_1_3_2_1_1.setBounds(6, 32, 122, 25);
		topSparePanel.add(lblInputHalfspanLength_1_3_2_1_1);
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_1 = new JLabel("Spar height [mm]");
		lblInputHalfspanLength_1_3_2_1_1_1.setBounds(6, 57, 122, 25);
		topSparePanel.add(lblInputHalfspanLength_1_3_2_1_1_1);
		
		// ------------------------------------------
		// ------------------------------------------

		inputHasSparBottom = new JCheckBox("Spar wing bottom");
		inputHasSparBottom.setSelected(false);
		inputHasSparBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project.setHasSparTop(inputHasSparBottom.isSelected());
				setPanelEnabled(bottomSparePanel, inputHasSparBottom.isSelected());
			}
		});
		inputHasSparBottom.setBounds(281, 129, 193, 26);
		add(inputHasSparBottom);
		// ------------------------------------------

		
		bottomSparePanel.setBounds(275, 159, 216, 89);
		add(bottomSparePanel);
		bottomSparePanel.setLayout(null);
		bottomSparePanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		
		// ------------------------------------------
		inputSparOffsetBottom = new JFormattedTextField(doubleFormatter);
		inputSparOffsetBottom.setBounds(135, 6, 75, 25);
		bottomSparePanel.add(inputSparOffsetBottom);
		inputSparOffsetBottom.setText("0.0");
		inputSparOffsetBottom.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSparOffsetBottom.getValue() != null) { project.setSparOffsetBottom( Double.parseDouble(inputSparOffsetBottom.getText()) ); }
			}
		});
		// ------------------------------------------
		inputSparWidthBottom = new JFormattedTextField(doubleFormatter);
		inputSparWidthBottom.setBounds(135, 31, 75, 25);
		bottomSparePanel.add(inputSparWidthBottom);
		inputSparWidthBottom.setText("0.0");
		inputSparWidthBottom.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSparWidthBottom.getValue() != null) { project.setSparWidthBottom( Double.parseDouble(inputSparWidthBottom.getText()) ); }
			}
		});
		
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_1_1 = new JLabel("Spar offset [%]");
		lblInputHalfspanLength_1_3_2_1_1_1_1.setBounds(6, 6, 127, 25);
		bottomSparePanel.add(lblInputHalfspanLength_1_3_2_1_1_1_1);
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_1_1_1 = new JLabel("Spar width [mm]");
		lblInputHalfspanLength_1_3_2_1_1_1_1_1.setBounds(6, 31, 127, 25);
		bottomSparePanel.add(lblInputHalfspanLength_1_3_2_1_1_1_1_1);
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_1_1_2 = new JLabel("Spar height [mm]");
		lblInputHalfspanLength_1_3_2_1_1_1_1_2.setBounds(6, 56, 127, 25);
		bottomSparePanel.add(lblInputHalfspanLength_1_3_2_1_1_1_1_2);
		// ------------------------------------------
		inputSparHeightBottom = new JFormattedTextField(doubleFormatter);
		inputSparHeightBottom.setBounds(134, 56, 76, 25);
		bottomSparePanel.add(inputSparHeightBottom);
		inputSparHeightBottom.setText("0.0");
		
		inputSparHeightBottom.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (inputSparHeightBottom.getValue() != null) { project.setSparHeightBottom( Double.parseDouble(inputSparHeightBottom.getText()) ); }
			}
		});
		
		// ===============================================================
		// Data for hollowed Profile
		// ===============================================================

		// ------------------------------------------
		inputIsHollowed = new JCheckBox("Wing is hollowed");
		inputIsHollowed.setSelected(false);
		inputIsHollowed.setBounds(509, 9, 193, 18);
		add(inputIsHollowed);
		// ------------------------------------------
		hollowProfilePanel.setBounds(503, 31, 236, 217);
		add(hollowProfilePanel);
		hollowProfilePanel.setLayout(null);
		hollowProfilePanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		// ------------------------------------------
		inputIsHollowedFrontOnly = new JCheckBox("Front only");
		inputIsHollowedFrontOnly.setBounds(6, 9, 193, 18);
		hollowProfilePanel.add(inputIsHollowedFrontOnly);
		inputIsHollowedFrontOnly.setSelected(false);
		// ------------------------------------------
		inputWallThickness = new JFormattedTextField(doubleFormatter);
		inputWallThickness.setBounds(154, 31, 75, 25);
		hollowProfilePanel.add(inputWallThickness);
		inputWallThickness.setText("0.0");
		// ------------------------------------------
		inputCrosspieceWidth = new JFormattedTextField(doubleFormatter);
		inputCrosspieceWidth.setBounds(154, 56, 75, 25);
		hollowProfilePanel.add(inputCrosspieceWidth);
		inputCrosspieceWidth.setText("0.0");
		// ------------------------------------------
		inputCrosspieceOffset = new JFormattedTextField(doubleFormatter);
		inputCrosspieceOffset.setBounds(154, 81, 75, 25);
		hollowProfilePanel.add(inputCrosspieceOffset);
		inputCrosspieceOffset.setText("0.0");
		// ------------------------------------------
		inputFrontHollowOffset = new JFormattedTextField(doubleFormatter);
		inputFrontHollowOffset.setBounds(154, 105, 75, 25);
		hollowProfilePanel.add(inputFrontHollowOffset);
		inputFrontHollowOffset.setText("0.0");
		// ------------------------------------------
		inputBackHollowOffset = new JFormattedTextField(doubleFormatter);
		inputBackHollowOffset.setBounds(154, 131, 75, 25);
		hollowProfilePanel.add(inputBackHollowOffset);
		inputBackHollowOffset.setText("0.0");
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_2 = new JLabel("Wall thickness [mm]");
		lblInputHalfspanLength_1_3_2_1_1_2.setBounds(6, 31, 148, 25);
		hollowProfilePanel.add(lblInputHalfspanLength_1_3_2_1_1_2);
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_3 = new JLabel("Crosspiece width [mm]");
		lblInputHalfspanLength_1_3_2_1_1_3.setBounds(6, 56, 148, 25);
		hollowProfilePanel.add(lblInputHalfspanLength_1_3_2_1_1_3);
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_4 = new JLabel("Crosspiece offset [%]");
		lblInputHalfspanLength_1_3_2_1_1_4.setBounds(6, 81, 148, 25);
		hollowProfilePanel.add(lblInputHalfspanLength_1_3_2_1_1_4);
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_4_1 = new JLabel("Front offset [%]");
		lblInputHalfspanLength_1_3_2_1_1_4_1.setBounds(6, 105, 148, 25);
		hollowProfilePanel.add(lblInputHalfspanLength_1_3_2_1_1_4_1);
		// ------------------------------------------
		JLabel lblInputHalfspanLength_1_3_2_1_1_4_2 = new JLabel("Back offset [%]");
		lblInputHalfspanLength_1_3_2_1_1_4_2.setBounds(6, 131, 148, 25);
		hollowProfilePanel.add(lblInputHalfspanLength_1_3_2_1_1_4_2);
		
		// ===============================================================
		// Labels
		// ===============================================================
		
		JLabel lblName = new JLabel("Name");
		lblName.setBounds(6, 6, 49, 25);
		add(lblName);
		
		JLabel lblInputHalfspanLength = new JLabel("Half wingspan [mm]");
		lblInputHalfspanLength.setBounds(6, 47, 170, 25);
		add(lblInputHalfspanLength);
		
		JLabel lblInputHalfspanLength_1 = new JLabel("Base cord [mm]");
		lblInputHalfspanLength_1.setBounds(6, 71, 170, 25);
		add(lblInputHalfspanLength_1);
		
		JLabel lblInputHalfspanLength_1_1 = new JLabel("Tip cord [mm]");
		lblInputHalfspanLength_1_1.setBounds(6, 97, 170, 25);
		add(lblInputHalfspanLength_1_1);
		
		JLabel lblInputHalfspanLength_1_2 = new JLabel("Melting loss base [mm]");
		lblInputHalfspanLength_1_2.setBounds(6, 122, 170, 25);
		add(lblInputHalfspanLength_1_2);
		
		JLabel lblInputHalfspanLength_1_3 = new JLabel("Melting loss tip [mm]");
		lblInputHalfspanLength_1_3.setBounds(6, 147, 170, 25);
		add(lblInputHalfspanLength_1_3);
		
		JLabel lblInputHalfspanLength_1_3_1 = new JLabel("Wing sweep [deg]");
		lblInputHalfspanLength_1_3_1.setBounds(6, 172, 170, 25);
		add(lblInputHalfspanLength_1_3_1);
		
		JLabel lblInputHalfspanLength_1_3_2 = new JLabel("Wing tip offset [deg]");
		lblInputHalfspanLength_1_3_2.setBounds(6, 197, 170, 25);
		add(lblInputHalfspanLength_1_3_2);
		
		JLabel lblInputHalfspanLength_1_3_2_2 = new JLabel("Mid cord length [mm]");
		lblInputHalfspanLength_1_3_2_2.setBounds(6, 533, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2);
		
		// Output
		
		JLabel lblInputHalfspanLength_1_3_2_2_1 = new JLabel("Wire cord base [mm]");
		lblInputHalfspanLength_1_3_2_2_1.setBounds(6, 559, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_1 = new JLabel("Wire cord base + [mm]");
		lblInputHalfspanLength_1_3_2_2_1_1.setBounds(6, 585, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_1);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_2 = new JLabel("Wire cord tip [mm]");
		lblInputHalfspanLength_1_3_2_2_1_2.setBounds(6, 610, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_2);
		
		JLabel lblInputHalfspanLength_1_3_2_2_1_3 = new JLabel("Wire cord tip + [mm]");
		lblInputHalfspanLength_1_3_2_2_1_3.setBounds(6, 635, 157, 25);
		add(lblInputHalfspanLength_1_3_2_2_1_3);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(16, 219, 1, 2);
		add(separator);
		
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
		lblInputWingTipYOffset.setBounds(6, 223, 170, 25);
		add(lblInputWingTipYOffset);
		
		// ------------------------------------------
		
		// ===============================================================
		
		// ------------------------------------------
		// =======================================================
		
    }
		
		
	public void setPanelValues() {
			
			inputHalfSpanLength.setValue(project.getHalfSpanLength());

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
