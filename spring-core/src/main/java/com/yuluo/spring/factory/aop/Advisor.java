package com.yuluo.spring.factory.aop;

import java.lang.reflect.Method;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/8/2 20:44
 * @Version V1.0
 */
public class Advisor {

    private Method method ;
    private Object aspect ;
    private String beanName ;
    private Class<?> clazz ;
    private String expression;
    private String aspectName ;

    public String getAspectName() {
        return aspectName;
    }

    public void setAspectName(String aspectName) {
        this.aspectName = aspectName;
    }

    public Advisor(Method method, String beanName, Class<?> clazz ,String pointcut) {
        this.method = method;
        this.beanName = beanName;
        this.clazz = clazz;
        this.expression = pointcut;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getAspect() {
        return aspect;
    }

    public void setAspect(Object aspect) {
        this.aspect = aspect;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
