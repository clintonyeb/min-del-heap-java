package com.clinton;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HeapTest {
    Heap<Integer> heap;
    Random random = new Random();
    final int SIZE = 20;

    @BeforeEach
    void setUp() {
        heap = new Heap<>();
    }

    @Test
    void insertItems() {
        for (int i = 0; i < SIZE; i++) {
            heap.insertItem(random.nextInt(1000) + 1);
        }
    }

    @Test
    void removeMin() {
        for (int i = 0; i < SIZE; i++) {
            heap.insertItem(random.nextInt(1000) + 1);
        }
        int[] res = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = heap.removeMin();
        }
        assertTrue(isSorted(res));
    }

    @Test
    void deleteItem() {
        heap.insertItem(5);
        heap.insertItem(10);
        heap.insertItem(15);
        heap.deleteItem(5);
        // 5 should not exist in heap
        assertThrows(IllegalStateException.class, () -> heap.deleteItem(5));
    }

    @Test
    void deleteAfterRemoveItem() {
        heap.insertItem(5);
        heap.insertItem(10);
        heap.insertItem(15);
        heap.removeMin();
        // 5 should not exist in heap
        assertThrows(IllegalStateException.class, () -> heap.deleteItem(5));
    }

    @Test
    void operateOnEmptyHeap() {
        assertThrows(IllegalStateException.class, () -> heap.removeMin());
    }

    @Test
    void test_1() {
        int[] data = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            data[i] = random.nextInt(1000) + 1;
        }

        for (int i = 0; i < SIZE; i++) {
            heap.insertItem(data[i]);
        }

        int[] res = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            res[i] = heap.removeMin();
        }
        assertTrue(isSorted(res));
    }

    private boolean isSorted(int[] arr) {
        for (int i = 0; i < arr.length-1; i++) {
            if(arr[i] > arr[i+1]) return false;
        }
        return true;
    }
}