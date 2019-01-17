/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.util;

import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author sarkhanrasullu
 */
public class ImageUtil {
    public static ImageIcon getIcon(String iconName){
        ImageIcon imageIcon = new ImageIcon(ImageUtil.class.getResource("/icons/"+iconName));
        Image temp = imageIcon.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
        return new ImageIcon(temp);
    }
    
     public static ImageIcon getIconforQueryPanel(String iconName){
        ImageIcon imageIcon = new ImageIcon(ImageUtil.class.getResource("/icons/"+iconName));
        Image temp = imageIcon.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
        return new ImageIcon(temp);
    }
     
     public static ImageIcon getIconforMysql(String iconName){
        ImageIcon imageIcon = new ImageIcon(ImageUtil.class.getResource("/icons/"+iconName));
        Image temp = imageIcon.getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT);
        return new ImageIcon(temp);
    }
}
