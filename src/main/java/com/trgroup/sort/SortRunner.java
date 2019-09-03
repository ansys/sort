package com.trgroup.sort;

import com.trgroup.aop.CglibProxy;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
@Slf4j
public class SortRunner {
    public static void main(String[] args) {

        int[] array = ThreadLocalRandom.current().ints(0, 100000).limit(100000).toArray();
        log.info(Arrays.stream(array).mapToObj(s -> String.valueOf(s)).collect(Collectors.joining(",", "[", "]")));
        CglibProxy proxy=new CglibProxy();
        BubbleSort bubbleSorter=(BubbleSort)proxy.getProxy(BubbleSort.class);
        int[] resultArray = bubbleSorter.bubbleSort(array);
        log.info(Arrays.stream(resultArray).mapToObj(s -> String.valueOf(s)).collect(Collectors.joining(",", "[", "]")));
    }
}
