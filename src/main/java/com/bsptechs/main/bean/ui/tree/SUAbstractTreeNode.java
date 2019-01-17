package com.bsptechs.main.bean.ui.tree;

import com.bsptechs.main.bean.SUArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public abstract class SUAbstractTreeNode<T> extends DefaultMutableTreeNode {

    private static final long serialVersionUID = 1L;

    protected final SUAbstractTree tree;
    protected final T dataBean;

    public SUAbstractTreeNode(SUAbstractTree tree, T dataBean) {
        this.tree = tree;
        this.dataBean = dataBean;
    }

    public abstract void onClick();

    public abstract void onDoubleClick();

    public abstract JPopupMenu getPopup();

    public abstract String getIcon();

    public void addChildren(List<? extends SUAbstractTreeNode> listData) {
        if (listData == null) {
            return;
        }

        for (SUAbstractTreeNode t : listData) {
            add(t);
        }

        nodeStructureChanged();

    }

    public void addChild(SUAbstractTreeNode t) {
        if (t == null) {
            return;
        }

        add(t);

        nodeStructureChanged();

    }

    public void addChild(SUAbstractTreeNode parentNode, SUAbstractTreeNode t) {
        if (t == null) {
            return;
        }

        parentNode.add(t);

        nodeStructureChanged();

    }

    public void addChildren(SUAbstractTreeNode parentNode, List<? extends SUAbstractTreeNode> listData) {
        if (listData == null) {
            return;
        }

        for (SUAbstractTreeNode t : listData) {
            parentNode.add(t);
        }

        nodeStructureChanged();

    }
    

    public <E> SUArrayList<E> getChildren(Class<E> clazz) {
        Enumeration en = this.children();
        SUArrayList<E> list = new SUArrayList<>();
        while (en.hasMoreElements()) {
            list.add((E) en.nextElement());
        }
        return list;
    }
    
    public SUArrayList<SUAbstractTreeNode> getChildren(){
        return getChildren(SUAbstractTreeNode.class);
    }

    @Override
    public void removeAllChildren() {
        super.removeAllChildren();
        nodeStructureChanged();
    }

    public void nodeChanged() {
        tree.getTreeModel().nodeChanged(this);
    }

    public void nodeStructureChanged() {
        tree.getTreeModel().nodeStructureChanged(this);
    }

    public void expand() {
        tree.expandPath(new TreePath(this.getPath()));
    }

}
