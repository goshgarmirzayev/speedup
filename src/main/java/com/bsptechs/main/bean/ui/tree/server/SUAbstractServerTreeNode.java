/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.tree.server;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.ui.button.SUAbstractButton;
import com.bsptechs.main.bean.ui.button.SUObjectActionButton;
import com.bsptechs.main.bean.ui.panel.PanelObjectMain;
import com.bsptechs.main.bean.ui.tree.SUAbstractTree;
import com.bsptechs.main.bean.ui.tree.SUAbstractTreeNode;

/**
 *
 * @author sarkhanrasullu
 */
public abstract class SUAbstractServerTreeNode<T> extends SUAbstractTreeNode<T> {

    protected SUAbstractServerTreeNode(SUAbstractTree tree, T dataBean) {
        super(tree, dataBean);
    }

    public abstract SUArrayList<SUObjectActionButton> getObjectControlButtons();
 
    public abstract SUArrayList<SUAbstractServerTreeNode> getObjectData();
    
    
    public void fireObjectsTab(){
        PanelObjectMain tab = Main.instance().getObjectTab();
        tab.refresh(this);
    }
    
   

}
