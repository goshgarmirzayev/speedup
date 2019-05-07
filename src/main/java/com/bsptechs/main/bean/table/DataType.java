/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.table;

import com.bsptechs.main.bean.table.dataTypePanel.DataTypePanel;
import lombok.Data;

/**
 *
 * @author Goshgar
 */
@Data
public class DataType {

    private String name;
    private boolean haveLength;
    private boolean isDecimal;
    //private boolean haveDefaultValue;
    private String panelType;
    private DataTypePanel panel;

    public DataType(String name, boolean haveLength, boolean isDecimal, String panelType, DataTypePanel panel) {
        this.name = name;
        this.haveLength = haveLength;
        this.isDecimal = isDecimal;
        
        this.panelType = panelType;
        this.panel = panel;
    }

    public DataType() {
    }

    @Override
    public String toString() {
        return this.name;
    }

}
