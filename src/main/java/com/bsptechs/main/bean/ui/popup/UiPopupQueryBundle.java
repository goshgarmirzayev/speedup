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
import com.bsptechs.main.bean.server.SUDatabaseBean;
import com.bsptechs.main.bean.server.SUQueryBean;
import com.bsptechs.main.util.FileUtility;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author Goshgar
 */
public class UiPopupQueryBundle extends UiPopupAbstract {

    private final SUDatabaseBean selectedDatabase = Main
            .instance()
            .getConnectionTree()
            .getCurrentDatabaseNode()
            .getDatabase();
    private final SUConnectionBean currentConnection = Main
            .instance()
            .getConnectionTree()
            .getCurrentConnectionNode().getConnection();

    public UiPopupQueryBundle() {
        addMenuItem("New Query", () -> {
            newQuery();
        });
        addMenuItem("New Group", () -> {

        });
        addMenuItem("Paste", () -> {
            pasteQuery();
        });
        addMenuItem("Open containing folder", () -> {

        });
        addMenuItem("Refresh", () -> {

        });
    }

    private void newQuery() {
        Main.instance().prepareNewQuery(null, false);
    }

    private void pasteQuery() {
        SUArrayList<SUConnectionBean> connectionList = Main.instance()
                .getConnectionTree()
                .getConnectionBeans();
        connectionList.remove(currentConnection);
        List<SUQueryBean> queryList = currentConnection.getQueries();
        SUQueryBean queryForCopy = (SUQueryBean) FileUtility.readFileDeserialize("forCopy.copy");
        queryForCopy.setDatabase(selectedDatabase);
        queryForCopy.setConnection(currentConnection);
        String newQueryName = (String) JOptionPane.showInputDialog(
                null,
                "Enter new name:",
                "Rename Query",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                queryForCopy.getName()
        );
        queryForCopy.setName(newQueryName);
        queryList.add(queryForCopy);
        connectionList.add(currentConnection);
        Config.instance().setConnectionBeans(connectionList);
        Config.instance().saveConfig();

    }
}
