package com.company;

import com.company.classes.OpenAddressingHashmap;

public class Main {

    public static void main(String[] args) {


        OpenAddressingHashmap<Integer, String> hashMap = new OpenAddressingHashmap<>(4);

        hashMap.put(0, "qwe");
        hashMap.put(16, "azs");

        hashMap.remove(5);
        hashMap.remove(15);
        hashMap.remove(25);
        hashMap.put(35, "Bar");
        //hashMap.print();
        //System.out.println(hashMap.get(21));
    }
}
