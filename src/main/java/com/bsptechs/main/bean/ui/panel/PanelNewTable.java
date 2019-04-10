/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.panel;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.table.DataType;
import com.bsptechs.main.bean.table.ForeignKey;
import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.server.SUConnectionBean;
import com.bsptechs.main.bean.table.Table;
import com.bsptechs.main.bean.table.TableField;
import com.bsptechs.main.bean.table.TableIndex;
import com.bsptechs.main.bean.server.SUDatabaseBean;
import com.bsptechs.main.bean.table.dataTypePanel.BlobFamilyPanel;
import com.bsptechs.main.bean.table.dataTypePanel.CharFamilyPanel;
import com.bsptechs.main.bean.table.dataTypePanel.DataTypePanel;
import com.bsptechs.main.bean.table.dataTypePanel.NumericFamilyPanel;
import com.bsptechs.main.bean.table.dataTypePanel.SetFamilyPanel;
import com.bsptechs.main.bean.table.dataTypePanel.WoutAIPanel;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import com.bsptechs.main.util.LogUtil;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Goshgar
 */
public class PanelNewTable extends javax.swing.JPanel {

    DefaultTableModel dm;
    DatabaseDAOImpl db = new DatabaseDAOImpl();
    String generatedSql;
    SUDatabaseBean currentDatabase = Main.instance().getConnectionTree().getCurrentDatabaseNode().getDatabase();
    SUConnectionBean currentConnection = Main.instance().getConnectionTree().getCurrentConnectionNode().getConnection();

    public PanelNewTable() {
        initComponents();

    }

    private String generateQuery() {
        txtQuery.setEditable(false);
        StringBuilder query = new StringBuilder(
                "CREATE TABLE `" + currentDatabase.getName() + "`.`Untitled` (");
        StringBuilder primaryKeys = new StringBuilder("PRIMARY KEY(");
        StringBuilder indexes = new StringBuilder("Index(");
        SUArrayList<TableField> fields = getTableFields();
        int primaryKeyCount = 0;
//        int fieldCount = 0;
        boolean havePrimaryKeys = false;
        for (TableField field : fields) {
            havePrimaryKeys = field.isPrimaryKey();

            query.append(field.getQuery());

            if (fields.size() > 1 || havePrimaryKeys) {
                query.append(",");
            }
            //NULL and NOTNULL end
            if (field.isPrimaryKey()) {
                primaryKeyCount++;
                if (primaryKeyCount > 1) {
                    primaryKeys.append(",`").append(field.getName()).append("`");
                } else {
                    primaryKeys.append("`").append(field.getName()).append("`");
                }
            }

        }
        if (havePrimaryKeys) {
            query.append(primaryKeys).append(")");
        }
        query.append(")");
        txtQuery.setText(query.toString());
        return query.toString();
    }

    private SUArrayList<TableField> getTableFields() {
        SUArrayList<TableField> fields = new SUArrayList<>();
        for (int i = 0; i < tblFieldPane.getRowCount(); i++) {
            String name = (String) tblFieldPane.getValueAt(i, 0);
            DataType type = (DataType) tblFieldPane.getValueAt(i, 1);
            int length = (int) tblFieldPane.getValueAt(i, 2);
            int decimal = (int) tblFieldPane.getValueAt(i, 3);
            boolean notNull = (boolean) tblFieldPane.getValueAt(i, 4);
            boolean isPrimaryKey = (boolean) tblFieldPane.getValueAt(i, 5);
            String comment = (String) tblFieldPane.getValueAt(i, 6);
            fields.add(new TableField(name, type, notNull, isPrimaryKey, length, decimal, comment));
        }
        return fields;
    }

    private SUArrayList<ForeignKey> getForeignKeys() {
        return null;
    }

    private SUArrayList<TableIndex> getIndexes() {
        return null;
    }

    private String getTableComment() {
        return txtComment.getText();
    }

    private Table getTable() {
        Table table = new Table("", getTableFields(), getForeignKeys(), getIndexes(), getTableComment());
        return table;
    }

    private String askTableName() {
        return JOptionPane.showInputDialog("Please enter table name: ");
    }

    private List<DataType> dataTypes() {
        List<DataType> list = new ArrayList<>();
        list.add(new DataType("bigint", true, false, "NumericFamily", new NumericFamilyPanel()));
        list.add(new DataType("binary", false, false, "BlobFamily", new BlobFamilyPanel()));
        list.add(new DataType("bit", false, false, "Default", null));
        list.add(new DataType("blob", false, false, "BlobFamily", new BlobFamilyPanel()));
        list.add(new DataType("char", true, false, "CharFamily", new CharFamilyPanel()));
        list.add(new DataType("date", false, false, "Default", null));
        list.add(new DataType("datetime", false, false, "Default", null));
        list.add(new DataType("decimal", true, true, "WoutAI", new WoutAIPanel()));//WIthOut AutoIncrement
        list.add(new DataType("double", true, true, "NumericFamily", new NumericFamilyPanel()));
        list.add(new DataType("enum", false, false, "EnumFamily", null));
        list.add(new DataType("float", true, true, "NumericFamily", new NumericFamilyPanel()));
        list.add(new DataType("geometry", false, false, "Default", null));
        list.add(new DataType("geometrycollection", false, false, "Default", null));
        list.add(new DataType("int", true, false, "NumericFamily", new NumericFamilyPanel()));
        list.add(new DataType("integer", true, false, "NumericFamily", new NumericFamilyPanel()));
        list.add(new DataType("linestring", false, false, "Default", null));
        list.add(new DataType("longblob", false, false, "BlobFamily", new BlobFamilyPanel()));
        list.add(new DataType("longtext", false, false, "CharFamily", new CharFamilyPanel()));
        list.add(new DataType("mediumblob", false, false, "BlobFamily", new BlobFamilyPanel()));
        list.add(new DataType("mediumint", true, false, "NumericFamily", new NumericFamilyPanel()));
        list.add(new DataType("mediumtext", false, false, "CharFamily", new CharFamilyPanel()));
        list.add(new DataType("multilinestring", false, false, "Default", null));
        list.add(new DataType("multipoint", false, false, "Default", null));
        list.add(new DataType("multipolygon", false, false, "Default", null));
        list.add(new DataType("numeric", true, true, "WoutAI", new WoutAIPanel()));
        list.add(new DataType("point", false, false, "Default", null));
        list.add(new DataType("polygon", false, false, "Default", null));
        list.add(new DataType("real", true, true, "NumericFamily", new NumericFamilyPanel()));
        list.add(new DataType("set", false, false, "SetFamily", new SetFamilyPanel()));
        list.add(new DataType("smallint", true, true, "NumericFamily", new NumericFamilyPanel()));
        list.add(new DataType("text", false, false, "CharFamily", new CharFamilyPanel()));
        list.add(new DataType("time", false, false, "Default", null));
        list.add(new DataType("timestamp", false, false, "OnUpdate", null));
        list.add(new DataType("tinyblob", false, false, "BlobFamily", new BlobFamilyPanel()));
        list.add(new DataType("tinyint", true, true, "NumericFamily", new NumericFamilyPanel()));
        list.add(new DataType("tinytext", false, false, "CharFamily", new CharFamilyPanel()));
        list.add(new DataType("varbinary", false, false, "BlobFamily", new BlobFamilyPanel()));
        list.add(new DataType("varchar", true, false, "CharFamily", new CharFamilyPanel()));
        list.add(new DataType("year", false, false, "Default", null));
        return list;
    }

    JComboBox<DataType> dataTypesCombo;

    private void comboboxadditem(List<DataType> list, Integer columnNumber) {
        TableColumn column = tblFieldPane.getColumnModel().getColumn(columnNumber);
        dataTypesCombo = new JComboBox<>();
        for (int i = 0; i < list.size(); i++) {
            dataTypesCombo.addItem(list.get(i));
        }
        column.setCellEditor(new DefaultCellEditor(dataTypesCombo));
        loadDataTypePane();
    }

    private void loadDataTypePane() {
        dataTypesCombo.addItemListener((java.awt.event.ItemEvent evt) -> {
            DataType type = (DataType) dataTypesCombo.getSelectedItem();
            loadPanel(type);
        });
//            if (type != null) {
//                
//                if (type.getPanelType().equalsIgnoreCase("Default")) {
//                    loadDefaultPanel();
//                }
//                if (type.getPanelType().equalsIgnoreCase("NumericFamily")) {
//                    loadNumericPanel();
//                }
//                if (type.getPanelType().equalsIgnoreCase("CharFamily")) {
//                    loadCharFamilyPanel();
//                }
//                if (type.getPanelType().equalsIgnoreCase("BlobFamily")) {
//                    loadBlobFamilyPanel();
//                }
//                if (type.getPanelType().equalsIgnoreCase("OnUpdate")) {
//                    loadTimestamp();
//                }
//                if (type.getPanelType().equalsIgnoreCase("WoutAI")) {
//                    loadWoutFamily();
//                }
//                if (type.getPanelType().equalsIgnoreCase("SetFamily")) {
//                    loadSetFamilyPanel();
//                }
//            }

//        });
    }

    private void loadButtons() {
        switch (tabbedPane.getSelectedIndex()) {
            case 0://Fields
                pnlControlButtons.removeAll();
                JButton addField = new JButton("Add field", null);
                JButton insertField = new JButton("Insert field", null);
                JButton deleteField = new JButton("Delete field", null);
                JButton primaryKey = new JButton("Primary key", null);
                SUArrayList<JButton> btns = new SUArrayList<>();
                btns.add(addField);
                btns.add(insertField);
                btns.add(deleteField);
                btns.add(primaryKey);
                for (int i = 0; i < btns.size(); i++) {
                    pnlControlButtons.add(btns.get(i).getName(), btns.get(i));

                }
                addField.addActionListener((ActionEvent e) -> {
                    dm = (DefaultTableModel) tblFieldPane.getModel();
                    comboboxadditem(dataTypes(), 1);
                    Object[] rowdata = {null, null, 0, 0, false, false, null};
                    addRow(dm, rowdata);
                });
                insertField.addActionListener((ActionEvent e) -> {
                    removeRow(tblFieldPane.getSelectedRow(), tblFieldPane);
                });
                pnlControlButtons.revalidate();
                break;
            case 1://indexes
                pnlControlButtons.removeAll();
                pnlControlButtons.revalidate();
                JButton insert = new JButton("Insert field", null);
                JButton delete = new JButton("Delete field", null);
                pnlControlButtons.add(insert);
                pnlControlButtons.add(delete);
                insert.addActionListener((ActionEvent e) -> {
                    dm = (DefaultTableModel) tblIndex.getModel();
                    comboboxadditem(dataTypes(), 1);
                    comboboxadditem(dataTypes(), 2);
                    Object[] rowdata = {null, null, null, null};
                    addRow(dm, rowdata);
                });
                delete.addActionListener((ActionEvent e) -> {
                    removeRow(tblIndex.getSelectedRow(), tblIndex);
                });
                pnlControlButtons.revalidate();
                System.out.println("indexes1");
                break;
            case 2://ForeignKeys
                pnlControlButtons.removeAll();
                pnlControlButtons.revalidate();
                System.out.println("indexes2");
                break;
            case 3://Triggers
                pnlControlButtons.removeAll();
                pnlControlButtons.revalidate();
                System.out.println("indexes3");
                break;
            case 4://Options
                pnlControlButtons.removeAll();
                System.out.println("indexes4");
                break;
            case 5://Comment
                pnlControlButtons.removeAll();
                System.out.println("indexes5");
                break;
            case 6://SQL preview
                pnlControlButtons.removeAll();
                generateQuery();
                break;
            default:
                break;
        }

    }

    //Load Data Type Panel Begin
    private void loadDefaultPanel() {
        dataTypePanel.removeAll();
        dataTypePanel.revalidate();
    }

    private void loadPanel(DataType type) {
        if (type == null) {
            return;
        }

        dataTypePanel.removeAll();

        DataTypePanel panel = type.getPanel();
        if (panel != null) {
            dataTypePanel.add(panel);
        }
        dataTypePanel.revalidate();
        dataTypePanel.setVisible(true);
    }

    private DataTypePanel loadNumericPanel() {
        dataTypePanel.removeAll();

        NumericFamilyPanel panel = new NumericFamilyPanel();
        dataTypePanel.add(panel);
        dataTypePanel.revalidate();
        dataTypePanel.setVisible(true);

        return panel;
    }

    private void loadBlobFamilyPanel() {

        dataTypePanel.removeAll();
        dataTypePanel.add(new BlobFamilyPanel());

        dataTypePanel.revalidate();
    }

    private void loadCharFamilyPanel() {
        dataTypePanel.removeAll();
        dataTypePanel.setVisible(true);
        dataTypePanel.add(new CharFamilyPanel());
        dataTypePanel.revalidate();
    }

    private void loadTimestamp() {
        JCheckBox onUpdate = new JCheckBox("On Update Current_Timestampt");
        dataTypePanel.add(onUpdate);
        dataTypePanel.revalidate();
    }

    private void loadWoutFamily() {
        dataTypePanel.removeAll();
        dataTypePanel.add(new WoutAIPanel());
        dataTypePanel.revalidate();

    }

    private void loadSetFamilyPanel() {
        dataTypePanel.removeAll();
        dataTypePanel.add(new SetFamilyPanel());
        dataTypePanel.revalidate();
    }
//Load Data Type Panel End

    private void addRow(DefaultTableModel dm, Object rowData[]) {
        dm.addRow(rowData);
    }

    private void removeRow(int selectedRow, JTable tblFieldPane) {
        selectedRow = tblFieldPane.getSelectedRow();
        if (selectedRow != -1) {
            LogUtil.log("selectedROW " + selectedRow);
            dm.removeRow(selectedRow);
        } else {
            int sonuncuCount = tblFieldPane.getModel().getRowCount() - 1;
            LogUtil.log("sonuncuCount " + sonuncuCount);
            dm.removeRow(sonuncuCount);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        tabbedPane = new javax.swing.JTabbedPane();
        pnlFields = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblFieldPane = new javax.swing.JTable();
        pnlIndexes = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblIndex = new javax.swing.JTable();
        pnlForeignKeys = new javax.swing.JPanel();
        pnlTriggers = new javax.swing.JPanel();
        pnlOptions = new javax.swing.JPanel();
        pnlComment = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        txtComment = new javax.swing.JTextArea();
        pnlSQLPreview1 = new javax.swing.JPanel();
        jScrollPane8 = new javax.swing.JScrollPane();
        txtQuery = new javax.swing.JTextArea();
        pnlControlButtons = new javax.swing.JPanel();
        typePanel = new javax.swing.JPanel();
        dataTypePanel = new javax.swing.JPanel();

        addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                formHierarchyChanged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                formMouseClicked(evt);
            }
        });
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setName(""); // NOI18N

        jPanel1.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                jPanel1formHierarchyChanged(evt);
            }
        });
        jPanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel1formMouseClicked(evt);
            }
        });
        jPanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jPanel1formKeyPressed(evt);
            }
        });
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSave.setText("Save");
        btnSave.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnSaveMouseClicked(evt);
            }
        });
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, -1, -1));

        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChanged(evt);
            }
        });

        tblFieldPane.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Type", "Length", "Decimal", "Not null", "Key", "Comment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblFieldPane.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                tblFieldPaneHierarchyChanged(evt);
            }
        });
        jScrollPane5.setViewportView(tblFieldPane);

        javax.swing.GroupLayout pnlFieldsLayout = new javax.swing.GroupLayout(pnlFields);
        pnlFields.setLayout(pnlFieldsLayout);
        pnlFieldsLayout.setHorizontalGroup(
            pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE)
        );
        pnlFieldsLayout.setVerticalGroup(
            pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFieldsLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 283, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Fields", pnlFields);

        tblIndex.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Fields", "Index Type", "Index Method", "Comment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblIndex.addHierarchyListener(new java.awt.event.HierarchyListener() {
            public void hierarchyChanged(java.awt.event.HierarchyEvent evt) {
                tblIndexHierarchyChanged(evt);
            }
        });
        jScrollPane6.setViewportView(tblIndex);

        javax.swing.GroupLayout pnlIndexesLayout = new javax.swing.GroupLayout(pnlIndexes);
        pnlIndexes.setLayout(pnlIndexesLayout);
        pnlIndexesLayout.setHorizontalGroup(
            pnlIndexesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlIndexesLayout.createSequentialGroup()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 872, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        pnlIndexesLayout.setVerticalGroup(
            pnlIndexesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 282, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Indexes", pnlIndexes);

        javax.swing.GroupLayout pnlForeignKeysLayout = new javax.swing.GroupLayout(pnlForeignKeys);
        pnlForeignKeys.setLayout(pnlForeignKeysLayout);
        pnlForeignKeysLayout.setHorizontalGroup(
            pnlForeignKeysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        pnlForeignKeysLayout.setVerticalGroup(
            pnlForeignKeysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Foreign Keys", pnlForeignKeys);

        javax.swing.GroupLayout pnlTriggersLayout = new javax.swing.GroupLayout(pnlTriggers);
        pnlTriggers.setLayout(pnlTriggersLayout);
        pnlTriggersLayout.setHorizontalGroup(
            pnlTriggersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        pnlTriggersLayout.setVerticalGroup(
            pnlTriggersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Triggers", pnlTriggers);

        javax.swing.GroupLayout pnlOptionsLayout = new javax.swing.GroupLayout(pnlOptions);
        pnlOptions.setLayout(pnlOptionsLayout);
        pnlOptionsLayout.setHorizontalGroup(
            pnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 865, Short.MAX_VALUE)
        );
        pnlOptionsLayout.setVerticalGroup(
            pnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 282, Short.MAX_VALUE)
        );

        tabbedPane.addTab("Options", pnlOptions);

        txtComment.setColumns(20);
        txtComment.setRows(5);
        jScrollPane7.setViewportView(txtComment);

        javax.swing.GroupLayout pnlCommentLayout = new javax.swing.GroupLayout(pnlComment);
        pnlComment.setLayout(pnlCommentLayout);
        pnlCommentLayout.setHorizontalGroup(
            pnlCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE)
        );
        pnlCommentLayout.setVerticalGroup(
            pnlCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCommentLayout.createSequentialGroup()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        tabbedPane.addTab("Comment", pnlComment);

        txtQuery.setColumns(20);
        txtQuery.setFont(new java.awt.Font("Courier New", 0, 13)); // NOI18N
        txtQuery.setRows(5);
        jScrollPane8.setViewportView(txtQuery);
        txtQuery.getAccessibleContext().setAccessibleDescription("");

        javax.swing.GroupLayout pnlSQLPreview1Layout = new javax.swing.GroupLayout(pnlSQLPreview1);
        pnlSQLPreview1.setLayout(pnlSQLPreview1Layout);
        pnlSQLPreview1Layout.setHorizontalGroup(
            pnlSQLPreview1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane8, javax.swing.GroupLayout.DEFAULT_SIZE, 865, Short.MAX_VALUE)
        );
        pnlSQLPreview1Layout.setVerticalGroup(
            pnlSQLPreview1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSQLPreview1Layout.createSequentialGroup()
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 281, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        tabbedPane.addTab("SQL Preview", pnlSQLPreview1);

        jPanel1.add(tabbedPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 50, 870, 310));
        jPanel1.add(pnlControlButtons, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 440, 40));

        jSplitPane1.setLeftComponent(jPanel1);

        javax.swing.GroupLayout typePanelLayout = new javax.swing.GroupLayout(typePanel);
        typePanel.setLayout(typePanelLayout);
        typePanelLayout.setHorizontalGroup(
            typePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dataTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 401, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 459, Short.MAX_VALUE))
        );
        typePanelLayout.setVerticalGroup(
            typePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(typePanelLayout.createSequentialGroup()
                .addComponent(dataTypePanel, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 376, Short.MAX_VALUE))
        );

        jSplitPane1.setRightComponent(typePanel);

        add(jSplitPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 910));
    }// </editor-fold>//GEN-END:initComponents

    private void formHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_formHierarchyChanged

    }//GEN-LAST:event_formHierarchyChanged

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed

    }//GEN-LAST:event_formKeyPressed

    private void formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseClicked

    }//GEN-LAST:event_formMouseClicked

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        String mainQuery = generateQuery().replace("Untitled", askTableName());
        db.createTable(currentDatabase, mainQuery);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblFieldPaneHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tblFieldPaneHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tblFieldPaneHierarchyChanged

    private void tabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChanged
        loadButtons();
    }//GEN-LAST:event_tabbedPaneStateChanged

    private void jPanel1formHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_jPanel1formHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1formHierarchyChanged

    private void jPanel1formMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1formMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1formMouseClicked

    private void jPanel1formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel1formKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jPanel1formKeyPressed

    private void tblIndexHierarchyChanged(java.awt.event.HierarchyEvent evt) {//GEN-FIRST:event_tblIndexHierarchyChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_tblIndexHierarchyChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSave;
    private javax.swing.JPanel dataTypePanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JPanel pnlComment;
    private javax.swing.JPanel pnlControlButtons;
    private javax.swing.JPanel pnlFields;
    private javax.swing.JPanel pnlForeignKeys;
    private javax.swing.JPanel pnlIndexes;
    private javax.swing.JPanel pnlOptions;
    private javax.swing.JPanel pnlSQLPreview1;
    private javax.swing.JPanel pnlTriggers;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JTable tblFieldPane;
    private javax.swing.JTable tblIndex;
    private javax.swing.JTextArea txtComment;
    private javax.swing.JTextArea txtQuery;
    private javax.swing.JPanel typePanel;
    // End of variables declaration//GEN-END:variables
}
