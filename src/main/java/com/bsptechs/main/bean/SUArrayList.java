/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bsptechs.main.bean;

import com.bsptechs.main.bean.ui.table.SUTableRow;
import java.util.Vector;

/**
 *
 * @author sarkhanrasullu
 */
public class SUArrayList<T> extends Vector<T> {

    @Override
    public T get(int index) {
        if (size() > 0 && index < size()) {
            return super.get(index);
        } else {
            return null;
        }
    }

    public T getByName(String name) {
        for (int i = 0; i < size(); i++) {
            T t = get(i);
            if (t.toString().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    public T getLast() {
        return get(size() - 1);
    }

    public T removeLast() {
        return this.remove(size() - 1);
    }
}
