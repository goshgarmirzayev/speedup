/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.tree.server;

import com.bsptechs.main.bean.ui.tree.server.bundle.SUConnectionBundleTreeNode;
import com.bsptechs.main.bean.ui.tree.server.bundle.SUDatabaseBundleTreeNode;
import com.bsptechs.main.Main;
import com.bsptechs.main.bean.server.SUConnectionBean;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.ui.panel.PanelUiElementInformation;
import com.bsptechs.main.bean.ui.tree.SUAbstractTree;
import com.bsptechs.main.bean.ui.tree.SUAbstractTreeNode;
import com.bsptechs.main.bean.server.SUAbstractServerBean;
import com.bsptechs.main.util.MouseUtil;
import java.awt.event.MouseAdapter;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import com.bsptechs.main.util.LogUtil;

/**
 *
 * @author sarkhanrasullu
 */
public class SUServerTree extends SUAbstractTree {

    private SUConnectionBundleTreeNode currentConnectionNode = null;
    private SUDatabaseBundleTreeNode currentDatabaseNode = null;

    public void addConnectionNode(SUConnectionBean connection) {
        LogUtil.log(connection);
        this.addCustomTreeNodeToRoot(new SUConnectionBundleTreeNode(this, connection));
    }

    public SUArrayList<SUConnectionBean> getConnectionBeans() {
        SUArrayList<SUConnectionBundleTreeNode> connections = getConnectionNodes();
        SUArrayList<SUConnectionBean> connectionBeans = new SUArrayList<SUConnectionBean>();
        for (SUConnectionBundleTreeNode conn : connections) {
            connectionBeans.add(conn.getConnection());
        }
        return connectionBeans;
    }

    public SUAbstractServerTreeNode getSelectedServerTreeNode() {
        return getSelectedCustomTreeNodeGeneric(SUAbstractServerTreeNode.class);
    }

    public SUDatabaseBundleTreeNode getSelectedDatabaseNode() {
        return getSelectedCustomTreeNodeGeneric(SUDatabaseBundleTreeNode.class);
    }

    public SUTableTreeNode getSelectedTableNode() {
        return getSelectedCustomTreeNodeGeneric(SUTableTreeNode.class);
    }

    public SUQueryTreeNode getSelectedQueryNode() {
        return getSelectedCustomTreeNodeGeneric(SUQueryTreeNode.class);
    }

    public SUConnectionBundleTreeNode getSelectedConnectionNode() {
        return getSelectedCustomTreeNodeGeneric(SUConnectionBundleTreeNode.class);
    }

    public SUArrayList<SUConnectionBundleTreeNode> getConnectionNodes() {
        DefaultTreeModel treeModel = (DefaultTreeModel) this.getModel();
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treeModel.getRoot();
        Enumeration en = parentNode.children();

        SUArrayList<SUConnectionBundleTreeNode> list = new SUArrayList<>();
        while (en.hasMoreElements()) {
            list.add((SUConnectionBundleTreeNode) en.nextElement());
        }
        return list;
    }

    public void addConnectionNodes(List<SUConnectionBean> connections) {
        for (SUConnectionBean cnb : connections) {
            this.addCustomTreeNodeToRoot(new SUConnectionBundleTreeNode(this, cnb));
        }
    }

    public void setCurrentConnectionNode(SUConnectionBundleTreeNode connection) {
        currentConnectionNode = connection;
    }

    public SUConnectionBundleTreeNode getCurrentConnectionNode() {
        return currentConnectionNode;
    }

    public SUDatabaseBundleTreeNode getCurrentDatabaseNode() {
        return currentDatabaseNode;
    }

    public void setCurrentDatabaseNode(SUDatabaseBundleTreeNode currentDatabaseNode) {
        this.currentDatabaseNode = currentDatabaseNode;
    }

    public boolean hasAnyActiveConnection() {
        List<SUConnectionBean> l = this.getConnectionBeans();
        boolean found = false;
        for (int i = 0; i < l.size(); i++) {
            SUConnectionBean cn = l.get(i);
            if (cn.getParentConnection() != null) {
                found = true;
                break;
            }
        }
        return found;
    }

    @Override
    protected MouseAdapter getAdapter() {

        MouseAdapter m = new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent e) {
                SUAbstractTreeNode element = getSelectedCustomTreeNode();
                if (element == null) {
                    return;
                }
                if (MouseUtil.isRightClicked(e)) {
                    element.getPopup().show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                DefaultMutableTreeNode selectedUiElement = getSelectedNode();
                if (selectedUiElement == null || !(selectedUiElement instanceof SUAbstractServerTreeNode)) {
                    return;
                }
                SUAbstractServerTreeNode element = (SUAbstractServerTreeNode) selectedUiElement;

                if (MouseUtil.isLeftDoubleClicked(evt)) {
                    element.onDoubleClick();
                } else {
                    element.onClick();
                }

                element.fireObjectsTab();

                if (MouseUtil.isLeftClicked(evt)) {
                    LogUtil.log("left clicked");
                    PanelUiElementInformation pnlInfor = Main.instance().getInformationPanel();
                    pnlInfor.preparePanel(element);
                }
            }
        };

        return m;
    }

}
