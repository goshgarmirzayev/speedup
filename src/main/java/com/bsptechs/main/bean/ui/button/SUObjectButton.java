/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.button;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.ui.panel.PanelObjectMain;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author sarkhanrasullu
 */
public class SUObjectButton extends SUAbstractButton {

    public SUObjectButton(String icon, String label, JPanel panel) {
        super(icon, label);
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PanelObjectMain tab = Main.instance().getObjectTab();
                tab.refresh(Main.instance().getConnectionTree().getSelectedServerTreeNode());
                Main.instance().getTabbedPaneCenter().setSelectedIndex(0);
                Main.instance().getTabbedPaneCenter().revalidate();
            }
        });

    }

}
