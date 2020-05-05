package com.clinton;

public interface IHeap<K extends Comparable<K>> {
    void insertItem(K key);

    void deleteItem(K key);

    K removeMin();
}
