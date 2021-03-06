/*
6612
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

import com.geberl.winggcodedesigner.eventing.ProjectChangeEvent;
import com.geberl.winggcodedesigner.eventing.ProjectChangeEventListener;
import com.geberl.winggcodedesigner.uielements.GUIHelpers;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author moll
 */
public class ProjectFactory {
	
	private static transient final Collection<ProjectChangeEventListener> projectChangeEventListener = new ArrayList<>();

	private static final Logger logger = Logger.getLogger(ProjectFactory.class.getName());

    public static Project project;
    public static Settings settings;

    public static void setSettings(Settings aValue) { settings = aValue; }

    
    public static Project newProject() {
       project = new Project();
       project.setIsDirty(false);
       project.setProjectPath("");
       project.setFile(null);
       logger.info("New project.");
       return project;
    }
    
    public static Project loadProject() {

		JFileChooser projectFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    	if (settings == null) {
    		projectFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	}
    	else {
    		projectFileChooser.setCurrentDirectory(new File(settings.getProjectDefaultPath()));    		
    	}
		
		projectFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		projectFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
		projectFileChooser.setAcceptAllFileFilterUsed(false);
		
		int returnVal = projectFileChooser.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File fileToOpen = projectFileChooser.getSelectedFile();
			
				project = new Gson().fromJson(new FileReader(fileToOpen), Project.class);
				project.setProjectPath(fileToOpen.getAbsolutePath());
				project.setFile(fileToOpen);
                project.setIsDirty(false);
				
	            logger.log(Level.INFO, "Project location: {0}", fileToOpen.getAbsolutePath());
	            logger.info("Loading project.");
				
			} catch (Exception ex) {
				GUIHelpers.displayErrorDialog("Problem loading project file: " + ex.getMessage());
			}
		}

		return project;
    }

    
    public static void saveProject() {
        
        try {
            // Save json file.
            try (FileWriter fileWriter = new FileWriter(project.getFile())) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                fileWriter.write(gson.toJson(project, Project.class));
                project.setIsDirty(false);
                
                logger.info("Saving project: " + project.getProjectPath());
                
            }
         } catch (Exception e) {
            e.printStackTrace();
            logger.warning("Can't save project!");
        }
    }

    public static void saveProjectAs() {
    	
		JFileChooser projectFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

    	if (settings == null) {
    		projectFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	}
    	else {
    		projectFileChooser.setCurrentDirectory(new File(settings.getProjectDefaultPath()));    		
    	}
		
		projectFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		projectFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JSON Files", "json"));
		projectFileChooser.setAcceptAllFileFilterUsed(false);

		int returnVal = projectFileChooser.showSaveDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File fileToSave = projectFileChooser.getSelectedFile();
				
				if (FilenameUtils.getExtension(fileToSave.getName()).equalsIgnoreCase("json")) {
				    // filename is OK as-is
				} else {
					fileToSave = new File(fileToSave.getParentFile(), FilenameUtils.getBaseName(fileToSave.getName())+".json");
				}

	            try (FileWriter fileWriter = new FileWriter(fileToSave)) {
	                Gson gson = new GsonBuilder().setPrettyPrinting().create();
	                fileWriter.write(gson.toJson(project, Project.class));

					project.setProjectPath(fileToSave.getAbsolutePath());
					project.setFile(fileToSave);
					
	                project.setIsDirty(false);
	                
	                logger.info("Saving project: " + project.getProjectPath());

	            }
				
			} catch (Exception ex) {
				GUIHelpers.displayErrorDialog("Problem saving project file: " + ex.getMessage());
			}
		}
    }



	public static void requestCalculation() {
		
		sendProjectChangeEvent(new ProjectChangeEvent(ProjectChangeEvent.EventType.PROJECT_CALC_REQUESTED_EVENT));
		
		if (project.getIsDirty()) {
			// dirty Project cannot be calculated
		}
		else {
			// this.sendProjectChangeEvent(new ProjectChangeEvent(ProjectChangeEvent.EventType.PROJECT_CALC_REQUESTED_EVENT));
		};
	}


	// ==================
	// Profilkoordinaten laden
    // 1 ... Base
    // 2 ... Tip
	// ==================
	public static void loadProfileData(Integer profileTypeCode) {
		
		JFileChooser profileFileChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
		if (profileTypeCode == 1) { project.baseProfileSet.clear(); };
		if (profileTypeCode == 2) { project.tipProfileSet.clear(); };

    	if (settings == null) {
    		profileFileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
    	}
    	else {
    		profileFileChooser.setCurrentDirectory(new File(settings.getProfileDefaultPath()));    		
    	}
		
    	profileFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    	profileFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("DAT Files (Profile)", "dat"));
    	profileFileChooser.setAcceptAllFileFilterUsed(false);
		
		int returnVal = profileFileChooser.showOpenDialog(null);
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				File fileToOpen = profileFileChooser.getSelectedFile();
			

				String oldProfileLine = "";
				String profileLine = "";
				Integer pointNum = 0;
				BufferedReader profileReader = new BufferedReader(new FileReader(fileToOpen.getAbsolutePath())); 
				
				while ((profileLine = profileReader.readLine()) != null) {
					// just skip empty Lines
					if (!(profileLine.trim()).contentEquals("")) {
						profileLine = profileLine.trim();
						if (pointNum == 0) {
							if (profileTypeCode == 1) { project.baseProfileName = profileLine; };
							if (profileTypeCode == 2) { project.tipProfileName = profileLine; };
						} else {
							if (profileTypeCode == 1) { project.baseProfileSet.add(new ProfileCoordinate(profileLine, oldProfileLine, pointNum)); };
							if (profileTypeCode == 2) { project.tipProfileSet.add(new ProfileCoordinate(profileLine, oldProfileLine, pointNum)); };
							oldProfileLine = profileLine;
						}
						pointNum = pointNum + 1;
					};
				};
				
				
				if (profileTypeCode == 1) { project.baseProfileNumberPoints = pointNum -1; };
				if (profileTypeCode == 2) { project.tipProfileNumberPoints = pointNum -1; };
				
	            logger.log(Level.INFO, "Profile location: {0}", fileToOpen.getAbsolutePath());
	            logger.info("Loading profile.");
				
			} catch (Exception ex) {
				GUIHelpers.displayErrorDialog("Problem loading profile file: " + ex.getMessage());
			}
		}
	}

	// ==================
	// Eventing
	// ==================
	private static void sendProjectChangeEvent(ProjectChangeEvent event) {
		projectChangeEventListener.forEach(l -> l.ProjectChangeEvent(event));
	}
	
	public static void addProjectChangeListener(ProjectChangeEventListener listener) {
		if (!projectChangeEventListener.contains(listener)) {
			projectChangeEventListener.add(listener);
		}
	}

	public static void removeProjectChangeListener(ProjectChangeEventListener listener) {
		if (projectChangeEventListener.contains(listener)) {
			projectChangeEventListener.remove(listener);
		}
	}
	    
    
    
}
