package com.demo.mycode.lambda;

/**
 * @Description: 我的断言接口
 * @Author: zhangchao
 * @Date: 2019-12-16 00:28
 **/
public interface MyPredicate<T> {
    boolean predicate(T emp);
}
