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

import javax.swing.JTextField;



public class Project {
    private static final Logger logger = Logger.getLogger(Project.class.getName());

	// =====================================
	// Input
	// =====================================
    private transient Boolean isDirty = false;
    private transient String projectPath = "";
    
    private String projectName = "";
    private String baseProfilePath = "";
    private String tipProfilePath ="";
	private Integer baseProfileNumberPoints;
	private Integer tipProfileNumberPoints;
	private Boolean baseDirection = true;
	private Boolean tipDirection = true;
	private Boolean cutBaseFirst = false;

	
    private Double halfSpanLength = 0.0;
	private Double baseCordLength = 0.0;
	private Double tipCordLength = 0.0;
	private Double baseMeltingLoss = 0.0;
	private Double tipMeltingLoss = 0.0;
	private Double wingSweep = 0.0;
	private Double wingTipOffset = 0.0;
	private Double wingTipYOffset = 0.0;

	private Boolean hasSparTop = false;
	private Boolean hasSparBottom = false;
	private Double sparOffsetTop = 0.0;
	private Double sparWidthTop = 0.0;
	private Double sparHeightTop = 0.0;
	private Double sparOffsetBottom = 0.0;
	private Double sparWidthBottom = 0.0;
	private Double sparHeightBottom = 0.0;
	
	private Boolean isHollowed = false;
	private Boolean isHollowedFrontOnly = false;
	private Double wallThickness = 0.0;
	private Double crosspieceWidth = 0.0;
	private Double crosspieceOffset = 0.0;
	private Double frontHollowOffset = 0.0;
	private Double backHollowOffset = 0.0;


    /**
     * .
     */
    public Project() {
        logger.fine("Initializing project ...");
    }

    
    // ==================
	// Get / Set
	// ==================
	public void setIsDirty(Boolean aValue) {
		this.isDirty = aValue;
	}

    
    
	public void setProjectName(String aValue) { this.projectName = aValue; this.setIsDirty(true); }
	public void setBaseProfilePath(String aValue) { this.baseProfilePath = aValue; this.setIsDirty(true); }
	public void setTipProfilePath(String aValue) { this.tipProfilePath = aValue; this.setIsDirty(true); }

	public void setCutBaseFirst(Boolean aValue) { this.cutBaseFirst = aValue; this.setIsDirty(true); }

	
    public void setHalfSpanLength(Double aValue) { this.halfSpanLength = aValue; this.setIsDirty(true); }
    public void setBaseCordLength(Double aValue) { this.baseCordLength = aValue; this.setIsDirty(true); }
	public void setTipCordLength(Double aValue) { this.tipCordLength = aValue; this.setIsDirty(true); }
	public void setBaseMeltingLoss(Double aValue) { this.baseMeltingLoss = aValue; this.setIsDirty(true); }
	public void setTipMeltingLoss(Double aValue) { this.tipMeltingLoss = aValue; this.setIsDirty(true); }

	public void setWingSweep(Double aValue) { this.wingSweep = aValue; this.setIsDirty(true); }
	public void setWingTipOffset(Double aValue) { this.wingTipOffset = aValue; this.setIsDirty(true); }
	public void setWingTipYOffset(Double aValue) { this.wingTipYOffset = aValue; this.setIsDirty(true); }

	public void setHasSparTop(Boolean aValue) { this.hasSparTop = aValue; this.setIsDirty(true); }
	public void setHasSparBottom(Boolean aValue) { this.hasSparBottom = aValue; this.setIsDirty(true); }
	public void setSparOffsetTop(Double aValue) { this.sparOffsetTop = aValue; this.setIsDirty(true); }
	public void setSparWidthTop(Double aValue) { this.sparWidthTop = aValue; this.setIsDirty(true); }
	public void setSparHeightTop(Double aValue) { this.sparHeightTop = aValue; this.setIsDirty(true); }
	public void setSparOffsetBottom(Double aValue) { this.sparOffsetBottom = aValue; this.setIsDirty(true); }
	public void setSparWidthBottom(Double aValue) { this.sparWidthBottom = aValue; this.setIsDirty(true); }
	public void setSparHeightBottom(Double aValue) { this.sparHeightBottom = aValue; this.setIsDirty(true); }
	
	public void setIsHollowed(Boolean aValue) { this.isHollowed = aValue; this.setIsDirty(true); }
	public void setIsHollowedFrontOnly(Boolean aValue) { this.isHollowedFrontOnly = aValue; this.setIsDirty(true); }
	public void setWallThickness(Double aValue) { this.wallThickness = aValue; this.setIsDirty(true); }
	public void setCrosspieceWidth(Double aValue) { this.crosspieceWidth = aValue; this.setIsDirty(true); }
	public void setCrosspieceOffset(Double aValue) { this.crosspieceOffset = aValue; this.setIsDirty(true); }
	public void setFrontHollowOffset(Double aValue) { this.frontHollowOffset = aValue; this.setIsDirty(true); }
	public void setBackHollowOffset(Double aValue) { this.backHollowOffset = aValue; this.setIsDirty(true); }

	public void setBaseDirection(Boolean aValue) { this.baseDirection = aValue; this.setIsDirty(true); }
	public void setTipDirection(Boolean aValue) { this.tipDirection = aValue; this.setIsDirty(true); }

	// ==================
	public Boolean getIsDirty() { return this.isDirty; }
	public String getProjectPath() { return this.projectPath; }
	
	public String getProjectName() { return this.projectName; }
	public String getBaseProfilePath() { return this.baseProfilePath; }
	public String getTipProfilePath() { return this.tipProfilePath; }
	
	public Boolean setCutBaseFirst() { return this.cutBaseFirst; }

	public Double getHalfSpanLength() {return this.halfSpanLength;};
	public Double getBaseCordLength() { return this.baseCordLength; }
	public Double getTipCordLength() { return this.tipCordLength; }
	public Double getBaseMeltingLoss() { return this.baseMeltingLoss; }
	public Double getTipMeltingLoss() { return this.tipMeltingLoss; }

	public Double getWingSweep() { return this.wingSweep; }
	public Double getWingTipOffget() { return this.wingTipOffset; }
	public Double getWingTipYOffget() { return this.wingTipYOffset; }

	public Boolean getHasSparTop() { return this.hasSparTop; }
	public Boolean getHasSparBottom() { return this.hasSparBottom; }
	public Double getSparOffgetTop() { return this.sparOffsetTop; }
	public Double getSparWidthTop() { return this.sparWidthTop; }
	public Double getSparHeightTop() { return this.sparHeightTop; }
	public Double getSparOffgetBottom() { return this.sparOffsetBottom; }
	public Double getSparWidthBottom() { return this.sparWidthBottom; }
	public Double getSparHeightBottom() { return this.sparHeightBottom; }
	
	public Boolean getIsHollowed() { return this.isHollowed; }
	public Boolean getIsHollowedFrontOnly() { return this.isHollowedFrontOnly; }
	public Double getWallThickness() { return this.wallThickness; }
	public Double getCrosspieceWidth() { return this.crosspieceWidth; }
	public Double getCrosspieceOffset() { return this.crosspieceOffset; }
	public Double getFrontHollowOffset() { return this.frontHollowOffset; }
	public Double getBackHollowOffset() { return this.backHollowOffset; }

	public Integer getBaseProfileNumberPoints() { return this.baseProfileNumberPoints; }
	public Integer getTipProfileNumberPoints() { return this.tipProfileNumberPoints; }

	public Boolean getTipDirection() { return this.tipDirection; }
	public Boolean getBaseDirection() { return this.baseDirection; }

	// ==================

    
    
}