/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.tree.server;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.ui.button.SUAbstractButton;
import com.bsptechs.main.bean.ui.button.SUSimpleButton;
import com.bsptechs.main.bean.server.SUTableBean;
import com.bsptechs.main.bean.ui.button.SUObjectActionButton;
import com.bsptechs.main.bean.ui.popup.UiPopupTable;
import com.bsptechs.main.bean.ui.tree.SUAbstractTreeNode;
import javax.swing.JPopupMenu;
import lombok.Data;

/**
 *
 * @author Goshgar
 */
@Data
public class SUTableTreeNode extends SUAbstractServerTreeNode<SUTableBean> {

    private final SUTableBean table;

    public SUTableTreeNode(SUServerTree tree, SUTableBean table) {
        super(tree, table);
        this.table = table;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onDoubleClick() {
        Main.instance().prepareNewQuery("select * from " + this.table.getName(), true);
    }

    @Override
    public JPopupMenu getPopup() {
        return new UiPopupTable();
    }

    @Override
    public String getIcon() {
        return "table.png";
    }

    @Override
    public String toString() {
        return table.getName();
    }

    @Override
    public SUArrayList<SUObjectActionButton> getObjectControlButtons() {
         return null;
    }

    @Override
    public SUArrayList<SUAbstractServerTreeNode> getObjectData() {
        return null;
    }
}
