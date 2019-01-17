/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean;

import java.util.List;
import lombok.Data;

/**
 *
 * @author Goshgar
 */
@Data
public class Charset {

    private String name;
    private List<Collation> collations;

    public Charset() {
    }

    public Charset(String name) {
        this.name = name;

    }

    public Charset(String name, List<Collation> collations) {
        this.name = name;
        this.collations = collations;
    }

    @Override
    public String toString() {
        return name;
    }
}
