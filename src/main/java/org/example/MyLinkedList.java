package org.example;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyLinkedList<T> {
    private Node<T> first;
    private Node<T> last;
    private int size;

    public MyLinkedList() {
    }

    public MyLinkedList(Collection<? extends T> collection) {
        this();
        this.addAll(collection);
    }

    public void add(T value) {
        size++;
        Node<T> currentLast = last;
        Node<T> newNode = new Node<>(value, null, null);
        if (currentLast == null) {
            last = newNode;
            first = newNode;
        } else {
            currentLast.next = newNode;
            newNode.prev = currentLast;
            last = newNode;
        }
    }

    public void add(int index, T value) {
        indexCheck(index);
        size++;
        Node<T> currentNode = first;
        Node<T> newNode = new Node<>(value, null, null);
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                Node<T> currentPrev = currentNode.prev;
                if (currentPrev != null) {
                    currentPrev.next = newNode;
                    newNode.prev = currentPrev;
                }
                currentNode.prev = newNode;
                newNode.next = currentNode;
                break;
            }
            currentNode = currentNode.next;
        }
    }

    public T get(int index) {
        indexCheck(index);
        Node<T> currentNode = first;
        Node<T> resultNode = null;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                resultNode = currentNode;
                break;
            }
            currentNode = currentNode.next;
        }
        return resultNode.value;
    }

    public T remove(int index) {
        indexCheck(index);
        size--;
        Node<T> currentNode = first;
        Node<T> resultNode = null;
        for (int i = 0; i <= index; i++) {
            if (i == index) {
                resultNode = currentNode;
                Node<T> currentPrev = currentNode.prev;
                Node<T> currentNext = currentNode.next;
                if (currentPrev == null) {
                    if (currentNext == null) {
                        first = null;
                        last = null;
                    } else {
                        first = currentNext;
                    }
                } else {
                    if (currentNext == null) {
                        currentPrev.next = null;
                        last = currentPrev;
                    } else {
                        currentPrev.next = currentNext;
                        currentNext.prev = currentPrev;
                    }
                }
                break;
            }
            currentNode = currentNode.next;
        }
        return resultNode.value;
    }

    public void addAll(Collection<? extends T> collection) {
        for (T t : collection) {
            add(t);
        }
    }

    public static <T extends Comparable<? super T>> void sort(MyLinkedList<T> myLinkedList) {
        boolean isSorted = false;
        int startLength = myLinkedList.size;
        while(!isSorted) {
            Node<T> currentNode = myLinkedList.first;
            isSorted = true;
            startLength--;
            for (int i = 0; i < startLength; i++) {
                T obj1 = currentNode.value;
                Node<T> nextNode = currentNode.next;
                T obj2 = nextNode.value;
                if (obj1.compareTo(obj2) > 0) {
                    isSorted = false;
                    myLinkedList.swapNodes(currentNode, nextNode);
                }
                currentNode = currentNode.next;
            }
        }
    }

    private void swapNodes(Node<T> currentNode, Node<T> nextNode) {
        T currentValue = currentNode.value;
        currentNode.value = nextNode.value;
        nextNode.value = currentValue;
    }


    private List<Object> getAllValues() {
        List<Object> result = new ArrayList<>();
        if (first != null) {
            result.add(first.value);
            Node<T> next = first.next;
            while (next != null) {
                result.add(next.value);
                next = next.next;
            }
        }
        return result;
    }

    public int getSize() {
        return size;
    }

    @Override
    public String toString() {
        return String.format("MyLinkedList%s", getAllValues()) ;
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(String.format("index %s, size %s", index, size));
        }
    }

    private static class Node<T> {
        private T value;
        private Node<T> prev;
        private Node<T> next;

        public Node(T value, Node<T> prev, Node<T> next) {
            this.value = value;
            this.prev = prev;
            this.next = next;
        }
    }
}
