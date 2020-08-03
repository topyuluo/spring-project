package com.yuluo.spring.factory;

import com.yuluo.spring.bean.MyBeanDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 15:06
 * @Version V1.0
 */
public class MyDefaultListableBeanFactory extends MyAbstractAutowireCapableBeanFactory implements MyConfigurableApplicationContext, MyBeanDefinitionRegistry {

    private final Map<String, MyBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private volatile List<String> beanDefinitionNames = new ArrayList<>(256);
    private final Map<Class<?>, String> allBeanNamesByType = new ConcurrentHashMap<>(64);


    /**
     * 向容器注册beanDefinition 信息
     * @param beanName
     * @param beanDefinition
     */
    @Override
    public void registerBeanDefinition(String beanName, MyBeanDefinition beanDefinition) {
        MyBeanDefinition definition = this.beanDefinitionMap.get(beanName);
        if (definition != null) {
//            throw new IllegalArgumentException("beanName + [ " + beanName + " ] , is exist !");
            return;
        }
        this.beanDefinitionMap.put(beanName, beanDefinition);
        this.beanDefinitionNames.add(beanName);
        Class<?> clazz = beanDefinition.getBeanClasses();
        if (clazz == null){
            return;
        }
        this.allBeanNamesByType.put(beanDefinition.getBeanClasses().getInterfaces().length == 0
                ? beanDefinition.getBeanClasses(): beanDefinition.getBeanClasses().getInterfaces()[0]
                , beanName);
    }

    @Override
    public void removeBeanDefinition(String beanName) {

    }

    @Override
    public MyBeanDefinition getBeanDefinition(String beanName) {
        MyBeanDefinition definition = this.beanDefinitionMap.get(beanName);
        return Optional.ofNullable(definition)
                .orElseThrow(() -> new IllegalArgumentException("beanName: [ " + beanName +" ] is not exist !"));
    }

    @Override
    protected String getBeanNameForType(Class<?> type) {
        return this.allBeanNamesByType.get(type);
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return beanDefinitionNames != null ? beanDefinitionNames.toArray(new String[0]) : new String[0];
    }

    @Override
    public int getBeanDefinitionCount() {
        return 0;
    }

    @Override
    public boolean isBeanNameInUse(String beanName) {
        return false;
    }

    @Override
    public void preInstantSingletons() {
        setBeanFactory(this);
        List<String> beanNames = new ArrayList<>(beanDefinitionNames);
        beanNames.forEach( beanName -> {
            getBean(beanName);
        });
    }



}
