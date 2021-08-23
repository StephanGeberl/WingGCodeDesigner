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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moll
 */
public class SettingsFactory {
	
    private static final Logger logger = Logger.getLogger(SettingsFactory.class.getName());
    private static final String USER_HOME = "user.home";
    private static final String FALSE = "false";
    public static final String SETTINGS_DIRECTORY_NAME = "WGCD";
    public static final String PROPERTIES_FILENAME = "WingGCodeDesigner.properties";
    public static final String JSON_FILENAME = "WingGCodeDesigner.json";
    public static final String MAC_LIBRARY = "/Library/Preferences/";

    private static Settings settings;

    public static Settings loadSettings() {
        if (settings == null) {
            // the defaults are now in the settings bean
            File settingsFile = getSettingsFile();

            if (!settingsFile.exists()) {
                settings = new Settings();
            } else {
                try {
                    logger.log(Level.INFO, "Settings location: {0}", settingsFile.getAbsolutePath());
                    logger.info("Loading settings.");
                    settings = new Gson().fromJson(new FileReader(settingsFile), Settings.class);
                } catch (FileNotFoundException ex) {
                     logger.log(Level.SEVERE, "Can't load settings, using defaults.", ex);
                }
            }
        }

        if (settings == null) {
            settings = new Settings();
        }
        return settings;
    }

    public static void saveSettings(Settings settings) {
        logger.info("Saving settings.");
        try {
            // Save json file.
            File jsonFile = getSettingsFile();
            try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                fileWriter.write(gson.toJson(settings, Settings.class));
            }
         } catch (Exception e) {
            e.printStackTrace();
            logger.warning("Can't save settings!");
        }
    }

    /**
     * This is public in case other classes need to save settings somewhere.
     */
    public static File getSettingsDirectory() {
        String homeDir = System.getProperty(USER_HOME);
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("mac")) {
            homeDir = homeDir + MAC_LIBRARY;
        }
        if (!homeDir.endsWith(File.separator)) {
            homeDir = homeDir + File.separator + ".";
        }

        File dir = new File(homeDir + SETTINGS_DIRECTORY_NAME);
        dir.mkdirs();
        return dir;
    }


    private static File getSettingsFile() {
        File settingDir = SettingsFactory.getSettingsDirectory();
        return new File (settingDir, JSON_FILENAME);
    }

    /**
     * Saves the current settings
     */
    public static void saveSettings() {
        if(settings == null) {
            throw new RuntimeException("No settings are loaded");
        }
        saveSettings(settings);
    }
}
