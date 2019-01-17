package com.bsptechs.main.bean.ui.tree.database.bundle;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.ui.popup.UiPopupDatabase;
import com.bsptechs.main.bean.ui.tree.database.SUBundleTreeNode;
import com.bsptechs.main.bean.ui.tree.database.SUDatabaseTree;
import com.bsptechs.main.bean.ui.tree.database.SUTableTreeNode;
import com.bsptechs.main.bean.ui.tree.database.bean.SUDatabaseBean;
import com.bsptechs.main.bean.ui.tree.database.bean.SUTableBean;
import java.util.List;
import javax.swing.JPopupMenu;

public class SUEventBundleTreeNode extends SUBundleTreeNode {

    private final SUDatabaseBean database;

    public SUEventBundleTreeNode(SUDatabaseTree tree, SUDatabaseBean database) {
        super(tree, database);
        this.database = database;
    }

    private SUArrayList<SUTableBean> getTableBeans() {
        SUArrayList<SUTableBean> list = new SUArrayList<>();
        List<SUTableTreeNode> l = super.getChildren(SUTableTreeNode.class);
        for (int i = 0; i < l.size(); i++) {
            list.add(l.get(i).getTable());
        }
        return list;
    }

    private void addTables(List<SUTableBean> tables) {
        SUArrayList<SUTableTreeNode> nodes = new SUArrayList<>();
        for (SUTableBean table : tables) {
            nodes.add(new SUTableTreeNode(getTree(), table));
        }
        System.out.println("nodes size=" + nodes.size());
        super.addChildren(nodes);
    }

    public SUDatabaseTree getTree() {
        return (SUDatabaseTree) tree;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onDoubleClick() {
        
        List<SUTableBean> tables = getTableBeans();
        if (tables.isEmpty()) {
            tables = dao.getAllTables(this.database);
            addTables(tables);
            expand();
        }
    }

    @Override
    public JPopupMenu getPopup() {
        return new UiPopupDatabase();
    }

    @Override
    public String getIcon() {
        return "mainframe/event.png";
    }

    @Override
    public String toString() {
        return "events";
    }
}
