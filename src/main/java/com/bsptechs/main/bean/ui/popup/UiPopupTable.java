/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.popup;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.server.SUTableBean;
import com.bsptechs.main.bean.ui.panel.PanelObjectMain;
import com.bsptechs.main.bean.ui.tree.server.SUTableTreeNode;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.dao.inter.DatabaseDAOInter;
import com.bsptechs.main.util.FileUtility;
import javax.swing.JOptionPane;
import com.bsptechs.main.util.LogUtil;

/**
 *
 * @author sarkhanrasullu
 */
public class UiPopupTable extends UiPopupAbstract {

    private static DatabaseDAOInter database = new DatabaseDAOImpl();

    public UiPopupTable() {

        addMenuItem("Delete Table", () -> {
            delete();
        });
        addMenuItem("Table Properties", () -> {
            properties();
        });

        addMenuItem("New Query", () -> {
            newQuery();
        });

        addMenuItem("View Table", () -> {
            viewTable();
        });
        addMenuItem("Rename", () -> {
            renameTable();
        });
        addMenuItem("Refresh", () -> {
            refresh();
        });
        addMenuItem("Empty Table", () -> {
            emptyTable();
        });
        addMenuItem("Truncate Table", () -> {
            truncateTeable();
        });
        addMenuItem("Copy", () -> {
            copyTable();
        });
        addMenuItem("Paste", () -> {
            pasteTable();
        });
        addMenuItem("Dublicate Table", () -> {
            dublicateTable();
        });
        addMenuItem("Dump Sql file", () -> {
            dumpSqlFile();
        });
        addMenuItem("Object Information", () -> {
            objectInformation();
        });

    }

    public void delete() {
        LogUtil.log("table delete");
        SUTableBean table = Main.instance().getConnectionTree().getSelectedTableNode().getTable();
        database.deleteTable(table);
    }

    public void properties() {
        LogUtil.log("table properties");
        //Tebriz burani dolduracaq
    }

    public void newQuery() {
        LogUtil.log("new query");
        Main.instance().prepareNewQuery("", false);
    }

    public void viewTable() {

        SUTableTreeNode element = Main.instance().getConnectionTree().getSelectedTableNode();

        if (element != null) {
            Main.instance().prepareNewQuery("select * from " + element.getTable().getName(), true);
        }
    }

    public SUTableTreeNode getSelectedTable() {
        return (SUTableTreeNode) getSelectedElement();
    }

    public void renameTable() {
        SUTableTreeNode tb = getSelectedTable();
        String newTblName = (String) JOptionPane.showInputDialog(
                null,
                "Enter new name:",
                "Rename Table",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                tb.getTable().getName()
        );
        database.renameTable(tb.getTable(), newTblName);
        tb.nodeChanged();
    }

    private void emptyTable() {
        SUTableTreeNode tb = getSelectedTable();
        database.emptyTable(tb.getTable().getDatabase(), tb.getTable().getName());
    }

    private void truncateTeable() {
        SUTableTreeNode tb = getSelectedTable();
        database.truncateTable(tb.getTable().getDatabase(), tb.getTable().getName());
    }

    private final SUTableBean selectedElementForCopy = getSelectedTable().getTable();

    private void copyTable() {
        FileUtility.writeObjectToFile(selectedElementForCopy, "forCopy.copy");

    }

    private void pasteTable() {
        SUTableTreeNode tb = getSelectedTable();
        SUTableBean copyTable = (SUTableBean) FileUtility.readFileDeserialize("forCopy.copy");
        String newTblName = (String) JOptionPane.showInputDialog(
                null,
                "Enter name:",
                "Paste Table",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                copyTable.getName()
        );

        database.pasteTable(
                copyTable.getDatabase() + "." + copyTable.getName(),
                tb.getTable().getDatabase(),
                newTblName
        );
    }

    private void dublicateTable() {
        SUTableTreeNode tb = getSelectedTable();
        database.dublicateTable(tb.getTable().getDatabase(), tb.getTable().getName());
    }

    private void dumpSqlFile() {
    }

    private void objectInformation() {
    }

    private void refresh() {
        PanelObjectMain tab = Main.instance().getObjectTab();
        tab.refresh(Main.instance().getConnectionTree().getSelectedServerTreeNode());
    }
}
