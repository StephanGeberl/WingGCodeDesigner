/*
    Copyright 2020 Stephan Geberl

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
package com.geberl.winggcodedesigner.uielements;

import com.geberl.winggcodedesigner.eventing.WingCalculatorEvent;
import com.geberl.winggcodedesigner.eventing.WingCalculatorEventListener;
import com.geberl.winggcodedesigner.model.GCodeCoordinate;
import com.geberl.winggcodedesigner.model.ProfileCoordinate;
import com.geberl.winggcodedesigner.model.ProjectFactory;
import com.geberl.winggcodedesigner.model.SettingsFactory;
import com.geberl.winggcodedesigner.model.WingCalculatorModel;

//import javax.annotation.PostConstruct;
import javax.swing.JPanel;
import javax.swing.text.StyleConstants.ColorConstants;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.LinkedHashSet;



public class ProfilesDrawPanel extends JPanel implements WingCalculatorEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1937670858404000349L;
	private WingCalculatorModel wingCalculatorModel = null;
	private Integer buildXMax = 0;
	private Integer buildYMax = 0;
	private Integer buildYHalf = 0;

	private Double wFactor = 0.1;

	
	public ProfilesDrawPanel(WingCalculatorModel aWingCalculatorModel, int panelLength) {
		
		wingCalculatorModel = aWingCalculatorModel;
		this.setLayout(null);
		
		this.setLayout(null);
		
	    double machineWidth = SettingsFactory.settings.getXAxisMax();
	    double machineHeight = SettingsFactory.settings.getYAxisMax();
		this.wFactor = panelLength / machineWidth;
		this.buildXMax = (int)(machineWidth * this.wFactor);
		this.buildYMax = (int)(machineHeight * this.wFactor);
		this.buildYHalf = this.buildYMax / 2;
		
		this.setPreferredSize(new java.awt.Dimension(buildXMax+10, (int)buildYMax + 50));
		
		this.createControls();
		repaint();
	}

	//	@PostConstruct
    public void createControls() {

	}

	@Override
	public void paintComponent(Graphics g) {
		
		if (wingCalculatorModel != null
				&& SettingsFactory.settings.getWireLength() > 0 
				&& wingCalculatorModel.getBaseCordLengthTipTotal() > 0) {
		    
			Double xStart = 0.0, xEnd = 0.0, yStart = 0.0, yEnd = 0.0;
			Boolean aFirstLine;

			super.paintComponent(g);
			
			Graphics2D g2d = (Graphics2D) g;
			float[] dash1 = { 2f, 0f, 2f };
		    BasicStroke bs1 = new BasicStroke(1, 
		            BasicStroke.CAP_BUTT, 
		            BasicStroke.JOIN_ROUND, 
		            1.0f, 
		            dash1,
		            2f);		
		    Stroke bs0 = g2d.getStroke();

		    g2d.setStroke(bs0);
		    g2d.setColor(Color.GRAY);
		    g2d.drawRect(0, 0, this.buildXMax, this.buildYMax);
		    g2d.setStroke(bs1);
		    g2d.setColor(Color.BLUE);
		    g2d.drawLine(0, this.buildYHalf, this.buildXMax, this.buildYHalf);

		    
			aFirstLine = true;
			Iterator<GCodeCoordinate> baseIterator = ProjectFactory.project.gCodeBaseProfileSet.iterator();
			while(baseIterator.hasNext()) 
			{
				GCodeCoordinate coordinate = baseIterator.next();
				
				xEnd = coordinate.getXGcodeCoordinate() * this.wFactor;
				yEnd = coordinate.getYGcodeCoordinate() * this.wFactor;
				if (aFirstLine) { yStart = yEnd; xStart = xEnd; }

				Double x1 = this.buildXMax - xStart;
				Double x2 = this.buildXMax - xEnd;
				Double y1 = this.buildYHalf - yStart;
				Double y2 = this.buildYHalf - yEnd;

				g2d.setStroke(bs0);
				g2d.setColor(Color.RED);
				g2d.drawLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
				
				xStart = xEnd;
				yStart = yEnd;
				aFirstLine = false;
			}

			xStart = 0.0; xEnd = 0.0; yStart = 0.0; yEnd = 0.0;
			aFirstLine = true;
			Iterator<GCodeCoordinate> tipIterator = ProjectFactory.project.gCodeTipProfileSet.iterator();
			while(tipIterator.hasNext()) 
			{
				GCodeCoordinate coordinate = tipIterator.next();
				
				xEnd = coordinate.getXGcodeCoordinate() * this.wFactor;
				yEnd = coordinate.getYGcodeCoordinate() * this.wFactor;
				if (aFirstLine) { yStart = yEnd; xStart = xEnd; }

				Double x1 = this.buildXMax - xStart;
				Double x2 = this.buildXMax - xEnd;
				Double y1 = this.buildYHalf - yStart;
				Double y2 = this.buildYHalf - yEnd;

				g2d.setStroke(bs0);
				g2d.setColor(Color.BLUE);
				g2d.drawLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
				
				xStart = xEnd;
				yStart = yEnd;
				aFirstLine = false;
			}
			
		}
		
	}

	
	
	@Override
	public void WingCalculatorEvent(WingCalculatorEvent evt) {
		switch(evt.getEventType()) {
			case CALCULATION_DONE_EVENT:
				repaint();
				break;

			default:
				break;
		}

	}

	
	// Hilfsfunktion
	private void setPanelEnabled(JPanel panel, Boolean isEnabled) {
	    panel.setEnabled(isEnabled);

	    Component[] components = panel.getComponents();

	    for (Component component : components) {
	        if (component instanceof JPanel) {
	            setPanelEnabled((JPanel) component, isEnabled);
	        }
	        component.setEnabled(isEnabled);
	    }
	}
}
