package com.company.classes;

import java.lang.reflect.Array;
import java.util.*;
import java.lang.Object;

public class OpenAddressingHashmap<K, V> implements Map<K, V> {

    private KeyValue<K, V>[] table = null;
    private boolean[] isDeleted = null;
    private int step = 1;
    int filledSize = 0;
    double filledKoef = 0;
    int powSize = 0;

    public <T>OpenAddressingHashmap(Class<T> clazz, int powSize){
        this.powSize = powSize;
        table = (KeyValue<K, V>[]) Array.newInstance(clazz, (int)Math.pow(2, powSize));
        isDeleted = new boolean[(int)Math.pow(2, powSize)];
    }

    private int hash(K key){
        return Math.abs(key.hashCode());
    }

    @Override
    public int size() {
        return filledSize;
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < table.length; i++){
            if (table[i] != null)
                return false;
        }
        return true;
    }

    @Override
    public boolean containsKey(Object key) {
        int hash = hash((K)key);
        int offset = 0;
        int index = (hash + offset) % table.length;
        int startingIndex = index;
        while(isDeleted[index] == false && table[index] != null){
            if (table[index].getKey().equals(key))
                return true;
            offset += step;
            index = (hash + offset) % table.length;
            if (index == startingIndex){
                break;
            }
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        for(int i = 0; i < table.length; i++){
            if (table[i] != null && table[i].getValue().equals(value))
                return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int hash = hash((K)key);
        int offset = 0;
        int index = (hash + offset) % table.length;
        int startingIndex = index;
        while(isDeleted[index] == false && table[index] != null){
            if (table[index].getKey().equals(key))
                return table[index].getValue();
            offset += step;
            index = (hash + offset) % table.length;
            if (index == startingIndex){
                break;
            }
        }
        return null;
    }

    @Override
    public V put(K key, V value) {
        filledSize++;
        return putTo(key, value, table, isDeleted);
    }


    private V putTo(K key, V value, KeyValue<K, V>[] destTable, boolean[] destIsDeleted) {
        int hash = hash(key);
        int offset = 0;
        int index = (hash + offset) % destTable.length;
        int startingIndex = index;
        while(destIsDeleted[index] == false && destTable[index] != null){
            if (destTable[index].getKey().equals(key)){
                destTable[index].setValue(value);
                return value;
            }
            offset += step;
            index = (hash + offset) % destTable.length;
            if (index == startingIndex){
                throw new OutOfMemoryError("Table is full");
            }
        }

        destTable[(hash + offset) % destTable.length] = new KeyValue<K, V>(key, value);
        destIsDeleted[index] = false;
        return value;
    }


    @Override
    public V remove(Object key) {
        filledSize--;
        if (containsKey(key) == false)
            return null;
        return removeFrom((K)key, table, isDeleted);
    }

    private V removeFrom(K key, KeyValue<K, V>[] srcTable, boolean[] srcIsDeleted) {
        int hash = hash(key);
        int offset = 0;
        int index = (hash + offset) % srcTable.length;
        //узнаем индекс элемента на удаление
        while(srcTable[index] != null){
            if (srcTable[index].getKey().equals(key) && srcIsDeleted[index] == false) {
                break;
            }
            offset += step;
            index = (hash + offset) % srcTable.length;
        }
        srcIsDeleted[index] = true;
        return srcTable[index].getValue();
    }


    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        table = (KeyValue<K, V>[]) new Object[(int)Math.pow(2, powSize)];
        isDeleted = new boolean[(int)Math.pow(2, powSize)];
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for(int i = 0; i < table.length; i++){
            if (table[i] != null)
                keySet.add(table[i].getKey());
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        Collection<V> valueSet = new ArrayList<>();
        for(int i = 0; i < table.length; i++){
            if (table[i] != null)
                valueSet.add(table[i].getValue());
        }
        return valueSet;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    public void print() {
        for(int i = 0; i < table.length; i++){
            System.out.println("Table[" + i + "] = " + (table[i] != null ? table[i] : "null") + " IsDeleted = " + isDeleted[i]);
        }
    }

}
