/*
    Copyright 2021 Stephan Geberl

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

import java.util.logging.Logger;

public class Settings {
	
    private static final Logger logger = Logger.getLogger(Settings.class.getName());
    private transient Boolean isDirty = false;

    private Double wireLength = 800.0;
    private Double xAxisMax = 300.0;
    private Double yAxisMax = 120.0;
    private Double startDistance = 20.0;
    private Double saveHeight = 50.0;
    private Double pause = 2.0;
    private Double wireSpeed = 100.0;
    private Double travelSpeed = 1000.0;
    private String projectDefaultPath = System.getProperty("user.home");
    private String profileDefaultPath = System.getProperty("user.home");
    private String axisA = "X";
    private String axisB = "Y";
    private String axisC = "Z";
    private String axisD = "A";
    

    /**
     * 
     */
    public Settings() {
        logger.fine("Initializing settings ...");
    }

	// ==================
	// Parameter
	// ==================
	public void setIsDirty(Boolean aValue) { this.isDirty = aValue; }
	
	public void setWireLength(Double aValue) { this.wireLength = aValue; this.setIsDirty(true); }
	public void setXAxisMax(Double aValue) { this.xAxisMax = aValue; this.setIsDirty(true); }
	public void setYAxisMax(Double aValue) { this.yAxisMax = aValue; this.setIsDirty(true); }
	public void setStartDistance(Double aValue) { this.startDistance = aValue; this.setIsDirty(true); }
	public void setSaveHeight(Double aValue) { this.saveHeight = aValue; this.setIsDirty(true); }
	public void setPause(Double aValue) { this.pause = aValue; this.setIsDirty(true); }
	public void setWireSpeed(Double aValue) { this.wireSpeed = aValue; this.setIsDirty(true); }
	public void setTravelSpeed(Double aValue) { this.travelSpeed = aValue; this.setIsDirty(true); }
	public void setProjectDefaultPath(String aValue) { this.projectDefaultPath = aValue; this.setIsDirty(true); }
	public void setProfileDefaultPath(String aValue) { this.profileDefaultPath = aValue; this.setIsDirty(true); }
	public void setAxisA(String axisA) { this.axisA = axisA; }
	public void setAxisB(String axisB) { this.axisB = axisB; }
	public void setAxisC(String axisC) { this.axisC = axisC; }
	public void setAxisD(String axisD) { this.axisD = axisD; }
	// ==================

	public Boolean isDirty() { return isDirty; }

	public Double getWireLength() {return this.wireLength;}
	public Double getXAxisMax() {return this.xAxisMax;}
	public Double getYAxisMax() {return this.yAxisMax;}
	public Double getStartDistance() {return this.startDistance;}
	public Double getSaveHeight() {return this.saveHeight;}
	public Double getPause() {return this.pause;}
	public Double getWireSpeed() {return this.wireSpeed;}
	public Double getTravelSpeed() {return this.travelSpeed;}
	public String getProjectDefaultPath() { return this.projectDefaultPath; }
	public String getProfileDefaultPath() { return this.profileDefaultPath; }
	public String getAxisA() { return this.axisA; }
	public String getAxisB() { return this.axisB; }
	public String getAxisC() { return this.axisC; }
	public String getAxisD() { return this.axisD; }
	// ==================



	
}
