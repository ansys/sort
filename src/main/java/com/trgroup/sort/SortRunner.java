package com.trgroup.sort;

import com.trgroup.aop.AOPHandler;
import com.trgroup.aop.CglibProxy;
import com.trgroup.sort.impl.BubbleSorter;
import com.trgroup.sort.impl.SelectionSorter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
public class SortRunner {
    public static final int[] originArray = ThreadLocalRandom.current().ints(0, 1000).limit(1000).toArray();

    public static void main(String[] args) {
        SortRunner.testBubbleSort();
        printBlankLine();
        SortRunner.testBubbleSortCglib();
        printBlankLine();
        SortRunner.testSelectionSort();
        printBlankLine();
    }

    public static void testBubbleSort() {
        int[] array = Arrays.copyOf(originArray, originArray.length);
        BubbleSorter bubbleSorter = new BubbleSorter();
        AOPHandler handler = new AOPHandler(bubbleSorter);
        printArray(array);
        ISorter iSorter = (ISorter) Proxy.newProxyInstance(BubbleSorter.class.getClassLoader(), new Class[]{ISorter.class}, handler);
        int[] sortedArray = iSorter.sort(array);
        printArray(sortedArray);
    }

    public static void testBubbleSortCglib() {
        int[] array = Arrays.copyOf(originArray, originArray.length);
        CglibProxy proxy = new CglibProxy();
        BubbleSort bubbleSort = (BubbleSort) proxy.getProxy(BubbleSort.class);
        printArray(array);
        int[] sortedArray = bubbleSort.sort(array);
        printArray(sortedArray);
    }


    public static void testSelectionSort() {
        int[] array = Arrays.copyOf(originArray, originArray.length);
        SelectionSorter selectionSorter = new SelectionSorter();
        AOPHandler handler = new AOPHandler(selectionSorter);
        printArray(array);
        ISorter iSorter = (ISorter) Proxy.newProxyInstance(SelectionSorter.class.getClassLoader(), new Class[]{ISorter.class}, handler);
        int[] sortedArray = iSorter.sort(array);
        printArray(sortedArray);
    }

    private static void printArray(int[] array) {
        log.info(Arrays.stream(array).mapToObj(s -> String.valueOf(s)).collect(Collectors.joining(",", "[", "]")));
    }

    private static void printBlankLine() {
        System.out.println("-------------------------------");

    }
}
