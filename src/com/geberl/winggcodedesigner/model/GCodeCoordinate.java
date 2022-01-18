/*
 * 
 * Coordinate for a Profile
 * 
 */

/*
	2020 Stephan Geberl

 */

package com.geberl.winggcodedesigner.model;

import java.util.LinkedHashSet;

public class GCodeCoordinate {

	public LinkedHashSet<GCodeCoordinate> profileAddition = new LinkedHashSet<GCodeCoordinate>();

	// Laufnummer
	private Integer coordNumber = 0;
	private Boolean isNosePoint = false;
	private Boolean ignorePoint = false;
	private Integer direction = 0;       // -1 Unterseite, +1 Oberseite
	
	// gelesener Koordinatenstring
	private String readCoordinateString = "";
	private String readOldCoordinateString = "";

	// gelesene Koordinaten
	private Double xReadCoordinate = 0.0;
	private Double yReadCoordinate = 0.0;
	
	// nach Richtungskorrektur
	private Double xDirectionCoordinate = 0.0;
	private Double yDirectionCoordinate = 0.0;

	// nach Umrechnung auf tatsaechliche Groesse
	private Double xBasicCoordinate = 0.0;
	private Double yBasicCoordinate = 0.0;

	// Koordinaten unter Einbezug von Startabstand, Trapez, Pfeilung
	private Double xGcodeCoordinate = 0.0;
	private Double yGcodeCoordinate = 0.0;
	
	private Double xOldCoordinate = 0.0;

	/**
	* Constructs and initializes a ProfileCoordinate from the read String.
	* @param String Containing x and y coordinates
	*/
	public GCodeCoordinate()
	{
		this.setReadCoordinateString("");
		this.setReadOldCoordinateString("");
		this.xReadCoordinate = 0.0;
		this.yReadCoordinate = 0.0;
		this.xOldCoordinate = 0.0;
		this.setDirection(0);      // -1 Unterseite, +1 Oberseite
	}

	public GCodeCoordinate(String aReadCoordinateString, String aOldCoordinateString, Integer aCoordinateNumber)
	{
		this.setReadCoordinateString(aReadCoordinateString);
		this.setReadOldCoordinateString(aOldCoordinateString);
		
		this.setCoordNumber(aCoordinateNumber);
		
		this.xReadCoordinate = this.calculateXCoordinate(aReadCoordinateString);
		this.yReadCoordinate = this.calculateYCoordinate(aReadCoordinateString);
		
		this.xOldCoordinate = 0.0;
		this.setDirection(0);      // -1 Unterseite, +1 Oberseite
		
		if (aOldCoordinateString.contentEquals("")) { xOldCoordinate = 1.0; }
		else { xOldCoordinate = this.calculateXCoordinate(aOldCoordinateString); }
		
		if (xReadCoordinate < xOldCoordinate) { setDirection(1); }
		else { setDirection(-1); }

	}

	// Konstruktor fuer Insets
	public GCodeCoordinate(Double xDirectionCoordinate, Double yDirectionCoordinate)
	{
		this.setReadCoordinateString("");
		this.setReadOldCoordinateString("");
		this.xReadCoordinate = 0.0;
		this.yReadCoordinate = 0.0;
		this.xOldCoordinate = 0.0;
		this.setDirection(0);      // -1 Unterseite, +1 Oberseite
		
		this.xDirectionCoordinate = xDirectionCoordinate;
		this.yDirectionCoordinate = yDirectionCoordinate;

	}
	
	
	
	
	
	private void setReadOldCoordinateString(String aOldCoordinateString) {
		this.readOldCoordinateString = aOldCoordinateString;
		
	}

	private void setReadCoordinateString(String aReadCoordinateString) {
		this.readCoordinateString = aReadCoordinateString;
		
	}

	// =========== Getter ========================

	public String getReadCoordinateString() { return this.readCoordinateString; }
	public String getReadOldCoordinateString() { return this.readOldCoordinateString; }
	public Double getXGcodeCoordinate() { return this.xGcodeCoordinate; }
	public Double getYGcodeCoordinate() { return this.yGcodeCoordinate; }
	public Double getXBasicCoordinate() { return this.xBasicCoordinate; }
	public Double getYBasicCoordinate() { return this.yBasicCoordinate; }
	public Double getYDirectionCoordinate() { return this.yDirectionCoordinate; }
	public Double getXDirectionCoordinate() { return this.xDirectionCoordinate; }
	
	public Integer getCoordNumber() { return this.coordNumber; }
	public Boolean getIsNosePoint() { return this.isNosePoint; }
	public Boolean getIgnorePoint() { return this.ignorePoint; }
	public Integer getDirection() { return this.direction; }

	// =========== Setter ========================

	public void setCoordNumber(Integer coordNumber) { this.coordNumber = coordNumber; }
	public void setIsNosePoint(Boolean isNosePoint) { this.isNosePoint = isNosePoint; }
	public void setIgnorePoint(Boolean ignorePoint) { this.ignorePoint = ignorePoint; }
	public void setYDirectionCoordinate(Double yDirectionCoordinate) { this.yDirectionCoordinate = yDirectionCoordinate; }
	public void setXDirectionCoordinate(Double xDirectionCoordinate) { this.xDirectionCoordinate = xDirectionCoordinate; }


	public void setDirection(Integer direction) { this.direction = direction; }

	
	// =========== Funktionen ========================
	// Richtung aendern
	public void changeDirection(Boolean aDirection) {
		yDirectionCoordinate = yReadCoordinate;
		if (aDirection) { xDirectionCoordinate = 1.0 - xReadCoordinate; }
		else { xDirectionCoordinate = xReadCoordinate; }
	}
	
	// Umrechnung auf tatsaechliche Groesse
	public void calcBasicCoordinate(Double aFactor) {
		
		xBasicCoordinate = xDirectionCoordinate * aFactor;
		yBasicCoordinate = yDirectionCoordinate * aFactor;
	}
	

	// Schränkung berechnen
	public void calcYOffset(Double offset, Double linearOffset, Double cordLength) {
		Double angleRad = Math.toRadians(offset);
		yBasicCoordinate = yBasicCoordinate + ((cordLength - xBasicCoordinate) * Math.tan(angleRad)) + linearOffset;
	}

	
	// GCode Koordinaten berechnen (+ Startabstand (+Pfeilung + Trapez)))
	public void calcGcodeCoordinate(Double deltaX, Double deltaY) {
		xGcodeCoordinate = xBasicCoordinate + deltaX;
		yGcodeCoordinate = yBasicCoordinate + deltaY;
	}
	
	// Zerlegen der Dateizeile X
	private Double calculateXCoordinate(String anProfileLine) {

		Double coordinate = 0.0;
		try {
			String[] parts = anProfileLine.split("\\s", 2);
			coordinate = Double.parseDouble(parts[0]);
		} catch (NumberFormatException ex) {
			coordinate = 0.0;
			System.out.println("An exception "+ex+" has occured converting a coordinate (Format)."); 
		} catch (Exception ex) {
			coordinate = 0.0;
			System.out.println("An exception "+ex+" has occured converting a coordinate (Somthing else)."); 
		}
		return coordinate;
	}

	// Zerlegen der Dateizeile Y
	private Double calculateYCoordinate(String anProfileLine) {
		Double coordinate = 0.0;
		try {
			String[] parts = anProfileLine.split("\\s", 2);
			coordinate = Double.parseDouble(parts[1]);
		} catch (NumberFormatException ex) {
			coordinate = 0.0;
			System.out.println("An exception "+ex+" has occured converting a coordinate (Format).");
		} catch (Exception ex) {
			coordinate = 0.0;
			System.out.println("An exception "+ex+" has occured converting a coordinate (Somthing else)."); 
		}
		return coordinate;
	}




}