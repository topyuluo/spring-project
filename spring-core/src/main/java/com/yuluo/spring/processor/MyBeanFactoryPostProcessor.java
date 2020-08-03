package com.yuluo.spring.processor;

import com.yuluo.spring.factory.MyConfigurableApplicationContext;

public interface MyBeanFactoryPostProcessor {
    void postProcessBeanFactory(MyConfigurableApplicationContext beanFactory);
}
