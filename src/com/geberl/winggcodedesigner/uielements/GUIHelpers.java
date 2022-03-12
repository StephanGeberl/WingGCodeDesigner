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
package com.geberl.winggcodedesigner.uielements;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import org.apache.commons.lang3.StringUtils;


/**
 *
 * @author wwinder
 */
public class GUIHelpers {

    /**
     * Displays an error message to the user which will not block the current thread.
     * @param errorMessage message to display in the dialog.
     */
    public static void displayErrorDialog(final String errorMessage) {
    	if (errorMessage == "") { displayErrorDialog("undefined", false); }
    	else { displayErrorDialog(errorMessage, false); };
    	
    }

    /**
     * Displays an error message to the user.
     * @param errorMessage message to display in the dialog.
     * @param modal toggle whether the message should block or fire and forget.
     */
    public static void displayErrorDialog(final String errorMessage, boolean modal) {
        if (StringUtils.isEmpty(errorMessage)) {
            return;
        }

        Runnable r = () -> {
              JOptionPane.showMessageDialog(new JFrame(), errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
        };

        if (modal) {
            r.run();
        } else {
          java.awt.EventQueue.invokeLater(r);
        }
    }


}
