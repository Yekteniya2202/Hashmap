package com.company.classes;

import java.util.*;

/**
 * Object of class <code>DoublyLinkedList</code> represents Linked list, implements <code>Map</code>
 * @param <K> - key
 * @param <V> - value
 * @author Michael Babaev
 * @since 1.0
 * @version First implementation
 */
public class DoublyLinkedList <K, V> implements Map<K, V> {
    private ListNode<K, V> front;
    private int elemsCount = 0;

    /**
     * Method <code>size()</code>
     * @return size of <code>DoublyLinkedList</code>
     */
    @Override
    public int size() {
        return elemsCount;
    }

    /**
     * Method <code>isEmpty()</code>
     * @return <code>true</code>, if <code>DoublyLinkedList</code> has no <code>ListNode</code>, otherwise <code>false</code>
     */
    @Override
    public boolean isEmpty() {
        return elemsCount == 0;
    }

    /**
     * Method <code>containsKey(Object key)</code>
     * @param key - represents <code>Object</code> key to find in <code>DoublyLinkedList</code>
     * @return <code>true</code>, if <code>DoublyLinkedList</code> contains key, otherwise <code>false</code>
     */
    @Override
    public boolean containsKey(Object key) {
        if (isEmpty())
            return false;
        var tmp = front;
        while(tmp != null){
            if (tmp.key.equals(key)) {
                return true;
            }
            tmp = tmp.next;
        }
        return false;
    }

    /**
     * Method <code>containsValue(Object value)</code>
     * @param value - represents <code>Object</code> value to find in <code>DoublyLinkedList</code>
     * @return <code>true</code>, if <code>DoublyLinkedList</code> contains value, otherwise <code>false</code>
     */
    @Override
    public boolean containsValue(Object value) {
        if (isEmpty())
            return false;
        var tmp = front;
        while(tmp != null){
            if (tmp.value.equals(value)) {
                return true;
            }
            tmp = tmp.next;
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
        if (isEmpty())
            return null;
        var tmp = front;
        while(tmp != null){
            if (tmp.key.equals(key)) {
                return tmp.value;
            }
            tmp = tmp.next;
        }
        return null;
    }

    /**
     * Method <code>put(K key, V value)</code>
     * @param key - represents key, assosiated with value
     * @param value - just value
     * @return old value, if node was modified, otherwise this value
     */
    @Override
    public V put(K key, V value) {

        //if empty
        if (isEmpty()){
            //make new node
            front = new ListNode<K, V>(key, value, null, null);
            this.elemsCount++;
            return front.value;
        }
        var tmp = front;
        while(tmp.next != null){
            if (tmp.key.equals(key)) {
                //if value changes
                V toReturn = tmp.value;
                tmp.value = value;
                //return value
                return toReturn;
            }
            tmp = tmp.next;
        }
        tmp.next = new ListNode<K, V>(key, value, null, tmp);
        this.elemsCount++;
        return tmp.value;
    }

    /**
     * Method <code>remove(Object key)</code>
     * @param key - represents <code>Object</code> key, assosiated with value
     * @return value, that was successfully removed, otherwise <code>null</code>
     */
    @Override
    public V remove(Object key) {
        if (isEmpty())
            return null;

        //Ищем
        var tmp = front;
        while(tmp != null && !tmp.key.equals(key)){
            tmp = tmp.next;
        }

        //Не нашли
        if(tmp == null)
            return null;

        //нашли
        if(tmp.equals(front) && tmp.key.equals(key)){
            front.prev = null;
            front = tmp.next;
            elemsCount--;
            return tmp.value;
        }
        if(tmp.next == null) {
            tmp.prev.next = null;
            elemsCount--;
            return tmp.value;
        }

        tmp.prev.next = tmp.next;
        tmp.next.prev = tmp.prev;
        elemsCount--;
        return tmp.value;
    }

    /**
     * Method <code>putAll(Map m)</code> puts every entry to <code>DoublyLinkedList</code>
     * @param map - entries to put
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {
        var mapClone = Map.copyOf(map);
        for (var entryClone : mapClone.entrySet()){
            if (put(entryClone.getKey(), entryClone.getValue()) != null){
                elemsCount++;
            }
        }
    }

    /**
     * Method <code>clear()</code> clears <code>DoubleLinkedList</code>, making <code>front</code> <code>null</code>, <code>size</code> = 0
     */
    @Override
    public void clear() {
        front = null;
        elemsCount = 0;
    }

    /**
     * Method <code>keySet()</code>
     * @return immutable keySet of <code>DoubleLinkedList</code>
     */
    @Override
    public Set<K> keySet() {
        if (isEmpty())
            return null;

        //immutable?
        Set<K> keySet = new HashSet<>();
        var tmp = front;
        while(tmp != null){
            keySet.add(tmp.key);
        }
        return keySet;
    }

    /**
     * Method <code>values()</code>
     * @return mutable velue collection of <code>DoubleLinkedList</code>
     */
    @Override
    public Collection<V> values() {
        if (isEmpty())
            return null;
        Collection<V> valueList = new ArrayList<>();
        var tmp = front;
        while(tmp != null){
            valueList.add(tmp.value);
        }
        return valueList;
    }

    /**
     * Method <code>entrySet()</code>
     * @return immutable set of entries of <code>DoubleLinkedList</code>
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        if (isEmpty())
            return null;

        //immutable?
        Set<Entry<K, V>> keySet = new HashSet<Entry<K, V>>();
        var tmp = front;
        while(tmp != null){
            //Implementation of Map.Entry (Abstract?)
            keySet.add(new AbstractMap.SimpleEntry<> (tmp.key, tmp.value));
        }
        return keySet;

    }

    /**
     * Method <code>toString()</code> overrides the <code>Object.toString()</code>
     * @return String in format [{1, "abc"}, {2, "qwer"}, ...]
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        if(isEmpty()){
            return "";
        }
        var tmp = front;
        while(tmp != null){
            sb.append("{" + tmp.key + "," + tmp.value + "}");
            tmp = tmp.next;
        }
        sb.append("]");
        return sb.toString();
    }
}
