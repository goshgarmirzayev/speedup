/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.util;

import com.bsptechs.main.bean.server.SUQueryBean;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import lombok.SneakyThrows;

/**
 *
 * @author Goshgar
 */
public class FileUtility {

    public static void writeDBAndTblNameFile(String text, String fileName) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            bw.write(text);
            LogUtil.log("Done");
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    public static String readDBAndTblName(String fileName) {
        try (InputStream in = new FileInputStream(fileName);
                BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                return line;
            }
        } catch (IOException x) {
            System.err.println(x);
        }
        return null;
    }

    public static Object readFileDeserialize(String name) {
        Object obj = null;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(name))) {
            obj = in.readObject();
        } finally {
            return obj;
        }
    }

    public static boolean writeObjectToFile(Object object, String name) throws RuntimeException {
       try (FileOutputStream fout = new FileOutputStream(name);
                    ObjectOutputStream oos = new ObjectOutputStream(fout);) {
                oos.writeObject(object);
                return true;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        
    }

 
    public static List<SUQueryBean> readObjectsAsList(String fileName) {
        List<SUQueryBean> list = new ArrayList<SUQueryBean>();
        boolean cont = true;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName));
            while (cont) {
              SUQueryBean query = null;
                try {
                    query =(SUQueryBean)ois.readObject();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                if (query != null) {
                    list.add(query);
                } else {
                    cont = false;
                }
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return list;

    }
}
