package com.bsptechs.main.dao.impl;

import com.bsptechs.main.Config;
import com.bsptechs.main.Main;
import com.bsptechs.main.bean.AutoComplete;
import com.bsptechs.main.bean.Charset;
import com.bsptechs.main.bean.Collation;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.server.SUQueryBean;
import com.bsptechs.main.bean.server.SUQueryResult;
import com.bsptechs.main.bean.server.SUConnectionBean;
import com.bsptechs.main.bean.server.SUDatabaseBean;
import com.bsptechs.main.bean.server.SUTableBean;
import com.bsptechs.main.bean.ui.table.SUTableCell;
import com.bsptechs.main.bean.ui.table.SUTableColumn;
import com.bsptechs.main.bean.ui.table.SUTableRow;
import com.bsptechs.main.dao.inter.AbstractDatabase;
import com.bsptechs.main.dao.inter.DatabaseDAOInter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import com.bsptechs.main.util.LogUtil;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Vector;

/**
 *
 * @author Penthos
 */
public class DatabaseDAOImpl extends AbstractDatabase implements DatabaseDAOInter {

    @SneakyThrows
    @Override
    public SUArrayList<SUDatabaseBean> getAllDatabases(SUConnectionBean connection) {
        SUArrayList<SUDatabaseBean> databasesList = new SUArrayList<>();

        Connection conn = connect(connection);
        Statement stmt = conn.createStatement();
        ResultSet resultset = stmt.executeQuery("SHOW DATABASES;");

        if (stmt.execute("SHOW DATABASES;")) {
            resultset = stmt.getResultSet();
        }

        while (resultset.next()) {
            String result = resultset.getString("Database");
            databasesList.add(new SUDatabaseBean(result, connection));
        }
        return databasesList;
    }

    @Override
    @SneakyThrows
    public SUArrayList<SUTableBean> getAllTables(SUDatabaseBean database) {
        SUArrayList<SUTableBean> list = new SUArrayList<>();
        Connection conn = connect(database.getConnection());
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM information_schema.tables where table_schema = ?");
        stmt.setString(1, database.getName());
        ResultSet resultset = stmt.executeQuery();
        while (resultset.next()) {
            String result = resultset.getString("table_name");
            list.add(new SUTableBean(result, database));
        }
        return list;
    }

    @Override
    @SneakyThrows
    public boolean renameTable(SUTableBean table, String newTblName) {
        Connection conn = connect(table.getDatabase().getConnection());
        PreparedStatement stmt = conn.prepareStatement(
                "RENAME "
                + " TABLE `" + table.getDatabase().getName() + "`.`" + table.getName() + "` "
                + " TO `" + table.getDatabase().getName() + "`.`" + newTblName + "`");
        stmt.executeUpdate();
        table.setName(newTblName);
        return true;
    }

    @SneakyThrows
    @Override
    public SUQueryResult runQuery(SUQueryBean queryBean) {
        SUConnectionBean connectionBean = queryBean.getConnection();
        SUDatabaseBean database = queryBean.getDatabase();
        String queryStr = queryBean.getQuery();

        Connection conn = connect(connectionBean);

        Statement stmt = conn.createStatement();
        if (database != null && StringUtils.isNoneEmpty(database.getName())) {
            String setDatabase = "USE " + database.getName() + ";";
            stmt.executeQuery(setDatabase);
        }

        ResultSet rs = stmt.executeQuery(queryStr);

        SUDatabaseBean db = getDatabase(connectionBean, rs, 1);

        SUArrayList<SUTableColumn> columns = getColumns(rs, connectionBean);

        SUArrayList<SUTableRow> rows = new SUArrayList<>();

        while (rs.next()) {
            SUTableRow row = new SUTableRow();

            for (int i = 0; i < columns.size(); i++) {
                SUTableColumn column = columns.get(i);
                Object value = rs.getObject(column.getName());
                String str = value == null ? null : value + "";
                row.add(new SUTableCell(column, str));
            }

            rows.add(row);
        }
        SUQueryResult result = new SUQueryResult(queryBean, columns, rows);
        return result;
    }

    @SneakyThrows
    @Override
    public boolean emptyTable(SUDatabaseBean DBName, String tblName) {
        Connection conn = connect(DBName.getConnection());
        PreparedStatement stmt = conn.prepareStatement("delete  from " + DBName + "." + tblName);

        stmt.executeUpdate();

        return true;
    }

    @SneakyThrows
    @Override
    public boolean truncateTable(SUDatabaseBean DBName, String tblName) {
        Connection conn = connect(DBName.getConnection());
        PreparedStatement stmt = conn.prepareStatement("TRUNCATE TABLE " + DBName + "." + tblName);
        stmt.executeUpdate();
        return true;
    }

    @SneakyThrows
    @Override
    public boolean dublicateTable(SUDatabaseBean DBName, String tbLName) {
        Connection conn = connect(DBName.getConnection());
        String newTbLName = tbLName.concat("_copy");
        PreparedStatement stmt = conn.prepareStatement("CREATE TABLE " + DBName + "." + newTbLName + " LIKE " + DBName + "." + tbLName);
        PreparedStatement stmt1 = conn.prepareStatement("INSERT " + DBName + "." + newTbLName + "SELECT * FROM " + DBName + "." + tbLName);

        stmt.executeUpdate();
        return true;
    }

    @SneakyThrows
    @Override
    public boolean deleteTable(SUTableBean table) {
        Connection conn = connect(table.getDatabase().getConnection());
        Statement stmt = conn.prepareStatement("DROP TABLE " + table.getDatabase() + "." + table);
        return true;
    }

    @SneakyThrows
    @Override
    public boolean pasteTable(String information, SUDatabaseBean DBName, String TblName) {

        Connection conn = connect(DBName.getConnection());
        PreparedStatement stmt = conn.prepareStatement("CREATE TABLE " + DBName + "." + TblName + " LIKE " + information);
        PreparedStatement stmt1 = conn.prepareStatement("INSERT " + DBName + "." + TblName + "SELECT * FROM " + information);
        stmt.executeUpdate();

        return true;

    }

    @SneakyThrows
    public boolean dataTransfer(SUDatabaseBean DBNameWeHave, String tbLNameWeHave, SUDatabaseBean DBNameWeWant, String tbLNameWeWant) {
        Connection connWeHave = connect(DBNameWeHave.getConnection());
        Connection connWeWant = connect(DBNameWeWant.getConnection());
        String newTbLName1 = tbLNameWeHave;
        String newTbLName = tbLNameWeWant;
        PreparedStatement stmtWeHave = connWeHave.prepareStatement("SELECT FROM " + DBNameWeHave);
        PreparedStatement stmtWeWant = connWeWant.prepareStatement("CREATE DATABASE " + DBNameWeHave);
//	    ResultSet rs = stmtWeWant.executeQuery();
//	    ResultSetMetaData rsmd = rs.getMetaData();
//	    int columnCount = rsmd.getColumnCount();
//	    String tableName = null;
//	    StringBuilder sb = new StringBuilder(1024);
//	    if (columnCount > 0) {
//		sb.append("Create table ").append(rsmd.getTableName(1)).append(" ( ");
//	    }
//	    for (int i = 1; i <= columnCount; i++) {
//		if (i > 1) {
//		    sb.append(", ");
//		}
//		String columnName = rsmd.getColumnLabel(i);
//		String columnType = rsmd.getColumnTypeName(i);
//
//		sb.append(columnName).append(" ").append(columnType);
//
//		int precision = rsmd.getPrecision(i);
//		if (precision != 0) {
//		    sb.append("( ").append(precision).append(" )");
//		}
//	    } // for columns
//	    sb.append(" ) ");
//	    LogUtil.log(sb.toString());
//	    stmt.executeUpdate();
        return true;

    }

    @SneakyThrows
    @Override
    public boolean createDb(SUConnectionBean connection, String name, String charset, String collate) {

        Connection conn = connect(connection);
        com.mysql.jdbc.PreparedStatement stmt = (com.mysql.jdbc.PreparedStatement) conn.createStatement();
        stmt.execute("CREATE SCHEMA `" + name + "` DEFAULT CHARACTER SET " + charset + " COLLATE " + collate + ";");

        return true;
    }

    @SneakyThrows
    @Override
    public List<Charset> getAllCharsets(SUConnectionBean connection) {
        List<Charset> charset = new ArrayList<>();
        Connection conn = connect(connection);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(" SHOW CHARACTER SET;");
        while (rs.next()) {
            String name = rs.getString("Charset");
            charset.add(new Charset(name));
        }
        return charset;
    }

    @Override
    @SneakyThrows
    public List<Collation> getAllCollations(SUConnectionBean connection, Charset charset) {
        if (charset != null && charset.getCollations() != null) {
            return charset.getCollations();
        }
        List<Collation> collations = new ArrayList<>();
        Connection conn = connect(connection);
        Statement stmt = conn.createStatement();
        stmt.execute(" SHOW COLLATION where CHARSET='" + charset + "'");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            String name = rs.getString("Collation");

            collations.add(new Collation(name));

        }

        charset.setCollations(collations);
        return collations;
    }

    @SneakyThrows
    @Override
    public boolean deleteRows(SUConnectionBean connection, List<SUTableRow> rows) {
        for (SUTableRow row : rows) {
            deleteRow(connection, row);
        }
        return true;
    }

    @SneakyThrows
    @Override
    public boolean deleteRow(SUConnectionBean connection, SUTableRow row) {
        SUTableCell aiCell = row.getAutoIncrementCell();

        if (aiCell == null) {
            deleteRowByAllCell(connection, row);
        } else {
            return deleteRowByPrimaryCell(connection, aiCell);
        }

        return true;
    }

    @SneakyThrows
    public boolean deleteRowByAllCell(SUConnectionBean connection, SUTableRow row) {
        Connection conn = connect(connection);

        Vector<SUTableCell> cells = row;
        String query = "delete "
                + " from " + row.getTable().getDatabase().getName() + "." + row.getTable().getName() + " where ";

        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            query += cell.getColumn().getName() + "=?";
        }
        LogUtil.log("query deleteRowByRow=" + query);
        PreparedStatement stmt = conn.prepareStatement(query);

        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            stmt.setObject(i + 1, cell.getValue());
        }

        stmt.executeUpdate();
        return true;
    }

    @SneakyThrows
    private boolean deleteRowByPrimaryCell(SUConnectionBean connection, SUTableCell cell) {
        Connection conn = connect(connection);

        SUTableBean table = cell.getColumn().getTable();
        String query = "delete "
                + " from " + table.getDatabase().getName() + "." + table.getName()
                + " where " + cell.getColumn().getName() + "=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        LogUtil.log("query deleteRowByCell=" + query);
        stmt.setObject(1, cell.getValue());
        stmt.executeUpdate();

        return true;
    }

    @SneakyThrows
    @Override
    public boolean saveRow(SUConnectionBean connection, SUTableRow row) {
        LogUtil.log("row.getAllEditingCell().size()=" + row.getAllEditingCell().size());
        if (row.getAllEditingCell().size() == 0) {
            return false;
        }

        SUTableCell aiCell = row.getAutoIncrementCell();

        if (aiCell == null) {
            return saveRowByAllCell(connection, row);
        } else {
            return saveRowByPrimaryCell(connection, row, aiCell);
        }
    }

    @SneakyThrows
    @Override
    public long insertRowByAllCell(SUConnectionBean connection, SUTableRow row) {
        Connection conn = connect(connection);

        SUArrayList<SUTableCell> cells = row;
        String query = "insert into "
                + " " + row.getTable().getDatabase().getName() + "." + row.getTable().getName() + " ( ";

        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            if (cell.isEdited()) {
                query += cell.getColumn().getName() + ",";
            }
        }
        query = query.substring(0, query.length() - 1);
        query += ") values ( ";
        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            if (cell.isEdited()) {
                query += "?,";
            }
        }
        query = query.substring(0, query.length() - 1);

        query += ")";

        LogUtil.log("query insertRow=" + query);
        PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        int index = 1;
        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            if (cell.isEdited()) {
                stmt.setObject(index++, cell.getValue());
                cell.setOriginalValue(cell.getValue());
            }
        }

        int affectedRows = stmt.executeUpdate();
        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }
        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            cell.setOriginalValue(cell.getValue());
        }

        ResultSet generatedKeys = stmt.getGeneratedKeys();

        if (generatedKeys != null && generatedKeys.next()) {
            int count = generatedKeys.getMetaData().getColumnCount();
            long val = generatedKeys.getLong(1);
            return val;
        }

        return -1;

    }

    @SneakyThrows
    private boolean saveRowByAllCell(SUConnectionBean connection, SUTableRow row) {
        Connection conn = connect(connection);

        SUArrayList<SUTableCell> cells = row;
        String query = "update "
                + " " + row.getTable().getDatabase().getName() + "." + row.getTable().getName() + " set ";

        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            query += cell.getColumn().getName() + "=?,";
        }
        query = query.substring(0, query.length() - 1);

        query += " where 1=1 ";
        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            query += " and " + cell.getColumn().getName() + "=? ";
        }
        LogUtil.log("query updateRow=" + query);
        PreparedStatement stmt = conn.prepareStatement(query);
        int index = 1;
        for (int j = 0; j < 2; j++) {
            for (int i = 0; i < cells.size(); i++) {
                SUTableCell cell = cells.get(i);
                stmt.setObject(index++, cell.getValue());
            }
        }

        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            cell.setOriginalValue(cell.getValue());
        }

        stmt.executeUpdate();
        return true;
    }

    @SneakyThrows
    private boolean saveRowByPrimaryCell(SUConnectionBean connection, SUTableRow row, SUTableCell primaryCell) {
        Connection conn = connect(connection);

        SUArrayList<SUTableCell> cells = row.getAllEditingCell();
        if (cells.size() == 0) {
            return false;
        }
        String query = "update "
                + " " + row.getTable().getDatabase().getName() + "." + row.getTable().getName() + " set ";

        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);

            query += cell.getColumn().getName() + "=?,";
        }
        query = query.substring(0, query.length() - 1);

        query += " where " + primaryCell.getColumn().getName() + "=" + primaryCell.getValue();

        LogUtil.log("query updateRow=" + query);
        PreparedStatement stmt = conn.prepareStatement(query);
        int index = 1;
        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            stmt.setObject(index++, cell.getValue());
        }

        for (int i = 0; i < cells.size(); i++) {
            SUTableCell cell = cells.get(i);
            cell.setOriginalValue(cell.getValue());
        }

        stmt.executeUpdate();

        return true;
    }

    @SneakyThrows
    @Override
    public boolean createDbGeneral(SUConnectionBean ui, String query) {
        Connection conn = connect(ui);
        Statement stmt = conn.createStatement();
        stmt.execute(query);
        return true;
    }

    @SneakyThrows
    @Override
    public boolean createDbOptions(SUConnectionBean ui, String name, String charset, String collate) {
        Connection conn = connect(ui);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE SCHEMA `" + name + "` DEFAULT CHARACTER SET " + charset + " COLLATE " + collate + ";");
        return true;

    }

    @SneakyThrows
    @Override
    public boolean saveQuery(SUConnectionBean c, SUQueryBean queryBean) {
        SUArrayList<SUConnectionBean> connectionList = Main.instance().getConnectionTree().getConnectionBeans();
        connectionList.remove(c);
        List<SUQueryBean> queries = new ArrayList<>();
        queries.add(queryBean);
        List<SUQueryBean> oldQueries = c.getQueries();
        if (oldQueries != null) {
            for (int i = 0; i < oldQueries.size(); i++) {
                queries.add(oldQueries.get(i));
            }
        }
        c.setQueries(queries);
        connectionList.add(c);
        Config.instance().setConnectionBeans(connectionList);
        Config.instance().saveConfig();
        return true;
    }

    @SneakyThrows
    @Override
    public LinkedHashSet<AutoComplete> getAllKeyWords(SUConnectionBean conn) {
        Connection connection = connect(conn);
        Statement stmt1 = connection.createStatement();
        stmt1.executeQuery("  SELECT TABLE_SCHEMA ,\n"
                + "       TABLE_NAME ,\n"
                + "       COLUMN_NAME ,\n"
                + "       DATA_TYPE \n"
                + "       \n"
                + "FROM   INFORMATION_SCHEMA.COLUMNS");
        ResultSet rs1 = stmt1.getResultSet();
        Statement stmt2 = connection.createStatement();
        LinkedHashSet<AutoComplete> words = new LinkedHashSet<>();
        while (rs1.next()) {
            String databases = rs1.getString("TABLE_SCHEMA");
            String tables = rs1.getString("TABLE_NAME");
            String columns = rs1.getString("COLUMN_NAME");
            String dataType = rs1.getString("DATA_TYPE");
            words.add(new AutoComplete(databases.toLowerCase(), "database", "/icons/database.png"));
            words.add(new AutoComplete(tables.toLowerCase(), "table", "/icons/table.png"));
            words.add(new AutoComplete(columns.toLowerCase(), "columns", null));
            words.add(new AutoComplete(dataType.toLowerCase(), "type", null));
        }
        stmt2.executeQuery("select * from mysql.help_keyword");
        ResultSet rs2 = stmt2.getResultSet();
        rs2 = stmt2.getResultSet();

        while (rs2.next()) {
            String helpKeywords = rs2.getString("name");
            words.add(new AutoComplete(helpKeywords.toLowerCase(), "key word", null));
        }
        return words;
    }

    @SneakyThrows
    @Override
    public boolean createTable(SUDatabaseBean database, String sql) {
        Connection conn = connect(database.getConnection());
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        return true;
    }
}
