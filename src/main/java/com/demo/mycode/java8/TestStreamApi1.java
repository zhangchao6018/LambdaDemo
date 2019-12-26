package com.demo.mycode.java8;

import com.demo.java8.Employee;
import com.demo.java8.TestStreamAPI1;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description:
 *  * 一、 Stream 的操作步骤
 *  *
 *  * 1. 创建 Stream
 *  *
 *  * 2. 中间操作
 *  *
 *  * 3. 终止操作
 * @Author: zhangchao
 * @Date: 2019-12-20 07:47
 **/
public class TestStreamApi1 {
    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    @Test
    public void test1(){

        emps.stream().map(Employee::getName).limit(2).forEach(System.out::println);
        System.out.println("-----------------");

        List<String> strList = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");
        strList.stream().map(String::toUpperCase).forEach(System.out::println);
        System.out.println("-----------------");

        Stream<Stream<Character>> streamStream = strList.stream().map(TestStreamAPI1::filterCharacter);
        streamStream.forEach((sm)->{
            sm.forEach(System.out::println);
        });
    }


    public static Stream<Character> filterCharacter(String str){
        List<Character> list = new ArrayList<>();
        for (char c : str.toCharArray()) {
            list.add(c);
        }
        return list.stream();
    }
}
