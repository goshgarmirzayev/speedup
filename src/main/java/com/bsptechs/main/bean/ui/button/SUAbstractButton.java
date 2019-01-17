/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.button;

import com.bsptechs.main.util.ImageUtil;
import java.awt.event.ActionListener;
import javax.swing.JButton;

/**
 *
 * @author sarkhanrasullu
 */
public abstract class SUAbstractButton extends JButton {

    private ActionListener clickActionListener;

    public SUAbstractButton(String icon, String label) {
        this.setIcon(ImageUtil.getIcon(icon));
        this.setText(label);
    }
}
