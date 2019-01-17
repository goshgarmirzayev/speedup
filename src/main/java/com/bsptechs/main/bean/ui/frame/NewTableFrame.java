/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.frame;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import com.bsptechs.main.util.LogUtil;

/**
 *
 * @author RafaelAhmedov
 */
public class NewTableFrame extends javax.swing.JFrame {

    DefaultTableModel dm;

    /**
     * Creates new form NewTableFrame
     */
    public NewTableFrame() {
        initComponents();

    }

    private void comboboxadditem() {
        TableColumn column = tblFieldPane.getColumnModel().getColumn(1);
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.addItem("bigint");
        comboBox.addItem("binary");
        comboBox.addItem("bit");
        comboBox.addItem("blob");
        comboBox.addItem("char");
        comboBox.addItem("date");
        comboBox.addItem("datetime");
        comboBox.addItem("decimal");
        comboBox.addItem("double");
        comboBox.addItem("enum");
        comboBox.addItem("float");
        comboBox.addItem("geometry");
        comboBox.addItem("geometrycollection");
        comboBox.addItem("int");
        comboBox.addItem("integer");
        comboBox.addItem("linestring");
        comboBox.addItem("longblob");
        comboBox.addItem("longtext");
        comboBox.addItem("mediumblob");
        comboBox.addItem("mediumint");
        comboBox.addItem("mediumtext");
        comboBox.addItem("multilinestring");
        comboBox.addItem("multipoint");
        comboBox.addItem("multipolygon");
        comboBox.addItem("numeric");
        comboBox.addItem("point");
        comboBox.addItem("polygon");
        comboBox.addItem("real");
        comboBox.addItem("set");
        comboBox.addItem("smallint");
        comboBox.addItem("text");
        comboBox.addItem("time");
        comboBox.addItem("timestamp");
        comboBox.addItem("tinyblob");
        comboBox.addItem("tinyint");
        comboBox.addItem("tinytext");
        comboBox.addItem("varbinary");
        comboBox.addItem("varchar");
        comboBox.addItem("year");
        column.setCellEditor(new DefaultCellEditor(comboBox));

    }

    private void addRow() {
        dm = (DefaultTableModel) tblFieldPane.getModel();
        comboboxadditem();
        Object[] rowdata = {null, null,0, 0, false, null, null};
        dm.addRow(rowdata);
    }

    private void removeRow() {
        int selectedRow = tblFieldPane.getSelectedRow();
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

        pane = new javax.swing.JTabbedPane();
        pnlFields = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFieldPane = new javax.swing.JTable();
        pnlIndexes = new javax.swing.JPanel();
        pnlForeignKeys = new javax.swing.JPanel();
        pnlTriggers = new javax.swing.JPanel();
        pnlOptions = new javax.swing.JPanel();
        pnlComment = new javax.swing.JPanel();
        pnlSQLPreview = new javax.swing.JPanel();
        btnSave = new javax.swing.JButton();
        btnAddField = new javax.swing.JButton();
        btnDeleteField = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tblFieldPane.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Type", "Length", "Decimal", "Not null", "Key", "Comment"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblFieldPane);

        javax.swing.GroupLayout pnlFieldsLayout = new javax.swing.GroupLayout(pnlFields);
        pnlFields.setLayout(pnlFieldsLayout);
        pnlFieldsLayout.setHorizontalGroup(
            pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 584, Short.MAX_VALUE)
        );
        pnlFieldsLayout.setVerticalGroup(
            pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 413, Short.MAX_VALUE)
        );

        pane.addTab("Fields", pnlFields);

        javax.swing.GroupLayout pnlIndexesLayout = new javax.swing.GroupLayout(pnlIndexes);
        pnlIndexes.setLayout(pnlIndexesLayout);
        pnlIndexesLayout.setHorizontalGroup(
            pnlIndexesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );
        pnlIndexesLayout.setVerticalGroup(
            pnlIndexesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        pane.addTab("Indexes", pnlIndexes);

        javax.swing.GroupLayout pnlForeignKeysLayout = new javax.swing.GroupLayout(pnlForeignKeys);
        pnlForeignKeys.setLayout(pnlForeignKeysLayout);
        pnlForeignKeysLayout.setHorizontalGroup(
            pnlForeignKeysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );
        pnlForeignKeysLayout.setVerticalGroup(
            pnlForeignKeysLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        pane.addTab("Foreign Keys", pnlForeignKeys);

        javax.swing.GroupLayout pnlTriggersLayout = new javax.swing.GroupLayout(pnlTriggers);
        pnlTriggers.setLayout(pnlTriggersLayout);
        pnlTriggersLayout.setHorizontalGroup(
            pnlTriggersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );
        pnlTriggersLayout.setVerticalGroup(
            pnlTriggersLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        pane.addTab("Triggers", pnlTriggers);

        javax.swing.GroupLayout pnlOptionsLayout = new javax.swing.GroupLayout(pnlOptions);
        pnlOptions.setLayout(pnlOptionsLayout);
        pnlOptionsLayout.setHorizontalGroup(
            pnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );
        pnlOptionsLayout.setVerticalGroup(
            pnlOptionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        pane.addTab("Options", pnlOptions);

        javax.swing.GroupLayout pnlCommentLayout = new javax.swing.GroupLayout(pnlComment);
        pnlComment.setLayout(pnlCommentLayout);
        pnlCommentLayout.setHorizontalGroup(
            pnlCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );
        pnlCommentLayout.setVerticalGroup(
            pnlCommentLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        pane.addTab("Comment", pnlComment);

        javax.swing.GroupLayout pnlSQLPreviewLayout = new javax.swing.GroupLayout(pnlSQLPreview);
        pnlSQLPreview.setLayout(pnlSQLPreviewLayout);
        pnlSQLPreviewLayout.setHorizontalGroup(
            pnlSQLPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 584, Short.MAX_VALUE)
        );
        pnlSQLPreviewLayout.setVerticalGroup(
            pnlSQLPreviewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );

        pane.addTab("SQL Preview", pnlSQLPreview);

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

        btnAddField.setText("Add Field");
        btnAddField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnAddFieldMouseClicked(evt);
            }
        });
        btnAddField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddFieldActionPerformed(evt);
            }
        });

        btnDeleteField.setText("Delete Field");
        btnDeleteField.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDeleteFieldMouseClicked(evt);
            }
        });
        btnDeleteField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteFieldActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddField)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteField))
                    .addComponent(pane, javax.swing.GroupLayout.PREFERRED_SIZE, 589, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnAddField)
                    .addComponent(btnDeleteField))
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(pane, javax.swing.GroupLayout.PREFERRED_SIZE, 441, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnAddFieldMouseClicked

    }//GEN-LAST:event_btnAddFieldMouseClicked

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSaveMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSaveMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSaveMouseClicked

    private void btnDeleteFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteFieldActionPerformed
        removeRow();
    }//GEN-LAST:event_btnDeleteFieldActionPerformed

    private void btnDeleteFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDeleteFieldMouseClicked

    }//GEN-LAST:event_btnDeleteFieldMouseClicked

    private void btnAddFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddFieldActionPerformed
        addRow();
    }//GEN-LAST:event_btnAddFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(NewTableFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(NewTableFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(NewTableFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(NewTableFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new NewTableFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddField;
    private javax.swing.JButton btnDeleteField;
    private javax.swing.JButton btnSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane pane;
    private javax.swing.JPanel pnlComment;
    private javax.swing.JPanel pnlFields;
    private javax.swing.JPanel pnlForeignKeys;
    private javax.swing.JPanel pnlIndexes;
    private javax.swing.JPanel pnlOptions;
    private javax.swing.JPanel pnlSQLPreview;
    private javax.swing.JPanel pnlTriggers;
    private javax.swing.JTable tblFieldPane;
    // End of variables declaration//GEN-END:variables
}
