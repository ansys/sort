package com.trgroup.sort;

import com.trgroup.aop.CglibProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class BubbleSort {

    /**
    * 1.冒泡
    */
    //2,5,11,3,55,6,234,111,9,1
    public int[] bubbleSort(int[] inputArray) {
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
