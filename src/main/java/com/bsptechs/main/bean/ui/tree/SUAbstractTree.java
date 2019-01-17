package com.bsptechs.main.bean.ui.tree;

import java.awt.event.MouseAdapter;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public abstract class SUAbstractTree extends JTree {

    public SUAbstractTree() {
        this.addMouseListener(getAdapter());
        this.setCellRenderer(new SUAbstractTreeCellRenderer());
        this.setRootVisible(false);

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("root node, should be invisible");
        DefaultTreeModel defaultTreeModel = new DefaultTreeModel(rootNode);
        this.setModel(defaultTreeModel);
    }

    public DefaultMutableTreeNode getSelectedNode() {
        TreePath selectionPath = this.getSelectionPath();
        if (selectionPath != null) {
            DefaultMutableTreeNode obj = (DefaultMutableTreeNode) selectionPath.getLastPathComponent();
            return obj;
        }
        return null;
    }

    protected <T> T getSelectedCustomTreeNodeGeneric(Class<T> clazz) {
        DefaultMutableTreeNode obj = getSelectedNode();
        if (obj != null && clazz.isInstance(obj)) {
            return (T) obj;
        }
        return null;
    }

    public SUAbstractTreeNode getSelectedCustomTreeNode() {
        DefaultMutableTreeNode obj = getSelectedNode();
        if (obj != null && obj instanceof SUAbstractTreeNode) {
            return (SUAbstractTreeNode) obj;
        }
        return null;
    }

    public void removeCustomTreeNode(SUAbstractTreeNode element) {
        getTreeModel().removeNodeFromParent(element);
    }

    public DefaultTreeModel getTreeModel() {
        return (DefaultTreeModel) this.getModel();
    }

    public DefaultMutableTreeNode addCustomTreeNodeToRoot(SUAbstractTreeNode node) {
        DefaultTreeModel treeModel = (DefaultTreeModel) this.getModel();
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treeModel.getRoot();
        return addCustomTreeNode(treeModel, parentNode, node);
    }

    private DefaultMutableTreeNode addCustomTreeNode(DefaultMutableTreeNode parentNode, SUAbstractTreeNode node) {
        DefaultTreeModel treeModel = (DefaultTreeModel) this.getModel();
        return addCustomTreeNode(treeModel, parentNode, node);
    }

    private DefaultMutableTreeNode addCustomTreeNode(DefaultTreeModel treeModel, DefaultMutableTreeNode parentNode, DefaultMutableTreeNode node) {
        treeModel.insertNodeInto(node, parentNode, parentNode.getChildCount());
        if (parentNode == treeModel.getRoot()) {
            treeModel.nodeStructureChanged((TreeNode) treeModel.getRoot());
        }
        return node;
    }

    public void removeAllCustomTreeNodes() {
        DefaultTreeModel treeModel = (DefaultTreeModel) this.getModel();
        DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) treeModel.getRoot();
        parentNode.removeAllChildren();
    }

    public void fillTreeAsRoot(List<? extends SUAbstractTreeNode> listData) {
        if (listData == null) {
            return;
        }

        for (SUAbstractTreeNode node : listData) {
            DefaultMutableTreeNode addEl = addCustomTreeNodeToRoot(node);
//            if (node.getSubList() != null) {
//                fillTree(node.getSubList(), addEl);
//            }
        }
    }

    protected abstract MouseAdapter getAdapter();

}
