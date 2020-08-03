package com.yuluo.spring.factory;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/26 23:33
 * @Version V1.0
 */
public interface MyBeanFactory {

    Object getBean(String name);
}
