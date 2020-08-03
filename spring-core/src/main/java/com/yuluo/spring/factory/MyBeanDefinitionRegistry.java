package com.yuluo.spring.factory;

import com.yuluo.spring.bean.MyBeanDefinition;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 15:08
 * @Version V1.0
 */
public interface MyBeanDefinitionRegistry {
    void registerBeanDefinition(String beanName, MyBeanDefinition beanDefinition);


    void removeBeanDefinition(String beanName) ;


    MyBeanDefinition getBeanDefinition(String beanName);


    boolean containsBeanDefinition(String beanName);


    String[] getBeanDefinitionNames();


    int getBeanDefinitionCount();


    boolean isBeanNameInUse(String beanName);
}
