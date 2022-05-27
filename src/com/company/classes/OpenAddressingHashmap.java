package com.company.classes;

import java.lang.reflect.Array;
import java.util.*;
import java.lang.Object;
/**
 * Object of class <code>OpenAddressingHashmap</code> represents hash map
 * using the open addressing linear method
 * table size is the power of 2, linear step is even
 * that allows to fill all elements in table
 * Deleting method is based on additional isDeleted array
 * @param <K> - key
 * @param <V> - value
 * @since 1.0
 * @version First implementation
 * @author Michael Babaev
 */
public class OpenAddressingHashmap<K, V> implements Map<K, V> {


    private Map.Entry<K, V>[] table = null;
    private boolean[] isDeleted = null;
    private int step = 1;
    int filledSize = 0;
    double filledKoef = 0.7;
    int powSize = 0;

    /**
     * Constructor initializes table and isDeleted
     * @param powSize - 2^powSize is the length of table and isDeleted
     */
    public OpenAddressingHashmap(int powSize){
        this.powSize = powSize;
        Map.Entry<K, V> me = new AbstractMap.SimpleEntry<K, V>(null, null);
        table = (Map.Entry<K, V>[]) Array.newInstance(me.getClass(), (int)Math.pow(2, powSize));
        isDeleted = new boolean[(int)Math.pow(2, powSize)];
    }

    /**
     * Method <code>hash(K key)</code> implements the method of hashing
     * @param key - key
     * @return - abs of key's hashCode
     */
    private int hash(K key){
        return Math.abs(key.hashCode());
    }

    /**
     * Method <code>size()</code>
     * @return the size of non-null and undeleted entries
     */
    @Override
    public int size() {
        return filledSize;
    }


    /**
     * Method <code>isEmpty()</code>
     * @return <code>true</code>, if every table is empty (otherwise isDeleted is true), otherwise <code>false</code>
     */
    @Override
    public boolean isEmpty() {
        return filledSize == 0;
    }

    /**
     * Method <code>containsKey(Object key)</code>
     * uses linear method to iterate the keys
     * uses isDeleted array to ignore deleted elements
     * @param key - represents <code>Object</code> key to find in <code>OpenAddressingHashMap</code>
     * @return <code>true</code>, if <code>OpenAddressingHashMap</code> contains key, otherwise <code>false</code>
     */
    @Override
    public boolean containsKey(Object key) {
        int hash = hash((K)key);
        int offset = 0;
        int index = (hash + offset) % table.length;
        int startingIndex = index;
        while(isDeleted[index] == false && table[index] != null){
            if (table[index].getKey().equals(key)) {
                return true;
            }
            offset += step;
            index = (hash + offset) % table.length;
            if (index == startingIndex){
                break;
            }
        }
        return false;
    }

    /**
     * Method <code>containsValue(Object value)</code>
     * @param value - represents <code>Object</code> value to find in <code>OpenAddressingHashMap</code>
     * @return <code>true</code>, if <code>OpenAddressingHashMap</code> contains value, otherwise <code>false</code>
     */
    @Override
    public boolean containsValue(Object value) {
        for(int i = 0; i < table.length; i++){
            if (table[i] != null && table[i].getValue().equals(value))
                return true;
        }
        return false;
    }

    /**
     * Method <code>get()</code>
     * uses linear method to iterate the keys
     * uses isDeleted array to ignore deleted elements
     * @param key - represents <code>Object</code> key, assosiated with value
     * @return V value, if key is assosiated with this value, otherwise <code>null</code>
     */
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
        //документация специфицирует возврат null при ненахождении по ключу. Но null возвращать нельзя из-за нарушения регламента по неймингу
        //??????????
        return null;
    }
    /**
     * Method <code>put(K key, V value)</code>
     * increments filledSize
     * if filled percentage is > 70%, rebuilds table and isDeleted
     * increasing its size in 2 times (powSize increases by 1)
     * @param key - represents key, assosiated with value
     * @param value - just value
     * @return old value, if node in chain was modified, otherwise this value
     */
    @Override
    public V put(K key, V value) {
        filledSize++;
        //increase capacity of table

        if (filledSize / table.length > filledKoef){
            rebuildGreaterTable();
        }
        return putTo(key, value, table, isDeleted);
    }


    /**
     * Method <code>rebuildGreaterTable()</code> rebuilds table and isDeleted
     * increasing its size in 2 times (powSize increases by 1)
     */
    private void rebuildGreaterTable() {
        Map.Entry<K, V> me = new AbstractMap.SimpleEntry<K, V>(null, null);
        //increase table capacity in 2 times
        Map.Entry<K, V>[] newtable = (Map.Entry<K, V>[]) Array.newInstance(me.getClass(), (int)Math.pow(2, ++powSize));
        boolean[] newIsDeleted = new boolean[(int)Math.pow(2, powSize)];
        for(int i = 0; i < table.length; i++){
            //if entry is presented
            if (isDeleted[i] == false && table[i] != null){
                putTo(table[i].getKey(), table[i].getValue(), newtable, newIsDeleted);
            }
        }
        isDeleted = newIsDeleted;
        table = newtable;
    }


    /**
     * Method <code>putTo(K key, V value, Map.Entry<K, V>[] destTable, boolean[] destIsDeleted)</code>
     * puts the entry in destination table and isDeleted
     * Method is additional
     * @param key - key
     * @param value - value
     * @param destTable - destination table to put entry
     * @param destIsDeleted destination isDeleted to put the deleted statement
     * @return old value, if node in chain was modified, otherwise this value
     */
    private V putTo(K key, V value, Map.Entry<K, V>[] destTable, boolean[] destIsDeleted) {
        int hash = hash(key);
        int offset = 0;
        int index = (hash + offset) % destTable.length;
        int startingIndex = index;
        while(destIsDeleted[index] == false && destTable[index] != null){
            if (destTable[index].getKey().equals(key)){
                V toReturn = destTable[index].getValue();
                destTable[index].setValue(value);
                return toReturn;
            }
            offset += step;
            index = (hash + offset) % destTable.length;
            if (index == startingIndex){
                throw new OutOfMemoryError("Table is full");
            }
        }

        destTable[(hash + offset) % destTable.length] = new AbstractMap.SimpleEntry<K, V>(key, value);;
        destIsDeleted[index] = false;
        return value;
    }

    /**
     * Method <code>remove(Object key)</code>
     * @param key - represents <code>Object</code> key, assosiated with value
     * @return value, that was successfully removed, otherwise <code>null</code>
     */
    @Override
    public V remove(Object key) {

        var a = key.getClass();
        var b = table[0].getKey().getClass();
        if (a.equals(b) == false) {
            return null;
        }
        filledSize--;
        return removeFrom((K)key, table, isDeleted);
    }


    /**
     * Method <code>removeFrom(K key, Map.Entry<K, V>[] srcTable, boolean[] srcIsDeleted)</code>
     * removes the entry in source table and isDeleted
     * Method is additional
     * @param key - key
     * @param srcTable - source table to extract entry
     * @param srcIsDeleted destination isDeleted to extract the deleted statement
     * @return removed value, otherwise <code>null</code>
     */
    private V removeFrom(K key, Map.Entry<K, V>[] srcTable, boolean[] srcIsDeleted) {
        int l = srcTable.length;
        boolean b = l == srcIsDeleted.length;
        int hash = hash(key);
        int offset = 0;
        int index = (hash + offset) % srcTable.length;
        int startingIndex = index;
        //узнаем индекс элемента на удаление
        while(srcTable[index] != null){

            if (srcTable[index].getKey().equals(key) && srcIsDeleted[index] == false) {
                srcIsDeleted[index] = true;
                return srcTable[index].getValue();
            }
            offset += step;
            index = (hash + offset) % srcTable.length;
            if (index == startingIndex){
                break;
            }
        }
        return null;
    }


    /**
     * Method <code>putAll(Map m)</code> puts every entry to <code>OpenAddressingHashmap</code>
     * @param m - entries to put
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        var mClone = Map.copyOf(m);
        for(var entryClone : mClone.entrySet()){
            put(entryClone.getKey(), entryClone.getValue());
        }
    }

    /**
     * Method <code>clear()</code> clears <code>OpenAddressingHashmap</code>
     * making every teble and isDeleted reinitialized with empty elements
     */
    @Override
    public void clear() {
        table = (Map.Entry<K, V>[]) new Object[(int)Math.pow(2, powSize)];
        isDeleted = new boolean[(int)Math.pow(2, powSize)];
    }

    /**
     * Method <code>keySet()</code>
     * @return immutable keySet of <code>OpenAddressingHashmap</code>
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for(int i = 0; i < table.length; i++){
            if (table[i] != null)
                keySet.add(table[i].getKey());
        }
        return keySet;
    }

    /**
     * Method <code>values()</code>
     * @return mutable velue collection of <code>OpenAddressingHashmap</code>
     */
    @Override
    public Collection<V> values() {
        Collection<V> valueSet = new ArrayList<>();
        for(int i = 0; i < table.length; i++){
            if (table[i] != null)
                valueSet.add(table[i].getValue());
        }
        return valueSet;
    }


    /**
     * Method <code>entrySet()</code>
     * @return immutable set of entries of <code>OpenAddressingHashmap</code>
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> es = new HashSet<>();
        for(int i = 0; i < table.length; i++){
            if (table[i] != null && isDeleted[i] == false)
                es.add(new AbstractMap.SimpleEntry<>(table[i].getKey(), table[i].getValue()));
        }
        return es;
    }
    /**
     * Method <code>toString()</code> overrides the <code>Object.toString()</code>
     * @return String in format [{1, "abc"}{2, "qwer"} ...]
     */
    @Override
    public String toString() {
        StringBuilder sb  =new StringBuilder("[");
        for(int i = 0; i < table.length; i++){
            if (table[i] != null && isDeleted[i] == false){
                sb.append("{" + table[i].getKey() + ", " + table[i].getValue() + "}");
            }
        }
        sb.append("]");
        return sb.toString();
    }

}
