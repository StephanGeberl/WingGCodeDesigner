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

public class ProfileCoordinate {

	public transient LinkedHashSet<ProfileCoordinate> profileAddition = new LinkedHashSet<ProfileCoordinate>();

	// Laufnummer
	private Integer coordNumber = 0;
	private transient Boolean isNosePoint = false;
	private transient Boolean ignorePoint = false;
	private Integer direction = 0;       // -1 Unterseite, +1 Oberseite
	
	// gelesener Koordinatenstring
	private String readCoordinateString = "";

	// gelesene Koordinaten
	private Double xReadCoordinate = 0.0;
	private Double yReadCoordinate = 0.0;
	
	// nach Richtungskorrektur, Holm und Innenausschnitt
	private transient Double xDirectionCoordinate = 0.0;
	private transient Double yDirectionCoordinate = 0.0;

	/**
	* Constructs and initializes a ProfileCoordinate from the read String.
	* @param String Containing x and y coordinates
	*/
	public ProfileCoordinate()
	{
		this.setReadCoordinateString("");
		this.xReadCoordinate = 0.0;
		this.yReadCoordinate = 0.0;
		this.setDirection(0);      // -1 Unterseite, +1 Oberseite
	}

	public ProfileCoordinate(String aReadCoordinateString, String aOldCoordinateString, Integer aCoordinateNumber)
	{
		this.setReadCoordinateString(aReadCoordinateString);
		
		this.setCoordNumber(aCoordinateNumber);
		
		this.xReadCoordinate = this.calculateXCoordinate(aReadCoordinateString);
		this.yReadCoordinate = this.calculateYCoordinate(aReadCoordinateString);
		
		Double xOldCoordinate = 0.0;
		this.setDirection(0);      // -1 Unterseite, +1 Oberseite
		
		if (aOldCoordinateString.contentEquals("")) { xOldCoordinate = 1.0; }
		else { xOldCoordinate = this.calculateXCoordinate(aOldCoordinateString); }
		
		if (xReadCoordinate < xOldCoordinate) { setDirection(1); }
		else { setDirection(-1); }

	}

	// Konstruktor fuer Insets
	public ProfileCoordinate(Double xDirectionCoordinate, Double yDirectionCoordinate)
	{
		this.setReadCoordinateString("");
		this.xReadCoordinate = 0.0;
		this.yReadCoordinate = 0.0;
		this.setDirection(0);      // -1 Unterseite, +1 Oberseite
		
		this.xDirectionCoordinate = xDirectionCoordinate;
		this.yDirectionCoordinate = yDirectionCoordinate;

	}

	// =========== Getter ========================

	public String getReadCoordinateString() { return this.readCoordinateString; }
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

	private void setReadCoordinateString(String aReadCoordinateString) { this.readCoordinateString = aReadCoordinateString; }
	public void setDirection(Integer direction) { this.direction = direction; }

	
	// =========== Funktionen ========================
	// Richtung aendern
	public void changeDirection(Boolean aDirection) {
		yDirectionCoordinate = yReadCoordinate;
		if (aDirection) { xDirectionCoordinate = 1.0 - xReadCoordinate; }
		else { xDirectionCoordinate = xReadCoordinate; }
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