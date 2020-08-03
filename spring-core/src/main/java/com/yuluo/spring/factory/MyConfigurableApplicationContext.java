package com.yuluo.spring.factory;

public interface MyConfigurableApplicationContext extends MyBeanFactory{
    void preInstantSingletons();
}
