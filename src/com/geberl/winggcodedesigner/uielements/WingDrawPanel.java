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
import com.geberl.winggcodedesigner.model.ProjectFactory;
import com.geberl.winggcodedesigner.model.SettingsFactory;
import com.geberl.winggcodedesigner.model.WingCalculatorModel;

import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;



public class WingDrawPanel extends JPanel implements WingCalculatorEventListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	
	private WingCalculatorModel wingCalculatorModel = null;

	
	public WingDrawPanel(WingCalculatorModel anWingCalculatorModel, int panelLength) {
		
		wingCalculatorModel = anWingCalculatorModel;
		this.setLayout(null);
		
	    double wMachineLength = SettingsFactory.settings.getWireLength();
		double xMachineWidth = SettingsFactory.settings.getXAxisMax();
		double wFactor = panelLength / wMachineLength;
		double drawXMax = xMachineWidth * wFactor;
		
		this.setPreferredSize(new java.awt.Dimension((int)panelLength, (int)drawXMax + 50));
	}

	@Override
	public void paintComponent(Graphics g) {

		if (wingCalculatorModel != null
				&& SettingsFactory.settings.getWireLength() > 0 
				&& wingCalculatorModel.getBaseCordLengthTipTotal() > 0) {

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
			
			
		    double wMachineLength = SettingsFactory.settings.getWireLength();
			double xMachineWidth = SettingsFactory.settings.getXAxisMax();
			
			double panelLength = this.getWidth() -1;
			double wFactor = panelLength / wMachineLength;
			
			double drawMiddle = (wMachineLength * wFactor)/2;
			double drawXMax = xMachineWidth * wFactor;
			double drawLengthMax = wMachineLength * wFactor;
			double drawXOffset = SettingsFactory.settings.getStartDistance() * wFactor;
			double drawSpanHalf = ProjectFactory.project.getHalfSpanLength() * wFactor;
			double drawShift = ProjectFactory.project.getShiftCenter() * wFactor;
			
			
			// Bounding Box (Machine) and Middle Line
			// this.setDimensions((int)panelLength, (int)drawLengthMax);
			
		    g2d.setStroke(bs0);
		    g2d.setColor(Color.GRAY);
		    g2d.drawRect(0, 0, (int)drawLengthMax, (int)drawXMax);
		    g2d.setStroke(bs1);
		    g2d.setColor(Color.GRAY);
		    g2d.drawLine((int)drawMiddle, 0, (int)drawMiddle, (int)drawXMax);


			// Border Lines
		    // Start Line
			g2d.setStroke(bs1);
			g2d.setColor(Color.BLUE);
			if (SettingsFactory.settings.getStartDistance() > 0) {
				g2d.drawLine(0, (int)drawXOffset, (int)drawLengthMax, (int)drawXOffset);
			};	
		    // Wing Span Lines
			g2d.drawLine((int)(drawMiddle - ((drawSpanHalf/2) - drawShift)), 0, (int)(drawMiddle - ((drawSpanHalf/2) - drawShift)), (int)drawXMax);
		    g2d.drawLine((int)(drawMiddle + ((drawSpanHalf/2) + drawShift)), 0, (int)(drawMiddle + ((drawSpanHalf/2) + drawShift)), (int)drawXMax);
			
			
		    // Wing Border Lines
			g2d.setStroke(bs1);
			g2d.setColor(Color.RED);

		    double baseCordEnd = drawXOffset;
		    double baseCordNose = drawXOffset + (wingCalculatorModel.getBaseCordWire() * wFactor);
		    double tipCordEnd = drawXOffset + (wingCalculatorModel.getTipDeltaAll() * wFactor);
		    double tipCordNose = tipCordEnd + (wingCalculatorModel.getTipCordWire() * wFactor);
			g2d.drawLine(0, (int)(baseCordEnd), (int)drawLengthMax, (int)(tipCordEnd));
			g2d.drawLine(0, (int)(baseCordNose), (int)drawLengthMax, (int)(tipCordNose));

		    // Wing Border
			g2d.setStroke(bs0);
			g2d.setColor(Color.BLACK);
			
			double xFactor = wingCalculatorModel.getTipDeltaAll() / wMachineLength;
			double drawL1 = drawMiddle - ((drawSpanHalf/2) - drawShift);
			double drawL2 = drawMiddle + ((drawSpanHalf/2) + drawShift);
			double drawX1End = drawXOffset + (drawL1 * xFactor);
			double drawX1Nose = drawX1End + (ProjectFactory.project.getBaseCordLength() * wFactor);
			double drawX2End = drawXOffset + (drawL2 * xFactor);
			double drawX2Nose = drawX2End + (ProjectFactory.project.getTipCordLength() * wFactor);;
			
			g2d.drawLine((int)drawL1, (int)(drawX1End), (int)drawL1, (int)(drawX1Nose));
			g2d.drawLine((int)drawL2, (int)(drawX2End), (int)drawL2, (int)(drawX2Nose));
			g2d.drawLine((int)drawL1, (int)(drawX1End), (int)drawL2, (int)(drawX2End));
			g2d.drawLine((int)drawL1, (int)(drawX1Nose), (int)drawL2, (int)(drawX2Nose));
			
			
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
}
