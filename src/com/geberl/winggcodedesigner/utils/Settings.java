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

import org.apache.commons.lang3.StringUtils;
import com.geberl.winggcodedesigner.types.WindowSettings;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.Deque;
import java.util.logging.Logger;

public class Settings {
    private static final Logger logger = Logger.getLogger(Settings.class.getName());

    // Transient, don't serialize or deserialize.
    transient private SettingChangeListener listener = null;
    transient public static int HISTORY_SIZE = 20;

    private String fileName = System.getProperty("user.home");
    private Deque<String> fileHistory = new ArrayDeque<>();
    private Deque<String> dirHistory = new ArrayDeque<>();

    private double wireLength = 800.0;
    private double startDistance = 20.0;
    private double saveHeight = 50.0;
    private double pause = 2.0;
    private double wireSpeed = 100.0;
    private double setupSpeed = 1000.0;
    private boolean cutBaseFirst = false;

	/**
     * A directory with gcode files for easy access through pendant
     */
    private String workspaceDirectory;

    /**
     * The GSON deserialization doesn't do anything beyond initialize what's in the json document.  Call finalizeInitialization() before using the Settings.
     */
    public Settings() {
        logger.fine("Initializing...");

        // Initialize macros with a default macro
    }


    /**
     * This method should only be called once during setup, a runtime exception
     * will be thrown if that contract is violated.
     */
    public void setSettingChangeListener(SettingChangeListener listener) {
        this.listener = listener;
    }

    private void changed() {
        if (listener != null) {
            listener.settingChanged();
        }
    }

    public String getLastOpenedFilename() {
        return fileName;
    }

    public void setLastOpenedFilename(String absolutePath) {
        Path p = Paths.get(absolutePath).toAbsolutePath();
        this.fileName = p.toString();
        updateRecentFiles(p.toString());
        updateRecentDirectory(p.getParent().toString());
        changed();
    }

    public Collection<String> getRecentFiles() {
      return Collections.unmodifiableCollection(fileHistory);
    }

    public void updateRecentFiles(String absolutePath) {
      updateRecent(this.fileHistory, HISTORY_SIZE, absolutePath);
    }

    public Collection<String> getRecentDirectories() {
      return Collections.unmodifiableCollection(dirHistory);
    }

    public void updateRecentDirectory(String absolutePath) {
      updateRecent(this.dirHistory, HISTORY_SIZE, absolutePath);
    }

    private static void updateRecent(Deque<String> stack, int maxSize, String element) {
      stack.remove(element);
      stack.push(element);
      while( stack.size() > maxSize)
        stack.removeLast();
    }

	public void setStartDistance(Double aValue) {
         this.startDistance = aValue;
         changed();
		 }
	public void setWireLength(Double aValue) {
		this.wireLength = aValue;
        changed();
		}
	public void setSaveHeight(Double aValue) {
		this.saveHeight = aValue;
        changed();
		}
	public void setCutBaseFirst(Boolean aValue) {
		this.cutBaseFirst = aValue;
        changed();
		}
	public void setPause(Double aValue) {
		this.pause = aValue;
        changed();
		}
	public void setWireSpeed(Double aValue) {
		this.wireSpeed = aValue;
        changed();
		}
	public void setSetupSpeed(Double aValue) {
		this.setupSpeed = aValue;
        changed();
		}

	public Double getStartDistance() {return this.startDistance;}
	public Double getWireLength() {return this.wireLength;}
	public Double getSaveHeight() {return this.saveHeight;}
	public Boolean getCutBaseFirst() {return this.cutBaseFirst;}
	public Double getPause() {return this.pause;}
	public Double getWireSpeed() {return this.wireSpeed;}
	public Double getSetupSpeed() {return this.setupSpeed;}

    
    public String getWorkspaceDirectory() {
        return this.workspaceDirectory;
    }

}
