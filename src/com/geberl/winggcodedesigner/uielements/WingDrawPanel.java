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

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Iterator;
import java.util.LinkedHashSet;



public class WingDrawPanel extends JPanel implements WingCalculatorEventListener {

	/**
	 * 
	 */
	
	private WingCalculatorModel wingCalculatorModel = null;
	private int buildXMax = 650;
	private int buildYMax = 200;
	private int buildXHalf = buildXMax / 2;
	private int borderOffset = 0;
	private double drawFactor = 1.0;

	private LinkedHashSet<ProfileCoordinate> baseProfileSet = new LinkedHashSet<ProfileCoordinate>();
	private LinkedHashSet<ProfileCoordinate> tipProfileSet = new LinkedHashSet<ProfileCoordinate>();
	
	
	public WingDrawPanel(WingCalculatorModel anWingCalculatorModel) {
		
		wingCalculatorModel = anWingCalculatorModel;
		
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
		Graphics2D g2d = (Graphics2D) g;
		float[] dash1 = { 2f, 0f, 2f };
	    BasicStroke bs1 = new BasicStroke(1, 
	            BasicStroke.CAP_BUTT, 
	            BasicStroke.JOIN_ROUND, 
	            1.0f, 
	            dash1,
	            2f);		
	    Stroke bs0 = g2d.getStroke();
		
		
		// setForeground(Color.BLUE);
		
	    g2d.setStroke(bs1);
	    g2d.setColor(Color.GRAY);
	    g2d.drawRect(this.borderOffset, this.borderOffset, this.buildXMax, this.buildYMax);
	    g2d.drawLine(this.buildXHalf, this.borderOffset, this.buildXHalf, this.buildYMax);
		
		if (wingCalculatorModel != null && wingCalculatorModel.settings.getWireLength() > 0 && wingCalculatorModel.getBaseCordLengthTipTotal() > 0) {
			
			double drawFactorX = 1.0;
			double drawFactorY = 1.0;
		
			Double maxY = wingCalculatorModel.getBaseCordLengthBaseTotal();
			if (maxY < wingCalculatorModel.getBaseCordLengthTipTotal()) { maxY = wingCalculatorModel.getBaseCordLengthTipTotal(); };
			drawFactorX = this.buildXMax / wingCalculatorModel.settings.getWireLength();
			drawFactorY = this.buildYMax / maxY;
			this.drawFactor = drawFactorX;
			if (drawFactorY < drawFactorX) { this.drawFactor = drawFactorY; };

			g2d.setColor(Color.BLUE);
			
			double baseCordStart = wingCalculatorModel.settings.getStartDistance() * this.drawFactor;
			
			g2d.drawLine(this.borderOffset, (int)baseCordStart, this.buildXMax, (int)baseCordStart);
			
			double halfSpan = wingCalculatorModel.getHalfSpanLength() /2  * this.drawFactor;
			double halfWire = wingCalculatorModel.settings.getWireLength() /2  * this.drawFactor;
			
			g2d.drawLine((int)(this.buildXHalf - halfWire), this.borderOffset, (int)(this.buildXHalf - halfWire), this.buildYMax);
			g2d.drawLine((int)(this.buildXHalf + halfWire), this.borderOffset, (int)(this.buildXHalf + halfWire), this.buildYMax);

		    g2d.setStroke(bs0);
			
			g2d.drawLine((int)(this.buildXHalf - halfSpan), this.borderOffset, (int)(this.buildXHalf - halfSpan), this.buildYMax);
			g2d.drawLine((int)(this.buildXHalf + halfSpan), this.borderOffset, (int)(this.buildXHalf + halfSpan), this.buildYMax);

			double baseCordWireY = wingCalculatorModel.getBaseCordWire() * this.drawFactor;
			
			g2d.setColor(Color.RED);
			
			double yStartBase = wingCalculatorModel.getBaseCordStartBaseTotal()  * this.drawFactor;
			double yEndBase = wingCalculatorModel.getBaseCordLengthBaseTotal() * this.drawFactor;
			double yStartTip = wingCalculatorModel.getBaseCordStartTipTotal()  * this.drawFactor;
			double yEndTip = wingCalculatorModel.getBaseCordLengthTipTotal()  * this.drawFactor;
			
			g2d.drawLine((int)(this.buildXHalf - halfWire), (int)(this.borderOffset + yStartBase), (int)(this.buildXHalf - halfWire), (int)(this.borderOffset + yEndBase));
			g2d.drawLine((int)(this.buildXHalf + halfWire), (int)(this.borderOffset + yStartTip), (int)(this.buildXHalf + halfWire), (int)(this.borderOffset + yEndTip));
			
			g2d.drawLine((int)(this.buildXHalf - halfWire), (int)(this.borderOffset + yStartBase), (int)(this.buildXHalf + halfWire), (int)(this.borderOffset + yStartTip));
			g2d.drawLine((int)(this.buildXHalf - halfWire), (int)(this.borderOffset + yEndBase), (int)(this.buildXHalf + halfWire), (int)(this.borderOffset + yEndTip));
			
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
