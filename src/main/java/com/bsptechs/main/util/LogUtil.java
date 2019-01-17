/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.util;

/**
 *
 * @author sarkhanrasullu
 */
public class LogUtil {

    private static boolean developmentMode = true;

    public static void log(Object txt) {
        if (developmentMode) {
            System.out.println(txt);
        }
    }

}
