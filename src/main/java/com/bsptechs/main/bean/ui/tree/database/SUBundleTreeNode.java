package com.bsptechs.main.bean.ui.tree.database;


public abstract class SUBundleTreeNode<T> extends SUAbstractTreeNode<T> {
  
    public SUBundleTreeNode(SUDatabaseTree tree, T dataBean) {
        super(tree, dataBean);
    }
  
}
