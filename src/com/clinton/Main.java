package com.clinton;

public class Main {

    public static void main(String[] args) {
        Heap<Integer> heap = new Heap<>();
        for (int i = 20; i > 0; i--) {
            heap.insertItem(i);
        }

        for (int i = 0; i < 20; i++) {
            System.out.print(heap.removeMin() + " ");
        }

        System.out.println("\n");

        for (int i = 20; i > 0; i--) {
            heap.insertItem(i);
        }

        heap.deleteItem(5);
        heap.deleteItem(11);
        heap.deleteItem(1);
        heap.printAsDiagram();

        System.out.println("Tests done...");
    }
}
