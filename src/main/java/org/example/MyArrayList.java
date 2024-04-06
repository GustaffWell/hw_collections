package org.example;

import java.util.Arrays;
import java.util.Collection;

public class MyArrayList<T> {
    private Object[] array;
    private static final int DEFAULT_ARRAY_LENGTH = 10;
    private int size;

    public MyArrayList() {
        array = new Object[DEFAULT_ARRAY_LENGTH];
        size = 0;
    }

    public MyArrayList(int initialArrayLength) {
        if (initialArrayLength >= 0) {
            array = new Object[initialArrayLength];
        } else {
            throw new IllegalArgumentException("Length cannot be less than zero");
        }
    }

    public MyArrayList(Collection<? extends T> collection) {
        this();
        addAll(collection);
    }

    public void add(T object) {
        if (size == array.length) {
            this.increaseArray();
        }
        array[size] = object;
        size++;
    }

    public void add(int index, T object) {
        if (size == array.length) {
            this.increaseArray();
        }
        indexCheck(index);
        Object[] newArray = new Object[array.length];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index, newArray, index + 1, size - index);
        newArray[index] = object;
        array = newArray;
        size++;
    }

    public T get(int index) {
        indexCheck(index);
        return (T) array[index];
    }

    public T remove(int index) {
        indexCheck(index);
        T object = (T) array[index];
        Object[] newArray = new Object[array.length];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index , size - index);
        array = newArray;
        size--;
        return object;
    }

    public void addAll(Collection<? extends T> collection) {
        Object[] colArray = collection.toArray();
        int newLength = size + colArray.length;
        Object[] newArray;
        if (newLength > array.length) {
            newArray = new Object[newLength];
        } else {
            newArray = new Object[array.length];
        }
        System.arraycopy(array, 0, newArray, 0, size);
        System.arraycopy(colArray, 0, newArray, size, colArray.length);
        array = newArray;
        size += colArray.length;
    }
    
    public static <T extends Comparable<? super T>> void sort(MyArrayList<T> myArrayList) {
        Object[] array = myArrayList.array;
        boolean isSorted = false;
        int startLength = myArrayList.size;
        while(!isSorted) {
            isSorted = true;
            startLength--;
            for (int i = 0; i < startLength; i++) {
                T obj1 = (T) array[i];
                T obj2 = (T) array[i + 1];
                if (obj1.compareTo(obj2) > 0) {
                    isSorted = false;
                    array[i] = obj2;
                    array[i + 1] =  obj1;
                }
            }
        }
    }

    private void increaseArray() {
        int newLength = (int) (array.length * 1.5);
        this.array = Arrays.copyOf(array, newLength);
    }

    private void indexCheck(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException(String.format("index %s, size %s", index, size));
        }
    }

    @Override
    public String toString() {
        return "MyArrayList" + Arrays.toString(Arrays.copyOf(array, size));
    }
}
