/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.button;
 
import com.bsptechs.main.Main;
import com.bsptechs.main.util.Util;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author sarkhanrasullu
 */
public class SUObjectActionButton extends SUAbstractButton {

     public SUObjectActionButton(String icon, String label, JPanel panel) {
        super(icon, label);
        addActionListener((ActionEvent e) -> {
            JTabbedPane tab = Main.instance().getTabbedPaneCenter();
            Util.addPanelToTab(tab, panel, "Default Tab Title");
        });

    }

}
