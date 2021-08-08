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

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

import com.geberl.winggcodedesigner.utils.GUIHelpers;
import com.geberl.winggcodedesigner.utils.SettingChangeListener;
import com.geberl.winggcodedesigner.utils.Settings;
// import com.geberl.winggcodedesigner.utils.SettingsFactory;
import com.geberl.winggcodedesigner.listeners.WingCalculatorEventListener;
 

/**
 * Model - all calculations
 * 
 * @author sgeberl
 */

public class WingCalculatorModel implements SettingChangeListener {

	private javax.swing.JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

	private final Collection<WingCalculatorEventListener> wingCalculatorEventListener = new ArrayList<>();

    private Settings settings;
	
	
	
	private LinkedHashSet<ProfileCoordinate> baseProfileSet = new LinkedHashSet<ProfileCoordinate>();
	private LinkedHashSet<ProfileCoordinate> tipProfileSet = new LinkedHashSet<ProfileCoordinate>();
	private LinkedHashSet<String> gCodeLines = new LinkedHashSet<String>();

	
	private String baseProfileName = "";
	private String tipProfileName = "";
	private Integer baseProfilePointNumber = 0;
	private Integer tipProfilePointNumber = 0;
	
	private Boolean baseDirection = true;
	private Boolean tipDirection = true;
	
	// =====================================
	// Parameter
	// =====================================
	private Double wireLength = 0.0;
	private Double startDistance = 0.0;
	private Double saveHeight = 0.0;
	private Double pause = 0.0;
	private Double wireSpeed = 0.0;
	private Double travelSpeed = 1000.00;
	private Boolean cutBaseFirst = false;
	// =====================================

	
	
	private Double halfspanLength = 0.0;
	private Double baseCordLength = 0.0;
	private Double tipCordLength = 0.0;
	private Double wingTipOffset = 0.0;
	private Double wingTipYOffset = 0.0;
	
	// calculated
	private Double middleCordLength = 0.0;
	private Double baseCordWire = 0.0;
	private Double tipCordWire = 0.0;
	private Double baseCordWireBase = 0.0;
	private Double tipCordWireBase = 0.0;
	
	// ----------
	
	private Double baseMeltingLoss = 0.0;
	private Double tipMeltingLoss = 0.0;
	private Double wingSweep = 0.0;
	
	private Boolean hasSparTop = false;
	private Boolean hasSparBottom = false;
	private Double sparOffsetTop = 0.0;
	private Double sparWidthTop = 0.0;
	private Double sparHeightTop = 0.0;
	private Double sparOffsetBottom = 0.0;
	private Double sparWidthBottom = 0.0;
	private Double sparHeightBottom = 0.0;

	private Double totalMaxX = 0.0;
	private Double totalMaxY = 0.0;

	private Double baseCordLengthBaseTotal = 0.0;
	private Double baseCordLengthTipTotal = 0.0;
	private Double baseCordStartBaseTotal = 0.0;
	private Double baseCordStartTipTotal = 0.0;
	
	
	private String statusMessage = "<html><b>Idle</b></html>";
	
	private Double tipDeltaBase = 0.0;
	private Double tipDeltaSweep = 0.0;
	private Double tipDeltaAll = 0.0;
	
	
	
	
	
	public WingCalculatorModel() {

    }

	public WingCalculatorModel(Settings settings) {
		this.settings = settings;
		this.settings.setSettingChangeListener(this);
        
		try {
            this.applySettings();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

	private void applySettings() {
		this.wireLength = settings.getWireLength();
		this.startDistance = settings.getStartDistance();
		this.saveHeight = settings.getSaveHeight();
		this.pause = settings.getPause();
		this.wireSpeed = settings.getWireSpeed();
		this.travelSpeed = settings.getTravelSpeed();
		this.cutBaseFirst = settings.getCutBaseFirst();
		
		
	}

	// ==================
	// Setter
	// ==================
	public void setBaseDirection(Boolean aValue) { this.baseDirection = aValue; this.changeBaseProfileDirection(aValue); }
	public void setTipDirection(Boolean aValue) { this.tipDirection = aValue; this.changeTipProfileDirection(aValue); }

	public void setHalfspanLength(Double aValue) { this.halfspanLength = aValue; }
	public void setBaseCordLength(Double aValue) { this.baseCordLength = aValue; }
	public void setTipCordLength(Double aValue) { this.tipCordLength = aValue; }
	public void setBaseMeltingLoss(Double aValue) { this.baseMeltingLoss = aValue; }
	public void setTipMeltingLoss(Double aValue) { this.tipMeltingLoss = aValue; }

	public void setWingSweep(Double aValue) { this.wingSweep = aValue; }
	public void setWingTipOffset(Double aValue) { this.wingTipOffset = aValue; }
	public void setWingTipYOffset(Double aValue) { this.wingTipYOffset = aValue; }

	public void setHasSparTop(Boolean aValue) { this.hasSparTop = aValue; }
	public void setHasSparBottom(Boolean aValue) { this.hasSparBottom = aValue; }
	public void setSparOffsetTop(Double aValue) { this.sparOffsetTop = aValue; }
	public void setSparWidthTop(Double aValue) { this.sparWidthTop = aValue; }
	public void setSparHeightTop(Double aValue) { this.sparHeightTop = aValue; }
	public void setSparOffsetBottom(Double aValue) { this.sparOffsetBottom = aValue; }
	public void setSparWidthBottom(Double aValue) { this.sparWidthBottom = aValue; }
	public void setSparHeightBottom(Double aValue) { this.sparHeightBottom = aValue; }

	// ==================
	// Parameter
	// ==================
	public void setWireLength(Double aValue) { this.wireLength = aValue; this.settings.setWireLength(aValue); }
	public void setStartDistance(Double aValue) { this.startDistance = aValue; this.settings.setStartDistance(aValue); }
	public void setSaveHeight(Double aValue) { this.saveHeight = aValue; this.settings.setSaveHeight(aValue); }
	public void setPause(Double aValue) { this.pause = aValue; this.settings.setPause(aValue); }
	public void setWireSpeed(Double aValue) { this.wireSpeed = aValue; this.settings.setWireSpeed(aValue); }
	public void setTravelSpeed(Double aValue) { this.travelSpeed = aValue; this.settings.setTravelSpeed(aValue); }
	public void setCutBaseFirst(Boolean aValue) { this.cutBaseFirst = aValue; this.settings.setCutBaseFirst(aValue); }

	public Double getWireLength() {return this.wireLength;}
	public Double getStartDistance() {return this.startDistance;}
	public Double getSaveHeight() {return this.saveHeight;}
	public Double getPause() {return this.pause;}
	public Double getWireSpeed() {return this.wireSpeed;}
	public Double getTravelSpeed() {return this.travelSpeed;}
	public Boolean getCutBaseFirst() {return this.cutBaseFirst;}
	
	
	// ==================
	// Getter
	// ==================
	public Double getBaseCordLengthBaseTotal() {return this.baseCordLengthBaseTotal;}
	public Double getBaseCordLengthTipTotal() {return this.baseCordLengthTipTotal;}
	public Double getBaseCordStartBaseTotal() {return this.baseCordStartBaseTotal;}
	public Double getBaseCordStartTipTotal() {return this.baseCordStartTipTotal;}

	public String getBaseProfileName() {return this.baseProfileName;}
	public Integer getBaseProfilePointNumber() {return this.baseProfilePointNumber;}
	public String getTipProfileName() {return this.tipProfileName;}
	public Integer getTipProfilePointNumber() {return this.tipProfilePointNumber;}
	public String getStatusMessage() {return this.statusMessage;}

	public Double getTotalMaxX() {return this.totalMaxX;}
	public Double getTotalMaxY() {return this.totalMaxY;}
	
	public Double getMiddleCordLength() {return this.middleCordLength;};
	public Double getBaseCordWireBase() {return this.baseCordWireBase;};
	public Double getBaseCordWire() {return this.baseCordWire;};
	public Double getTipCordWireBase() {return this.tipCordWireBase;};
	public Double getTipCordWire() {return this.tipCordWire;};
	public Double getTipDeltaBase() {return this.tipDeltaBase;};
	public Double getTipDeltaSweep() {return this.tipDeltaSweep;};
	public Double getTipDeltaAll() {return this.tipDeltaAll;};
	public Double getHalfspanLength() {return this.halfspanLength;};

	
	
	public LinkedHashSet<ProfileCoordinate> getBaseProfileSet() {return this.baseProfileSet;}
	public LinkedHashSet<ProfileCoordinate> getTipProfileSet() {return this.tipProfileSet;}

	// ==================
	// Calculations
	// ==================

	private void calcParameters() {
		
		Double dtiw = 0.0;
		Double dti = 0.0;
		
		this.middleCordLength = (this.baseCordLength + this.tipCordLength)/2;
		dti = (this.baseCordLength - this.middleCordLength)/2;
		dtiw = this.wireLength * dti / this.halfspanLength;
		
		this.baseCordWireBase = this.middleCordLength + (2 * dtiw);
		this.baseCordWire = this.baseCordWireBase + (2 * this.baseMeltingLoss);

		this.tipCordWireBase = this.middleCordLength - (2 * dtiw);
		this.tipCordWire = this.tipCordWireBase + (2 * this.tipMeltingLoss);

		// Versatz der schmalen Flaechentiefe
		this.tipDeltaBase = (this.baseCordWire - this.tipCordWire)/2;
		
		// Versatz bei Pfeilung
		Double angleRad = Math.toRadians(this.wingSweep);
		this.tipDeltaSweep = (-1) * (this.wireLength * Math.tan(angleRad));
		
		this.tipDeltaAll = this.tipDeltaBase + this.tipDeltaSweep;
		

		if (this.tipDeltaAll < 0) {
			this.baseCordLengthBaseTotal = this.startDistance + ((-1)* this.tipDeltaAll) + this.baseCordWireBase;
			this.baseCordLengthTipTotal = this.startDistance + this.tipCordWireBase;
			this.baseCordStartBaseTotal = this.startDistance + ((-1)* this.tipDeltaAll);
			this.baseCordStartTipTotal = this.startDistance;
		} else {
			this.baseCordLengthBaseTotal = this.startDistance + this.baseCordWireBase;
			this.baseCordLengthTipTotal = this.startDistance + this.tipDeltaAll + this.tipCordWireBase;
			this.baseCordStartBaseTotal = this.startDistance;
			this.baseCordStartTipTotal = this.startDistance + this.tipDeltaAll;
		}
	
	}

	// Schneiderichtung ändern (Standard ist immer invertieren) - Files fangen bei der Nase von 0 an
	// hier brauchen wir 0 an der Endleiste
	public void changeBaseProfileDirection(Boolean direction) {
		Iterator<ProfileCoordinate> iter = baseProfileSet.iterator();
		while(iter.hasNext()) 
		{
			ProfileCoordinate coordinate = iter.next();
			coordinate.changeDirection(direction);
		}
	}
	
	public void changeTipProfileDirection(Boolean direction) {
		Iterator<ProfileCoordinate> iter = tipProfileSet.iterator();
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
		sparTopStart = this.baseCordWire * (100 - this.sparOffsetTop)/100;
		sparTopEnd = sparTopStart - this.sparWidthTop;
		sparBottomStart = this.baseCordWire * (100 - this.sparOffsetBottom)/100;
		sparBottomEnd = sparBottomStart - this.sparWidthBottom;
		
		Iterator<ProfileCoordinate> iterBase = baseProfileSet.iterator();
		while(iterBase.hasNext()) 
		{
			coordinate = iterBase.next();
			coordinate.changeDirection(this.baseDirection);
			coordinate.calcBasicCoordinate(this.baseCordWire);
			coordinate.calcSparCoordinateTop(sparTopStart, sparTopEnd, this.sparHeightTop, this.hasSparTop);
			coordinate.calcSparCoordinateBottom(sparBottomStart, sparBottomEnd, this.sparHeightBottom, this.hasSparBottom);
		}
		
		sparTopStart = this.tipCordWire * (100 - this.sparOffsetTop)/100;
		sparTopEnd = sparTopStart - this.sparWidthTop;
		sparBottomStart = this.tipCordWire * (100 - this.sparOffsetBottom)/100;
		sparBottomEnd = sparBottomStart - this.sparWidthBottom;
		Iterator<ProfileCoordinate> iterTip = tipProfileSet.iterator();
		while(iterTip.hasNext()) 
		{
			coordinate = iterTip.next();
			coordinate.changeDirection(this.tipDirection);
			coordinate.calcBasicCoordinate(this.tipCordWire);
			coordinate.calcSparCoordinateTop(sparTopStart, sparTopEnd, this.sparHeightTop, this.hasSparTop);
			coordinate.calcSparCoordinateBottom(sparBottomStart, sparBottomEnd, this.sparHeightBottom, this.hasSparBottom);
		}

		// Berechne wenn gefragt die Schraenkung (im Moment nur Tip)
		Iterator<ProfileCoordinate> iterTipOffset = tipProfileSet.iterator();
		while(iterTipOffset.hasNext()) 
		{
			coordinate = iterTipOffset.next();
			coordinate.calcYOffset(this.wingTipOffset, this.wingTipYOffset, this.tipCordLength);
		}

		// GCode berechnen
		this.totalMaxX = 0.0;
		this.totalMaxY = 0.0;
		Double startDistanceBase = 0.0;
		Double startDistanceTip = 0.0;
		Boolean isFirstCoordinate;
		
		if (this.tipDeltaAll < 0) {
			startDistanceBase = this.startDistance + (-1)* this.tipDeltaAll;
			startDistanceTip = this.startDistance;
		} else {
			startDistanceBase = this.startDistance;
			startDistanceTip = this.startDistance + this.tipDeltaAll;
		}
		
		isFirstCoordinate = true;
		Iterator<ProfileCoordinate> iterGCodeBase = baseProfileSet.iterator();
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
		Iterator<ProfileCoordinate> iterGCodeTip = tipProfileSet.iterator();
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
		
		Double aSaveHeight = 100.0;
		ProfileCoordinate baseCordinate;
		ProfileCoordinate tipCordinate;
		
		String aLine = "";
		String aPrefix = "G01 ";
		String aPostfix = " F" + String.valueOf(this.wireSpeed.intValue());
		String aPostfixFast = " F" + String.valueOf(this.travelSpeed.intValue());
		String aWaitLine = "G4 P" + String.valueOf(this.pause.intValue()); // P in Sekunden!
				
		
		List<ProfileCoordinate> baseCoordinates = new ArrayList<ProfileCoordinate>( baseProfileSet );
		List<ProfileCoordinate> tipCoordinates = new ArrayList<ProfileCoordinate>( tipProfileSet );
		
		if (this.baseProfilePointNumber > 0 && this.tipProfilePointNumber > 0 &&
			(this.baseProfilePointNumber.intValue() == this.tipProfilePointNumber.intValue()) ) {
			
			this.gCodeLines.add("(Start processing coordinates)");
			// Startkoordinaten
			// baseCordinate = baseCoordinates.get(this.baseProfilePointNumber - 1);
			// tipCordinate = tipCoordinates.get(this.baseProfilePointNumber - 1);
			baseCordinate = baseCoordinates.get(0);
			tipCordinate = tipCoordinates.get(0);

			// ===================================================================
			// In eine hoehensichere Position gehen
			// ===================================================================
			this.gCodeLines.add("(Goto zero at save height)");
			this.gCodeLines.add(
					aPrefix
					+ "X0.0 Y" + String.valueOf(aSaveHeight) 
					+ " Z0.0 A" + String.valueOf(aSaveHeight)
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
						+ String.valueOf(aSaveHeight)
						+ " Z"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(aSaveHeight)
						+ aPostfixFast;
			} else {
				aLine = aPrefix
						+ "X"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " Y"
						+ String.valueOf(aSaveHeight)
						+ " Z"
						+ String.valueOf(baseCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(aSaveHeight)
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
			//	for (int i = this.baseProfilePointNumber - 1; i >= 0; i-- ) {
			// obere Profillinie zuerst
			for (int i = 0; i < this.baseProfilePointNumber - 1; i++ ) {
				
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
						+ String.valueOf(aSaveHeight)
						+ " Z"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(aSaveHeight)
						+ aPostfix;
			} else {
				aLine = aPrefix
						+ "X"
						+ String.valueOf(tipCordinate.getXGcodeCoordinate())
						+ " Y"
						+ String.valueOf(aSaveHeight)
						+ " Z"
						+ String.valueOf(baseCordinate.getXGcodeCoordinate())
						+ " A"
						+ String.valueOf(aSaveHeight)
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
					+ String.valueOf(aSaveHeight)
					+ " Z0.0"
					+ " A"
					+ String.valueOf(aSaveHeight)
					+ aPostfix			
					+ ">"
			);
			
			
			this.gCodeLines.add(
					aPrefix
					+ "X0.0"
					+ " Y"
					+ String.valueOf(aSaveHeight)
					+ " Z0.0"
					+ " A"
					+ String.valueOf(aSaveHeight)
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
	// Profilkoordinaten laden
	// ==================
	public void loadBaseProfileData() {
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				this.baseProfileName = "";
				this.baseProfilePointNumber = 0;
				this.baseProfileSet.clear();

				File baseProfileFile = fileChooser.getSelectedFile();
				String oldProfileLine = "";
				String profileLine = "";
				Integer pointNum = 0;
				BufferedReader profileReader = new BufferedReader(new FileReader(baseProfileFile.getAbsolutePath())); 
				
				while ((profileLine = profileReader.readLine()) != null) {
					// just skip empty Lines
					if (!(profileLine.trim()).contentEquals("")) {
						profileLine = profileLine.trim();
						if (pointNum == 0) {
							this.baseProfileName = profileLine;
						} else {
							baseProfileSet.add(new ProfileCoordinate(profileLine, oldProfileLine));
							oldProfileLine = profileLine;
						}
						pointNum = pointNum + 1;
					};
				}; 
				this.baseProfilePointNumber = pointNum -1;
				// this.calculateCoordinates(); 
				this.sendWingCalculatorEvent(new WingCalculatorEvent(WingCalculatorEvent.EventType.BASE_PROFILE_CHANGED_EVENT));
				this.sendWingCalculatorEvent(new WingCalculatorEvent(WingCalculatorEvent.EventType.CALCULATOR_STATUS_CHANGED_EVENT));
			
			} catch (Exception ex) {
				GUIHelpers.displayErrorDialog("Problem saving controller config: " + ex.getMessage());
			}
		}
	}

	public void loadTipProfileData() {
		
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				this.tipProfileName = "";
				this.tipProfilePointNumber = 0;
				this.tipProfileSet.clear();

				File tipProfileFile = fileChooser.getSelectedFile();
				String oldProfileLine = "";
				String profileLine = "";
				Integer pointNum = 0;
				BufferedReader profileReader = new BufferedReader(new FileReader(tipProfileFile.getAbsolutePath())); 
				
				while ((profileLine = profileReader.readLine()) != null) {
					if (!(profileLine.trim()).contentEquals("")) {
						profileLine = profileLine.trim();
						if (pointNum == 0) {
							this.tipProfileName = profileLine;
						} else {
							tipProfileSet.add(new ProfileCoordinate(profileLine, oldProfileLine));
							oldProfileLine = profileLine;
						}
						pointNum = pointNum + 1;
					};
				}; 
				this.tipProfilePointNumber = pointNum -1;
				// this.calculateCoordinates(); 
				
				this.sendWingCalculatorEvent(new WingCalculatorEvent(WingCalculatorEvent.EventType.TIP_PROFILE_CHANGED_EVENT));
				this.sendWingCalculatorEvent(new WingCalculatorEvent(WingCalculatorEvent.EventType.CALCULATOR_STATUS_CHANGED_EVENT));
			} catch (Exception ex) {
				GUIHelpers.displayErrorDialog("Problem saving controller config: " + ex.getMessage());
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

	// ==================
	// Eventing
	// ==================

	@Override
	public void settingChanged() {
		// TODO Auto-generated method stub
		
	}



}
