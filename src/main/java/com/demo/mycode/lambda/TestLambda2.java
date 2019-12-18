package com.demo.mycode.lambda;

import org.junit.Test;

import java.util.Comparator;
import java.util.function.Consumer;

/**
 * @Description:
 * （一）
 * Lambda基础语法
 * ->操作符
 * 左侧 参数列表
 * 右侧 所需执行的功能
 *
 * 语法格式一
 *  无参无返回值
 *      ()->System.out.pringln("...")；
 *
 * 语法格式二：有一个参数，无返回值
 *
 * 语法格式三：只有一个参数，参数小括号可以省略不写
 *        Consumer<String> consumer = x-> System.out.println(x);
 *
 * 语法格式四：多个参数，lambda体重中有多条语句，有返回值
 *
 * 语法格式五：如果只有一条语句，{}和return 都可以省略不写
 *
 * 语法格式六：可以省略不写的原因
 *      jvm编译器 上下文类型推断
 *
 * （二）
 * Lambda表达式需要函数式接口支持
 *
 *      函数式接口： 接口中只有一个抽象方法的接口，称为函数式接口
 *  @FunctionalInterface 修饰
 *
 * @Author: zhangchao
 * @Date: 2019-12-16 08:18
 **/
public class TestLambda2 {
    @Test
    public void test1(){
        int num  = 0;//jdk7以前必须是final修饰，java8省略了
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("hello world!"+num);
            }
        };

        r.run();

        System.out.println("------------");
        Runnable r2 = ()-> System.out.println("hello lambda!"+num);
        r2.run();
    }

    @Test
    public void test2(){
        Consumer<String> consumer = x-> System.out.println(x);
        consumer.accept("hello xxx");
    }

    @Test
    public void test3(){
        Comparator<Integer> com = (x,y)->{
            System.out.println(x);
            System.out.println(y);
            return Integer.compare(x,y);
        };
    }


    // 表达式  --实现接口
    @Test
    public void test4(){
        Integer operation = operation(2, (x) -> x * x);
        System.out.println(operation);
    }
    public Integer operation(Integer num , MyFun myFun){
        return myFun.getResult(num);
    }
}
