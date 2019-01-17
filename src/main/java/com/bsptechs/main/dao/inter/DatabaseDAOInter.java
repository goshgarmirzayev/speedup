package com.bsptechs.main.dao.inter;

import com.bsptechs.main.bean.Charset;
import com.bsptechs.main.bean.Collation;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.SUQueryBean;
import com.bsptechs.main.bean.SUQueryResult;
import com.bsptechs.main.bean.ui.tree.database.bean.SUConnectionBean;
import com.bsptechs.main.bean.ui.tree.database.bean.SUDatabaseBean;
import com.bsptechs.main.bean.ui.tree.database.bean.SUTableBean;
import com.bsptechs.main.bean.ui.table.SUTableRow;
import java.util.List;

/**
 *
 * @author sarkhanrasullu
 */
public interface DatabaseDAOInter {

    SUArrayList<SUDatabaseBean> getAllDatabases(SUConnectionBean connection);

    SUArrayList<SUTableBean> getAllTables(SUDatabaseBean database);

    boolean emptyTable(SUDatabaseBean db, String tblName);

    boolean truncateTable(SUDatabaseBean DBName, String tblName);

    boolean dublicateTable(SUDatabaseBean DBName, String tbLName);

    boolean pasteTable(String information, SUDatabaseBean DBName, String tbLName);

    boolean renameTable(SUTableBean table, String newTblName);

    public SUQueryResult runQuery(SUQueryBean query) throws Exception;

    public boolean createDb(SUConnectionBean ui, String name, String charset, String collate);

    public List<Charset> getAllCharsets(SUConnectionBean connection);

    public List<Collation> getAllCollations(SUConnectionBean connection, Charset charset);

    public boolean deleteRow(SUConnectionBean connection, SUTableRow row);

    public boolean deleteRows(SUConnectionBean connection, List<SUTableRow> rows);

    public boolean saveRow(SUConnectionBean connection, SUTableRow row);

    public boolean createDbGeneral(SUConnectionBean ui, String query);

    public boolean createDbOptions(SUConnectionBean ui, String name, String charset, String collate);

     public long insertRowByAllCell(SUConnectionBean connection, SUTableRow row);
}
