package com.company.classes;

import java.util.*;

/**
 * Object of class <code>ChainHashMap</code> represents hash map
 * using the chain method, built on <code>DoublyLinkedList</code> as chains, implements <code>Map</code>
 * @param <K> - key
 * @param <V> - value
 * @since 1.0
 * @version First implementation
 * @author Michael Babaev
 */
public class ChainHashMap<K, V> implements Map<K, V> {

    private DoublyLinkedList<K, V>[] chains = null;

    private int elemsCount = 0;
    /**
     * Constructor initialises array of chains
     * @param sizeOfTable - number of chains
     */
    public ChainHashMap(int sizeOfTable){
        chains = new DoublyLinkedList[sizeOfTable];
        for(int i = 0; i < chains.length; i++){
            chains[i] = new DoublyLinkedList<>();
        }
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
     * @return the size of represented elements
     */
    @Override
    public int size() {
        return elemsCount;
    }

    /**
     * Method <code>isEmpty()</code>
     * @return <code>true</code>, if every chain isEmpty, otherwise <code>false</code>
     */
    @Override
    public boolean isEmpty() {
        return elemsCount == 0;
    }

    /**
     * Method <code>containsKey(Object key)</code>
     * @param key - represents <code>Object</code> key to find in <code>ChainHashMap</code>
     * @return <code>true</code>, if <code>ChainHashMap</code> contains key, otherwise <code>false</code>
     */
    @Override
    public boolean containsKey(Object key) {
        int hash = hash((K)key);
        return chains[hash % chains.length].containsKey((K)key);
    }


    /**
     * Method <code>containsValue(Object value)</code>
     * @param value - represents <code>Object</code> value to find in <code>ChainHashMap</code>
     * @return <code>true</code>, if <code>ChainHashMap</code> contains value, otherwise <code>false</code>
     */
    @Override
    public boolean containsValue(Object value) {
        for(int i = 0; i < chains.length; i++){
            if (chains[i].containsValue(value))
                return true;
        }
        return false;
    }

    /**
     * Method <code>get()</code>
     * @param key - represents <code>Object</code> key, assosiated with value
     * @return V value, if key is assosiated with this value, otherwise <code>null</code>
     */
    @Override
    public V get(Object key) {
        int hash = hash((K)key);
        return chains[hash % chains.length].get((K)key);
    }

    /**
     * Method <code>put(K key, V value)</code>
     * @param key - represents key, assosiated with value
     * @param value - just value
     * @return old value, if node in chain was modified, otherwise this value
     */
    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        var putSuccessValue = chains[hash % chains.length].put(key, value);
        if (putSuccessValue != null){
            elemsCount++;
            return putSuccessValue;
        }
        return null;
    }

    /**
     * Method <code>remove(Object key)</code>
     * @param key - represents <code>Object</code> key, assosiated with value
     * @return value, that was successfully removed, otherwise <code>null</code>
     */
    @Override
    public V remove(Object key) {
        int hash = hash((K)key);
        var removeSuccessValue = chains[hash % chains.length].remove(key);
        if (removeSuccessValue != null){
            elemsCount--;
            return removeSuccessValue;
        }
        return null;
    }

    /**
     * Method <code>putAll(Map m)</code> puts every entry to <code>ChainHashMap</code>
     * @param map - entries to put
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        var mapClone = Map.copyOf(map);
        for(var entryClone : mapClone.entrySet()){
            int hash = hash(entryClone.getKey());
            if (chains[hash % chains.length].put(entryClone.getKey(), entryClone.getValue()) != null){
                elemsCount++;
            }
        }
    }

    /**
     * Method <code>clear()</code> clears <code>ChainHashMap</code>
     * making every chain reinitialized
     */
    @Override
    public void clear() {
        for(int i = 0; i < chains.length; i++){
            chains[i] = new DoublyLinkedList<>();
        }
        elemsCount = 0;
    }

    /**
     * Method <code>keySet()</code>
     * @return immutable keySet of <code>ChainHashMap</code>
     */
    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for(int i = 0; i < chains.length; i++){
            keySet.addAll(chains[i].keySet());
        }
        return keySet;
    }


    /**
     * Method <code>values()</code>
     * @return mutable velue collection of <code>ChainHashMap</code>
     */
    @Override
    public Collection<V> values() {
        Collection<V> valueCollection = new ArrayList<>();
        for(int i = 0; i < chains.length; i++){
            valueCollection.addAll(chains[i].values());
        }
        return valueCollection;
    }


    /**
     * Method <code>entrySet()</code>
     * @return immutable set of entries of <code>ChainHashMap</code>
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entrySet = new HashSet<>();
        for(int i = 0; i < chains.length; i++){
            entrySet.addAll(chains[i].entrySet());
        }
        return entrySet;
    }

    /**
     * Method <code>toString()</code> overrides the <code>Object.toString()</code>
     * @return String in format ([{1, "abc"}, {2, "qwer"}, ...] [{10, "abc"}, {20, "qwer"}, ...] ...)
     */
    public String toString(){
        StringBuilder formatViewBuilder  =new StringBuilder("(");
        for(int i = 0; i < chains.length; i++){
            if(chains[i].size() != 0) {
                formatViewBuilder.append(chains[i]);
            }
        }
        formatViewBuilder.append(")");
        return formatViewBuilder.toString();
    }
}
