package com.yuluo.spring.reader;

import com.yuluo.spring.bean.MyBeanDefinition;
import com.yuluo.spring.factory.MyBeanDefinitionRegistry;

/**
 * @Description 资源读取类
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 15:02
 * @Version V1.0
 */
public class MyAnnotatedBeanDefinitionReader {

    private MyBeanDefinitionRegistry registry ;


    public MyAnnotatedBeanDefinitionReader(MyBeanDefinitionRegistry registry){
        this.registry = registry;
    }

    public void register(Class<?>... annotatedClasses) {
        for (Class<?> annotatedClass : annotatedClasses) {
            registerBean(annotatedClass);
        }
    }

    private void registerBean(Class<?> annotatedClass) {
        doRegisterBean(annotatedClass);
    }

    private void doRegisterBean(Class<?> annotatedClass) {
        MyBeanDefinition definition = new MyBeanDefinition(annotatedClass);
        this.registry.registerBeanDefinition(definition.getBeanName() , definition);
    }
}
