package com.demo.mycode.lambda;

import com.demo.lambda.Employee;

/**
 * @Description:
 * @Author: zhangchao
 * @Date: 2019-12-16 00:30
 **/
public class FilterByAge implements MyPredicate<Employee> {
    @Override
    public boolean predicate(Employee emp) {
        return emp.getAge()<35;
    }
}
