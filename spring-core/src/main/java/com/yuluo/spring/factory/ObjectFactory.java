package com.yuluo.spring.factory;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 23:57
 * @Version V1.0
 */
@FunctionalInterface
public interface ObjectFactory<T> {

    T getObject() ;
}
