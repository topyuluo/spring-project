package com.yuluo.spring.factory;

import com.yuluo.spring.processor.MyConfigurationClassPostProcessor;

/**
 * @Description ApplicationContext的抽象接口，仅实现通用的上下文功能
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 15:07
 * @Version V1.0
 */
public abstract class MyAbstractApplicationContext implements MyConfigurableApplicationContext ,MyBeanFactory {

    /**
     * ioc 容器开始的入口
     */
    public void refresh() {
        //获取存放bean元数据的容器
        MyConfigurableApplicationContext beanFactory = obtainFreshBeanFactory();
        //钩子方法，针对不同的数据进行不同的操作
//        postProcessBeanFactory(beanFactory);

        //使用BeanFactoryPostProcessor 加载资源
        invokeBeanFactoryPostProcessors(beanFactory);

        //自定义数据进行初始化和注入
        finishBeanFactoryInitialization(beanFactory);
    }

    /**
     * 实例化元数据
     *
     * @param beanFactory
     */
    private void finishBeanFactoryInitialization(MyConfigurableApplicationContext beanFactory) {
        beanFactory.preInstantSingletons();
    }

    /**
     * 解析定义的配置文件，加载数据
     *
     * @param beanFactory
     */
    protected void invokeBeanFactoryPostProcessors(MyConfigurableApplicationContext beanFactory) {
        if (beanFactory instanceof MyBeanDefinitionRegistry) {
            MyBeanDefinitionRegistry registry = (MyBeanDefinitionRegistry) beanFactory;
            invokeBeanDefinitionRegistryPostProcessors(registry);
        }
    }

    /**
     * 执行具体的加载操作
     * 程序简化， 直接创建处理配置文件的处理器
     *
     * @param registry
     */
    private void invokeBeanDefinitionRegistryPostProcessors(MyBeanDefinitionRegistry registry) {
        MyConfigurationClassPostProcessor processor = new MyConfigurationClassPostProcessor();
        processor.postProcessBeanDefinitionRegistry(registry);
    }

    //钩子方法
    protected abstract void postProcessBeanFactory(MyConfigurableApplicationContext beanFactory);

    protected MyConfigurableApplicationContext obtainFreshBeanFactory() {
        return getBeanFactory();
    }

    /**
     * 获取父类的容器方法
     * @return
     */
    protected abstract MyConfigurableApplicationContext getBeanFactory();


    /**
     * 根据名字获取容器中的类
     * @param name
     * @return
     */
    @Override
    public Object getBean(String name) {
        return getBeanFactory().getBean(name);
    }

}
