package com.bsptechs.main.bean.table;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import com.bsptechs.main.bean.SUArrayList;
import lombok.Data;

/**
 *
 * @author Goshgar
 */
@Data
public class Table {

    private String name;
    private SUArrayList<TableField> fields;
    private SUArrayList<ForeignKey> foreignKeys;
    private SUArrayList<TableIndex> indexes;
    private String comment;

    public Table() {
    }

    public Table(String name, SUArrayList<TableField> fields, SUArrayList<ForeignKey> foreignKeys, SUArrayList<TableIndex> indexes, String comment) {
        this.name = name;
        this.fields = fields;
        this.foreignKeys = foreignKeys;
        this.indexes = indexes;
        this.comment = comment;
    }

}
