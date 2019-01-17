/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.server;

import com.bsptechs.main.bean.SUArrayList;
import com.bsptechs.main.bean.ui.table.SUTableColumn;
import java.util.Objects;
import lombok.Data;

/**
 *
 * @author sarkhanrasullu
 */
@Data
public class SUTableBean extends SUAbstractServerBean{

    private String name;
    private SUDatabaseBean database;
    
    public SUTableBean(String name, SUDatabaseBean database){
        this.name = name;
        this.database = database;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SUTableBean other = (SUTableBean) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
    
    
}
