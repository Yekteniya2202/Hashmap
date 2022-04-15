package com.company.classes;

public class ListNode <K, V>{
    K key;
    V value;
    ListNode <K, V> next;
    ListNode <K, V> prev;

    ListNode(K key, V value) {
        this.key = key;
        this.value = value;
        this.next = this.prev = null;
    }

    ListNode(K key, V value, ListNode <K, V> next, ListNode <K, V> prev) {
        this.key = key;
        this.value = value;
        this.prev = prev;
    }
}
