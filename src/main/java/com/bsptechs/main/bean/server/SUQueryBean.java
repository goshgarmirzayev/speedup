/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean.server;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author sarkhanrasullu
 */
@Data
public class SUQueryBean implements Serializable{
private static final long serialVersionUID = 2436470121850753296L;
    private transient SUConnectionBean connection;
    private SUDatabaseBean database;
    private String query;
    private String name;
    
    public SUQueryBean(SUConnectionBean connection, SUDatabaseBean database, String query) {
        this.connection = connection;
        this.database = database;
        this.query = query;
    }

    public SUQueryBean() {
    }
    

    public SUQueryBean(SUConnectionBean connection, SUDatabaseBean database, String query, String name) {
        this.connection = connection;
        this.database = database;
        this.query = query;
        this.name = name;
    }
    
    
    
}
