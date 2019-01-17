/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.util;

import java.awt.event.MouseEvent;
import javax.swing.SwingUtilities;

/**
 *
 * @author sarkhanrasullu
 */
public class MouseUtil {

    public static boolean isLeftDoubleClicked(MouseEvent evt) {
        return evt.getClickCount() == 2 && SwingUtilities.isLeftMouseButton(evt);
    }

    public static boolean isLeftClicked(MouseEvent evt) {
        return SwingUtilities.isLeftMouseButton(evt);
    }

    public static boolean isRightClicked(MouseEvent evt) {
        return SwingUtilities.isRightMouseButton(evt);
    }
}
