package com.bsptechs.main.bean.ui.tree.server.bundle;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.server.SUDatabaseBean;
import com.bsptechs.main.bean.ui.tree.server.SUAbstractServerTreeNode;
import com.bsptechs.main.bean.ui.tree.server.SUServerTree;
import com.bsptechs.main.bean.server.SUQueryBean;
import com.bsptechs.main.bean.ui.button.SUObjectActionButton;
import com.bsptechs.main.bean.ui.panel.PanelObjectMain;
import com.bsptechs.main.bean.ui.popup.UiPopupQuery;
import com.bsptechs.main.bean.ui.tree.server.SUQueryTreeNode;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.dao.inter.DatabaseDAOInter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPopupMenu;
import lombok.Data;

@Data
public class SUQueryBundleTreeNode extends SUAbstractBundleTreeNode<SUQueryBean> {

    private final SUQueryBean query;
    private final DatabaseDAOInter dao = new DatabaseDAOImpl();

    public SUQueryBundleTreeNode(SUServerTree tree, SUQueryBean query) {
        super(tree, query);
        this.query = query;
    }

    private SUArrayList<SUQueryBean> getQueryBeans() {
        SUArrayList<SUQueryBean> list = new SUArrayList<>();
        List<SUQueryTreeNode> l = super.getChildren(SUQueryTreeNode.class);
        for (int i = 0; i < l.size(); i++) {
            list.add(l.get(i).getQuery());
        }
        return list;
    }

    private void addQueries(List<SUQueryBean> queries) {
        SUArrayList<SUQueryTreeNode> nodes = new SUArrayList<>();
        for (SUQueryBean query : queries) {
            nodes.add(new SUQueryTreeNode(getTree(), query));
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
        return new UiPopupQuery();
    }

    @Override
    public String getIcon() {
        return "mainframe/query.png";
    }

    @Override
    public String toString() {
        return "queries";
    }

    @Override
    public SUArrayList<SUObjectActionButton> getObjectControlButtons() {
        SUArrayList<SUObjectActionButton> list = new SUArrayList<>();
        list.add(new SUObjectActionButton("mainframe/query.png", "Design Query", new PanelObjectMain()));
        list.add(new SUObjectActionButton("mainframe/query.png", "New Query", new PanelObjectMain()));
        list.add(new SUObjectActionButton("mainframe/query.png", " Delete Query", new PanelObjectMain()));
        return list;
    }

    @Override
    public void fillData() {
        SUDatabaseBean selectedDatabase = Main.instance()
                .getConnectionTree()
                .getCurrentDatabaseNode()
                .getDatabase();
        List<SUQueryBean> sortedQueries = new ArrayList<>();
        if (Main.instance()
                .getConnectionTree()
                .getCurrentConnectionNode()
                .getConnection()
                .getQueries() != null) {
            List<SUQueryBean> queries = Main.instance()
                    .getConnectionTree()
                    .getCurrentConnectionNode()
                    .getConnection()
                    .getQueries();
            removeAllChildren();

            for (SUQueryBean query : queries) {
                if (selectedDatabase.equals(query.getDatabase())) {
                    sortedQueries.add(query);
                }
                if (query.getDatabase() == null) {
                    sortedQueries.add(query);
                }
            }
        }
        addQueries(sortedQueries);
    }

    @Override
    public SUArrayList<SUAbstractServerTreeNode> getObjectData() {
        return getChildren(SUAbstractServerTreeNode.class);
    }
}
