/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.table;

import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.SUQueryBean;
import com.bsptechs.main.bean.SUQueryResult;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.dao.inter.DatabaseDAOInter;
import java.awt.Color;
import javax.swing.DefaultListSelectionModel;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import lombok.Data;
import com.bsptechs.main.util.LogUtil;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author sarkhanrasullu
 */
class MyTableColumnModel extends DefaultTableColumnModel {

    private SUTableCellEditor cellEditor;
    

    public MyTableColumnModel(SUTableCellEditor cellEditor) {
        this.cellEditor = cellEditor;
    }

    @Override
    public void addColumn(TableColumn column) {
        column.setCellEditor(cellEditor);
        super.addColumn(column);
    }
}

@Data
public class SUTable extends JTable {

    private SUTableCellEditor cellEditor;
    private SUQueryResult rs;
    private static final DatabaseDAOInter db = new DatabaseDAOImpl();
    private SUTableListener<SUTableRow> onRowAdd;
    private SUTableListener<SUTableCell> onChange;

    public SUTable() {
        super(new SUTableModel());
        setDefaultRenderer(SUTableColumn.class, new SUTableCellRenderer());
        cellEditor = new SUTableCellEditor();
        setColumnModel(new MyTableColumnModel(cellEditor));

        this.setRowHeight(25);
        setShowVerticalLines(true);
        setShowHorizontalLines(true);
        setGridColor(new Color(239, 239, 239));

        this.setCellSelectionEnabled(true);
        DefaultListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        selectionModel.addListSelectionListener(new ListSelectionListener() {
            int prev = 0;
            int i = 0;

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (getRowCount() == 1) {
                    return;
                }
                LogUtil.log("prev=" + prev);
                if (getSelectedRow() < 0) {
                    return;
                }
                i++;

                SUTableRow selectedRow = getTableModel().getTableRows().get(prev);
                prev = getSelectedRow();

                LogUtil.log("Selected: " + selectedRow);
                saveOrInsertRow(selectedRow, false);
//                getTableModel().fireTableDataChanged();
            }

        });
        this.setSelectionModel(selectionModel);

//        KeyStroke down = KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0);
//        String downMapKey = "anActionMapKey";
//        this.getInputMap().put(down, downMapKey);
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println("e.getKeyCode()=" + e.getKeyCode());
                System.out.println("getSelectedRow()=" + getSelectedRow());
                System.out.println("getRowCount() - 1=" + (getRowCount() - 1));
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    if (getSelectedRow() == getRowCount() - 1) {
                        addEmptyRow();
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });

    }

    public SUTableRow addEmptyRow() {
        int col = getSelectedColumn();
        SUTableRow row = getTableModel().addEmptyRow();
        changeSelection(getRowCount() - 1, col, false, false);
        return row;
    }

    private void selectRow(int index) {
        System.out.println("select row=" + index);
        changeSelection(index, index, false, false);
    }

    private void selectColumn(int row, int col) {
        System.out.println("select row=" + row);
        changeSelection(row, col, false, false);
    }

    private void selectLastRow() {
        selectRow(getRowCount() - 1);
    }

    public SUTableModel getTableModel() {
        return (SUTableModel) super.getModel();
    }

    public SUTableRow getSelectedTableRow() {
        int selectedRowIndex = this.getSelectedRow();

        return (SUTableRow) getTableModel().getTableRow(selectedRowIndex);
    }

    public SUTableRow getTableRow(int rowIndex) {
        SUTableModel model = (SUTableModel) this.getModel();

        return (SUTableRow) getTableModel().getTableRow(rowIndex);
    }

    public SUTableCell getSelectedTableCell() {
        SUTableRow row = getSelectedTableRow();
        if (row == null) {
            return null;
        }
        return row.get(getSelectedColumn());
    }

    public SUTableCell getTableCell(int rowIndex, int columnIndex) {
        SUTableRow row = getTableRow(rowIndex);
        if (row == null) {
            return null;
        }
        return row.get(columnIndex);
    }

    public SUArrayList<SUTableRow> getSelectedTableRows() {
        int[] selectedRowIndexes = this.getSelectedRows();
        if (selectedRowIndexes.length <= 0) {
            return null;
        }
        DefaultTableModel model = (DefaultTableModel) this.getModel();
        SUArrayList<SUTableRow> rows = new SUArrayList<>();
        for (int selectedRowIndex : selectedRowIndexes) {
            SUTableRow row = (SUTableRow) model.getDataVector().get(selectedRowIndex);
            rows.add(row);
        }
        return rows;
    }

    public void refreshData(SUQueryResult rs) {
        this.rs = rs;
        SUArrayList<SUTableColumn> columns = rs.getColumns();
        SUArrayList<SUTableRow> rows = rs.getRows();
        SUTableModel model = new SUTableModel(rows, columns);
        setModel(model);
    }

    public void saveOrInsertRow() {
        SUTableRow row = getTableModel().getEditedOrAddedRow();

        saveOrInsertRow(row, true);
    }

    private void saveOrInsertRow(SUTableRow row, boolean skipEditCheck) {
        if (row == null) {
            return;
        }
        int colIndex = getSelectedColumn();
        int rowIndex = getSelectedRow();
        SUQueryBean query = rs.getQuery();
        boolean res = false;
        if (row.isNewRow()) {
            if (row.isEdited() || skipEditCheck) {
                TableCellEditor editor = getCellEditor();
                cellEditor.stopCellEditing();
                long id = db.insertRowByAllCell(query.getConnection(), row);
                SUTableCell cell = row.getAutoIncrementCell();
                if (cell != null) {
                    cell.setValue(id + "");
                }
                res = true;
                getTableModel().fireTableDataChanged();
            } else {
                int col = getSelectedColumn();
                getTableModel().discardChanges();
                changeSelection(getRowCount() - 1, col, false, false);
            }
        } else if (row.isEdited()) {
            TableCellEditor editor = getCellEditor();
            cellEditor.stopCellEditing();
            res = db.saveRow(query.getConnection(), row);
            getTableModel().fireTableDataChanged();
        }
        if (res) {
            row.commitEditing();
//            selectColumn(rowIndex, colIndex);
        }

        LogUtil.log("editing row=" + row);
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        LogUtil.log("Table is cell editable");
        return true;
    }

    public void setOnRowAdd(SUTableListener<SUTableRow> listener) {
        onRowAdd = listener;
        getTableModel().setOnRowAdd(listener);
    }

    public void setOnCellChange(SUTableListener<SUTableCell> listener) {
        this.onChange = listener;
        cellEditor.setOnChange(listener);
        getTableModel().setOnCellChange(listener);
    }
    
    public void discardChanges(){
        cellEditor.cancelCellEditing();
        getTableModel().discardChanges();
    }

}
