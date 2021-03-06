/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.table.dataTypePanel;

import com.bsptechs.main.Main;
import com.bsptechs.main.bean.Charset;
import com.bsptechs.main.bean.Collation;
import com.bsptechs.main.bean.server.SUConnectionBean;
import com.bsptechs.main.bean.table.dataTypePanel.set.Default;
import com.bsptechs.main.bean.table.dataTypePanel.set.Values;
import java.awt.MouseInfo;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author Goshgar
 */
public class SetFamilyPanel extends DataTypePanel {

    /**
     * Creates new form SetFamilyPanel
     */
    private final SUConnectionBean currentConnection = Main.instance().getConnectionTree().getCurrentConnectionNode().getConnection();
    

    private SetFamilyPanel() {
        initComponents();
        CharFamilyPanel.fillCharsetCombo(currentConnection, cmbCharset);

    }
    private static final SetFamilyPanel thisPanel = new SetFamilyPanel();

    public static SetFamilyPanel instance() {
        return thisPanel;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtValues = new javax.swing.JTextField();
        txtDefault = new javax.swing.JTextField();
        cmbCharset = new javax.swing.JComboBox<>();
        cmbCollation = new javax.swing.JComboBox<>();
        btnValues = new javax.swing.JButton();
        btnDefault = new javax.swing.JButton();

        jLabel1.setText("Values:");

        jLabel2.setText("Default:");

        jLabel3.setText("Character Set:");

        jLabel4.setText("Collation:");

        cmbCharset.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbCharsetItemStateChanged(evt);
            }
        });

        btnValues.setText("jButton1");
        btnValues.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValuesActionPerformed(evt);
            }
        });

        btnDefault.setText("jButton1");
        btnDefault.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnDefaultMouseClicked(evt);
            }
        });
        btnDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDefaultActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtValues)
                    .addComponent(txtDefault, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                    .addComponent(cmbCharset, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbCollation, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnValues, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDefault, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(txtValues, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnValues))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtDefault, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnDefault)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cmbCharset, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(cmbCollation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 144, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cmbCharsetItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbCharsetItemStateChanged
        Charset charset = (Charset) cmbCharset.getSelectedItem();
        if (charset != null) {
            CharFamilyPanel.fillCollateCombo(currentConnection, charset, cmbCollation);
        }
    }//GEN-LAST:event_cmbCharsetItemStateChanged

    private void btnDefaultActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDefaultActionPerformed

    }//GEN-LAST:event_btnDefaultActionPerformed

    private void btnValuesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValuesActionPerformed
        Values values = Values.instance();
        values.setVisible(true);
        int y = MouseInfo.getPointerInfo().getLocation().x - 350;
        int x = MouseInfo.getPointerInfo().getLocation().x - values.getWidth();
        values.setLocation(x, y);

    }//GEN-LAST:event_btnValuesActionPerformed

    private void btnDefaultMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDefaultMouseClicked
        Default dflt = Default.instance();
        dflt.setVisible(true);
        int y = MouseInfo.getPointerInfo().getLocation().x - 230;
        int x = MouseInfo.getPointerInfo().getLocation().x - dflt.getWidth();
        dflt.setLocation(x, y);
    }//GEN-LAST:event_btnDefaultMouseClicked
    public void setValuesForTxtValues(List<String> valueList) {
        StringBuilder values = new StringBuilder("");
        if (valueList != null) {
            for (int i = 0; i < valueList.size(); i++) {
                values.append("'").append(valueList.get(i)).append("'").append(",");
            }
            values.setLength(values.length() - 1);
        }
        txtValues.setText(values.toString());
    }

    @Override
    public String getQuery() {
        String str = "($VALUE) $CH_SET $COLLATE $NOT_NULL $DEFAULT";
        str = str.replace("$VALUE", txtValues.getText());
        str = str.replace("$CH_SET", "CHARACTER SET " + cmbCharset.getSelectedItem().toString());
        str = str.replace("$COLLATE", "COLLATE " + cmbCollation.getSelectedItem().toString());
        str = str.replace("$DEFAULT", "DEFAULT " + txtDefault.getText());
        return str;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDefault;
    private javax.swing.JButton btnValues;
    private javax.swing.JComboBox<Charset> cmbCharset;
    private javax.swing.JComboBox<Collation> cmbCollation;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField txtDefault;
    private javax.swing.JTextField txtValues;
    // End of variables declaration//GEN-END:variables
}
