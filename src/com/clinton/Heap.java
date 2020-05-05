package com.clinton;

import java.util.*;

/**
 * Code shell for a MinHeap that support a deleteItem operation
 */
public class Heap<K extends Comparable<K>> implements IHeap<K> {
    private Node<K> root;
    private Node<K> lastItem;
    private Map<K, Node<K>> locator;

    public Heap() {
        locator = new HashMap<>();
    }

    @Override
    public void insertItem(K key) {
        //create new Node n, insert key, and place as child
        Node<K> node = new Node<>();
        node.key = key;

        if (root == null) {
            root = node;
            locator.put(key, node);
            lastItem = node;
            return;
        }

        // find node to insert below
        Node<K> curr = lastItem;
        while (curr.parent != null && curr == curr.parent.right) curr = curr.parent;

        if (curr.parent != null) {
            if (curr.parent.right != null) {
                curr = curr.parent.right;
                while (curr.left != null) {
                    curr = curr.left;
                }
            } else {
                curr = curr.parent;
            }
        } else {
            while (curr.left != null) curr = curr.left;
        }

        if (curr.left == null) {
            curr.left = node;
        } else {
            curr.right = node;
        }
        node.parent = curr;
        locator.put(key, node);
        lastItem = node;
        upHeap(node);
    }

    @Override
    public void deleteItem(K key) {
        if (!locator.containsKey(key)) throw new IllegalStateException("Does not contain key");
        remove(locator.get(key));
    }

    @Override
    public K removeMin() {
        if(root == null) throw new IllegalStateException("Heap is empty");
        return remove(root);
    }

    private K remove(Node<K> node) {
        K key = node.key;
        locator.remove(key);

        if (root.left == null && root.right == null) {
            root = null;
            lastItem = null;
            return key;
        }

        // Locate the node before the last node.
        Node<K> curr = lastItem;
        while (curr.parent != null && curr == curr.parent.left) {
            curr = curr.parent;
        }
        if (curr.parent != null) {
            curr = curr.parent.left;
        }
        while (curr.right != null) {
            curr = curr.right;
        }

        // Disconnect the last node.
        if (lastItem == lastItem.parent.right) {
            lastItem.parent.right = null;
        } else {
            lastItem.parent.left = null;
        }

        if (node == lastItem) {
            lastItem = curr;
        } else {
            node.key = lastItem.key;
            locator.put(node.key, node);
            if (node != curr) {
                lastItem = curr;
            }
            downHeap(node);
        }

        return key;
    }

    private void upHeap(Node<K> n) {
        while (n.parent != null && n.compareTo(n.parent) < 0) {
            swap(n, n.parent);
            locator.put(n.parent.key, n.parent);
            locator.put(n.key, n);
            n = n.parent;
        }
    }

    private void downHeap(Node<K> node) {
        Node<K> curr = node;
        while (curr.left != null || curr.right != null) {
            boolean isRight = false;
            assert (curr.left != null);
            if (curr.right != null && curr.right.compareTo(curr.left) < 0) isRight = true;
            if (isRight) {
                if (curr.compareTo(curr.right) > 0) {
                    swap(curr, curr.right);
                    locator.put(curr.right.key, curr.right);
                    locator.put(curr.key, curr);
                    curr = curr.right;
                } else break;
            } else {
                if (curr.compareTo(curr.left) > 0) {
                    swap(curr, curr.left);
                    locator.put(curr.left.key, curr.left);
                    locator.put(curr.key, curr);
                    curr = curr.left;
                } else break;
            }
        }
    }

    private void swap(Node<K> node1, Node<K> node2) {
        K temp = node1.key;
        node1.key = node2.key;
        node2.key = temp;
    }

    /**
     * Prints to the console a visual representation of this
     * tree, relying on the nested class BTreePrinter
     */
    public void printAsDiagram() {
        BTreePrinter.printNode(root);
    }

    static class Node<K extends Comparable<K>> implements Comparable<Node<K>> {
        Node<K> left;
        Node<K> right;
        Node<K> parent;
        K key;

        @Override
        public int compareTo(Node<K> node) {
            return this.key.compareTo(node.key);
        }

        @Override
        public String toString() {
            return "" + key;
        }
    }

    /**
     * Specialized nested class to produce
     * a visual image of this tree. This is
     * third-party code. To access the functionality
     * of this class, make a call to the static method
     * printNode like this:  printNode(root)
     */
    static class BTreePrinter {
        public static void printNode(Node root) {
            int maxLevel = BTreePrinter.maxLevel(root);
            printNodeInternal(
                    Collections.singletonList(root), 1, maxLevel);
        }

        @SuppressWarnings({"rawtypes", "unchecked"})
        private static void printNodeInternal(List nodes,
                                              int level, int maxLevel) {
            if (nodes.isEmpty() || BTreePrinter.isAllElementsNull(nodes))
                return;
            int floor = maxLevel - level;
            int endgeLines = (int) Math.pow(2, (Math.max(floor - 1, 0)));
            int firstSpaces = (int) Math.pow(2, (floor)) - 1;
            int betweenSpaces = (int) Math.pow(2, (floor + 1)) - 1;
            BTreePrinter.printWhitespaces(firstSpaces);
            List newNodes = new ArrayList();
            for (Object node : nodes) {
                if (node != null) {
                    System.out.print(((Node) node).key);
                    newNodes.add(((Node) node).left);
                    newNodes.add(((Node) node).right);
                } else {
                    newNodes.add(null);
                    newNodes.add(null);
                    System.out.print(" ");
                }

                BTreePrinter.printWhitespaces(betweenSpaces);
            }
            System.out.println();

            for (int i = 1; i <= endgeLines; i++) {
                for (int j = 0; j < nodes.size(); j++) {
                    BTreePrinter.printWhitespaces(firstSpaces - i);
                    if (nodes.get(j) == null) {
                        BTreePrinter.printWhitespaces(endgeLines + endgeLines + i + 1);
                        continue;
                    }

                    if (((Node) nodes.get(j)).left != null)
                        System.out.print("/");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(i + i - 1);

                    if (((Node) nodes.get(j)).right != null)
                        System.out.print("\\");
                    else
                        BTreePrinter.printWhitespaces(1);

                    BTreePrinter.printWhitespaces(endgeLines + endgeLines - i);
                }

                System.out.println();
            }

            printNodeInternal(newNodes, level + 1, maxLevel);
        }

        static void printWhitespaces(int count) {
            for (int i = 0; i < count; i++)
                System.out.print(" ");
        }

        static int maxLevel(Node node) {
            if (node == null)
                return 0;

            return Math.max(BTreePrinter.maxLevel(node.left), BTreePrinter.maxLevel(node.right)) + 1;
        }

        @SuppressWarnings("rawtypes")
        static boolean isAllElementsNull(List list) {
            for (Object object : list) {
                if (object != null)
                    return false;
            }

            return true;
        }
    }
}
