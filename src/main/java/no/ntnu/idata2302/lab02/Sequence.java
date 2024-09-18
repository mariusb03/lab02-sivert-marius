/*
 * This file is part of NTNU's IDATA2302 Lab02.
 *
 * Copyright (C) NTNU 2022
 * All rights reserved.
 *
 */
package no.ntnu.idata2302.lab02;

import java.util.ArrayList;

/**
 * Implement the Sequence ADT from Lecture 2.2
 *
 * The items in the sequence are indexed from 1 (as opposed to Java arrays that
 * are indexed from 0)
 */
public class Sequence {

    private static final int INITIAL_CAPACITY = 100;

    private int capacity;
    private int length;
    private int[] items;

    public Sequence() {
        this(INITIAL_CAPACITY, new int[]{});
    }

    public Sequence(int capacity, int[] items) {
        if (capacity < items.length) {
            throw new IllegalArgumentException("Capacity must be greater than item count");
        }
        this.capacity = capacity;
        this.length = items.length;
        this.items = new int[capacity];
        for (int i=0 ; i<items.length ; i++) {
            this.items[i] = items[i];
        }
    }

    /**
     * @return The number of items in the sequence
     */
    public int getLength() {
        return this.length;
    }

    /**
     * @return the number of "buckets" currently allocated
     */
    int getCapacity() {
        return this.capacity;
    }

    /**
     * Return the item stored at the given index
     *
     * @param index the index of the desired item, starting at 1
     * @return the item at the given index.
     */
    public int get(int index) {
        if (index < 1 || index > length) {
            throw new IllegalArgumentException("Invalid index!");
        }
        return this.items[index - 1];
    }

    /**
     * Append the given item at the end of the sequence
     *
     * @param item the item that must be inserted
     */
    public void insert(int item, int index) {
        if (index < 1 || index > this.length + 1) {
            throw new IllegalArgumentException("Invalid index!");
        }

        if (length == capacity) {
            doubleArraySize();
        }

        if (this.length == this.capacity) {
            doubleArraySize();
        }
        int zeroBasedIndex = index - 1;

        for (int i = this.length - 1; i >= zeroBasedIndex; i--) {
            this.items[i + 1] = this.items[i];
        }
        this.items[zeroBasedIndex] = item;
        this.length++;
    }


    private void doubleArraySize() {
        int newCapacity = capacity * 2;
        int[] newItems = new int[newCapacity];

        for (int i = 0; i < this.length; i++) {
            newItems[i] = this.items[i];
        }
        this.items = newItems;
        this.capacity = newCapacity;
    }


    /**
     * Remove the index at the given index
     *
     * @param index the index that must be removed.
     */
    public void remove(int index) {
        if (length == 0) {
            throw new IllegalArgumentException("Cannot remove from an empty sequence");
        }
        if (index < 1 || index > length) {
            throw new IllegalArgumentException("Invalid index!");
        }
        int zeroBasedIndex = index - 1;
        for (int i = zeroBasedIndex; i < length - 1; i++) {
            items[i] = items[i + 1];
        }
        items[length - 1] = 0;
        length--;
        if (length > 0 && (length <= capacity / 4)) {
            halveArraySize();
        }
    }

    private void halveArraySize() {
        int newCapacity = capacity / 2;
        int[] newItems = new int[newCapacity];

        for (int i = 0; i < length; i++) {
            newItems[i] = items[i];
        }
        items = newItems;
        capacity = newCapacity;
    }

    /**
     * Find a index where the given item can be found. Returns 0 if that item cannot
     * be found.
     *
     * @param item the item whose index must be found
     * @return an
     */
    public int search(int item) {
        for (int i = 0; i < length; i++) {
            if (items[i] == item) {
                return i + 1;
            }
        }
        return 0;
    }

    /**
     * Find both the smallest and the largest items in the sequence.
     *
     * @return an array of length two where the first entry is the minimum and the
     *         second the maximum
     */
    public int[] extrema() {
        if (length == 0) {
            throw new IllegalStateException("Sequence is empty!");
        }

        int min = items[0];
        int max = items[0];

        for (int i = 1; i < length; i++) {
            if (items[i] < min) {
                min = items[i];
            }
            if (items[i] > max) {
                max = items[i];
            }
        }

        return new int[]{min, max};
    }

    /**
     * Check whether the given sequence contains any duplicate item
     *
     * @return true if the sequence has the the same items at multiple indices
     */
    public boolean hasDuplicate() {
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (items[i] == items[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Convert the sequence into an Java array
     */
    public int[] toArray() {
        return items;
    }

}
