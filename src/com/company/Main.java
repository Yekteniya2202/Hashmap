package com.company;

import com.company.classes.ChainHashMap;
import com.company.classes.KeyValue;
import com.company.classes.OpenAddressingHashmap;

import java.lang.invoke.VarHandle;

public class Main {

    public static void main(String[] args) {
        KeyValue<Integer, String> clazz = new KeyValue<>();
        OpenAddressingHashmap<Integer, String> hashMap = new OpenAddressingHashmap<>(clazz.getClass(),4);

        hashMap.put(0, "qwe");
        hashMap.put(16, "azs");
        /*
        hashMap.remove(5);
        hashMap.remove(15);
        hashMap.remove(25);
        hashMap.put(35, "Bar");*/
        hashMap.print();
        System.out.println(hashMap.get(21));
    }
}
