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

package com.geberl.winggcodedesigner.types;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
// import org.apache.commons.lang3.StringUtils;
// import com.geberl.winggcodedesigner.types.WindowSettings;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Project {
    private static final Logger logger = Logger.getLogger(Project.class.getName());

	// =====================================
	// Input
	// =====================================
    private transient Boolean isDirty = false;
    
    private String projectName = "";
    private String baseProfilePath = "";
    private String tipProfilePath ="";
	
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
	private Double wallThickness = 0.0;
	private Double crosspieceWidth = 0.0;
	private Double crossperceOffset = 0.0;


    /**
     * .
     */
    public Project() {
        logger.fine("Initializing project ...");

    }

    
    // ==================
	// Get / Set
	// ==================
	public void setIsDirty(Boolean aValue) { this.isDirty = aValue; }
	
	public void setProjectName(String aValue) { this.projectName = aValue; }
	public void setBaseProfilePath(String aValue) { this.baseProfilePath = aValue; }
	public void setTipProfilePath(String aValue) { this.tipProfilePath = aValue; }

    
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
	public Boolean getIsDirty() { return this.isDirty; }
	
	public String getProjectName() { return this.projectName; }
	public String getBaseProfilePath() { return this.baseProfilePath; }
	public String getTipProfilePath() { return this.tipProfilePath; }
	
	
	public Double getHalfSpanLength() {return this.halfSpanLength;};
	public Double getTipCordLength(Double aValue) { return this.tipCordLength; }
	public Double getBaseMeltingLoss(Double aValue) { return this.baseMeltingLoss; }
	public Double getTipMeltingLoss(Double aValue) { return this.tipMeltingLoss; }

	public Double getWingSweep(Double aValue) { return this.wingSweep; }
	public Double getWingTipOffget(Double aValue) { return this.wingTipOffset; }
	public Double getWingTipYOffget(Double aValue) { return this.wingTipYOffset; }

	public Boolean getHasSparTop(Boolean aValue) { return this.hasSparTop; }
	public Boolean getHasSparBottom(Boolean aValue) { return this.hasSparBottom; }
	public Double getSparOffgetTop(Double aValue) { return this.sparOffsetTop; }
	public Double getSparWidthTop(Double aValue) { return this.sparWidthTop; }
	public Double getSparHeightTop(Double aValue) { return this.sparHeightTop; }
	public Double getSparOffgetBottom(Double aValue) { return this.sparOffsetBottom; }
	public Double getSparWidthBottom(Double aValue) { return this.sparWidthBottom; }
	public Double getSparHeightBottom(Double aValue) { return this.sparHeightBottom; }


	// ==================

    
    
}
