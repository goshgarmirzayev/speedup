/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.popup;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.ui.panel.queryresult.PanelQuery;
import com.bsptechs.main.Config;
import com.bsptechs.main.bean.ui.tree.database.SUTableTreeNode;
import com.bsptechs.main.bean.ui.tree.SUAbstractTreeNode;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.dao.inter.DatabaseDAOInter;
import java.util.List;
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
        //Tebriz burani dolduracaq
    }

    public void properties() {
        LogUtil.log("table properties");
        //Tebriz burani dolduracaq
    }

    public void newQuery() {
        LogUtil.log("new query");
        //Tebriz burani dolduracaq
    }

    public void viewTable() {

        SUTableTreeNode element = Main.instance().getConnectionTree().getSelectedTableNode();

        if (element !=null) {
            Main.instance().prepareNewQuery("select * from "+element.getTable().getName(), true);
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

    private SUTableTreeNode selectedElementForCopy;

    private void copyTable() {
        SUTableTreeNode tb = getSelectedTable();
        this.selectedElementForCopy = tb;
    }

    private void pasteTable() {
        SUTableTreeNode tb = getSelectedTable();

        String newTblName = (String) JOptionPane.showInputDialog(
                null,
                "Enter name:",
                "Paste Table",
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                tb.getTable().getName()
        );

        database.pasteTable(
                selectedElementForCopy.getTable().getDatabase() + "." + selectedElementForCopy.getTable().getName(),
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
}
