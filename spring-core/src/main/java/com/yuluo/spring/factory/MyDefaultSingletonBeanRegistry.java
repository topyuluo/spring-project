package com.yuluo.spring.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 23:41
 * @Version V1.0
 */
public class MyDefaultSingletonBeanRegistry {

    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);
    private final Map<String, ObjectFactory<?>> singletonFactories = new HashMap<>(16);

    public Object getSingleton(String beanName) {
        return getSingleton(beanName, true);
    }

    public Object getSingleton(String beanName ,  boolean allowEarlyReference){
        Object singletonObject = this.singletonObjects.get(beanName);
        return singletonObject;
    }

    public Object getSingleton(String beanName , ObjectFactory<?> singletonFactory){
        Object obj = this.singletonObjects.get(beanName);
        if (obj == null){
            obj = singletonFactory.getObject();
        }
//        this.singletonObjects.put(beanName , obj);
        return obj;
    }

    public void addSingletonFactory(String beanName , ObjectFactory<?> singletonFactory){
        if (!this.singletonObjects.containsKey(beanName)){
            this.singletonFactories.put(beanName , singletonFactory);
        }
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        synchronized (this.singletonObjects) {
            this.singletonObjects.put(beanName, singletonObject);
//            this.singletonFactories.remove(beanName);
//            this.earlySingletonObjects.remove(beanName);
//            this.registeredSingletons.add(beanName);
        }
    }
}
