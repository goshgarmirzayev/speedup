/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.table;

import lombok.Data;
import com.bsptechs.main.util.LogUtil;
import java.util.Objects;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author sarkhanrasullu
 */
public class SUTableCell {

    private SUTableColumn column;
    private String value;
    private String originalValue;

    private SUTableListener<SUTableCell> onChange = e -> {
    };

    public SUTableCell(SUTableColumn column, String value) {
        this.column = column;
        this.value = value;
        this.originalValue = value;
    }

    public void setOnChange(SUTableListener<SUTableCell> listener) {
        this.onChange = listener;
    }

    public boolean isEdited() {
        return !StringUtils.equals(value, originalValue);
    }

    public void setValue(String value) {
        if (this.value == null && StringUtils.isEmpty(value)) {
            return;
        }

        LogUtil.log("old value=" + originalValue);
        LogUtil.log("new value=" + value);

        this.value = value;
        this.onChange.action(this);
    }

    public SUTableColumn getColumn() {
        return column;
    }

    public void setColumn(SUTableColumn column) {
        this.column = column;
    }

    public String getOriginalValue() {
        return originalValue;
    }

    public void setOriginalValue(String originalValue) {
        this.originalValue = originalValue;
        this.onChange.action(this);
        System.out.println("originalvalue=" + this.originalValue);
        System.out.println("originalvalue=" + this.value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SUTableCell)) {
            return false;
        }

        final SUTableCell other = (SUTableCell) obj;
        if (!Objects.equals(this.value, other.value)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return value == null ? "(Null)" : value;
    }

}
