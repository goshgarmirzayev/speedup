/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.popup;

import com.bsptechs.main.Main;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import javax.swing.JList;
import com.bsptechs.main.Config;
import com.bsptechs.main.bean.ui.tree.database.bean.SUConnectionBean;
import com.bsptechs.main.bean.ui.tree.database.bean.SUDatabaseBean;
import com.bsptechs.main.util.FileUtility;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import com.bsptechs.main.util.LogUtil;
/**
 *
 * @author sarkhanrasullu
 */
public class UiPopupDatabase extends UiPopupAbstract {

    DatabaseDAOImpl database = new DatabaseDAOImpl();

    JList list;

    public UiPopupDatabase() {
        addMenuItem("Database Properties", () -> {
            properties();
        });
        addMenuItem("Delete Database", () -> {
            delete();
        });
        addMenuItem("New Query", () -> {
            newQuery();
        });

        addMenuItem("Dump SQL file", () -> {
            dumpSQLFile();
        });

    }

    public void delete() {
        LogUtil.log("delete database");
        //Tebriz burani dolduracaq
    }

    public void properties() {
        LogUtil.log("properites database");
        //Tebriz burani dolduracaq
    }

    public void newQuery() {
        LogUtil.log("new query");
        Main.instance().prepareNewQuery(null, false);
    }

    private void dumpSQLFile() {
        Config n = (Config) FileUtility.readFileDeserialize("mySql.txt");
        Main m = Main.instance();
        SUDatabaseBean database = (SUDatabaseBean) m.getConnectionTree()
                .getSelectedDatabaseNode().getDatabase();

        SUConnectionBean connection = database.getConnection();
        String dbName = database.getName();
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new File("/Users/Goshgar/Documents/" + dbName));
        int retrival = chooser.showSaveDialog(null);
        if (retrival == JFileChooser.APPROVE_OPTION) {
            try {
                String source = chooser.getCurrentDirectory() + "//" + chooser.getSelectedFile().getName();
                String executeCmd = "";
                executeCmd = "C:\\Program Files\\MySQL\\MySQL Server 5.5\\bin\\mysqldump -u " + connection.getUserName() + " -p" + connection.getPassword() + " " + dbName + " -r " + source + ".sql";
                Runtime runtime = Runtime.getRuntime();
                runtime.exec(executeCmd, null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
