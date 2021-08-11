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
public class ProjectFactory {
    private static final Logger logger = Logger.getLogger(ProjectFactory.class.getName());
    private static final String USER_HOME = "user.home";
    private static final String FALSE = "false";

    public static final String PROJECT_DIRECTORY_NAME = "WingGCodeDesignerProjects";
    public static final String JSON_FILENAME = "WingProject.json";

    private static Project project;

    
    public static Project newProject() {
       project = new Project();
       return project;
    }

    
    
    public static Project loadProject(File jsonFile) {

        try {
            logger.log(Level.INFO, "Log location: {0}", jsonFile.getAbsolutePath());
            logger.info("Loading project.");
            project = new Gson().fromJson(new FileReader(jsonFile), Project.class);
        } catch (FileNotFoundException ex) {
             logger.log(Level.SEVERE, "Can't load project file.", ex);
        }
        return project;
    }

    
    public static void saveProject(Project project, File jsonFile) {
        logger.info("Saving project.");
        try {
            // Save json file.
            try (FileWriter fileWriter = new FileWriter(jsonFile)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                fileWriter.write(gson.toJson(project, Project.class));
            }
         } catch (Exception e) {
            e.printStackTrace();
            logger.warning("Can't save project!");
        }
    }


}
