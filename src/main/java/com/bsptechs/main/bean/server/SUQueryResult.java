/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.server;

import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.ui.table.SUTableColumn;
import com.bsptechs.main.bean.ui.table.SUTableRow;
import lombok.Data;

/**
 *
 * @author sarkhanrasullu
 */
@Data
public class SUQueryResult {

    private SUArrayList<SUTableColumn> columns;
    private SUArrayList<SUTableRow> rows;
    private SUQueryBean query;
    
    public SUQueryResult(SUQueryBean query, SUArrayList<SUTableColumn> columns, SUArrayList<SUTableRow> rows) {
        this.columns = columns;
        this.rows = rows;
        this.query = query;
    }

}
