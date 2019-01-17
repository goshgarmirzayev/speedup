package com.bsptechs.main.bean.ui.tree.server.bundle;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.ui.panel.PanelObjectMain;
import com.bsptechs.main.bean.server.SUDatabaseBean;
import com.bsptechs.main.bean.server.SUQueryBean;
import com.bsptechs.main.bean.ui.button.SUObjectActionButton;
import com.bsptechs.main.bean.ui.panel.PanelDataTransferGeneral;
import com.bsptechs.main.bean.ui.popup.UiPopupDatabase;
import com.bsptechs.main.bean.ui.tree.server.SUAbstractServerTreeNode;
import com.bsptechs.main.bean.ui.tree.server.SUServerTree;
import javax.swing.JPopupMenu;

public class SUDatabaseBundleTreeNode extends SUAbstractBundleTreeNode<SUDatabaseBean> {

    private final SUTableBundleTreeNode tableBundle;
    private final SUViewBundleTreeNode viewBundle;
    private final SUFunctionBundleTreeNode functionBundle;
    private final SUEventBundleTreeNode eventBundle;
    private final SUQueryBundleTreeNode queryBundle;
    private final SUReportBundleTreeNode reportBundle;
    private final SUBackupBundleTreeNode backupBundle;

    public SUDatabaseBundleTreeNode(SUServerTree tree, SUDatabaseBean database) {
        super(tree, database);
        tableBundle = new SUTableBundleTreeNode(tree, database);
        viewBundle = new SUViewBundleTreeNode(tree, database);
        functionBundle = new SUFunctionBundleTreeNode(tree, database);
        eventBundle = new SUEventBundleTreeNode(tree, database);
        queryBundle = new SUQueryBundleTreeNode(tree, new SUQueryBean());
        reportBundle = new SUReportBundleTreeNode(tree, database);
        backupBundle = new SUBackupBundleTreeNode(tree, database);
    }

    public SUDatabaseBean getDatabase() {
        return dataBean;
    }

    public SUServerTree getTree() {
        return (SUServerTree) tree;
    }

    @Override
    public void onClick() {
        PanelObjectMain tab = Main.instance().getObjectTab();
        tab.refresh(Main.instance().getConnectionTree().getSelectedServerTreeNode());
//                Main.instance().getTabbedPaneCenter().setSelectedIndex(0);
//                Main.instance().getTabbedPaneCenter().revalidate();
    }

    @Override
    public void onDoubleClick() {
        Main.instance().getConnectionTree().setCurrentDatabaseNode(this);
        super.onDoubleClick();
    }

    @Override
    public void fillData() {
        tableBundle.fillData();
        super.addChild(tableBundle);
        super.addChild(viewBundle);
        super.addChild(functionBundle);
        super.addChild(eventBundle);
        super.addChild(queryBundle);
        super.addChild(reportBundle);
        super.addChild(backupBundle);
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
 
    @Override
    public SUArrayList<SUObjectActionButton> getObjectControlButtons() {
        SUArrayList<SUObjectActionButton> list = new SUArrayList<>();
        list.add(new SUObjectActionButton("database.png", "Add", new PanelObjectMain()));
        list.add(new SUObjectActionButton("database.png", "Edit",new PanelObjectMain()));
        list.add(new SUObjectActionButton("database.png", "Remove",new PanelObjectMain()));
        return list;
    }
    
    @Override
    public SUArrayList<SUAbstractServerTreeNode> getObjectData() {
       return tableBundle.getObjectData();
    }

}
