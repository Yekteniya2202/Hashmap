package com.company.tests;

import com.company.classes.ChainHashMap;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class ChainHashMapTest {

    @org.junit.jupiter.api.Test
    void containsKey() {
        Map<Integer, String> hm = new ChainHashMap<>(30);
        for(int i = -1000; i <= 1000; i++){
            String rndStr = getRandomString(random, "qwerty", 20);
            var result = hm.put(i, rndStr);
            Assert.assertNotNull(result);
        }

        for(int i = -1000; i <= 1000; i++){
            Assert.assertTrue(hm.containsKey(i));
        }
    }

    @org.junit.jupiter.api.Test
    void containsValue() {
        Map<Integer, String> hm = new ChainHashMap<>(30);
        ArrayList<String> added = new ArrayList<>();
        for(int i = -1000; i <= 1000; i++){
            String rndStr = getRandomString(random, "qwerty", 20);
            added.add(rndStr);
            var result = hm.put(i, rndStr);
            Assert.assertNotNull(result);
        }

        for(var str : added){
            Assert.assertTrue(hm.containsValue(str));
        }
    }

    @org.junit.jupiter.api.Test
    void get() {
        Map<Integer, String> hm = new ChainHashMap<>(30);
        for(int i = -1000; i <= 1000; i++){
            String rndStr = getRandomString(random, "qwerty", 20);
            var result = hm.put(i, rndStr);
            Assert.assertNotNull(result);
        }


        for(int i = -1000; i <= 1000; i++){
            Assert.assertNotNull(hm.get(i));
        }
    }

    @org.junit.jupiter.api.Test
    void putUniqueKeys() {
        Map<Integer, String> hm = new ChainHashMap<>(30);

        for(int i = -1000; i <= 1000; i++){
            String rndStr = getRandomString(random, "qwerty", 20);
            var result = hm.put(i, rndStr);
            Assert.assertNotNull(result);
        }

        for(int i = -1000; i <= 1000; i++){
            Assert.assertNotNull(hm.containsKey(i));
        }
    }

    @org.junit.jupiter.api.Test
    void putNonUniqueKeys(){
        Map<Integer, String> hm = new ChainHashMap<>(30);

        for(int i = -1000; i <= 1000; i++){
            String rndStr = getRandomString(random, "qwerty", 20);
            var result = hm.put(i, rndStr);
            Assert.assertNotNull(result);
        }

        for(int i = -1000; i <= 1000; i++){
            var old = hm.get(i);
            String rndStr = getRandomString(random, "qwerty", 5);
            var result = hm.put(i, rndStr);
            //System.out.println(i + " - " + hm.get(i) + " - " + result + " - " + old);
            Assert.assertNotNull(result);
        }
    }

    @org.junit.jupiter.api.Test
    void removeUniqueKeys() {
        Map<Integer, String> hm = new ChainHashMap<>(30);

        for(int i = -1000; i <= 1000; i++){
            String rndStr = getRandomString(random, "qwerty", 20);
            var result = hm.put(i, rndStr);
            Assert.assertNotNull(result);
        }


        for(int i = -1000; i <= 1000; i++){
            hm.remove(i);
        }

        for(int i = -1000; i <= 1000; i++){
            Assert.assertFalse(hm.containsKey(i));
        }

    }

    @org.junit.jupiter.api.Test
    void removeNonUniqueKeys() {
        Map<Integer, String> hm = new ChainHashMap<>(30);

        for(int i = -1000; i <= 1000; i++){
            String rndStr = getRandomString(random, "qwerty", 20);
            var result = hm.put(i, rndStr);
            Assert.assertNotNull(result);
        }


        for(int i = -1000; i <= 1000; i+= 2){
            hm.remove(i);
        }

        for(int i = -1000; i <= 1000; i+=2){
            Assert.assertNull(hm.remove(i));
        }

    }

    Random random = new Random();
    private static String getRandomString(Random rng, String characters, int size){
        char[] text = new char[size];
        for (int i = 0; i < size; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }

}