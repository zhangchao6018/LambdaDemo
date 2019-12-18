package com.demo.mycode.lambda;

import com.demo.lambda.Employee;
import org.junit.Test;

import java.util.*;

/**
 * @Description:
 * 1.lambda
 *
 * @Author: zhangchao
 * @Date: 2019-12-15 23:56
 **/
public class TestLambda {
    //原来的匿名内部类
    @Test
    public void test1()
    {
        Comparator<Integer> com = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(01, 02);
            }
        };
        TreeSet<Integer> ts = new TreeSet<>(com);
    }
    //lambda
    @Test
    public void test2()
    {
        Comparator<Integer> com = (a,b)-> Integer.compare(a,b);
        TreeSet<Integer> ts = new TreeSet<>(com);
    }


    List<Employee> emps = Arrays.asList(
            new Employee(101, "张三", 18, 9999.99),
            new Employee(102, "李四", 59, 6666.66),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );

    //需求：获取公司中年龄小于 35 的员工信息
    public List<Employee> filterEmployeeAge(List<Employee> emps)
    {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : emps) {
            if ((emp.getAge()<35)) {
                result.add(emp);
            }
        }
        return result;
    }


    //需求：获取公司中工资大于 5000 的员工信息
    public List<Employee> filterEmployeeSalary(List<Employee> emps)
    {
        List<Employee> result = new ArrayList<>();
        for (Employee emp : emps) {
            if ((emp.getSalary()>5000)) {
                result.add(emp);
            }
        }
        return result;
    }
    //等等类似需求....



    /**
     * 优化一改造之：设计模式（策略模式）
     * @param emps
     * @param myPredicate
     * @return
     */
    public List<Employee> filterEmployeeDesign(List<Employee> emps,MyPredicate<Employee> myPredicate)
    {
        List<Employee> result = new ArrayList<>();
        for (Employee emp :  emps) {
            if (myPredicate.predicate(emp)) {
                result.add(emp);
            }
        }
        return result;
    }

    @Test
    public void test3(){

        for (Employee employee : filterEmployeeAge(emps)) {
            System.out.println(employee);
        }
        System.out.println("------");
        for (Employee employee : filterEmployeeSalary(emps)) {
            System.out.println(employee);
        }
        System.out.println("-----设计模式,只用实现MyPredicate方法增量式写接口就行------");

        for (Employee employee : filterEmployeeDesign(emps, new FilterByAge())) {
            System.out.println(employee);
        }
        System.out.println("-----------");
        for (Employee employee : filterEmployeeDesign(emps, new FilterBySalary())) {
            System.out.println(employee);
        }
    }
    /**
     * 优化方式二，匿名内部类（原来新增策略需要新增一个MyPredicate实现,内部类可以不用新增实现类）
     */
    @Test
    public void test4(){
        List<Employee> result = filterEmployeeDesign(emps, new MyPredicate<Employee>() {
            @Override
            public boolean predicate(Employee emp) {
                return emp.getSalary()>5000;
            }
        });
        for (Employee employee : result) {
            System.out.println(employee);
        }
    }

    //问题  上述内部类方式 有效代码只有一句 return emp.getSalary()>5000;

    /**
     * 优化方式三  lambda
     */
    @Test
    public void test5(){
        //filterEmployeeDesign方法需要制定泛型
        List<Employee> result = filterEmployeeDesign(emps, (emp) -> emp.getSalary() > 5000);
        //todo 打印
        result.forEach(System.out::println);
    }

    /**
     * 优化方式四（没有接口，没有实现类）  lambda.stream
     */
    @Test
    public void test6(){
        emps.stream().filter((e)->e.getSalary()>5000).forEach(System.out::println);

        System.out.println("取前面两个");
        emps.stream().filter((e)->e.getSalary()>5000).limit(2).forEach(System.out::println);
        System.out.println("将所有名字拿出来");
        emps.stream().map(Employee::getName).forEach(System.out::println);
    }

}
