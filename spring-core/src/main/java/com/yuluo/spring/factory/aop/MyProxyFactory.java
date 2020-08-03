package com.yuluo.spring.factory.aop;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/8/2 0:13
 * @Version V1.0
 */
public class MyProxyFactory {

    public Object getProxy(MyAdvisedSupport config) {
        return createAopProxy(config);
    }

    private Object createAopProxy(MyAdvisedSupport config) {
        if (config.getTargetClass() == null){
            throw new IllegalArgumentException("targetClass not null !");
        }

        return new ObjectCglibAopProxy(config).getProxy();
    }

}
