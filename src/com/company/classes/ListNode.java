package com.company.classes;

/**
 * Object of class <code>ListNode</code> represents a simple node in <code>DoublyLinkedList</code> as <code>Map</code>
 * @param <K> - key
 * @param <V> - value
 * @author Michal Babaev
 * @since 1.0
 * @version First implementation
 */
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
        this.next = next;
        this.prev = prev;
    }
}
