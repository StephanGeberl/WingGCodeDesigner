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

import com.geberl.winggcodedesigner.eventing.ProjectChangeEvent;
import com.geberl.winggcodedesigner.eventing.ProjectChangeEventListener;
import com.geberl.winggcodedesigner.model.Project;
import com.geberl.winggcodedesigner.model.ProjectFactory;
import com.geberl.winggcodedesigner.model.SettingsFactory;
import com.geberl.winggcodedesigner.model.WingCalculatorModel;
// import com.geberl.winggcodedesigner.utils.GUIHelpers;

import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.logging.Logger;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
//import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
//import javax.swing.filechooser.FileNameExtensionFilter;
//import javax.swing.filechooser.FileSystemView;
import javax.swing.text.NumberFormatter;
import java.beans.PropertyChangeListener;
//import java.io.File;
import java.beans.PropertyChangeEvent;
import javax.swing.JSeparator;


public class ProjectPanel extends JPanel implements ProjectChangeEventListener {

	/**
	 * 
	 */
    private static final Logger logger = Logger.getLogger(SettingsFactory.class.getName());
	private static final long serialVersionUID = 1L;

	private Project project = null;

	private JPanel topSparePanel = new JPanel();
	private JPanel bottomSparePanel = new JPanel();
	private JPanel hollowProfilePanel = new JPanel();

	private JButton btnSaveProject = null;
	private JButton btnNewProject = null;
	private JButton btnLoadProject = null;
	
	private JTextField inputProjectPath;
	private JTextField inputProjectName;
	private JCheckBox inputCutBaseFirst;
	private JCheckBox inputCutTopOnly;

	private JTextField inputBaseProfileName;
	private JFormattedTextField inputBaseProfileNumberPoints;
	private JCheckBox inputBaseDirection;
	private JTextField inputTipProfileName;
	private JFormattedTextField inputTipProfileNumberPoints;
	private JCheckBox inputTipDirection;

	private JFormattedTextField inputHalfSpanLength;
	private JFormattedTextField inputBaseCordLength;
	private JFormattedTextField inputTipCordLength;
	private JFormattedTextField inputBaseMeltingLoss;
	private JFormattedTextField inputTipMeltingLoss;
	private JFormattedTextField inputWingSweep;
	private JFormattedTextField inputWingTipOffset;
	private JFormattedTextField inputWingTipYOffset;
	private JFormattedTextField inputShiftCenter;
	
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
		
		this.project = anWingDesignerModel.project;
		addMeAsProjectValuesChangedListener();
		
		// setForeground(Color.LIGHT_GRAY);
		this.setLayout(null);
		this.setPreferredSize(new Dimension(937, 344));
		
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
		
		inputBaseProfileNumberPoints = new JFormattedTextField(integerFormatter);
		inputBaseProfileNumberPoints.setBounds(876, 44, 49, 25);
		add(inputBaseProfileNumberPoints);
		inputBaseProfileNumberPoints.setEditable(false);
		inputBaseProfileNumberPoints.setBackground(Color.LIGHT_GRAY);
		
		inputTipProfileName = new JTextField();
		inputTipProfileName.setBounds(755, 155, 122, 25);
		add(inputTipProfileName);
		inputTipProfileName.setColumns(10);
		inputTipProfileName.setEditable(false);
		inputTipProfileName.setBackground(Color.LIGHT_GRAY);
		
		inputTipProfileNumberPoints = new JFormattedTextField(integerFormatter);
		inputTipProfileNumberPoints.setBounds(876, 155, 49, 25);
		add(inputTipProfileNumberPoints);
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
		inputTipDirection.setBounds(755, 181, 170, 23);
		add(inputTipDirection);
		
		
		// ================================================
		JButton loadTipWingProfile = new JButton("Load Tip Wing Profile");
		loadTipWingProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectFactory.loadProfileData(2);
				setProfileValues();
			}
		});
		loadTipWingProfile.setBounds(755, 120, 170, 27);
		add(loadTipWingProfile);
		
		JButton loadBaseWingProfile = new JButton("Load Base Wing Profile");
		loadBaseWingProfile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectFactory.loadProfileData(1);
				setProfileValues();
				
			}
		});
		loadBaseWingProfile.setBounds(755, 9, 170, 27);
		add(loadBaseWingProfile);
		
		
		// ============= Daten erfassen ===================

		// ============= Manage Projects ===================
		btnLoadProject = new JButton("Load Project");
		btnLoadProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project = ProjectFactory.loadProject();
				addMeAsProjectValuesChangedListener();
				setPanelValues();
				setProfileValues();
				inputProjectPath.setText(project.getProjectPath());
				// btnSaveProject.setEnabled(false);

				}
		});
		btnLoadProject.setBounds(755, 232, 170, 27);
		add(btnLoadProject);
		// ------------------------------------------
		btnNewProject = new JButton("New Project");
		btnNewProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project = ProjectFactory.newProject();
				addMeAsProjectValuesChangedListener();
				setPanelValues();
				setProfileValues();
				inputProjectPath.setText("");
				// btnSaveProject.setEnabled(false);
				}
		});
		btnNewProject.setBounds(755, 261, 170, 27);
		add(btnNewProject);
		// ------------------------------------------
		btnSaveProject = new JButton("Save and Calc Project");
		btnSaveProject.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProjectFactory.saveProjectAs();
				setPanelValues();
				setProfileValues();
				inputProjectPath.setText(project.getProjectPath());
				}
		});
		btnSaveProject.setBounds(755, 289, 170, 27);
		add(btnSaveProject);
		// ------------------------------------------
		inputProjectPath = new JFormattedTextField();
		inputProjectPath.setBackground(Color.LIGHT_GRAY);
		inputProjectPath.setEditable(false);
		inputProjectPath.setBounds(275, 290, 469, 25);
		add(inputProjectPath);
		// ------------------------------------------
		// ============= Manage Projects Ende ===================
		
		inputProjectName = new JFormattedTextField();
		inputProjectName.setEditable(true);
		inputProjectName.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setProjectName( inputProjectName.getText() ); }
			}
		});
		inputProjectName.setBounds(47, 9, 200, 25);
		add(inputProjectName);
		// ------------------------------------------
		inputHalfSpanLength = new JFormattedTextField(doubleFormatter);
		inputHalfSpanLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setHalfSpanLength( Double.parseDouble(inputHalfSpanLength.getText()) ); }
			}
		});
		inputHalfSpanLength.setBounds(172, 44, 75, 25);
		add(inputHalfSpanLength);
		// ------------------------------------------
		inputBaseCordLength = new JFormattedTextField(doubleFormatter);
		inputBaseCordLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setBaseCordLength( Double.parseDouble(inputBaseCordLength.getText()) );  }
			}
		});
		inputBaseCordLength.setBounds(172, 70, 75, 25);
		add(inputBaseCordLength);
		// ------------------------------------------
		inputTipCordLength = new JFormattedTextField(doubleFormatter);
		inputTipCordLength.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setTipCordLength( Double.parseDouble(inputTipCordLength.getText()) ); }
			}
		});
		inputTipCordLength.setBounds(172, 94, 75, 25);
		add(inputTipCordLength);
		// ------------------------------------------
		
		inputBaseMeltingLoss = new JFormattedTextField(doubleFormatter);
		inputBaseMeltingLoss.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setBaseMeltingLoss( Double.parseDouble(inputBaseMeltingLoss.getText()) ); }
			}
		});
		inputBaseMeltingLoss.setBounds(172, 119, 75, 25);
		add(inputBaseMeltingLoss);
		// ------------------------------------------
		inputTipMeltingLoss = new JFormattedTextField(doubleFormatter);
		inputTipMeltingLoss.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setTipMeltingLoss( Double.parseDouble(inputTipMeltingLoss.getText()) ); }
			}
		});
		inputTipMeltingLoss.setBounds(172, 143, 75, 25);
		add(inputTipMeltingLoss);
		// ------------------------------------------
		inputWingSweep = new JFormattedTextField(degreeFormatter);
		inputWingSweep.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setWingSweep( Double.parseDouble(inputWingSweep.getText()) ); }
			}
		});
		inputWingSweep.setBounds(172, 169, 75, 25);
		add(inputWingSweep);
		// ------------------------------------------
		inputWingTipOffset = new JFormattedTextField(degreeFormatter);
		inputWingTipOffset.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setWingTipOffset( Double.parseDouble(inputWingTipOffset.getText()) ); }
			}
		});
		inputWingTipOffset.setBounds(172, 194, 75, 25);
		add(inputWingTipOffset);
		// ------------------------------------------
		inputWingTipYOffset = new JFormattedTextField(decimalFormater);
		inputWingTipYOffset.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setWingTipYOffset( Double.parseDouble(inputWingTipYOffset.getText()) ); }
			}
		});
		inputWingTipYOffset.setBounds(172, 220, 75, 25);
		add(inputWingTipYOffset);
		// ------------------------------------------
		inputCutBaseFirst = new JCheckBox("Cut Profile base first");
		inputCutBaseFirst.setSelected(false);
		inputCutBaseFirst.setBounds(6, 275, 193, 18);
		add(inputCutBaseFirst);
		inputCutBaseFirst.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project.setCutBaseFirst(inputCutBaseFirst.isSelected());
			}
		});

		// ------------------------------------------
		inputCutTopOnly = new JCheckBox("Cut only profile top");
		inputCutTopOnly.setSelected(false);
		inputCutTopOnly.setBounds(6, 298, 193, 18);
		add(inputCutTopOnly);
		inputCutTopOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project.setCutTopOnly(inputCutTopOnly.isSelected());
			}
		});
		// ------------------------------------------
		inputShiftCenter = new JFormattedTextField(decimalFormater);
		inputShiftCenter.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setShiftCenter( Double.parseDouble(inputShiftCenter.getText()) ); }
			}
		});
		inputShiftCenter.setBounds(172, 245, 75, 25);
		add(inputShiftCenter);
		
		
		
		
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
		inputSparOffsetTop.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setSparOffsetTop( Double.parseDouble(inputSparOffsetTop.getText()) ); }
			}
		});
		// ------------------------------------------
		inputSparWidthTop = new JFormattedTextField(doubleFormatter);
		inputSparWidthTop.setBounds(134, 32, 75, 25);
		topSparePanel.add(inputSparWidthTop);
		inputSparWidthTop.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setSparWidthTop( Double.parseDouble(inputSparWidthTop.getText()) ); }
			}
		});
		// ------------------------------------------
		inputSparHeightTop = new JFormattedTextField(doubleFormatter);
		inputSparHeightTop.setBounds(134, 57, 75, 25);
		topSparePanel.add(inputSparHeightTop);
		inputSparHeightTop.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setSparHeightTop( Double.parseDouble(inputSparHeightTop.getText()) ); }
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
				project.setHasSparBottom(inputHasSparBottom.isSelected());
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
		inputSparOffsetBottom.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setSparOffsetBottom( Double.parseDouble(inputSparOffsetBottom.getText()) ); }
			}
		});
		// ------------------------------------------
		inputSparWidthBottom = new JFormattedTextField(doubleFormatter);
		inputSparWidthBottom.setBounds(135, 31, 75, 25);
		bottomSparePanel.add(inputSparWidthBottom);
		inputSparWidthBottom.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setSparWidthBottom( Double.parseDouble(inputSparWidthBottom.getText()) ); }
			}
		});
		// ------------------------------------------
		inputSparHeightBottom = new JFormattedTextField(doubleFormatter);
		inputSparHeightBottom.setBounds(134, 56, 76, 25);
		bottomSparePanel.add(inputSparHeightBottom);
		inputSparHeightBottom.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setSparHeightBottom( Double.parseDouble(inputSparHeightBottom.getText()) ); }
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
		
		// ===============================================================
		// Data for hollowed Profile
		// ===============================================================

		// ------------------------------------------
		hollowProfilePanel.setBounds(503, 31, 236, 217);
		add(hollowProfilePanel);
		hollowProfilePanel.setLayout(null);
		hollowProfilePanel.setBorder(BorderFactory.createLineBorder(Color.darkGray));
		// ------------------------------------------
		inputIsHollowed = new JCheckBox("Wing is hollowed");
		inputIsHollowed.setSelected(false);
		inputIsHollowed.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project.setIsHollowed(inputIsHollowed.isSelected());
				setPanelEnabled(hollowProfilePanel, inputIsHollowed.isSelected());
			}
		});
		inputIsHollowed.setBounds(509, 9, 193, 18);
		add(inputIsHollowed);
		// ------------------------------------------
		inputIsHollowedFrontOnly = new JCheckBox("Front only");
		inputIsHollowedFrontOnly.setSelected(false);
		inputIsHollowedFrontOnly.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				project.setIsHollowedFrontOnly(inputIsHollowedFrontOnly.isSelected());
			}
		});
		inputIsHollowedFrontOnly.setBounds(6, 9, 193, 18);
		hollowProfilePanel.add(inputIsHollowedFrontOnly);
		// ------------------------------------------
		inputWallThickness = new JFormattedTextField(doubleFormatter);
		inputWallThickness.setBounds(154, 31, 75, 25);
		hollowProfilePanel.add(inputWallThickness);
		inputWallThickness.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setWallThickness( Double.parseDouble(inputWallThickness.getText()) ); }
			}
		});
		// ------------------------------------------
		inputCrosspieceWidth = new JFormattedTextField(doubleFormatter);
		inputCrosspieceWidth.setBounds(154, 56, 75, 25);
		hollowProfilePanel.add(inputCrosspieceWidth);
		inputCrosspieceWidth.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setCrosspieceWidth( Double.parseDouble(inputCrosspieceWidth.getText()) ); }
			}
		});
		// ------------------------------------------
		inputCrosspieceOffset = new JFormattedTextField(doubleFormatter);
		inputCrosspieceOffset.setBounds(154, 81, 75, 25);
		hollowProfilePanel.add(inputCrosspieceOffset);
		inputCrosspieceOffset.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setCrosspieceOffset( Double.parseDouble(inputCrosspieceOffset.getText()) ); }
			}
		});
		// ------------------------------------------
		inputFrontHollowOffset = new JFormattedTextField(doubleFormatter);
		inputFrontHollowOffset.setBounds(154, 105, 75, 25);
		hollowProfilePanel.add(inputFrontHollowOffset);
		inputFrontHollowOffset.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setFrontHollowOffset( Double.parseDouble(inputFrontHollowOffset.getText()) ); }
			}
		});
		// ------------------------------------------
		inputBackHollowOffset = new JFormattedTextField(doubleFormatter);
		inputBackHollowOffset.setBounds(154, 131, 75, 25);
		hollowProfilePanel.add(inputBackHollowOffset);
		inputBackHollowOffset.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (project != null) { project.setBackHollowOffset( Double.parseDouble(inputBackHollowOffset.getText()) ); }
			}
		});
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
		lblName.setBounds(6, 10, 49, 25);
		add(lblName);
		
		JLabel lblInputHalfspanLength = new JLabel("Half wingspan [mm]");
		lblInputHalfspanLength.setBounds(6, 44, 170, 25);
		add(lblInputHalfspanLength);
		
		JLabel lblInputHalfspanLength_1 = new JLabel("Base cord [mm]");
		lblInputHalfspanLength_1.setBounds(6, 68, 170, 25);
		add(lblInputHalfspanLength_1);
		
		JLabel lblInputHalfspanLength_1_1 = new JLabel("Tip cord [mm]");
		lblInputHalfspanLength_1_1.setBounds(6, 94, 170, 25);
		add(lblInputHalfspanLength_1_1);
		
		JLabel lblInputHalfspanLength_1_2 = new JLabel("Melting loss base [mm]");
		lblInputHalfspanLength_1_2.setBounds(6, 119, 170, 25);
		add(lblInputHalfspanLength_1_2);
		
		JLabel lblInputHalfspanLength_1_3 = new JLabel("Melting loss tip [mm]");
		lblInputHalfspanLength_1_3.setBounds(6, 144, 170, 25);
		add(lblInputHalfspanLength_1_3);
		
		JLabel lblInputHalfspanLength_1_3_1 = new JLabel("Wing sweep [deg]");
		lblInputHalfspanLength_1_3_1.setBounds(6, 169, 170, 25);
		add(lblInputHalfspanLength_1_3_1);
		
		JLabel lblInputHalfspanLength_1_3_2 = new JLabel("Wing tip offset [deg]");
		lblInputHalfspanLength_1_3_2.setBounds(6, 194, 170, 25);
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
		lblInputWingTipYOffset.setBounds(6, 220, 170, 25);
		add(lblInputWingTipYOffset);
		
		JLabel lblShiftCentermm = new JLabel("Shift center [mm]");
		lblShiftCentermm.setBounds(6, 245, 170, 25);
		add(lblShiftCentermm);

		
		// ------------------------------------------
		
		// ===============================================================
		
		// ------------------------------------------
		// =======================================================

		setPanelValues();
		setProfileValues();
		
    }
		
		
	public void setPanelValues() {
			
		if (project != null) {
		
			inputProjectName.setText(project.getProjectName());	
			inputHalfSpanLength.setValue(project.getHalfSpanLength());
			
			inputCutBaseFirst.setSelected(project.getCutBaseFirst());
			inputCutTopOnly.setSelected(project.getCutTopOnly());
			
			inputBaseCordLength.setValue(project.getBaseCordLength());
			inputTipCordLength.setValue(project.getTipCordLength());
			inputBaseMeltingLoss.setValue(project.getBaseMeltingLoss());
			inputTipMeltingLoss.setValue(project.getTipMeltingLoss());
			inputWingSweep.setValue(project.getWingSweep());
			inputWingTipOffset.setValue(project.getWingTipOffset());
			inputWingTipYOffset.setValue(project.getWingTipYOffset());
			inputShiftCenter.setValue(project.getShiftCenter());
			
			inputHasSparTop.setSelected(project.getHasSparTop());
			inputHasSparBottom.setSelected(project.getHasSparBottom());
			
			inputSparOffsetTop.setValue(project.getSparOffsetTop());
			inputSparWidthTop.setValue(project.getSparWidthTop());
			inputSparHeightTop.setValue(project.getSparHeightTop());
			inputSparOffsetBottom.setValue(project.getSparOffsetBottom());
			inputSparWidthBottom.setValue(project.getSparWidthBottom());
			inputSparHeightBottom.setValue(project.getSparHeightBottom());
			
			inputIsHollowed.setSelected(project.getIsHollowed());
			inputIsHollowedFrontOnly.setSelected(project.getIsHollowedFrontOnly());
			
			inputWallThickness.setValue(project.getWallThickness());
			inputCrosspieceWidth.setValue(project.getCrosspieceWidth());
			inputCrosspieceOffset.setValue(project.getCrosspieceOffset());
			inputFrontHollowOffset.setValue(project.getFrontHollowOffset());
			inputBackHollowOffset.setValue(project.getBackHollowOffset());
			
			inputBaseDirection.setSelected(project.getBaseDirection());
			inputTipDirection.setSelected(project.getTipDirection());

			setPanelEnabled(topSparePanel, project.getHasSparTop());
			setPanelEnabled(bottomSparePanel, project.getHasSparBottom());
			setPanelEnabled(hollowProfilePanel, project.getIsHollowed());
		
		}
	}
		
	public void setProfileValues() {
		
		if (project != null) {
			
			inputBaseProfileName.setText(project.getBaseProfileName());
			inputBaseProfileNumberPoints.setValue(project.getBaseProfileNumberPoints());
			inputBaseDirection.setSelected(project.getBaseDirection());

			inputTipProfileName.setText(project.getTipProfileName());
			inputTipProfileNumberPoints.setValue(project.getTipProfileNumberPoints());
			inputTipDirection.setSelected(project.getTipDirection());
		
		}
	}
    
	private void addMeAsProjectValuesChangedListener() {

		project.addProjectChangeListener(this);
	}
	
    
	// Hilfsfunktion
	// enable/disable Panels
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

	@Override
	public void ProjectValuesChangedEvent(ProjectChangeEvent evt) {
		if(evt.isProjectChangedCleanEvent()) {
			// btnSaveProject.setEnabled(false);
		};
		if(evt.isProjectChangedDirtyEvent()) {
			// btnSaveProject.setEnabled(true);	
		};
	}
}
