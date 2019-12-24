package com.demo.java8;

import com.demo.java8.Employee.Status;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TestStreamAPI3 {
	
	List<Employee> emps = Arrays.asList(
			new Employee(102, "李四", 79, 6666.66, Status.BUSY),
			new Employee(101, "张三", 18, 9999.99, Status.FREE),
			new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
			new Employee(104, "赵六", 8, 7777.77, Status.BUSY),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(104, "赵六", 8, 7777.77, Status.FREE),
			new Employee(105, "田七", 38, 5555.55, Status.BUSY)
	);

	//3. 终止操作
	/*
		归约  规约函数->
		reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
	@Test
	public void test1(){
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		Integer sum = list.stream()
			.reduce(0, (x, y) -> x + y);
		System.out.println(sum);

		//工资求和（可配合filter）
		Optional<Double> reduce = emps.stream().map(Employee::getSalary).reduce((x, y) -> x + y);
		System.out.println("规约求和："+reduce.get());

		Optional<Double> reduce1 = emps.stream()
				.filter((e) -> e.getAge() > 18)
				.map(Employee::getSalary)
				.reduce((x, y) -> x + y);
		System.out.println("先过滤再规约求和："+reduce1.get());


		System.out.println("----------------------------------------");
		
		Optional<Double> op = emps.stream()
			.map(Employee::getSalary)
			.reduce(Double::sum);
		
		System.out.println(op.get());
	}
	
	//需求：搜索名字中 “六” 出现的次数
	@Test
	public void test2(){
		Optional<Integer> sum = emps.stream()
			.map(Employee::getName)
			.flatMap(TestStreamAPI1::filterCharacter)
			.map((ch) -> {
				if(ch.equals('六'))
					return 1;
				else
					return 0;
			}).reduce(Integer::sum);
		
		System.out.println(sum.get());
	}


	//collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
	@Test
	public void test3(){
		System.out.println("-----------------收集到list-----------------");
		//收集到list
		List<String> list = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toList());
		
		list.forEach(System.out::println);
		Set set1 = new HashSet();
		list.forEach((x)->set1.add(x));
		System.out.println(set1);
		System.out.println("-----------------收集到set-----------------");

		//收集到set
		Set<String> set = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toSet());
		
		set.forEach(System.out::println);

		System.out.println("--------------收集到指定类型的容器对象--------------------");

		//收集到指定类型的容器对象
		HashSet<String> hs = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.toCollection(HashSet::new));
		
		hs.forEach(System.out::println);

		System.out.println("--------------收集到指定类型的容器对象--------------------");
		Set<String> collect = emps.stream().map(Employee::getName).collect(Collectors.toCollection(LinkedHashSet::new));
		collect.forEach(System.out::println);

	}
	
	@Test
	public void test4(){
		//最大值的值
		Optional<Double> max = emps.stream()
			.map(Employee::getSalary)
			.collect(Collectors.maxBy(Double::compare));

		System.out.println(max.get());

		//最小值的对象
		Optional<Employee> op = emps.stream()
			.collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
		
		System.out.println(op.get());

		//求和
		Double sum = emps.stream()
			.collect(Collectors.summingDouble(Employee::getSalary));
		
		System.out.println(sum);

		//平均值
		Double avg = emps.stream()
			.collect(Collectors.averagingDouble(Employee::getSalary));
		
		System.out.println(avg);
		//count
		Long count = emps.stream()
			.collect(Collectors.counting());
		
		System.out.println(count);
		
		System.out.println("--------------------------------------------");
		
		DoubleSummaryStatistics dss = emps.stream()
			.collect(Collectors.summarizingDouble(Employee::getSalary));
		
		System.out.println(dss.getMax());
	}
	
	//分组
	@Test
	public void test5(){
		Map<Status, List<Employee>> map = emps.stream()
			.collect(Collectors.groupingBy(Employee::getStatus));
		
		System.out.println(map);


	}
	
	//多级分组
	@Test
	public void test6(){
		Map<Status, Map<String, List<Employee>>> map = emps.stream()
			.collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
				if(e.getAge() >= 60)
					return "老年";
				else if(e.getAge() >= 35)
					return "中年";
				else
					return "成年";
			})));
		
		System.out.println(map);
	}
	
	//分区
	@Test
	public void test7(){
		Map<Boolean, List<Employee>> map = emps.stream()
			.collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
		
		System.out.println(map);
	}
	
	//
	@Test
	public void test8(){
		String str = emps.stream()
			.map(Employee::getName)
			.collect(Collectors.joining("," , "----", "----"));
		
		System.out.println(str);
	}
	
	@Test
	public void test9(){
		Optional<Double> sum = emps.stream()
			.map(Employee::getSalary)
			.collect(Collectors.reducing(Double::sum));
		
		System.out.println(sum.get());
	}

	//类似mysql聚合函数
	@Test
	public void test10(){
		DoubleSummaryStatistics collect = emps.stream().collect(Collectors.summarizingDouble(Employee::getSalary));
		System.out.println(collect.getSum());
		System.out.println(collect.getAverage());
		System.out.println(collect.getCount());
		System.out.println(collect.getMax());
	}

	//连接字符串
	@Test
	public void test11(){
		String collect = emps.stream().map(Employee::getName).collect(Collectors.joining(","));
		System.out.println(collect);
	}


}
