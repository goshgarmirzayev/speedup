/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.table;

import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.table.TableField;
import lombok.Data;

/**
 *
 * @author Goshgar
 */
@Data
public class TableIndex {

    private String name;
    private SUArrayList<TableField> fileds;
    private String indexType;
    private String method;
    private String comment;

    public TableIndex(String name, SUArrayList<TableField> fileds, String indexType, String method, String comment) {
        this.name = name;
        this.fileds = fileds;
        this.indexType = indexType;
        this.method = method;
        this.comment = comment;
    }

    public TableIndex() {
    }

}
