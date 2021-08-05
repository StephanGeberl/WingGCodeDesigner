/*
 * 
 * Coordinate for a Profile
 * 
 */

/*
	2020 Stephan Geberl

 */

package com.geberl.winggcodedesigner.model;

public class ProfileCoordinate {

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
	private Integer direction = 0;       // -1 Unterseite, +1 Oberseite

	/**
	* Constructs and initializes a ProfileCoordinate from the read String.
	* @param String Containing x and y coordinates
	*/
	public ProfileCoordinate()
	{
		this.xReadCoordinate = 0.0;
		this.yReadCoordinate = 0.0;
		this.xOldCoordinate = 0.0;
		this.direction = 0;      // -1 Unterseite, +1 Oberseite
	}

	public ProfileCoordinate(String aReadCoordinateString, String aOldCoordinateString)
	{
		this.xReadCoordinate = this.calculateXCoordinate(aReadCoordinateString);
		this.yReadCoordinate = this.calculateYCoordinate(aReadCoordinateString);
		
		this.xOldCoordinate = 0.0;
		this.direction = 0;      // -1 Unterseite, +1 Oberseite
		
		if (aOldCoordinateString.contentEquals("")) { xOldCoordinate = 1.0; }
		else { xOldCoordinate = this.calculateXCoordinate(aOldCoordinateString); }
		
		if (xReadCoordinate < xOldCoordinate) { direction = 1; }
		else { direction = -1; }

	}

	// =========== Getter ========================

	public double getXGcodeCoordinate() { return this.xGcodeCoordinate; }
	public double getYGcodeCoordinate() { return this.yGcodeCoordinate; }
	public double getXBasicCoordinate() { return this.xBasicCoordinate; }
	public double getYBasicCoordinate() { return this.yBasicCoordinate; }
	
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
	
	// Spantenausschnitte berechnen oben
	public void calcSparCoordinateTop(Double sparTopStart, Double sparTopEnd, Double topHeight, Boolean hasSparTop) {
		if (hasSparTop) {
			if (xBasicCoordinate >= sparTopEnd && xBasicCoordinate <= sparTopStart && direction > 0) {
				yBasicCoordinate = yBasicCoordinate - topHeight;
			}
		}
	}
	
	// Spantenausschnitte berechnen unten
	public void calcSparCoordinateBottom(Double sparBottomStart, Double sparBottomEnd, Double bottomHeight, Boolean hasSparBottom) {
		if (hasSparBottom) {
			if (xBasicCoordinate >= sparBottomEnd && xBasicCoordinate <= sparBottomStart && direction < 0) {
				yBasicCoordinate = yBasicCoordinate + bottomHeight;
			}
		}
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