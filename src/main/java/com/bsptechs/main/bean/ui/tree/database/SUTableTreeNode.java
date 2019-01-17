/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.tree.database;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.ui.tree.database.bean.SUTableBean;
import com.bsptechs.main.bean.ui.popup.UiPopupTable;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import lombok.Data;
import com.bsptechs.main.util.LogUtil;
/**
 *
 * @author Goshgar
 */
@Data
public class SUTableTreeNode extends SUAbstractTreeNode {

    private final SUTableBean table;

    public SUTableTreeNode(SUDatabaseTree tree, SUTableBean table) {
        super(tree, table);
        this.table = table;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onDoubleClick() {
        JTabbedPane tabbedPaneCenter = Main.instance().getTabbedPaneCenter();

        tabbedPaneCenter.setEnabled(true);
        tabbedPaneCenter.removeAll();
        List<String> names = new ArrayList<>();
        names.add("Query");
        names.add("Design Table");
        names.add("New Table");
        names.add("Tables");
        List<JTabbedPane> tabbedList = Main.instance().getTabPanesTable();
        LogUtil.log("names=" + names);
        for (int i = 0; i < tabbedList.size(); i++) {
            tabbedPaneCenter.add(names.get(i), tabbedList.get(i));
        }

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
}
