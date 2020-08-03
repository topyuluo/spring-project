package com.yuluo.spring.factory.aop;

import com.yuluo.spring.factory.MyAbstractAutowireCapableBeanFactory;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Description 织入方法的配置信息类
 * @Author 蚂蚁不是ant
 * @Date 2020/8/2 0:18
 * @Version V1.0
 */
public class MyAdvisedSupport {

    //保存方法的配置信息
    private transient Map<Method, List<Advisor>> cacheMap ;

    private Class<?> targetClass;
    private Object target;

    public MyAdvisedSupport(Class<?> targetClass, Object target ,  Map<Method, List<Advisor>> advisorsCache) {
        this.targetClass = targetClass;
        this.target = target;
        this.cacheMap = advisorsCache;
    }

    private MyAbstractAutowireCapableBeanFactory beanFactory;


    public Class<?> getTargetClass() {
        return targetClass;
    }

    public void setTargetClass(Class<?> targetClass) {
        this.targetClass = targetClass;
    }

    public Object getTargetObject() {

        return this.target;
    }

    /**
     * 获取代理对象方法执行之前的调用链
     *
     * @param method
     * @param targetClass
     * @return
     */
    public List<Advisor> getAdviceList(Method method, Class<?> targetClass) {
        List<Advisor> list = this.cacheMap.get(method);
        return list;
    }

}


