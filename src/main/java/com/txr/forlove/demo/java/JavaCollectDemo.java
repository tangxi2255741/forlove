package com.txr.forlove.demo.java;

import java.util.IntSummaryStatistics;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

public class JavaCollectDemo {
    public static void main(String[] args) {

    }

    private static void stream(){
        /*=======================拓展IntStream,LongStream,DoubleStream==========================*/

        //1.1、mapToInt将Stream转换成IntStream， summaryStatistics是对IntStream数据进行汇总统计的方法，（LongStream,DoubleStream同理）
//        IntSummaryStatistics summary = numbers.stream().mapToInt(x ->x).summaryStatistics();
//        System.out.println(summary.getAverage());
//        System.out.println(summary.getCount());
//        System.out.println(summary.getMax());
//        System.out.println(summary.getMin());
//        System.out.println(summary.getSum());

        //1.2、IntStream,LongStream创建区间方式是一样的
        int[] range1 = IntStream.rangeClosed(13, 15).toArray();//生产区间 [a,b]      range1=[13,14,15]
        int[] range2 = IntStream.range(13, 15).toArray();//生产区间 [a,b)     range2=[13,14]
        double[] doubles = DoubleStream.of(5.33, 2.34, 5.32, 2.31, 3.51).toArray(); //doubles=[5.33, 2.34, 5.32, 2.31, 3.51]

        //1.2、IntStream的统计方法（LongStream,DoubleStream同理）
        double average = IntStream.range(13, 15).average().getAsDouble();//average=13.5
        int max = IntStream.range(13, 15).max().getAsInt(); //max=14
        int min = IntStream.range(13, 15).min().getAsInt(); //min=13
        int sum = IntStream.range(13, 15).sum(); //sum=27
    }
}
