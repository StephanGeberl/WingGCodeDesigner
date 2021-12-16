/*
    Copyright 2021 Stephan Geberl

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
package com.geberl.winggcodedesigner.eventing;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Event zur Benachrichtigung Project.
 *
 * @author Stephan Geberl
 */
public class ProjectChangeEvent {
	private final EventType evt;

	public enum EventType {
		PROJECT_CALC_REQUESTED_EVENT }


	public EventType getEventType(){
		return evt;
	}


	public boolean isProjectCalcRequestedEvent() {
		return EventType.PROJECT_CALC_REQUESTED_EVENT.equals(evt);
	}


	/**
	* Create a new event of given type. 
	* @param type 
	*/
	public ProjectChangeEvent(EventType type) {
		evt = type;
		switch (evt) {
			case PROJECT_CALC_REQUESTED_EVENT:
		}
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
