package com.txr.forlove.demo.java;

import com.alibaba.fastjson.JSON;
import com.txr.forlove.common.utils.MessageFormats;
import com.txr.forlove.domain.demo.UserDm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JavaEightDemo {
    private static final Logger LOGGER = LoggerFactory.getLogger(JavaEightDemo.class);

    public static void main(String[] args) {
//        paralleStreamDemo();
        collectDemo();
//        optionalDemo();
    }


    private static void paralleStreamDemo(){
//        串行add：10000条数据，耗时：34ms
//        并行add：4009条数据，耗时：4ms
//        加锁并行add：10000条数据，耗时：2ms
//        并行线程安全的add：10000条数据，耗时：5ms
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        List<Integer> list3 = new ArrayList<>();
        List<Integer> list4 = new ArrayList<>();
        Lock lock = new ReentrantLock();
        // IntStream.range ： for循环从int i=0,i<10000,i++
        long start = System.currentTimeMillis();
        IntStream.range(0, 10000).forEach(list1::add);
        System.out.println(MessageFormats.format("串行add：{}条数据，耗时：{}ms",list1.size(),(System.currentTimeMillis() - start)));
        start = System.currentTimeMillis();
        IntStream.range(0, 10000).parallel().forEach(list2::add);
        System.out.println(MessageFormats.format("并行add：{}条数据，耗时：{}ms",list2.size(),(System.currentTimeMillis() - start)));
        start = System.currentTimeMillis();
        IntStream.range(0, 10000).forEach(i -> {
            lock.lock();
            try {
                list3.add(i);
            }finally {
                lock.unlock();
            }
        });
        System.out.println(MessageFormats.format("加锁并行add：{}条数据，耗时：{}ms",list3.size(),(System.currentTimeMillis() - start)));
        // paralleStream里直接去修改变量是非线程安全的，但是采用collect和reduce操作就是满足线程安全的了。
        start = System.currentTimeMillis();
        // 也可以采用.collect(Collectors.groupingByConcurrent())
        IntStream.range(0, 10000).parallel().forEachOrdered(list4::add);
        System.out.println(MessageFormats.format("并行线程安全的add：{}条数据，耗时：{}ms",list4.size(),(System.currentTimeMillis() - start)));
    }

    private static void collectDemo(){
        List<UserDm> list = UserDm.createdList(20);
        // map ：遍历集合处理
        List<String> strings = list.stream().map(UserDm::getName).collect(Collectors.toList());
        LOGGER.info("strings = {}",strings.toString());
        // filter 过滤集合 - > listFilter = [{"id":2,"name":"name2"}]
        List<UserDm> listFilter = list.stream().filter(item -> item.getName().equals("name2")).collect(Collectors.toList());
        LOGGER.info("listFilter = {}", JSON.toJSONString(listFilter));
        // Arrays 跟Lists.newArrayList类似
        List<String> teamIndia = Arrays.asList("Virat", "Dhoni", "Jadeja");
        List<String> teamAustralia = Arrays.asList("Warner", "Watson", "Smith");
        // flatMap 可以处理多个集合，默认实现多CPU并行执行
        List<String> together = Stream.of(teamIndia, teamAustralia)
                .flatMap(names -> names.stream())
                .collect(Collectors.toList());
        // together = ["Virat","Dhoni","Jadeja","Warner","Watson","Smith"]
        LOGGER.info("together = {}", JSON.toJSONString(together));

        List<Integer> intList = Arrays.asList(1,2,3,3,5,9,7,6);
        System.out.println("intList = " + intList);
        // distinct去重
        intList = intList.stream().distinct().collect(Collectors.toList());
        System.out.println("distinct intList = " + intList);
        // sorted排序
        intList = intList.stream().sorted().collect(Collectors.toList());
        System.out.println("distinct sorted = " + intList);
        // limit限制数量
        intList = intList.stream().limit(3).collect(Collectors.toList());
        System.out.println("distinct limit = " + intList);
    }

    private static void optionalDemo(){
        UserDm userDm = null;
        // isPresent 为空 返回 false
        System.out.println(Optional.ofNullable(userDm).isPresent());
        // 为空抛异常
        try{
            Optional.of(userDm);
        }catch (NullPointerException npe){
            System.out.println(npe);
        }
        // 为空执行orElse，如果内容复杂可执行：orElseGet
        userDm = Optional.ofNullable(userDm).orElse(new UserDm(555L,"name555"));
        System.out.println(Optional.of(userDm).isPresent());
        System.out.println(MessageFormats.format("userDm={}",JSON.toJSONString(userDm)));
    }
}
