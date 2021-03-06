
package com.bsptechs.main.bean.table.dataTypePanel;

import java.awt.event.ItemEvent;

/**
 *
 * @author Goshgar
 */
public class NumericFamilyPanel extends DataTypePanel {

    
    private boolean isAutoIncr;
    private boolean isUnsigned;
    private boolean isZerofill;

    public NumericFamilyPanel() {
        initComponents();

    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        autoIncrement = new javax.swing.JCheckBox();
        unsigned = new javax.swing.JCheckBox();
        zerofill = new javax.swing.JCheckBox();
        jLabel2 = new javax.swing.JLabel();
        defaultCombo = new javax.swing.JComboBox<>();

        autoIncrement.setText("Auto Increment");
        autoIncrement.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                autoIncrementItemStateChanged(evt);
            }
        });
        autoIncrement.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                autoIncrementStateChanged(evt);
            }
        });
        autoIncrement.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoIncrementActionPerformed(evt);
            }
        });

        unsigned.setText("Unsigned");
        unsigned.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                unsignedItemStateChanged(evt);
            }
        });
        unsigned.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                unsignedStateChanged(evt);
            }
        });

        zerofill.setText("Zerofill");
        zerofill.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                zerofillItemStateChanged(evt);
            }
        });
        zerofill.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zerofillStateChanged(evt);
            }
        });

        jLabel2.setText("Default:");

        defaultCombo.setEditable(true);
        defaultCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "", "NULL", "EMPTY STRING" }));
        defaultCombo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                defaultComboItemStateChanged(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(autoIncrement, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                        .addComponent(unsigned, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(zerofill, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(defaultCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(defaultCombo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addComponent(autoIncrement)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(unsigned)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(zerofill)
                .addContainerGap(45, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void autoIncrementActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoIncrementActionPerformed
        
    }//GEN-LAST:event_autoIncrementActionPerformed

    private void autoIncrementStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_autoIncrementStateChanged

    }//GEN-LAST:event_autoIncrementStateChanged

    private void unsignedStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_unsignedStateChanged

    }//GEN-LAST:event_unsignedStateChanged

    private void zerofillStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zerofillStateChanged

    }//GEN-LAST:event_zerofillStateChanged

    private void autoIncrementItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_autoIncrementItemStateChanged
      

    }//GEN-LAST:event_autoIncrementItemStateChanged

    private void unsignedItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_unsignedItemStateChanged
        this.isUnsigned = evt.getStateChange() == ItemEvent.SELECTED;
    }//GEN-LAST:event_unsignedItemStateChanged

    private void zerofillItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_zerofillItemStateChanged
        this.isZerofill = evt.getStateChange() == ItemEvent.SELECTED;
    }//GEN-LAST:event_zerofillItemStateChanged

    private void defaultComboItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_defaultComboItemStateChanged

    }//GEN-LAST:event_defaultComboItemStateChanged
    private String getDefaultValue() {
//    if(cmb)
        return null;
    }

    @Override
    public String getQuery() {
        String str = "$UNSIGNED $ZEROFILL $NOT_NULL $DEFAULT $AUTO_INCREMENT ";
        str=str.replace("$UNSIGNED",unsigned.isSelected()?"UNSIGNED":"");
        str=str.replace("$ZEROFILL",zerofill.isSelected()?"ZEROFILL":"");
        str=str.replace("$AUTO_INCREMENT",autoIncrement.isSelected()?"AUTO_INCREMENT":"");
        int defaultInt=0;
        str=str.replace("$DEFAULT",defaultCombo.getSelectedItem()!=null ? "DEFAULT "+defaultCombo.getSelectedItem():"");
        return str;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox autoIncrement;
    private javax.swing.JComboBox<String> defaultCombo;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JCheckBox unsigned;
    private javax.swing.JCheckBox zerofill;
    // End of variables declaration//GEN-END:variables
}
