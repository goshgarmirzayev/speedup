package com.bsptechs.main.bean.ui.tree.server.bundle;

import com.bsptechs.main.bean.ui.tree.server.bundle.SUDatabaseBundleTreeNode;
import com.bsptechs.main.bean.server.SUConnectionBean;
import com.bsptechs.main.bean.server.SUDatabaseBean;
import com.bsptechs.main.bean.ui.popup.UiPopupConnection;
import com.bsptechs.main.bean.SUArrayList;
import javax.swing.JPopupMenu;
import com.bsptechs.main.Main;
import com.bsptechs.main.bean.ui.button.SUAbstractButton;
import com.bsptechs.main.bean.ui.button.SUObjectActionButton;
import com.bsptechs.main.bean.ui.tree.server.SUAbstractServerTreeNode;
import com.bsptechs.main.bean.ui.tree.server.SUServerTree;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.dao.inter.DatabaseDAOInter;
import java.util.List;
import lombok.Data;
import com.bsptechs.main.util.LogUtil;

@Data
public class SUConnectionBundleTreeNode extends SUAbstractBundleTreeNode<SUConnectionBean> {

    private final SUConnectionBean connection = dataBean;
    private final DatabaseDAOInter dao = new DatabaseDAOImpl();

    public SUConnectionBundleTreeNode(SUServerTree tree, SUConnectionBean connection) {
        super(tree, connection);

    }

    public void connect() {
//        if (connection.getDatabases() == null) {
        LogUtil.log("connection connect");
        connection.setDatabases(dao.getAllDatabases(connection));
        getTree().setCurrentConnectionNode(this);
        addDatabases(connection.getDatabases());
        Main.instance().refreshNewQuery();

//        }
    }

    public void addDatabases(List<SUDatabaseBean> databases) {
        SUArrayList<SUDatabaseBundleTreeNode> dbNodes = new SUArrayList<>();
        for (SUDatabaseBean db : databases) {
            dbNodes.add(new SUDatabaseBundleTreeNode(getTree(), db));
        }
        super.addChildren(dbNodes);
    }

    public SUArrayList<SUDatabaseBean> getAllDatabaseBeans() {
        SUArrayList<SUDatabaseBean> list = new SUArrayList<>();
        List<SUDatabaseBundleTreeNode> l = getChildren(SUDatabaseBundleTreeNode.class);
        for (int i = 0; i < l.size(); i++) {
            list.add(l.get(i).getDatabase());
        }
        return list;
    }

    public SUServerTree getTree() {
        return (SUServerTree) tree;
    }

    public void reset() {
        this.connection.reset();
    }

    @Override
    public void onClick() {
    }

    @Override
    public JPopupMenu getPopup() {
        return new UiPopupConnection();
    }

    @Override
    public String getIcon() {
        return "connection.png";
    }

    @Override
    public String toString() {
        return connection.getName();
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
        connect();
    }

}
