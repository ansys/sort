package com.trgroup.sort.impl;

import com.trgroup.sort.ISorter;


/**
 *  min:O(n)
 *  max:O(n^2)
 *  avg:O(n^2)
 */
public class BubbleSorter implements ISorter {
    @Override
    public int[] sort(int[] inputArray) {
        int arraySize = inputArray.length;
        for (int i = 0; i < arraySize - 1; i++) {
            for (int j = 0; j < arraySize - 1 - i; j++) {
                if (inputArray[j] > inputArray[j + 1]) {
                    int temp = inputArray[j];
                    inputArray[j] = inputArray[j + 1];
                    inputArray[j + 1] = temp;
                }
            }
        }
        return inputArray;
    }
}
