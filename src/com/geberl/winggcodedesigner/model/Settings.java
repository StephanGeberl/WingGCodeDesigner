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

public class Settings {
	
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

    }

	// ==================
	// Parameter
	// ==================
	
	public void setWireLength(Double aValue) { this.wireLength = aValue; }
	public void setXAxisMax(Double aValue) { this.xAxisMax = aValue; }
	public void setYAxisMax(Double aValue) { this.yAxisMax = aValue; }
	public void setStartDistance(Double aValue) { this.startDistance = aValue; }
	public void setSaveHeight(Double aValue) { this.saveHeight = aValue; }
	public void setPause(Double aValue) { this.pause = aValue; }
	public void setWireSpeed(Double aValue) { this.wireSpeed = aValue; }
	public void setTravelSpeed(Double aValue) { this.travelSpeed = aValue; }
	public void setProjectDefaultPath(String aValue) { this.projectDefaultPath = aValue; }
	public void setProfileDefaultPath(String aValue) { this.profileDefaultPath = aValue; }
	public void setAxisA(String axisA) { this.axisA = axisA; }
	public void setAxisB(String axisB) { this.axisB = axisB; }
	public void setAxisC(String axisC) { this.axisC = axisC; }
	public void setAxisD(String axisD) { this.axisD = axisD; }
	// ==================

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
