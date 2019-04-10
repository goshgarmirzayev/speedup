/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.table;

import com.bsptechs.main.bean.SUArrayList;
import lombok.Data;

/**
 *
 * @author Goshgar
 */
@Data
public class ForeignKey {

    private String name;
    private SUArrayList<TableField> fields;
    private String referenceSchema;
    private String referenceTable;
    private String referenceFields;
    private String onUpdate;
    private String onDelete;

    public ForeignKey(String name, SUArrayList<TableField> fields, String referenceSchema, String referenceTable, String referenceFields, String onUpdate, String onDelete) {
        this.name = name;
        this.fields = fields;
        this.referenceSchema = referenceSchema;
        this.referenceTable = referenceTable;
        this.referenceFields = referenceFields;
        this.onUpdate = onUpdate;
        this.onDelete = onDelete;
    }

}
