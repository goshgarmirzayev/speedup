package com.bsptechs.main.bean.ui.tree.server.bundle;

import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.ui.button.SUAbstractButton;
import com.bsptechs.main.bean.ui.popup.UiPopupDatabase;
import com.bsptechs.main.bean.ui.tree.server.SUAbstractServerTreeNode;
import com.bsptechs.main.bean.ui.tree.server.SUServerTree;
import com.bsptechs.main.bean.ui.tree.server.SUTableTreeNode;
import com.bsptechs.main.bean.server.SUDatabaseBean;
import com.bsptechs.main.bean.server.SUTableBean;
import com.bsptechs.main.bean.ui.button.SUObjectActionButton;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.dao.inter.DatabaseDAOInter;
import java.util.List;
import javax.swing.JPopupMenu;

public class SUFunctionBundleTreeNode extends SUAbstractBundleTreeNode<SUDatabaseBean> {

    private final SUDatabaseBean database;
    private final DatabaseDAOInter dao = new DatabaseDAOImpl();

    public SUFunctionBundleTreeNode(SUServerTree tree, SUDatabaseBean database) {
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

    public SUServerTree getTree() {
        return (SUServerTree) tree;
    }

    @Override
    public void onClick() {

    }

    @Override
    public JPopupMenu getPopup() {
        return new UiPopupDatabase();
    }

    @Override
    public String getIcon() {
        return "mainframe/function.png";
    }

    @Override
    public String toString() {
        return "functions";
    }

    @Override
    public SUArrayList<SUObjectActionButton> getObjectControlButtons() {
        return null;
    }

    @Override
    public SUArrayList<SUAbstractServerTreeNode> getObjectData() {
        return null;
    }

    @Override
    public void fillData() {
        List<SUTableBean> tables = dao.getAllTables(this.database);
        addTables(tables);
    }
}
