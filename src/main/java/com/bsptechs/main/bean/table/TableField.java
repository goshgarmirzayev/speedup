/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.table;

import com.bsptechs.main.bean.table.dataTypePanel.DataTypePanel;
import com.bsptechs.main.bean.table.dataTypePanel.NumericFamilyPanel;
import lombok.Data;

/**
 * @author Goshgar
 */
@Data
public class TableField {

    private String name;
    private DataType dataType;
    private boolean notNull = false;
    private boolean isPrimaryKey = false;
    private int length;
    private int decimal;
    private String comment;

    public TableField(String name, DataType dataType, boolean notNull, boolean isPrimaryKey, int length, int decimal, String comment) {
        this.name = name;
        this.dataType = dataType;
        this.notNull = notNull;
        this.isPrimaryKey = isPrimaryKey;
        this.length = length;
        this.decimal = decimal;
        this.comment = comment;
    }

    public TableField() {

    }

    public String getQuery() {
        StringBuilder query = new StringBuilder();
        TableField field = this;
        if (field.getName() != null) {
            query.append("`")
                    .append(field.getName())
                    .append("` ");
        }
        if (field.getDataType() != null) {
            query.
                    append(field.getDataType());

            if (field.getDataType().isHaveLength() && !field.getDataType().isDecimal()) {
                query.
                        append("(").
                        append(field.getLength()).
                        append(")");
            }
            if (field.getDataType().isDecimal()) {
                query.append("(")
                        .append(field.getLength())
                        .append(",").append(field.getDecimal())
                        .append(")");
            }

        }

        String panelQuery = analyzePanel();
        String queryPanel;
        if (field.isNotNull()) {
            queryPanel = panelQuery.replace("$NOT_NULL", " NOT NULL ");

        } else {
            queryPanel = panelQuery.replace("$NOT_NULL", " NULL ");

        }
        query.append(queryPanel);
        return query.toString();
    }

    public String analyzePanel() {
        if (this.getDataType() == null || this.getDataType().getPanel() == null) {
            return "";
        }
        DataTypePanel panel = this.getDataType().getPanel();
        String result = panel.getQuery();
        return result;
    }
}
