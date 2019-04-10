/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.table.dataTypePanel;

/**
 *
 * @author Goshgar
 */
public abstract class DataTypePanel extends javax.swing.JPanel{
    
    //bu abstract olacaq ve butun paneller oz queryisini return edecek
    public abstract String getQuery();
}
