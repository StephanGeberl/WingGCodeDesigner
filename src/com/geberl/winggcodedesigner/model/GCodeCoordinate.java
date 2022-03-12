/*
 * 
 * Coordinate for a Profile
 * 
 */

/*
	2020 Stephan Geberl

 */

package com.geberl.winggcodedesigner.model;

public class GCodeCoordinate {

	// Laufnummer
	private Integer coordNumber = 0;
	private Boolean isNosePoint = false;
	private Boolean setWaitAfterCoordinate = false; 	// nach Abarbeiten des Punktes einen STOP einlegen
	private Integer direction = 0;       			// -1 Unterseite, +1 Oberseite
	
	// nach Richtungskorrektur, Holm und Innenausschnitt
	private Double xDirectionCoordinate = 0.0;
	private Double yDirectionCoordinate = 0.0;

	// nach Umrechnung auf tatsaechliche Groesse
	private Double xBasicCoordinate = 0.0;
	private Double yBasicCoordinate = 0.0;

	// Koordinaten unter Einbezug von Startabstand, Trapez, Pfeilung
	private Double xGcodeCoordinate = 0.0;
	private Double yGcodeCoordinate = 0.0;

	
	/**
	* Constructs and initializes a ProfileCoordinate from the read String.
	* @param String Containing x and y coordinates
	*/
	public GCodeCoordinate()
	{
		this.setDirection(0);      // -1 Unterseite, +1 Oberseite
	}

	// Konstruktor fuer Insets
	public GCodeCoordinate(	Double xDirectionCoordinate,
							Double yDirectionCoordinate,
							Integer coordNumber,
							Boolean isNosePoint,
							Boolean setWaitAfterCoordinate,
							Integer direction )
	{
		this.coordNumber = coordNumber;
		this.direction = direction;      // -1 Unterseite, +1 Oberseite
		this.isNosePoint = isNosePoint;
		this.setWaitAfterCoordinate = setWaitAfterCoordinate;
		
		this.xDirectionCoordinate = xDirectionCoordinate;
		this.yDirectionCoordinate = yDirectionCoordinate;

	}
	

	// =========== Getter ========================

	public Double getXGcodeCoordinate() { return this.xGcodeCoordinate; }
	public Double getYGcodeCoordinate() { return this.yGcodeCoordinate; }
	public Double getXBasicCoordinate() { return this.xBasicCoordinate; }
	public Double getYBasicCoordinate() { return this.yBasicCoordinate; }
	public Double getYDirectionCoordinate() { return this.yDirectionCoordinate; }
	public Double getXDirectionCoordinate() { return this.xDirectionCoordinate; }
	
	public Integer getCoordNumber() { return this.coordNumber; }
	public Boolean getIsNosePoint() { return this.isNosePoint; }
	public Boolean getSetWaitAfterCoordinate() { return this.setWaitAfterCoordinate; }
	public Integer getDirection() { return this.direction; }

	// =========== Setter ========================

	public void setCoordNumber(Integer coordNumber) { this.coordNumber = coordNumber; }
	public void setIsNosePoint(Boolean isNosePoint) { this.isNosePoint = isNosePoint; }
	public void setYDirectionCoordinate(Double yDirectionCoordinate) { this.yDirectionCoordinate = yDirectionCoordinate; }
	public void setXDirectionCoordinate(Double xDirectionCoordinate) { this.xDirectionCoordinate = xDirectionCoordinate; }


	public void setDirection(Integer direction) { this.direction = direction; }
	public void setSetWaitAfterCoordinate(Boolean stopBeforeContinue) { this.setWaitAfterCoordinate = setWaitAfterCoordinate; }

	
	// =========== Funktionen ========================
	
	// Umrechnung auf tatsaechliche Groesse (bei der Drahtwurzel)
	public void calcBasicCoordinate(Double aFactor) {
		
		xBasicCoordinate = xDirectionCoordinate * aFactor;
		yBasicCoordinate = yDirectionCoordinate * aFactor;
	}
	

	// Koordinatenoffset (Y) fuer Schraenkung und Ueberhoehung
	public void calcYOffset(Double offset, Double linearOffset, Double cordLength) {
		Double angleRad = Math.toRadians(offset);
		// hinreichend fuer kleine Winkel (-10 : +10 Grad) nur y zu berechnen
		yBasicCoordinate = yBasicCoordinate + ((cordLength - xBasicCoordinate) * Math.tan(angleRad));
		yBasicCoordinate = yBasicCoordinate + linearOffset;
	}

	
	// GCode Koordinate (+Startabstand fuer X)
	public void calcGcodeCoordinate(Double deltaX) {
		xGcodeCoordinate = xBasicCoordinate + deltaX;
		yGcodeCoordinate = yBasicCoordinate;
	}
	


}