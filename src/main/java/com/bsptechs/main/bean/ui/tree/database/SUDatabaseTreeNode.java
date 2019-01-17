package com.bsptechs.main.bean.ui.tree.database;

import com.bsptechs.main.bean.ui.tree.database.bundle.SUTableBundleTreeNode;
import com.bsptechs.main.Main;
import com.bsptechs.main.bean.ui.tree.database.bean.SUDatabaseBean;
import com.bsptechs.main.bean.ui.popup.UiPopupDatabase;
import com.bsptechs.main.bean.ui.tree.database.bundle.SUBackupBundleTreeNode;
import com.bsptechs.main.bean.ui.tree.database.bundle.SUEventBundleTreeNode;
import com.bsptechs.main.bean.ui.tree.database.bundle.SUFunctionBundleTreeNode;
import com.bsptechs.main.bean.ui.tree.database.bundle.SUQueryBundleTreeNode;
import com.bsptechs.main.bean.ui.tree.database.bundle.SUReportBundleTreeNode;
import com.bsptechs.main.bean.ui.tree.database.bundle.SUViewBundleTreeNode;
import javax.swing.JPopupMenu;

public class SUDatabaseTreeNode extends SUAbstractTreeNode<SUDatabaseBean> {

    private SUTableBundleTreeNode tableBundle;
    private SUViewBundleTreeNode viewBundle;
    private SUFunctionBundleTreeNode functionBundle;
    private SUEventBundleTreeNode eventBundle;
    private SUQueryBundleTreeNode queryBundle;
    private SUReportBundleTreeNode reportBundle;
    private SUBackupBundleTreeNode backupBundle;

    public SUDatabaseTreeNode(SUDatabaseTree tree, SUDatabaseBean database) {
        super(tree, database);
        tableBundle = new SUTableBundleTreeNode(tree, database);
        viewBundle = new SUViewBundleTreeNode(tree, database);
        functionBundle = new SUFunctionBundleTreeNode(tree, database);
        eventBundle = new SUEventBundleTreeNode(tree, database);
        queryBundle = new SUQueryBundleTreeNode(tree, database);
        reportBundle = new SUReportBundleTreeNode(tree, database);
        backupBundle = new SUBackupBundleTreeNode(tree, database);
    }

    public SUDatabaseBean getDatabase() {
        return dataBean;
    }

//    private SUArrayList<SUTableBean> getTableBeans() {
//        SUArrayList<SUTableBean> list = new SUArrayList<>();
//        List<SUTableTreeNode> l = tableBundle.getChildren(SUTableTreeNode.class);
//        for (int i = 0; i < l.size(); i++) {
//            list.add(l.get(i).getTable());
//        }
//        return list;
//    }
//    private void addTables(List<SUTableBean> tables) {
//        SUArrayList<SUTableTreeNode> nodes = new SUArrayList<>();
//        for (SUTableBean table : tables) {
//            nodes.add(new SUTableTreeNode(getTree(), table));
//        }
//        System.out.println("nodes size="+nodes.size());
//        super.addChildren(tableBundle, nodes);
//    }
    public SUDatabaseTree getTree() {
        return (SUDatabaseTree) tree;
    }

    @Override
    public void onClick() {

    }

    @Override
    public void onDoubleClick() {
        Main.instance().getConnectionTree().setCurrentDatabaseNode(this);
        if (getChildCount() == 0) {
            super.addChild(tableBundle);
            super.addChild(viewBundle);
            super.addChild(functionBundle);
            super.addChild(eventBundle);
            super.addChild(queryBundle);
            super.addChild(reportBundle);
            super.addChild(backupBundle);
            expand();
        }
    }

    @Override
    public JPopupMenu getPopup() {
        return new UiPopupDatabase();
    }

    @Override
    public String getIcon() {
        return "database.png";
    }

    @Override
    public String toString() {
        return dataBean.getName();
    }

}
