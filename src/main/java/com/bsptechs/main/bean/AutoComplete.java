/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean;

import lombok.Data;

/**
 *
 * @author Goshgar
 */
@Data
public class AutoComplete {

    public AutoComplete(String name, String referenceObject, String iconPath) {
        this.name = name;
        this.referenceObject = referenceObject;
        this.iconPath = iconPath;
    }

    public AutoComplete() {
    }

    private String name;
    private String referenceObject;
    private String iconPath;
}
