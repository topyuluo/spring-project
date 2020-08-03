package com.yuluo.spring.factory.aop;

import com.yuluo.spring.bean.MyBeanDefinition;
import com.yuluo.spring.factory.MyAbstractAutowireCapableBeanFactory;
import com.yuluo.spring.factory.MyAbstractBeanFactory;
import com.yuluo.spring.factory.MyDefaultListableBeanFactory;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/8/1 18:53
 * @Version V1.0
 */
public class MyAnnoationAwareAspectJAtuoProxyCreator extends MyAbstractAutowireCapableBeanFactory {

    private MyDefaultListableBeanFactory beanFactory;

    public MyAnnoationAwareAspectJAtuoProxyCreator(MyDefaultListableBeanFactory beanFactory) {
        this.beanFactory = beanFactory;
        setBeanFactory(this.beanFactory);
    }


    /**
     * 处理器后置方法
     * @param obj
     * @param name
     * @return
     */
    public Object postProcessAfterInitialization(Object obj, String name) {
        if (obj != null){
            // 如果它适合被代理，则需要封装指定的bean
            return wrapIfNecessary(obj, name);
        }
        return obj;
    }


    @Override
    protected MyBeanDefinition getBeanDefinition(String name) {
        return this.beanFactory.getBeanDefinition(name);
    }

    @Override
    protected String getBeanNameForType(Class<?> type) {
        return null;
    }


}
