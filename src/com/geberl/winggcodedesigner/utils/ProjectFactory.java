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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author moll
 */
public class ProjectFactory {
    private static final Logger logger = Logger.getLogger(ProjectFactory.class.getName());
    private static final String USER_HOME = "user.home";
    private static final String FALSE = "false";
    private static Project project;

    public static final String PROJECT_DIRECTORY_NAME = "WingGCodeDesignerProjects";
    public static final String JSON_FILENAME = "WingProject.json";

    
    public static Project loadProject() {
        if (project == null) {
            // the defaults are now in the settings bean
            File projectFile = getProjectFile();

            if (!projectFile.exists()) {
                project = new Project();
            } else {
                try {
                    logger.log(Level.INFO, "Log location: {0}", projectFile.getAbsolutePath());
                    logger.info("Loading settings.");
                    project = new Gson().fromJson(new FileReader(projectFile), Project.class);
                } catch (FileNotFoundException ex) {
                     logger.log(Level.SEVERE, "Can't load project file.", ex);
                }
            }
        }

        if (project == null) {
        	project = new Project();
        }
        return project;
    }

    public static void saveProject(Project project) {
        logger.info("Saving project.");
        try {
            // Save json file.
            File jsonFile = getProjectFile();
            try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                fileWriter.write(gson.toJson(project, Project.class));
            }
         } catch (Exception e) {
            e.printStackTrace();
            logger.warning("Can't save project!");
        }
    }

    /**
     * This is public.
     */
    public static File getProjectDirectory() {
        String homeDir = System.getProperty(USER_HOME);
        String osName = System.getProperty("os.name").toLowerCase();

        if (osName.contains("mac")) {
            homeDir = homeDir;
        }
        if (!homeDir.endsWith(File.separator)) {
            homeDir = homeDir + File.separator;
        }

        File dir = new File(homeDir + PROJECT_DIRECTORY_NAME);
        dir.mkdirs();
        return dir;
    }


    private static File getProjectFile() {
        File projectDir = ProjectFactory.getProjectDirectory();
        return new File (projectDir, JSON_FILENAME);
    }

    /**
     * Saves the current settings
     */
    public static void saveProject() {
        if(project == null) {
            throw new RuntimeException("No project is loaded");
        }
        saveProject(project);
    }
}
