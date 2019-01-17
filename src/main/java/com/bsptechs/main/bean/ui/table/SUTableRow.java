/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.table;

import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.server.SUTableBean;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import com.bsptechs.main.util.LogUtil;

/**
 *
 * @author sarkhanrasullu
 */
@Data
public class SUTableRow extends SUArrayList<SUTableCell> {

    private SUTableBean table;
    private boolean newRow;

    public SUTableRow() {
        LogUtil.log("SUTableRow constr");
    }

    public SUTableCell getAutoIncrementCell() {
        if (isEmpty()) {
            return null;
        }

        for (SUTableCell cell : this) {
            if (cell.getColumn().isAutoIncrement()) {
                return cell;
            }
        }
        return null;
    }

    public SUTableCell getByColumnName(String columnName) {
        for (SUTableCell cell : this) {
            if (cell.getColumn().getName().equals(columnName)) {
                return cell;
            }
        }
        return null;
    }

    @Override
    public boolean add(SUTableCell cell) {
        table = cell.getColumn().getTable();
        return super.add(cell);
    }

    public boolean isEdited() {
        for (SUTableCell cell : this) {
            if (cell.isEdited()) {
                return true;
            }
        }
        return false;
    }

    public SUArrayList<SUTableCell> getAllEditingCell() {
        SUArrayList<SUTableCell> res = new SUArrayList<>();
        for (SUTableCell cell : this) {
            if (cell.isEdited()) {
                res.add(cell);
            }
        }
        return res;
    }

    public void discardChanges() {
        forEach(s -> {
            s.setValue(s.getOriginalValue());
        });
    }

//    public void discardEditing() {
//        discardChanges();
//        setEmptyRow(false);
//    }
    public void commitEditing() {
        forEach(s -> {
            s.setOriginalValue(s.getValue());
        });
        setNewRow(false);
    }

    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < size(); i++) {
            str += get(i).getValue() + " ";
        }
        return str;
    }
}
