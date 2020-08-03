package com.yuluo.spring.factory;

import com.yuluo.spring.annoation.MyAutowired;
import com.yuluo.spring.bean.MyBeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 23:11
 * @Version V1.0
 */
public abstract class MyAbstractBeanFactory extends MyDefaultSingletonBeanRegistry implements MyBeanFactory {

    public Object getBean(String beanName) {
        return doGetBean(beanName);
    }

    /**
     * 执行具体的创建逻辑
     * 1. 实例化
     * 2. 初始化
     * 3. 执行postBeanProcess 方法，bean的增强处理就在 initializaBean方法中执行
     * @param name
     * @param <T>
     * @return
     */
    private <T> T doGetBean(String name) {

        Object singleton = getSingleton(name);
        if (singleton != null) {
            return (T) singleton;
        }

        MyBeanDefinition mbd = getBeanDefinition(name);
        singleton = getSingleton(name, () -> createBean(name, mbd));
//        populateBean(name, mbd, singleton);
//        singleton = initializeBean(name, singleton, mbd);
        return (T) singleton;
    }

    protected abstract Object createBean(String name, MyBeanDefinition mbd);




//    /**
//     * 生成代理类
//     * @param wrapperBean
//     * @param name
//     * @param cacheKey
//     * @return
//     */
//    private Object wrapIfNecessary(Object wrapperBean, String name, Object cacheKey) {
//
//        return null;
//    }








    protected abstract MyBeanDefinition getBeanDefinition(String name);

    public Object getValue(Class<?> type) {
        String beanName = getBeanNameForType(type);
        return getBean(beanName);
    }

    protected abstract String getBeanNameForType(Class<?> type);
}
