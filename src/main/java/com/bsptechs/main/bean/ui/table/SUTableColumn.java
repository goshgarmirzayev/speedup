/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.table;

import com.bsptechs.main.bean.ui.tree.database.bean.SUTableBean;
import lombok.Data;

/**
 *
 * @author sarkhanrasullu
 */
@Data
public class SUTableColumn {

    private SUTableBean table;
    private String name;
//    private boolean primaryKey;
    private SUTableColumnType type;
    private SUTableColumn referencedColumn;
    private boolean autoIncrement;

    public SUTableColumn(SUTableBean table, String name,boolean autoIncrement, SUTableColumnType type) {
        this.table = table;
        this.name = name;
        this.type = type;
        this.autoIncrement = autoIncrement;
    }

    @Override
    public String toString() {
        return name;
    }

}
