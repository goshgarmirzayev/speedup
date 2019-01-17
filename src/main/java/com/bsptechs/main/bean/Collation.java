/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean;

import lombok.Data;

/**
 *
 * @author sarkhanrasullu
 */
@Data
public class Collation {

    private String name;

    
    public Collation(String name){
        this.name = name;
    }
    
    @Override
    public String toString(){
        return name;
    }
}
