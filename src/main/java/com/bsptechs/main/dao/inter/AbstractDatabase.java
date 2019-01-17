package com.bsptechs.main.dao.inter;

import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.ui.table.SUTableColumn;
import com.bsptechs.main.bean.ui.table.SUTableColumnType;
import com.bsptechs.main.bean.server.SUConnectionBean;
import com.bsptechs.main.bean.server.SUDatabaseBean;
import com.bsptechs.main.bean.server.SUTableBean;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import static javax.swing.UIManager.get;
import lombok.SneakyThrows;
import com.bsptechs.main.util.LogUtil;

/**
 *
 * @author sarkhanrasullu
 */
public abstract class AbstractDatabase {

    public Connection connect(SUConnectionBean connection) throws ClassNotFoundException, SQLException {
        if (connection.getParentConnection() != null) {
            LogUtil.log(connection.getName() + " is using its own connection which created before");
            return connection.getParentConnection();
        }

        Class.forName("com.mysql.jdbc.Driver");

        String url = "jdbc:mysql://" + connection.getIpAdr() + ":" + connection.getPort() + "/";
        String username = connection.getUserName();
        String password = connection.getPassword();
        Connection c = DriverManager.getConnection(url, username, password);
        connection.setParentConnection(c);

        return c;
    }

//    @SneakyThrows
//    private SUArrayList<SUTableColumn> getAllPrimaryKey(SUTableBean table) {
//        Connection c = table.getDatabase().getConnection().getParentConnection();
//        DatabaseMetaData databaseMetaData = c.getMetaData();
//        ResultSet rs = databaseMetaData.getPrimaryKeys(table.getDatabase().getName(), null, table.getName());
//        SUArrayList<SUTableColumn> columns = new SUArrayList<>();
//        while (rs.next()) {
//            columns.add(new SUTableColumn(table, rs.getString(4), true, null));
//        }
//        return columns;
//    }
    public SUTableBean getTable(SUConnectionBean connection, ResultSet rs, int columnIndex) throws Exception {
        SUDatabaseBean databaseBean = getDatabase(connection, rs, columnIndex);
        ResultSetMetaData metadata = rs.getMetaData();
        String name = metadata.getTableName(columnIndex);
        return new SUTableBean(name, databaseBean);
    }

    protected SUDatabaseBean getDatabase(SUConnectionBean connection, ResultSet rs, int columnIndex) throws Exception {
        ResultSetMetaData metadata = rs.getMetaData();
        String name = metadata.getCatalogName(columnIndex);
        return new SUDatabaseBean(name, connection);
    }

    @SneakyThrows
    private SUTableColumnType getColumnType(ResultSet rs, SUConnectionBean connection, int columnIndex) {
        ResultSetMetaData metadata = rs.getMetaData();
        int columnTypeId = metadata.getColumnType(columnIndex);
        String columnTypeName = metadata.getColumnTypeName(columnIndex);
        return new SUTableColumnType(columnTypeId, columnTypeName);
    }

    @SneakyThrows
    public SUArrayList<SUTableColumn> getColumns(ResultSet rs, SUConnectionBean connection) throws SQLException {
        ResultSetMetaData metadata = rs.getMetaData();
        int cnt = metadata.getColumnCount();
        SUArrayList<SUTableColumn> columns = new SUArrayList<>();

        for (int i = 0; i < cnt; i++) {
            int columnIndex = i + 1;
            String name = metadata.getColumnLabel(columnIndex);
            String label = metadata.getColumnLabel(columnIndex);
            boolean isAutoIncrement = metadata.isAutoIncrement(columnIndex);
//            LogUtil.log("label="+label);
            SUTableBean tableBean = getTable(connection, rs, columnIndex);
            SUTableColumn column = new SUTableColumn(tableBean, name, isAutoIncrement, getColumnType(rs, connection, columnIndex));
            columns.add(column);
        }
        fillReferencedColumns(columns, connection);
//        fillPrimaryKeys(columns, connection);
        return columns;
    }

    private SUTableBean getTable(SUArrayList<SUTableColumn> columns) {
        if (columns == null || columns.size() == 0) {
            return null;
        }
        SUTableBean res = columns.get(0).getTable();
        for (SUTableColumn c : columns) {
            if (!res.getName().equals(c.getTable().getName())) {
                return null;
            }
        }
        return res;
    }

//    private void fillPrimaryKeys(SUArrayList<SUTableColumn> columns, SUConnectionBean connection) {
//        SUTableBean table = getTable(columns);
//        if (table == null) {
//            return;
//        }
//        SUArrayList<SUTableColumn> pkColumns = getAllPrimaryKey(table);
//        for (SUTableColumn column : columns) {
//            column.setPrimaryKey(pkColumns.getByName(column.getName()) != null);
//            if (column.isPrimaryKey()) {
//                LogUtil.log("primary key=" + column);
//            }
//        }
//    }
    @SneakyThrows
    private void fillReferencedColumns(SUArrayList<SUTableColumn> columns, SUConnectionBean connection) {
        SUTableBean table = getTable(columns);
        if (table == null) {
            LogUtil.log("table is not unique");
            return;
        }
        Connection conn = connect(connection);

        String sqlQuery = "select "
                + "     table_name, column_name,referenced_table_name, referenced_column_name "
                + " from "
                + "     information_schema.KEY_COLUMN_USAGE "
                + " where table_name = ? and referenced_table_name is not null";

        PreparedStatement stmt = conn.prepareStatement(sqlQuery);
        LogUtil.log("table.getName()=" + table.getName());
        stmt.setString(1, table.getName());

        ResultSet rs = stmt.executeQuery();
        ResultSetMetaData metadata = rs.getMetaData();
        int i=1;
        while (rs.next()) {
            String tableName = rs.getString("table_name");
            String columnName = rs.getString("column_name");
            SUTableColumn ownerColumn = columns.getByName(columnName);

            String refTableName = rs.getString("referenced_table_name");
            String refColumnName = rs.getString("referenced_column_name");
            
            boolean isAutoIncrement = metadata.isAutoIncrement(i);
            SUTableColumn refColumnBean = new SUTableColumn(new SUTableBean(refTableName, null), refColumnName, isAutoIncrement, getColumnType(rs, connection, 4));

            ownerColumn.setReferencedColumn(refColumnBean);
            LogUtil.log("owner column=" + ownerColumn);
            LogUtil.log("referenced column=" + refColumnBean);
            LogUtil.log("-------");
        }

    }

}
