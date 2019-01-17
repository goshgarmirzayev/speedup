/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.tree.database;

import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.dao.inter.DatabaseDAOInter;
import java.io.Serializable;

/**
 *
 * @author sarkhanrasullu
 */
public abstract class SUAbstractTreeNode<T> extends com.bsptechs.main.bean.ui.tree.SUAbstractTreeNode implements Serializable {
    protected DatabaseDAOInter dao = new DatabaseDAOImpl();
    protected T dataBean;
    
    
    protected SUAbstractTreeNode(SUDatabaseTree tree, T dataBean){
        super(tree);
        this.dataBean = dataBean;
    }
}
