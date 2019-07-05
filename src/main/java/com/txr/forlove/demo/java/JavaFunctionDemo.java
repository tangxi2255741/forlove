package com.txr.forlove.demo.java;

import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @Description：Java8函数式接口
 * @Author：T.X
 * @CreateTime：2019/6/15-18:01
*/
public class JavaFunctionDemo {

    // Consumer消费型接口，类似void方法；
    static Consumer<String> consumer = (x -> System.out.println("我的交通工具为：" + x));

    public static void main(String[] args) {
        String s = "飞机";
        Integer userId = 1;
        // 执行方法
        consumer.accept(s);
        consumer.accept("汽车");

        // Supplier<T>：供给型接口（T get（））只有返回值，没有入参
        Supplier<Integer> randomNum = (() -> new Random().nextInt(10));
        Consumer<Integer> printRandom = (x -> System.out.println("取到的随机数为：" + x));
        printRandom.accept(randomNum.get());

        // Function<T, R>：函数型接口（R apply（T t））输入一个类型得参数，输出一个类型得参数
        Function<Integer,String> intToString = (x -> "num_" + x);

        System.out.println("intToString : " + intToString.apply(20));
//        1）.BiFunction<T, U, R> 参数类型有2个，为T，U，返回值为R，其中方法为R apply(T t, U u)
//        2）.UnaryOperator<T>(Function子接口)参数为T，对参数为T的对象进行一元操作，并返回T类型结果，其中方法为T apply(T t)
//        3）.BinaryOperator<T>(BiFunction子接口)参数为T，对参数为T得对象进行二元操作，并返回T类型得结果，其中方法为T apply（T t1， T t2）
//        4）.BiConsumcr(T, U) 参数为T，U无返回值，其中方法为 void accept(T t, U u)
//        5）.ToIntFunction<T>、ToLongFunction<T>、ToDoubleFunction<T>参数类型为T，返回值分别为int，long，double，分别计算int，long，double得函数。
//        6）.IntFunction<R>、LongFunction<R>、DoubleFunction<R>参数分别为int，long，double，返回值为R。
    }
}
