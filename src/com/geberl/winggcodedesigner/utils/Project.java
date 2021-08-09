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

package com.geberl.winggcodedesigner.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
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

public class Project {
    private static final Logger logger = Logger.getLogger(Project.class.getName());

    // Transient, don't serialize or deserialize.
    transient public static int HISTORY_SIZE = 20;

    private double wireLength = 800.0;
    private double startDistance = 20.0;
    private double saveHeight = 50.0;
    private double pause = 2.0;
    private double wireSpeed = 100.0;
    private double travelSpeed = 1000.0;
    private boolean cutBaseFirst = false;

	/**
     * A directory with gcode files for easy access through pendant
     */
    private String workspaceDirectory;

    /**
     * The GSON deserialization doesn't do anything beyond initialize what's in the json document.  Call finalizeInitialization() before using the Settings.
     */
    public Project() {
        logger.fine("Initializing...");

        // Initialize macros with a default macro
    }

    private static void updateRecent(Deque<String> stack, int maxSize, String element) {
      stack.remove(element);
      stack.push(element);
      while( stack.size() > maxSize)
        stack.removeLast();
    }

	// ==================
	// Parameter
	// ==================
	public void setWireLength(Double aValue) {
		this.wireLength = aValue;
		}
	public void setStartDistance(Double aValue) {
         this.startDistance = aValue;
 		 }
	public void setSaveHeight(Double aValue) {
		this.saveHeight = aValue;
		}
	public void setPause(Double aValue) {
		this.pause = aValue;
		}
	public void setWireSpeed(Double aValue) {
		this.wireSpeed = aValue;
		}
	public void setTravelSpeed(Double aValue) {
		this.travelSpeed = aValue;
		}
	public void setCutBaseFirst(Boolean aValue) {
		this.cutBaseFirst = aValue;
		}
	// ==================

	public Double getWireLength() {return this.wireLength;}
	public Double getStartDistance() {return this.startDistance;}
	public Double getSaveHeight() {return this.saveHeight;}
	public Double getPause() {return this.pause;}
	public Double getWireSpeed() {return this.wireSpeed;}
	public Double getTravelSpeed() {return this.travelSpeed;}
	public Boolean getCutBaseFirst() {return this.cutBaseFirst;}

	// ==================
    
    public String getWorkspaceDirectory() {
        return this.workspaceDirectory;
    }

    
    
    
    
     
    
    
    
    
    
    
    
    
    
}
