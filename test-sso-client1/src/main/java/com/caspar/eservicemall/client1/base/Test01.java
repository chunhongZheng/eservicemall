package com.caspar.eservicemall.client1.base;

import java.util.Arrays;
import java.util.List;

public class Test01 {
    public static void main(String[] args) {
        //“::”是Java 8 引入的新特性之一，常常被称作为方法引用，提供了一种不执行方法的方法。 方法的引用
        String[] array = {"aaaa", "bbbb", "cccc"};
        Arrays.asList(array).forEach(x-> System.out.println(x));
        System.out.println("----------------");
        //使用“::”方法引用
        //“::”可以省略某些情况的lambda
        Arrays.asList(array).forEach(System.out::println);
        System.out.println("---引用静态方法,通过类名+::+方法名的方式-------------");
        //引用静态方法,通过类名+::+方法名的方式
        Arrays.asList(array).forEach(Test01::print);
    }
    public static void print(String s){
        System.out.println(s);
    }
}
