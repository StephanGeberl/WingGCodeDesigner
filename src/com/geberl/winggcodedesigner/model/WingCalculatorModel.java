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



import java.io.BufferedWriter;
import java.io.File;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Logger;

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
    private static final Logger logger = Logger.getLogger(Settings.class.getName());

	private final Collection<WingCalculatorEventListener> wingCalculatorEventListener = new ArrayList<>();
	
	
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
	
	private Double calcWingTipWrenchingOffset = 0.0;
	private Double calcWingTipOffset = 0.0;
	

	
	private String statusMessage = "<html><b>Idle</b></html>";
	
	
	public WingCalculatorModel() {

    }

	public WingCalculatorModel(Project project, Settings settings) {
         
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
		
		Double deltaBase = 0.0;
		Double deltaTip = 0.0;
		Double rightAddition = 0.0;  // Hilfsvariable: Abstand Winttip - Drahtbasis
		
		Double wireLength = SettingsFactory.settings.getWireLength();
		Double startDistance = SettingsFactory.settings.getStartDistance();
		Double shiftCenter = ProjectFactory.project.getShiftCenter();
		Double tipCordLength = ProjectFactory.project.getTipCordLength();
		Double baseCordLength = ProjectFactory.project.getBaseCordLength();
		Double halfSpanLength = ProjectFactory.project.getHalfSpanLength();
		Double origWingTipWrenchingOffset = ProjectFactory.project.getWingTipOffset();
		Double origWingTipOffset = ProjectFactory.project.getWingTipYOffset();
		
		rightAddition = ((wireLength - halfSpanLength) / 2) - shiftCenter;
		
		
		
		this.middleCordLength = (baseCordLength + tipCordLength)/2;
		deltaBase = ((wireLength + (2*shiftCenter))*(baseCordLength - this.middleCordLength))/ halfSpanLength;
		deltaTip = ((wireLength - (2*shiftCenter))*(baseCordLength - this.middleCordLength))/ halfSpanLength;
		

		// Berechne die Laengen an den Drahtdrehpunkten
		this.baseCordWireBase = this.middleCordLength + deltaBase;
		this.baseCordWire = this.baseCordWireBase + (2 * ProjectFactory.project.getBaseMeltingLoss());

		this.tipCordWireBase = this.middleCordLength - deltaTip;
		this.tipCordWire = this.tipCordWireBase + (2 * ProjectFactory.project.getTipMeltingLoss());

		
		
		// Schraenkung umrechnen
		
		this.calcWingTipWrenchingOffset = (origWingTipWrenchingOffset *(rightAddition + halfSpanLength)) / halfSpanLength;

		// Ueberhoehung umrechen

		this.calcWingTipOffset = (origWingTipOffset *(rightAddition + halfSpanLength)) / halfSpanLength;
		
		// Versatz der schmalen Flaechentiefe
		this.tipDeltaBase = (this.baseCordWire - this.tipCordWire)/2;
		
		// Versatz bei Pfeilung
		Double angleRad = Math.toRadians(ProjectFactory.project.getWingSweep());
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
	
	// der grosse Rechner
	public void calculateCoordinates() {
		
		ProfileCoordinate coordinate = null;

		// Berechne die Parameter
		calcParameters();
		
		// Koordinaten passend drehen und Nasenpunkt berechnen
		this.changeDirectionAndCalculateNose(ProjectFactory.project.baseProfileSet);
		this.changeDirectionAndCalculateNose(ProjectFactory.project.tipProfileSet);
		
		// Liste mit normierten Parametern und Holmen berechnen
		
		this.calculateNormMeasureWithSpars(ProjectFactory.project.baseProfileSet, ProjectFactory.project.getBaseCordLength());
		this.calculateNormMeasureWithSpars(ProjectFactory.project.tipProfileSet, ProjectFactory.project.getTipCordLength());
		
		// Hier Innenausschnitt berechnen
		
		// noch nicht implementiert
		
		// Listen generieren
		this.calculateGCodeList(ProjectFactory.project.baseProfileSet, ProjectFactory.project.gCodeBaseProfileSet);
		this.calculateGCodeList(ProjectFactory.project.tipProfileSet, ProjectFactory.project.gCodeTipProfileSet);

		
		// auf Wire-Ebene umrechnen mit Schränkung, Pfeilung und Startversatz
		
		Iterator<GCodeCoordinate> gCodeIterator = ProjectFactory.project.gCodeBaseProfileSet.iterator();
		while(gCodeIterator.hasNext()) 
		{
			GCodeCoordinate gCodeCoordinate = gCodeIterator.next();
			// Umrechnung Groesse auf Drahtebene
			gCodeCoordinate.calcBasicCoordinate(this.baseCordWire);
			// Startabstand addieren
			gCodeCoordinate.calcGcodeCoordinate(this.baseCordStartBaseTotal);
		
		}
		gCodeIterator = ProjectFactory.project.gCodeTipProfileSet.iterator();
		while(gCodeIterator.hasNext()) 
		{
			GCodeCoordinate gCodeCoordinate = gCodeIterator.next();
			// Umrechnung Groesse auf Drahtebene
			gCodeCoordinate.calcBasicCoordinate(this.tipCordWire);
			// Schraenkung und Y Ueberhoehung
			gCodeCoordinate.calcYOffset(this.calcWingTipWrenchingOffset, this.calcWingTipOffset, this.tipCordWire);
			// Startabstand addieren (+ implizit Trapez)
			gCodeCoordinate.calcGcodeCoordinate(this.baseCordStartTipTotal);

		}
		
			
		Integer n=0;	
		
		
		/*		
		
		
		Double startDistance = SettingsFactory.settings.getStartDistance();
		ProfileCoordinate coordinate = null;
		
		Double startBaseX = 0.0;
		Double startBaseY = 0.0;
		Double endBaseX = 0.0;
		Double endBaseY = 0.0;
		Double startTipX = 0.0;
		Double startTipY = 0.0;
		Double endTipX = 0.0;
		Double endTipY = 0.0;
		
		
		
		


		
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
		Iterator<ProfileCoordinate> iterGCodeBase = ProjectFactory.project.baseProfileSet.iterator();
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
		Iterator<ProfileCoordinate> iterGCodeTip = ProjectFactory.project.tipProfileSet.iterator();
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
*/		
		
	}

	
	// ==================
	// Koordinaten drehen (1 muss vorne liegen) und Nasenpunkt berechnen (muss immer nach dem Drehen sein)
	// ==================
	private void changeDirectionAndCalculateNose(LinkedHashSet<ProfileCoordinate> aProfileSet) {
		
		ProfileCoordinate coordinate = null;
		
		Iterator<ProfileCoordinate> iterBase = aProfileSet.iterator();
		while(iterBase.hasNext()) 
		{
			coordinate = iterBase.next();
			// X drehen so dass 1 vorne liegt (Profilnase)
			coordinate.changeDirection(ProjectFactory.project.getBaseDirection());
			// Nasenpunkt markieren
			if (coordinate.getXDirectionCoordinate() == 1.0 && coordinate.getYDirectionCoordinate() == 0.0) {
				coordinate.setIsNosePoint(true);
			}
		}
	}	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

	// ==================
	// Nasenpunkt berechnen
	// Koordinaten drehen (x Nase = 1)
	// Holme berechnen incl. dann nicht anfahrbaren Punkten
	// setzt xDirectionCoordinate und yDirectionCoordinate
	// ==================
	private void calculateNormMeasureWithSpars(LinkedHashSet<ProfileCoordinate> aProfileSet, Double aReferenceWidth) {
		
		Double sparTopWidth = 0.0;
		Double sparTopStart = 0.0;
		Double sparTopEnd = 0.0;
		Double sparTopHeight = 0.0;
		Double sparBottomWidth = 0.0;
		Double sparBottomStart = 0.0;
		Double sparBottomEnd = 0.0;
		Double sparBottomHeight = 0.0;
		
		LinkedHashSet<ProfileCoordinate> additionalProfileSet = null;
		
		ProfileCoordinate coordinate = null;
		ProfileCoordinate preCoordinate = null;
		ProfileCoordinate anchorCoordinate = null;
		Integer sparMarker = 0;
		Double sparYDirectionCoordinate = 0.0;
		
		sparTopStart = 1 - (ProjectFactory.project.getSparOffsetTop()/100);
		sparBottomStart = 1 - (ProjectFactory.project.getSparOffsetBottom()/100);
		
		sparTopWidth = ProjectFactory.project.getSparWidthTop() / aReferenceWidth;
		sparTopHeight = ProjectFactory.project.getSparHeightTop() / aReferenceWidth;
		sparTopEnd = sparTopStart - sparTopWidth;
		
		sparBottomWidth = ProjectFactory.project.getSparWidthBottom() / aReferenceWidth;
		sparBottomHeight = ProjectFactory.project.getSparHeightBottom() / aReferenceWidth;
		sparBottomEnd = sparBottomStart - sparBottomWidth;
		
		Iterator<ProfileCoordinate> iterBase = aProfileSet.iterator();
		while(iterBase.hasNext()) 
		{
			preCoordinate = coordinate;
			coordinate = iterBase.next();
			coordinate.setIgnorePoint(false);
			
			// =====================================================
			// Berechne Holm auf der Oberseite
			coordinate.profileAddition.clear();
			if (coordinate.getDirection() > 0 && ProjectFactory.project.getHasSparTop()) {
				if (coordinate.getXDirectionCoordinate() > sparTopEnd && coordinate.getXDirectionCoordinate() < sparTopStart) {
					
					coordinate.setIgnorePoint(true);
					sparMarker = sparMarker + 1;
					
					if(sparMarker == 1) {
						additionalProfileSet = new LinkedHashSet<ProfileCoordinate>();
						
						anchorCoordinate = preCoordinate;
						sparYDirectionCoordinate = anchorCoordinate.getYDirectionCoordinate();
						Double sparXDirectionCoordinate = sparTopEnd;
						
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, sparYDirectionCoordinate));
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, coordinate.getYDirectionCoordinate() - sparTopHeight));
						
					}
					else {
						Double sparXDirectionCoordinate = coordinate.getXDirectionCoordinate();
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, coordinate.getYDirectionCoordinate() - sparTopHeight));
						
						anchorCoordinate.profileAddition = additionalProfileSet;
						//sparMarker = 0;
					}
				}
				else {
					if (sparMarker > 0) {
						Double sparXDirectionCoordinate = sparTopStart;
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, coordinate.getYDirectionCoordinate() - sparTopHeight));
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, coordinate.getYDirectionCoordinate()));
						
						anchorCoordinate.profileAddition = additionalProfileSet;
						sparMarker = 0;
					}
				}
			}

			// =====================================================
			// Berechne Holm auf der Unterseite
			coordinate.profileAddition.clear();
			if (coordinate.getDirection() < 0 && ProjectFactory.project.getHasSparBottom()) {
				if (coordinate.getXDirectionCoordinate() > sparBottomEnd && coordinate.getXDirectionCoordinate() < sparBottomStart) {
					
					coordinate.setIgnorePoint(true);
					sparMarker = sparMarker + 1;
					
					if(sparMarker == 1) {
						additionalProfileSet = new LinkedHashSet<ProfileCoordinate>();
						
						anchorCoordinate = preCoordinate;
						sparYDirectionCoordinate = anchorCoordinate.getYDirectionCoordinate();
						Double sparXDirectionCoordinate = sparBottomStart;
						
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, sparYDirectionCoordinate));
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, coordinate.getYDirectionCoordinate() + sparBottomHeight));
						
					}
					else {
						Double sparXDirectionCoordinate = coordinate.getXDirectionCoordinate();
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, coordinate.getYDirectionCoordinate() + sparBottomHeight));
						
						anchorCoordinate.profileAddition = additionalProfileSet;
						//sparMarker = 0;
					}
				}
				else {
					if (sparMarker > 0) {
						Double sparXDirectionCoordinate = sparBottomEnd;
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, coordinate.getYDirectionCoordinate() + sparBottomHeight));
						additionalProfileSet.add(new ProfileCoordinate(sparXDirectionCoordinate, coordinate.getYDirectionCoordinate()));
						
						anchorCoordinate.profileAddition = additionalProfileSet;
						sparMarker = 0;
					}
				}
			}
		}
	}

	// ==================
	// G-Code Listen berechnen (verarbeitung der Extensions)
	// ==================
	private void calculateGCodeList(LinkedHashSet<ProfileCoordinate> profileSet, LinkedHashSet<GCodeCoordinate> gCodeSet) {
		// Wurzel
		Integer i = 0;
		ProfileCoordinate coordinate = null;
		
		gCodeSet.clear();
		
		Iterator<ProfileCoordinate> iterBase = profileSet.iterator();
		while(iterBase.hasNext()) 
		{
			coordinate = iterBase.next();
			if (!coordinate.getIgnorePoint()) {
				i = i + 1;
				gCodeSet.add(new GCodeCoordinate( coordinate.getXDirectionCoordinate(),
											 	  coordinate.getYDirectionCoordinate(),
											 	  i,
											 	  coordinate.getIsNosePoint(),
											 	  coordinate.getDirection()
												));
				
				if (!coordinate.profileAddition.isEmpty()) {
					Iterator<ProfileCoordinate> iterAddition = coordinate.profileAddition.iterator();
					while(iterAddition.hasNext()) 
					{
						coordinate = iterAddition.next();
						if (!coordinate.getIgnorePoint()) {
							i = i + 1;
							gCodeSet.add(new GCodeCoordinate( coordinate.getXDirectionCoordinate(),
														 	  coordinate.getYDirectionCoordinate(),
														 	  i,
														 	  false,
														 	  0
															));
						}
					}
				}
			}
		}

	}
	
	
	
	
	
	
	// ==================
	// G-Code berechnen und in das Array schreiben
	// ==================


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
	
	
	private void generateGcodeList(Boolean isRight) {
		
		Double saveHeight = SettingsFactory.settings.getSaveHeight();
		Double pause = SettingsFactory.settings.getPause();
		Double wireSpeed = SettingsFactory.settings.getWireSpeed();
		Double travelSpeed = SettingsFactory.settings.getTravelSpeed();
		
		GCodeCoordinate baseCordinate;
		GCodeCoordinate tipCordinate;
		
		String aLine = "";
		String aPrefix = "G01 ";
		String aPostfix = " F" + String.valueOf(wireSpeed.intValue());
		String aPostfixFast = " F" + String.valueOf(travelSpeed.intValue());
		String aWaitLine = "G4 P" + String.valueOf(pause.intValue()); // P in Sekunden!
				
		
		List<GCodeCoordinate> baseCoordinates = new ArrayList<GCodeCoordinate>( ProjectFactory.project.gCodeBaseProfileSet );
		List<GCodeCoordinate> tipCoordinates = new ArrayList<GCodeCoordinate>( ProjectFactory.project.gCodeTipProfileSet );
		
		if (ProjectFactory.project.getBaseProfileNumberPoints() > 0 && ProjectFactory.project.getTipProfileNumberPoints() > 0 &&
			(ProjectFactory.project.getBaseProfileNumberPoints() == ProjectFactory.project.getTipProfileNumberPoints()) ) {
			
			this.gCodeLines.add("(Start processing coordinates)");
			// Startkoordinaten
			// baseCordinate = baseCoordinates.get(ProjectFactory.project.getBaseProfileNumberPoints() - 1);
			// tipCordinate = tipCoordinates.get(ProjectFactory.project.getTipProfileNumberPoints() - 1);
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
			//	for (int i = ProjectFactory.project.getBaseProfileNumberPoints() - 1; i >= 0; i-- ) {
			// obere Profillinie zuerst
			for (int i = 0; i < ProjectFactory.project.getBaseProfileNumberPoints() - 1; i++ ) {
				
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
		this.sendWingCalculatorEvent(new WingCalculatorEvent(WingCalculatorEvent.EventType.CALCULATION_DONE_EVENT));

		
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
	public void ProjectChangeEvent(ProjectChangeEvent evt) {
		// TODO Auto-generated method stub

		if (evt.isProjectCalcRequestedEvent()) {
			logger.info("Request CALC");

			this.calculateCoordinates();
			this.sendWingCalculatorEvent(new WingCalculatorEvent(WingCalculatorEvent.EventType.CALCULATION_DONE_EVENT));
			
			
			
		};

		
	}


}
