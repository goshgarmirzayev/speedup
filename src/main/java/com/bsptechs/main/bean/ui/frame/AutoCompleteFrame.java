/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.frame;

import com.bsptechs.main.bean.AutoComplete;
import com.bsptechs.main.bean.server.SUConnectionBean;
import com.bsptechs.main.dao.impl.DatabaseDAOImpl;
import java.awt.MouseInfo;
import java.awt.Point;
import java.util.LinkedHashSet;
import java.util.List;

/**
 *
 * @author Goshgar
 */
public class AutoCompleteFrame extends javax.swing.JFrame {

    /**
     * Creates new form AutoCompleteFrame
     */
    public AutoCompleteFrame() {
        initComponents();
        fillList();
        setOpenedArea();
    }
    DatabaseDAOImpl db = new DatabaseDAOImpl();
 

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        suggestions = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        suggestions.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(suggestions);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    public static String typedWord;
    public static SUConnectionBean selectedConnection;
    
    private void fillList() {
         if(typedWord!=null && selectedConnection!=null){
        
        LinkedHashSet<AutoComplete> list = db.getAllKeyWords(selectedConnection);
        List<AutoComplete> result = list.stream()
                .filter(item -> item.getName().startsWith(typedWord.toLowerCase()))
                .collect(java.util.stream.Collectors.toList());
        String arr[] = new String[result.size()];
        for (int i = 0; i < result.size(); i++) {
            arr[i] = result.get(i).getName();
        }
        suggestions.removeAll();
        suggestions.setListData(arr);
        suggestions.setVisibleRowCount(5);
    }
    }

    private void setOpenedArea() {
        Point location = MouseInfo.getPointerInfo().getLocation();
        int x = (int) location.getX();
        int y = (int) location.getY();
        //this is just the initialization of the window
        this.setLocation(x, y);
    }

    private void isFrameFocused() {

    }

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
            java.util.logging.Logger.getLogger(AutoCompleteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutoCompleteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutoCompleteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutoCompleteFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AutoCompleteFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> suggestions;
    // End of variables declaration//GEN-END:variables
}
