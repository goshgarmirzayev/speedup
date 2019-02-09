/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.tree.server.bundle;

import com.bsptechs.main.bean.ui.tree.server.SUAbstractServerTreeNode;
import com.bsptechs.main.bean.ui.tree.server.SUServerTree;

/**
 *
 * @author sarkhanrasullu
 */
public abstract class SUAbstractBundleTreeNode<T> extends SUAbstractServerTreeNode<T> {

    public SUAbstractBundleTreeNode(SUServerTree tree, T database) {
        super(tree, database);
    }
    
    public abstract void fillData();
    
    
    @Override
    public void onDoubleClick() {
        if (getChildCount()==0) {
            fillData();
            nodeStructureChanged();
            expand();
        }
    }
}
