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
package com.geberl.winggcodedesigner.model;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import com.geberl.winggcodedesigner.utils.GUIHelpers;
import com.geberl.winggcodedesigner.eventing.ProjectChangeEvent;
import com.geberl.winggcodedesigner.eventing.ProjectChangeEventListener;
import com.geberl.winggcodedesigner.eventing.WingCalculatorEvent;
import com.geberl.winggcodedesigner.eventing.WingCalculatorEventListener;
 

/**
 * Model - all calculations
 * 
 * @author sgeberl
 */

public class WingCalculatorModel implements ProjectChangeEventListener{

	JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

	private final Collection<WingCalculatorEventListener> wingCalculatorEventListener = new ArrayList<>();
	
	// =====================================
	// Input
	// =====================================
    public Settings settings;
    public Project project;
	
	private LinkedHashSet<String> gCodeLines = new LinkedHashSet<String>();
	// =====================================
	
	// calculated
	private Double middleCordLength = 0.0;
	private Double baseCordWire = 0.0;
	private Double tipCordWire = 0.0;
	private Double baseCordWireBase = 0.0;
	private Double tipCordWireBase = 0.0;

	private Double totalMaxX = 0.0;
	private Double totalMaxY = 0.0;

	private Double baseCordLengthBaseTotal = 0.0;
	private Double baseCordLengthTipTotal = 0.0;
	private Double baseCordStartBaseTotal = 0.0;
	private Double baseCordStartTipTotal = 0.0;

	private Double tipDeltaBase = 0.0;
	private Double tipDeltaSweep = 0.0;
	private Double tipDeltaAll = 0.0;
	

	
	private String statusMessage = "<html><b>Idle</b></html>";
	
	
	
	
	
	public WingCalculatorModel() {
        this.settings = null;
        this.project = null;
        

    }

	public WingCalculatorModel(Project project, Settings settings) {
        this.settings = settings;
        this.project = project;
        
    }


	// ==================
	// Set/Get Input Values
	// ==================
	

	// Return calculated Values
	
	public Double getMiddleCordLength() {return this.middleCordLength;};
	public Double getBaseCordWireBase() {return this.baseCordWireBase;};
	public Double getBaseCordWire() {return this.baseCordWire;};
	public Double getTipCordWireBase() {return this.tipCordWireBase;};
	public Double getTipCordWire() {return this.tipCordWire;};
	public Double getTipDeltaBase() {return this.tipDeltaBase;};
	public Double getTipDeltaSweep() {return this.tipDeltaSweep;};
	public Double getTipDeltaAll() {return this.tipDeltaAll;};
	
	
	public String getStatusMessage() {return this.statusMessage;}

	public Double getTotalMaxX() {return this.totalMaxX;}
	public Double getTotalMaxY() {return this.totalMaxY;}
	
	
	
	
	
	
	
	
	// ==================
	// Getter
	// ==================

	public Double getBaseCordLengthBaseTotal() {return this.baseCordLengthBaseTotal;}
	public Double getBaseCordLengthTipTotal() {return this.baseCordLengthTipTotal;}
	public Double getBaseCordStartBaseTotal() {return this.baseCordStartBaseTotal;}
	public Double getBaseCordStartTipTotal() {return this.baseCordStartTipTotal;}

	// ==================
	// Calculations
	// ==================

	private void calcParameters() {
		
		Double dtiw = 0.0;
		Double dti = 0.0;
		
		Double wireLength = this.settings.getWireLength();
		Double startDistance = this.settings.getStartDistance();
		
		this.middleCordLength = (project.getBaseCordLength() + project.getTipCordLength())/2;
		dti = (project.getBaseCordLength() - this.middleCordLength)/2;
		dtiw = wireLength * dti / project.getHalfSpanLength();
		
		this.baseCordWireBase = this.middleCordLength + (2 * dtiw);
		this.baseCordWire = this.baseCordWireBase + (2 * project.getBaseMeltingLoss());

		this.tipCordWireBase = this.middleCordLength - (2 * dtiw);
		this.tipCordWire = this.tipCordWireBase + (2 * project.getTipMeltingLoss());

		// Versatz der schmalen Flaechentiefe
		this.tipDeltaBase = (this.baseCordWire - this.tipCordWire)/2;
		
		// Versatz bei Pfeilung
		Double angleRad = Math.toRadians(project.getWingSweep());
		this.tipDeltaSweep = (-1) * (wireLength * Math.tan(angleRad));
		
		this.tipDeltaAll = this.tipDeltaBase + this.tipDeltaSweep;
		

		if (this.tipDeltaAll < 0) {
			this.baseCordLengthBaseTotal = startDistance + ((-1)* this.tipDeltaAll) + this.baseCordWireBase;
			this.baseCordLengthTipTotal = startDistance + this.tipCordWireBase;
			this.baseCordStartBaseTotal = startDistance + ((-1)* this.tipDeltaAll);
			this.baseCordStartTipTotal = startDistance;
		} else {
			this.baseCordLengthBaseTotal = startDistance + this.baseCordWireBase;
			this.baseCordLengthTipTotal = startDistance + this.tipDeltaAll + this.tipCordWireBase;
			this.baseCordStartBaseTotal = startDistance;
			this.baseCordStartTipTotal = startDistance + this.tipDeltaAll;
		}
	
	}

	// Schneiderichtung ändern (Standard ist immer invertieren) - Files fangen bei der Nase von 0 an
	// hier brauchen wir 0 an der Endleiste
	public void changeBaseProfileDirection(Boolean direction) {
		Iterator<ProfileCoordinate> iter = project.baseProfileSet.iterator();
		while(iter.hasNext()) 
		{
			ProfileCoordinate coordinate = iter.next();
			coordinate.changeDirection(direction);
		}
	}
	
	public void changeTipProfileDirection(Boolean direction) {
		Iterator<ProfileCoordinate> iter = project.tipProfileSet.iterator();
		while(iter.hasNext()) 
		{
			ProfileCoordinate coordinate = iter.next();
			coordinate.changeDirection(direction);
		}
	}
	

	// G-Code erzeugen
	public void addHeader(Boolean isRight) {
		
		this.gCodeLines.add("(Wing Cutter G-Code)");
		if (isRight == true) {
			this.gCodeLines.add("(Wingside: RIGHT)");
		}
		else {
			this.gCodeLines.add("(Wingside: LEFT)");
		};
		this.gCodeLines.add("G21");
		this.gCodeLines.add("G90");
		this.gCodeLines.add("G94");
	}
	
	public void addFooter() {
		this.gCodeLines.add("M30");

	}
	
	
	// der grosse Rechner
	public void calculateCoordinates() {
		
		ProfileCoordinate coordinate = null;
		Double startDistance = this.settings.getStartDistance();

		double sparTopStart = 0.0;
		double sparTopEnd = 0.0;
		double sparBottomStart = 0.0;
		double sparBottomEnd = 0.0;
		double startBaseX = 0.0;
		double startBaseY = 0.0;
		double endBaseX = 0.0;
		double endBaseY = 0.0;
		double startTipX = 0.0;
		double startTipY = 0.0;
		double endTipX = 0.0;
		double endTipY = 0.0;
		
		// Berechne die Parameter
		calcParameters();

		// Berechne die tatsächlichen Ausmasse incl. Holmausschnitte
		sparTopStart = this.baseCordWire * (100 - project.getSparOffsetTop())/100;
		sparTopEnd = sparTopStart - project.getSparWidthTop();
		sparBottomStart = this.baseCordWire * (100 - project.getSparOffsetBottom())/100;
		sparBottomEnd = sparBottomStart - project.getSparWidthBottom();
		
		Iterator<ProfileCoordinate> iterBase = project.baseProfileSet.iterator();
		while(iterBase.hasNext()) 
		{
			coordinate = iterBase.next();
			coordinate.changeDirection(project.getBaseDirection());
			coordinate.calcBasicCoordinate(this.baseCordWire);
			coordinate.calcSparCoordinateTop(sparTopStart, sparTopEnd, project.getSparHeightTop(), project.getHasSparTop());
			coordinate.calcSparCoordinateBottom(sparBottomStart, sparBottomEnd, project.getSparHeightBottom(), project.getHasSparBottom());
		}
		
		sparTopStart = this.tipCordWire * (100 - project.getSparOffsetTop())/100;
		sparTopEnd = sparTopStart - project.getSparWidthTop();
		sparBottomStart = this.tipCordWire * (100 - project.getSparOffsetBottom())/100;
		sparBottomEnd = sparBottomStart - project.getSparWidthBottom();
		Iterator<ProfileCoordinate> iterTip = project.tipProfileSet.iterator();
		while(iterTip.hasNext()) 
		{
			coordinate = iterTip.next();
			coordinate.changeDirection(project.getTipDirection());
			coordinate.calcBasicCoordinate(this.tipCordWire);
			coordinate.calcSparCoordinateTop(sparTopStart, sparTopEnd, project.getSparHeightTop(), project.getHasSparTop());
			coordinate.calcSparCoordinateBottom(sparBottomStart, sparBottomEnd, project.getSparHeightBottom(), project.getHasSparBottom());
		}

		// Berechne wenn gefragt die Schraenkung (im Moment nur Tip)
		Iterator<ProfileCoordinate> iterTipOffset = project.tipProfileSet.iterator();
		while(iterTipOffset.hasNext()) 
		{
			coordinate = iterTipOffset.next();
			coordinate.calcYOffset(project.getWingTipOffset(), project.getWingTipYOffset(), project.getTipCordLength());
		}

		// GCode berechnen
		this.totalMaxX = 0.0;
		this.totalMaxY = 0.0;
		Double startDistanceBase = 0.0;
		Double startDistanceTip = 0.0;
		Boolean isFirstCoordinate;
		
		if (this.tipDeltaAll < 0) {
			startDistanceBase = startDistance + (-1)* this.tipDeltaAll;
			startDistanceTip = startDistance;
		} else {
			startDistanceBase = startDistance;
			startDistanceTip = startDistance + this.tipDeltaAll;
		}
		
		isFirstCoordinate = true;
		Iterator<ProfileCoordinate> iterGCodeBase = project.baseProfileSet.iterator();
		while(iterGCodeBase.hasNext()) 
		{
			coordinate = iterGCodeBase.next();
			coordinate.calcGcodeCoordinate(startDistanceBase, 0.0);
			if (coordinate.getXGcodeCoordinate() > this.totalMaxX) { this.totalMaxX = coordinate.getXGcodeCoordinate(); }
			if (coordinate.getYGcodeCoordinate() > this.totalMaxY) { this.totalMaxY = coordinate.getYGcodeCoordinate(); }
			if (isFirstCoordinate) {
				startBaseX = coordinate.getXGcodeCoordinate();
				startBaseY = coordinate.getYGcodeCoordinate();
			}
			isFirstCoordinate = false;
		}
		if (coordinate != null ) { endBaseX = coordinate.getXGcodeCoordinate(); }
		if (coordinate != null ) { endBaseY = coordinate.getYGcodeCoordinate(); }
		
		isFirstCoordinate = true;
		Iterator<ProfileCoordinate> iterGCodeTip = project.tipProfileSet.iterator();
		while(iterGCodeTip.hasNext()) 
		{
			coordinate = iterGCodeTip.next();
			coordinate.calcGcodeCoordinate(startDistanceTip, 0.0);
			if (coordinate.getXGcodeCoordinate() > this.totalMaxX) { this.totalMaxX = coordinate.getXGcodeCoordinate(); }
			if (coordinate.getYGcodeCoordinate() > this.totalMaxY) { this.totalMaxY = coordinate.getYGcodeCoordinate(); }
			if (isFirstCoordinate) {
				startTipX = coordinate.getXGcodeCoordinate();
				startTipY = coordinate.getYGcodeCoordinate();
			}
			isFirstCoordinate = false;
		}
		if (coordinate != null ) { endTipX = coordinate.getXGcodeCoordinate(); }
		if (coordinate != null ) { endTipY = coordinate.getYGcodeCoordinate(); }
		
		
		this.statusMessage = "<html><b>Width: "
								+ String.valueOf(this.totalMaxX.intValue())
								+ " mm / Height: " 
								+ String.valueOf(this.totalMaxY.intValue()) 
								+ " mm</b></html>";
		
		this.sendWingCalculatorEvent(new WingCalculatorEvent(WingCalculatorEvent.EventType.CALCULATOR_STATUS_CHANGED_EVENT));
		
	}

	// ==================
	// G-Code berechnen und in das Array schreiben
	// ==================
	
	private void generateGcodeList(Boolean isRight) {
		
		Double saveHeight = settings.getSaveHeight();
		Double pause = settings.getPause();
		Double wireSpeed = settings.getWireSpeed();
		Double travelSpeed = settings.getTravelSpeed();
		
		ProfileCoordinate baseCordinate;
		ProfileCoordinate tipCordinate;
		
		String aLine = "";
		String aPrefix = "G01 ";
		String aPostfix = " F" + String.valueOf(wireSpeed.intValue());
		String aPostfixFast = " F" + String.valueOf(travelSpeed.intValue());
		String aWaitLine = "G4 P" + String.valueOf(pause.intValue()); // P in Sekunden!
				
		
		List<ProfileCoordinate> baseCoordinates = new ArrayList<ProfileCoordinate>( project.baseProfileSet );
		List<ProfileCoordinate> tipCoordinates = new ArrayList<ProfileCoordinate>( project.tipProfileSet );
		
		if (project.getBaseProfileNumberPoints() > 0 && project.getTipProfileNumberPoints() > 0 &&
			(project.getBaseProfileNumberPoints() == project.getTipProfileNumberPoints()) ) {
			
			this.gCodeLines.add("(Start processing coordinates)");
			// Startkoordinaten
			// baseCordinate = baseCoordinates.get(project.getBaseProfileNumberPoints() - 1);
			// tipCordinate = tipCoordinates.get(project.getTipProfileNumberPoints() - 1);
			baseCordinate = baseCoordinates.get(0);
			tipCordinate = tipCoordinates.get(0);

			// ===================================================================
			// In eine hoehensichere Position gehen
			// ===================================================================
			this.gCodeLines.add("(Goto zero at save height)");
			this.gCodeLines.add(
					aPrefix
					+ "X0.0 Y" + String.valueOf(saveHeight) 
					+ " Z0.0 A" + String.valueOf(saveHeight)
					+ aPostfixFast
					);

			// ===================================================================
			// an die X (Z) Startposition horizontal gehen (Pfeilung/ Trapez)
			// ===================================================================
	
			if (isRight) {
				aLine = aPrefix
						+ "X"
						+ String.valueOf(baseCordinate.getXGcodeCoordinate())
						+ " Y"
						+ String.valueOf(saveHeight)
						+ " Z"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(saveHeight)
						+ aPostfixFast;
			} else {
				aLine = aPrefix
						+ "X"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " Y"
						+ String.valueOf(saveHeight)
						+ " Z"
						+ String.valueOf(baseCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(saveHeight)
						+ aPostfixFast;
			}
			this.gCodeLines.add("(Goto start position at save height)");
			this.gCodeLines.add(aLine);
			
			// ===================================================================
			// auf die Endleistenstartposition vertikal gehen (1. Schnitt) und pause sec. warten
			// ===================================================================
			// die letzten Koordinaten verwenden
			
			if (isRight) {
				aLine = aPrefix
						+ "X"
						+ String.valueOf(baseCordinate.getXGcodeCoordinate())
						+ " Y"
						+ String.valueOf(baseCordinate.getYGcodeCoordinate())
						+ " Z"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(tipCordinate.getYGcodeCoordinate())
						+ aPostfix;
			} else {
				aLine = aPrefix
						+ "X"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " Y"
						+ String.valueOf(tipCordinate.getYGcodeCoordinate())
						+ " Z"
						+ String.valueOf(baseCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(baseCordinate.getYGcodeCoordinate())
						+ aPostfix;
			}
			this.gCodeLines.add("(Goto vertical start position)");
			this.gCodeLines.add(aLine);
			this.gCodeLines.add(aWaitLine);
			
			// ===================================================================
			// Koordinaten abfahren und am Ende 4 sec. warten
			// ===================================================================

			this.gCodeLines.add("(Start process profile coordinates)");
			// untere Profillinie zuerst
			//	for (int i = project.getBaseProfileNumberPoints() - 1; i >= 0; i-- ) {
			// obere Profillinie zuerst
			for (int i = 0; i < project.getBaseProfileNumberPoints() - 1; i++ ) {
				
				baseCordinate = baseCoordinates.get(i);
				tipCordinate = tipCoordinates.get(i);
				

				if (isRight) {
					aLine = aPrefix
							+ "X"
							+ String.valueOf(baseCordinate.getXGcodeCoordinate())
							+ " Y"
							+ String.valueOf(baseCordinate.getYGcodeCoordinate())
							+ " Z"
							+ String.valueOf(tipCordinate.getXGcodeCoordinate())
							+ " A"
							+ String.valueOf(tipCordinate.getYGcodeCoordinate())
							+ aPostfix;
				} else {
					aLine = aPrefix
							+ "X"
							+ String.valueOf(tipCordinate.getXGcodeCoordinate())
							+ " Y"
							+ String.valueOf(tipCordinate.getYGcodeCoordinate())
							+ " Z"
							+ String.valueOf(baseCordinate.getXGcodeCoordinate())
							+ " A"
							+ String.valueOf(baseCordinate.getYGcodeCoordinate())
							+ aPostfix;
				}
				this.gCodeLines.add(aLine);
			}
			
			
			
			
			this.gCodeLines.add("(End process profile coordinates)");
			this.gCodeLines.add(aWaitLine);
			
			// ===================================================================
			// In eine hoehensichere Position gehen (letzter Schnitt) und wieder warten
			// ===================================================================

			// Achtung, wir nutzen die letzten Koordinaten des Profils
			if (isRight) {
				aLine = aPrefix
						+ "X"
						+ String.valueOf(baseCordinate.getXGcodeCoordinate())
						+ " Y"
						+ String.valueOf(saveHeight)
						+ " Z"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(saveHeight)
						+ aPostfix;
			} else {
				aLine = aPrefix
						+ "X"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " Y"
						+ String.valueOf(saveHeight)
						+ " Z"
						+ String.valueOf(baseCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(saveHeight)
						+ aPostfix;
			}
			this.gCodeLines.add("(Goto save height verticaly)");
			this.gCodeLines.add(aLine);
			this.gCodeLines.add(aWaitLine);

			// ===================================================================
			// In save hight move to 0 horizontal
			// ===================================================================
			
			this.gCodeLines.add("(Goto zero horizontaly)");

			System.out.println(
					"<"
					+ aPrefix
					+ "X0.0"
					+ " Y"
					+ String.valueOf(saveHeight)
					+ " Z0.0"
					+ " A"
					+ String.valueOf(saveHeight)
					+ aPostfix			
					+ ">"
			);
			
			
			this.gCodeLines.add(
					aPrefix
					+ "X0.0"
					+ " Y"
					+ String.valueOf(saveHeight)
					+ " Z0.0"
					+ " A"
					+ String.valueOf(saveHeight)
					+ aPostfix
					);
			
			// ===================================================================
			// In Y (A) (vertikal) auf 0 fahren
			// ===================================================================
			this.gCodeLines.add("(Goto zero verticaly)");
			this.gCodeLines.add(
					aPrefix
					+ "X0.0 Y0.0 Z0.0 A0.0"
					+ aPostfixFast
					);

			this.gCodeLines.add("(End processing coordinates)");
			
		}
		else {
			GUIHelpers.displayErrorDialog("GCode export is only possible on two profiles with identical number of coordinates");
		}
	}

	
	// ==================
	// G-Code Speichern
	// ==================

	public void saveRightGcodeList() {
		this.gCodeLines.clear();
		this.calculateCoordinates();
		this.addHeader(true);
		this.generateGcodeList(true);
		this.addFooter();
		this.saveGcodeFile();
	}
	
	public void saveLeftGcodeList() {
		this.gCodeLines.clear();
		this.calculateCoordinates();
		this.addHeader(false);
		this.generateGcodeList(false);
		this.addFooter();
		this.saveGcodeFile();
	}
	
	public void drawDraft() {
		this.calculateCoordinates();
		this.sendWingCalculatorEvent(new WingCalculatorEvent(WingCalculatorEvent.EventType.CALCULATOR_DRAW_EVENT));
		
	}

	
	// Speicherroutine
	public void saveGcodeFile() {
		int returnVal = fileChooser.showSaveDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				BufferedWriter aWriter = null;
				File GCodeFile = fileChooser.getSelectedFile();
				File outputFile = new File(GCodeFile.getAbsolutePath());
				if (!outputFile.exists()) {
					outputFile.createNewFile();
				}
				FileWriter fileWriter = new FileWriter(outputFile);
				aWriter = new BufferedWriter(fileWriter);
				
				Iterator<String> iterGCode = gCodeLines.iterator();
				while(iterGCode.hasNext()) 
				{
					String aLine = iterGCode.next();
					aWriter.write(aLine);
					aWriter.newLine();
				}
				aWriter.flush();
				aWriter.close();
				
				System.out.println("File written Successfully");
				
			} catch (Exception ex) {
				GUIHelpers.displayErrorDialog("An exception "+ex.getMessage()+" has occured reading profile coordinates.");
			}
		}
	}


	
	// ==================
	// Eventing
	// ==================
	private void sendWingCalculatorEvent(WingCalculatorEvent event) {
		wingCalculatorEventListener.forEach(l -> l.WingCalculatorEvent(event));
	}

	public void addWingCalculatorEventListener(WingCalculatorEventListener listener) {
		if (!wingCalculatorEventListener.contains(listener)) {
			wingCalculatorEventListener.add(listener);
		}
	}

	public void removeWingCalculatorEventListener(WingCalculatorEventListener listener) {
		if (wingCalculatorEventListener.contains(listener)) {
			wingCalculatorEventListener.remove(listener);
		}
	}

	@Override
	public void ProjectValuesChangedEvent(ProjectChangeEvent evt) {
		// TODO Auto-generated method stub
		
		if (evt.isProjectChangedCleanEvent()) {
			System.out.println("Project is Clean");
			
			
		}
		if (evt.isProjectChangedDirtyEvent()) {
			System.out.println("Project is Dirty");
			
		};

		
	}

	public void saveSettings() {
		SettingsFactory.saveSettings(settings);

	}


}
