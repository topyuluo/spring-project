package com.yuluo.spring.factory;

import com.yuluo.spring.bean.MyBeanDefinition;

/**
 * @Description 通用应用上下文，内部持有一个DefaultListableBeanFactory实例 实现了BeanDefinitionRegistry接口，
 *   可以使用bean definition 读取
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 15:04
 * @Version V1.0
 */
public class MyGenericApplicationContext extends MyAbstractApplicationContext implements MyBeanDefinitionRegistry  {

    private final MyDefaultListableBeanFactory beanFactory;


    public MyGenericApplicationContext() {
        this.beanFactory = new MyDefaultListableBeanFactory();
    }

    @Override
    public void registerBeanDefinition(String beanName, MyBeanDefinition beanDefinition) {
        this.beanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

    @Override
    public void removeBeanDefinition(String beanName) {

    }

    @Override
    public MyBeanDefinition getBeanDefinition(String beanName) {
        return null;
    }

    @Override
    public boolean containsBeanDefinition(String beanName) {
        return false;
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return new String[0];
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
    protected void postProcessBeanFactory(MyConfigurableApplicationContext beanFactory) {

    }

    @Override
    protected MyConfigurableApplicationContext getBeanFactory() {
        return this.beanFactory;
    }

    @Override
    public void preInstantSingletons() {

    }
}
