/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.tree.server;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.server.SUQueryBean;
import com.bsptechs.main.bean.ui.button.SUObjectActionButton;
import com.bsptechs.main.bean.ui.panel.PanelObjectMain;
import com.bsptechs.main.bean.ui.popup.UiPopupQuery;
import javax.swing.JPopupMenu;
import lombok.Data;

/**
 *
 * @author Goshgar
 */
@Data
public class SUQueryTreeNode extends SUAbstractServerTreeNode<SUQueryBean> {

    private final SUQueryBean query;

    public SUQueryTreeNode(SUServerTree tree, SUQueryBean query) {
        super(tree, query);
        this.query = query;
    }

    @Override
    public void onClick() {
        PanelObjectMain tab = Main.instance().getObjectTab();
        tab.refresh(Main.instance().getConnectionTree().getSelectedServerTreeNode());
    }

    @Override
    public void onDoubleClick() {
        Main.instance().prepareNewQuery(this.query.getQuery(), false);
    }

    @Override
    public JPopupMenu getPopup() {
        return new UiPopupQuery();
    }

    @Override
    public String getIcon() {
        return "mainframe/query.png";
    }

    @Override
    public String toString() {
        return query.getName();
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
