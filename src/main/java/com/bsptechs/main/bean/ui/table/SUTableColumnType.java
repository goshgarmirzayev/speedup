/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.ui.table;

import lombok.Data;

/**
 *
 * @author sarkhanrasullu
 */
@Data
public class SUTableColumnType {
    private int id;
    private String name;

    public SUTableColumnType(int id, String name) {
        this.id = id;
        this.name = name;
    }
    
    
}
