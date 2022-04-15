package com.company.classes;

import java.util.*;

public class ChainHashMap<K, V> implements Map<K, V> {

    private DoublyLinkedList<K, V>[] chains = null;

    public ChainHashMap(int size){
        chains = new DoublyLinkedList[size];
        for(int i = 0; i < chains.length; i++){
            //как прокинуть тип?
            chains[i] = new DoublyLinkedList<>();
        }
    }

    private int hash(K key){
        return Math.abs(key.hashCode());
    }

    @Override
    public int size() {
        return chains.length;
    }

    @Override
    public boolean isEmpty() {
        for(int i = 0; i < chains.length; i++){
            if (chains[i].size() != 0)
                return false;
        }
        return true;
    }

    @Override
    public boolean containsKey(Object key) {
        int hash = hash((K)key);
        return chains[hash % chains.length].containsKey((K)key);
    }

    @Override
    public boolean containsValue(Object value) {
        for(int i = 0; i < chains.length; i++){
            if (chains[i].containsValue(value))
                return true;
        }
        return false;
    }

    @Override
    public V get(Object key) {
        int hash = hash((K)key);
        return chains[hash % chains.length].get((K)key);
    }

    @Override
    public V put(K key, V value) {
        int hash = hash(key);
        return chains[hash % chains.length].put(key, value);
    }

    @Override
    public V remove(Object key) {
        int hash = hash((K)key);
        return chains[hash % chains.length].remove((K)key);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        for(int i = 0; i < chains.length; i++){
            //как прокинуть тип?
            chains[i] = new DoublyLinkedList<>();
        }
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for(int i = 0; i < chains.length; i++){
            keySet.addAll(chains[i].keySet());
        }
        return keySet;
    }

    @Override
    public Collection<V> values() {
        Collection<V> valueSet = new ArrayList<>();
        for(int i = 0; i < chains.length; i++){
            valueSet.addAll(chains[i].values());
        }
        return valueSet;
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    public void print(){
        for(int i = 0; i < chains.length; i++){
            if(chains[i].size() != 0) {
                System.out.println("Index = " + i);
                System.out.print("-> "); chains[i].printForward();
            }
        }
    }
}
