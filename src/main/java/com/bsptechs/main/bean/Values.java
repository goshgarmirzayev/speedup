/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean;

import java.io.Serializable;
import lombok.Data;

/**
 *
 * @author Goshgar
 */
@Data
public class Values implements Serializable {

    private String name;

    public Values() {
    }

    public Values(String name) {
        this.name = name;
    }

}
