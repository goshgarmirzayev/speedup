/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.popup;

import com.bsptechs.main.Config;
import com.bsptechs.main.Main;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.server.SUConnectionBean;
import com.bsptechs.main.bean.server.SUQueryBean;
import com.bsptechs.main.bean.ui.tree.server.SUAbstractServerTreeNode;
import com.bsptechs.main.bean.ui.tree.server.SUQueryTreeNode;
import com.bsptechs.main.bean.ui.tree.server.bundle.SUConnectionBundleTreeNode;
import com.bsptechs.main.bean.ui.tree.server.bundle.SUQueryBundleTreeNode;
import com.bsptechs.main.util.LogUtil;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Goshgar
 */
public class UiPopupQuery extends UiPopupAbstract {

    private SUQueryBean selectedQuery = Main.instance().getConnectionTree().getSelectedQueryNode().getQuery();

    public UiPopupQuery() {
        addMenuItem("Design Query", () -> {
            designQuery();
        });
        addMenuItem("New Query", () -> {
            newQuery();
        });

        addMenuItem("Delete Query", () -> {
            deleteQuery();
        });

        addMenuItem("Export Wizard", () -> {

        });
        addMenuItem("Copy", () -> {

        });
        addMenuItem("Rename", () -> {
            renameQuery();
        });
        addMenuItem("Open Containing Folder", () -> {

        });
        addMenuItem("Refresh", () -> {

        });
        addMenuItem("Copy", () -> {

        });
        addMenuItem("Object Information", () -> {

        });

    }

    private SUAbstractServerTreeNode getSelectedQueryNode() {
        return (SUQueryTreeNode) getSelectedElement();
    }
    
    String designedQuery;
    public void setDesignedQuery(String designedQuery) {
        this.designedQuery = designedQuery;
    }

    private void designQuery() {
        Main.instance().prepareNewQuery(selectedQuery.getQuery(), false);
        SUArrayList<SUConnectionBean> connectionList = Main.instance()
                .getConnectionTree()
                .getConnectionBeans();
        SUConnectionBean selectedConnection = Main.instance()
                .getConnectionTree()
                .getCurrentConnectionNode()
                .getConnection();
        connectionList.remove(selectedConnection);
        List<SUQueryBean> oldQueries = selectedConnection.getQueries();
        oldQueries.remove(selectedQuery);
        selectedQuery.setQuery(designedQuery);
    }

    private void newQuery() {
        Main.instance().prepareNewQuery(null, false);
    }

    private void deleteQuery() {
        //JOptionPane elave etmek
        SUArrayList<SUConnectionBean> connectionList = Main.instance()
                .getConnectionTree()
                .getConnectionBeans();
        SUConnectionBean selectedConnection = Main.instance()
                .getConnectionTree()
                .getCurrentConnectionNode()
                .getConnection();
        connectionList.remove(selectedConnection);
        List<SUQueryBean> oldQueries = selectedConnection.getQueries();
        oldQueries.remove(selectedQuery);
        selectedConnection.setQueries(oldQueries);
        connectionList.add(selectedConnection);
        Config.instance().setConnectionBeans(connectionList);
        Config.instance().saveConfig();
        Main.instance().getConnectionTree().getSelectedQueryNode().fireObjectsTab();
    }

    private void renameQuery() {
        SUArrayList<SUConnectionBean> connectionList = Main.instance()
                .getConnectionTree()
                .getConnectionBeans();
        SUConnectionBean selectedConnection = Main.instance()
                .getConnectionTree()
                .getCurrentConnectionNode()
                .getConnection();
        connectionList.remove(selectedConnection);
        List<SUQueryBean> oldQueries = selectedConnection.getQueries();
        oldQueries.remove(selectedQuery);
        String newQueryName = (String) JOptionPane.showInputDialog(
                null,
                "Enter new name:",
                "Rename Query",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                selectedQuery.getName()
        );
        selectedQuery.setName(newQueryName);
        oldQueries.add(selectedQuery);
        selectedConnection.setQueries(oldQueries);
        connectionList.add(selectedConnection);
        Config.instance().saveConfig();
        Config.instance().setConnectionBeans(connectionList);
    }
}
