package com.bsptechs.main.bean.ui.table;

import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.SUQueryBean;
import com.bsptechs.main.bean.SUQueryResult;
import com.bsptechs.main.bean.ui.tree.database.bean.SUTableBean;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.util.LogUtil;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author sarkhanrasullu
 */
class SUTableCellEditor extends AbstractCellEditor implements TableCellEditor {

    private DatabaseDAOImpl dao = new DatabaseDAOImpl();

    private JComponent component;
    private SUTableCell cell;
    private SUTable table;
    private int rowIndex, colIndex;
    private SUTableListener<SUTableCell> onChange;

    public void setOnChange(SUTableListener<SUTableCell> listener) {
        this.onChange = listener;
    }

    @Override
    public Component getTableCellEditorComponent(
            JTable table,
            Object value,
            boolean isSelected,
            int rowIndex,
            int colIndex
    ) {
        this.table = (SUTable) table;
        this.cell = (SUTableCell) value;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;

        SUTableColumn column = cell.getColumn();
        SUTableBean tableBean = column.getTable();
        if (column.getReferencedColumn() != null) {

            SUQueryResult rs = dao.runQuery(new SUQueryBean(
                    tableBean.getDatabase().getConnection(), tableBean.getDatabase(),
                    "select * from " + column.getReferencedColumn().getTable().getName()));

            SUArrayList<SUTableRow> rows = rs.getRows();
            Vector<SUTableCell> cells = new Vector<>();
            for (SUTableRow row : rows) {
                SUTableCell cell = row.getByColumnName(column.getReferencedColumn().getName());
                cells.add(cell);
            }
            JComboBox cb = new JComboBox<SUTableCell>(cells);
            cb.setSelectedItem(cell);
            LogUtil.log("cell value=" + cell.getValue());
            component = cb;
            cb.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    cell.setValue(getValue());
                    onChange.action(cell);
                    if (cell.isEdited()) {
                        cb.setBackground(new Color(255, 242, 251));
                    } else {
                        cb.setBackground(Color.white);
                    }
                }
            });
        } else {

            JTextField textField = new JTextField();
            textField.setText(cell.getValue() == null ? "" : cell.getValue());

            textField.getDocument().addDocumentListener(new DocumentListener() {
                public void changedUpdate(DocumentEvent e) {
                    LogUtil.log("text updated");
                    warn();
                }

                public void removeUpdate(DocumentEvent e) {
                    LogUtil.log("text removed");
                    warn();
                }

                public void insertUpdate(DocumentEvent e) {
                    LogUtil.log("text inserted");
                    warn();
                }
                private void warn(){
                    cell.setValue(getValue());
                    onChange.action(cell);
                    if (cell.isEdited()) {
                        textField.setBackground(new Color(255, 242, 251));
                    } else {
                        textField.setBackground(Color.white);
                    }
                }
            });
            component = textField;
        }

        return component;
    }

    private String getValue() {
        String str = null;
        if (component instanceof JTextField) {
            JTextField txt = (JTextField) component;
            str = txt.getText();

        } else if (component instanceof JComboBox) {
            JComboBox cb = (JComboBox) component;
            str = cb.getModel().getSelectedItem() + "";

        }
        return str;
    }

    @Override
    public Object getCellEditorValue() {

        cell.setValue(getValue());

        int selectedRowIndex = table.getSelectedRow();
//        LogUtil.log("cell editor value=" + cell);
//        LogUtil.log("cell is editing=" + cell.isEditing());
//        if (rowIndex != selectedRowIndex) {
//            LogUtil.log("row changed");
//            SUTableRow row = table.getTableRow(rowIndex);
//            table.saveRow(row);
//        }
//        table.getTableModel().fireTableCellUpdated(rowIndex, colIndex);
        return cell;
    }

}
