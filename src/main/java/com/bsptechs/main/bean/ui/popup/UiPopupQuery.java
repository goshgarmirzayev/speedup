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
import com.bsptechs.main.popup.ClipBoard.MyClipBoard;
import com.bsptechs.main.util.FileUtility;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Goshgar
 */
public class UiPopupQuery extends UiPopupAbstract {

    private static final SUQueryBean selectedQuery = Main.instance().getConnectionTree().getSelectedQueryNode().getQuery();

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
            try {
                copyQuery();

            } catch (Exception ex) {
                Logger.getLogger(UiPopupQuery.class.getName()).log(Level.SEVERE, null, ex);
            }
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
    public static boolean isDesigning = false;

    public static boolean getIsDesigning() {
        return isDesigning;
    }

    private void designQuery() {
        isDesigning = true;
        Main.instance().prepareNewQuery(selectedQuery.getQuery(), false);
    }

    public static void saveDesignedQuery(String designedQuery) {
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
        oldQueries.add(selectedQuery);
        Config.instance().saveConfig();
    }

    private void newQuery() {
        Main.instance().prepareNewQuery(null, false);
    }

    private void deleteQuery() {
        //JOptionPane elave etmek qalib
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

    private void copyQuery() throws Exception {
        FileUtility.writeObjectToFile(selectedQuery, "forCopy.copy");
    }
}
