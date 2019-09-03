package com.trgroup.sort;

import com.trgroup.aop.AOPHandler;
import com.trgroup.aop.CglibProxy;
import com.trgroup.sort.impl.BubbleSorter;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

@Slf4j
public class SortRunner {
    public static void main(String[] args) {
        //生成随机数组
        final int[] originArray = ThreadLocalRandom.current().ints(0, 1000).limit(1000).toArray();
        int[] array1 = Arrays.copyOf(originArray,originArray.length);
        int[] array2 = Arrays.copyOf(originArray,originArray.length);
        //reflect
        BubbleSorter bubbleSorter1=new BubbleSorter();
        AOPHandler handler=new AOPHandler(bubbleSorter1);
        log.info(Arrays.stream(array1).mapToObj(s -> String.valueOf(s)).collect(Collectors.joining(",", "[", "]")));
        ISorter iSorter=(ISorter) Proxy.newProxyInstance(BubbleSorter.class.getClassLoader(),new Class[]{ISorter.class},handler);
        int[] result2=iSorter.sort(array1);
        log.info(Arrays.stream(result2).mapToObj(s -> String.valueOf(s)).collect(Collectors.joining(",", "[", "]")));


        log.info(Arrays.stream(array2).mapToObj(s -> String.valueOf(s)).collect(Collectors.joining(",", "[", "]")));
        //cglib
        CglibProxy proxy = new CglibProxy();
        BubbleSort bubbleSorter = (BubbleSort) proxy.getProxy(BubbleSort.class);
        int[] resultArray = bubbleSorter.bubbleSort(array2);
        log.info(Arrays.stream(resultArray).mapToObj(s -> String.valueOf(s)).collect(Collectors.joining(",", "[", "]")));
    }
}
