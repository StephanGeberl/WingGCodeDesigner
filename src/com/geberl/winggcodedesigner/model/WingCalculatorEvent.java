/*
    Copyright 2020 Stephan Geberl

    This file is part of Universal Gcode Sender (UGS).

    UGS is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    UGS is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with UGS.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.geberl.winggcodedesigner.model;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Event zur Benachrichtigung Modell -> Form.
 *
 * @author Stephan Geberl
 */
public class WingCalculatorEvent {
	private final EventType evt;

	public enum EventType {
		BASE_PROFILE_CHANGED_EVENT,
		TIP_PROFILE_CHANGED_EVENT,
		CALCULATOR_STATUS_CHANGED_EVENT,
		CALCULATOR_DRAW_EVENT,
		GCODE_CHANGED_EVENT
	}


	public EventType getEventType(){
		return evt;
	}

	public boolean isRootProfileChangedEvent() {
		return EventType.BASE_PROFILE_CHANGED_EVENT.equals(evt);
	}

	public boolean isTipProfileChangedEvent() {
		return EventType.TIP_PROFILE_CHANGED_EVENT.equals(evt);
	}

	public boolean isCalculatorStatusChangedEvent() {
		return EventType.CALCULATOR_STATUS_CHANGED_EVENT.equals(evt);
	}

	public boolean isCalculatorDrawEvent() {
		return EventType.CALCULATOR_DRAW_EVENT.equals(evt);
	}

	public boolean isGCodeChangedEvent() {
		return EventType.GCODE_CHANGED_EVENT.equals(evt);
	}

	/**
	* Create a new event of given type. 
	* @param type 
	*/
	public WingCalculatorEvent(EventType type) {
		evt = type;
		switch (evt) {
			case BASE_PROFILE_CHANGED_EVENT:
			case TIP_PROFILE_CHANGED_EVENT:
			case CALCULATOR_STATUS_CHANGED_EVENT:
			case CALCULATOR_DRAW_EVENT:
			case GCODE_CHANGED_EVENT:
		}
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
