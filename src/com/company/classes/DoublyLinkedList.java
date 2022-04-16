package com.company.classes;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.*;

public class DoublyLinkedList <K, V> implements Map<K, V> {
    private ListNode<K, V> front;
    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

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

    @Override
    public V put(K key, V value) {

        if (isEmpty()){
            front = new ListNode<K, V>(key, value, null, null);
            this.size++;
            return front.value;
        }
        var tmp = front;
        while(tmp.next != null){
            if (tmp.key.equals(key)) {
                tmp.value = value;
                return tmp.value;
            }
            tmp = tmp.next;
        }
        tmp.next = new ListNode<K, V>(key, value, null, tmp);
        this.size++;
        return tmp.value;
    }

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
            if (size == 1){
                front = null;
                size--;
                return tmp.value;
            }
            front = tmp.next;
            front.prev = null;
            size--;
            return tmp.value;
        }
        if(tmp.next == null) {
            tmp.prev.next = null;
            size--;
            return tmp.value;
        }

        tmp.prev.next = tmp.next;
        tmp.next.prev = tmp.prev;
        size--;
        return tmp.value;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {
        front = null;
        size = 0;
    }

    @Override
    public Set<K> keySet() {
        if (isEmpty())
            return null;
        Set<K> keySet = new HashSet<>();
        var tmp = front;
        while(tmp != null){
            keySet.add(tmp.key);
        }
        return keySet;
    }

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

    @Override
    public Set<Entry<K, V>> entrySet() {
        return null;
    }

    public void printForward() {
        if(isEmpty()){
            return;
        }
        var tmp = front;
        while(tmp != null){
            System.out.print("{" + tmp.key + "," + tmp.value + "}" + " < = > ");
            tmp = tmp.next;
        }
        System.out.println();
    }
}
