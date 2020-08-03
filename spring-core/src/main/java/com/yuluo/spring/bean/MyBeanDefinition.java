package com.yuluo.spring.bean;

import com.yuluo.spring.util.StringUtils;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 15:09
 * @Version V1.0
 */
public class MyBeanDefinition {

    private Class<?> beanClasses;
    private String beanName ;
    private String factoryBean ;
    private String factoryMethod ;

    public MyBeanDefinition() {

    }

    public MyBeanDefinition(Class<?> clazz){
        setBeanClass(clazz);
    }

    public MyBeanDefinition(String beanName , String classPath){
        this.beanName = beanName ;
        try {
            this.beanClasses = Class.forName(classPath);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public MyBeanDefinition(String beanName , Class<?> clazz){
        this.beanName = beanName ;
        this.beanClasses = clazz ;
    }

    private void setBeanClass(Class<?> clazz) {
        setBeanClasses(clazz);
        this.beanName = StringUtils.toLowerFirstCase(clazz.getSimpleName());
    }

    private void setBeanClasses(Class<?> clazz) {
        this.beanClasses = clazz;
    }

    public Class<?> getBeanClasses() {
        return beanClasses;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getFactoryBean() {
        return factoryBean;
    }

    public void setFactoryBean(String factoryBean) {
        this.factoryBean = factoryBean;
    }

    public String getFactoryMethod() {
        return factoryMethod;
    }

    public void setFactoryMethod(String factoryMethod) {
        this.factoryMethod = factoryMethod;
    }
}
