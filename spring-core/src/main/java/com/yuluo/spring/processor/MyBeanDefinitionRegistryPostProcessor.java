package com.yuluo.spring.processor;

import com.yuluo.spring.factory.MyBeanDefinitionRegistry;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 18:34
 * @Version V1.0
 */
public interface MyBeanDefinitionRegistryPostProcessor extends MyBeanFactoryPostProcessor {
    void postProcessBeanDefinitionRegistry(MyBeanDefinitionRegistry registry);
}
