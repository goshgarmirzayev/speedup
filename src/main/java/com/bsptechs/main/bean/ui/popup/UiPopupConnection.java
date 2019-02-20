/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.popup;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.ui.frame.ConnectionFrame;
import com.bsptechs.main.Config;
import com.bsptechs.main.bean.ui.frame.CreateDatabase;
import com.bsptechs.main.bean.ui.tree.server.bundle.SUConnectionBundleTreeNode;
import com.bsptechs.main.util.LogUtil;
/**
 *
 * @author sarkhanrasullu
 */
public class UiPopupConnection extends UiPopupAbstract {

   

    public UiPopupConnection() {
       
        
        addMenuItem("Delete Connection", () -> {
            delete();
        });
        addMenuItem("Connection Properties", () -> {
            properties();
        });

        addMenuItem("Connect", () -> {
            connect();
        });

        addMenuItem("Disconnect", () -> {   
            disconnect();
        });
        
        addMenuItem("Create Database", () -> {
            createDb();
        });
        
    }
    
    private SUConnectionBundleTreeNode getSelectedConnection(){
        return (SUConnectionBundleTreeNode) getSelectedElement();
    }

    public void delete() {
        LogUtil.log("delete connection");
        SUConnectionBundleTreeNode c = getSelectedConnection();
        c.reset();
        Main.instance().getConnectionTree().removeCustomTreeNode(c);
        Config.instance().setConnectionBeans(Main.instance().getConnectionTree().getConnectionBeans());
        Config.instance().saveConfig();
    }

    public void properties() {
        LogUtil.log("properites connection");
        ConnectionFrame.showAsUpdate(getSelectedConnection().getConnection());
    }

    public void connect() {
        LogUtil.log("connection connection");
        getSelectedConnection().connect();
    }

    public void disconnect() {
        LogUtil.log("disconnection connection");
        SUConnectionBundleTreeNode cn = getSelectedConnection();
        cn.reset();
        cn.removeAllChildren();
    }

    
    public void createDb() {
        LogUtil.log("create database");
        CreateDatabase create = new CreateDatabase();
        create.setVisible(true);
    }
}
