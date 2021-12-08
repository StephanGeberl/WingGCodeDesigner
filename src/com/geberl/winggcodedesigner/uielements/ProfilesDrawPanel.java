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
import com.geberl.winggcodedesigner.model.ProfileCoordinate;
import com.geberl.winggcodedesigner.model.WingCalculatorModel;

//import javax.annotation.PostConstruct;
import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedHashSet;



public class ProfilesDrawPanel extends JPanel implements WingCalculatorEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1937670858404000349L;
	private WingCalculatorModel wingCalculatorModel = null;
	private Integer buildXMax = 650;
	private Integer buildYMax = 200;
	private Integer buildYHalf = buildYMax / 2;
	private Integer borderOffset = 0;
	private Double drawFactor = 1.0;
	
	
	public ProfilesDrawPanel(WingCalculatorModel anWingDesignerModel) {
		
		wingCalculatorModel = anWingDesignerModel;
		
		this.setLayout(null);
		this.setPreferredSize(new Dimension(buildXMax + (2 * this.borderOffset), buildYMax + (2 * this.borderOffset)));
		this.createControls();
		repaint();
	}

	//	@PostConstruct
    public void createControls() {

	}

	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		// setForeground(Color.BLUE);
		g.setColor(Color.BLUE);
		g.drawRect(this.borderOffset, this.borderOffset, this.buildXMax, this.buildYMax);
		g.drawLine(this.borderOffset, this.buildYHalf, this.buildXMax, this.buildYHalf);
		
		if (wingCalculatorModel != null) {
			
			Double xStart = 0.0, xEnd = 0.0, yStart = 0.0, yEnd = 0.0;
			Boolean aFirstLine;

			Double xDrawFactor = this.buildXMax / wingCalculatorModel.getTotalMaxX();
			Double yDrawFactor = this.buildYMax / wingCalculatorModel.getTotalMaxY();

			
			this.drawFactor = xDrawFactor;
			if (yDrawFactor < xDrawFactor) { this.drawFactor = yDrawFactor; }

			xStart = 0.0; xEnd = 0.0; yStart = 0.0; yEnd = 0.0;
			aFirstLine = true;
			Iterator<ProfileCoordinate> baseIterator = wingCalculatorModel.project.baseProfileSet.iterator();
			while(baseIterator.hasNext()) 
			{
				ProfileCoordinate coordinate = baseIterator.next();
				xEnd = coordinate.getXGcodeCoordinate();
				yEnd = coordinate.getYGcodeCoordinate();
				if (aFirstLine) { yStart = yEnd; }

				Double x1 = (this.buildXMax + this.borderOffset) - (xStart * this.drawFactor);
				Double x2 = (this.buildXMax + this.borderOffset) - (xEnd * this.drawFactor);
				Double y1 = (this.buildYHalf + this.borderOffset) - (yStart * this.drawFactor);
				Double y2 = (this.buildYHalf + this.borderOffset) - (yEnd * this.drawFactor);

				g.setColor(Color.RED);
				g.drawLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
				xStart = xEnd;
				yStart = yEnd;
				aFirstLine = false;
			}

			xStart = 0.0; xEnd = 0.0; yStart = 0.0; yEnd = 0.0;
			aFirstLine = true;
			Iterator<ProfileCoordinate> tipIterator = wingCalculatorModel.project.tipProfileSet.iterator();
			while(tipIterator.hasNext()) 
			{
				ProfileCoordinate coordinate = tipIterator.next();
				xEnd = coordinate.getXGcodeCoordinate();
				yEnd = coordinate.getYGcodeCoordinate();
				if (aFirstLine) { yStart = yEnd; }

				Double x1 = (this.buildXMax + this.borderOffset) - (xStart * this.drawFactor);
				Double x2 = (this.buildXMax + this.borderOffset) - (xEnd * this.drawFactor);
				Double y1 = (this.buildYHalf + this.borderOffset) - (yStart * this.drawFactor);
				Double y2 = (this.buildYHalf + this.borderOffset) - (yEnd * this.drawFactor);

				g.setColor(Color.CYAN);
				g.drawLine(x1.intValue(), y1.intValue(), x2.intValue(), y2.intValue());
				xStart = xEnd;
				yStart = yEnd;
				aFirstLine = false;
			}
			
		}
		
	}

	
	
	@Override
	public void WingCalculatorEvent(WingCalculatorEvent evt) {
		switch(evt.getEventType()) {
			case CALCULATOR_DRAW_EVENT:
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
