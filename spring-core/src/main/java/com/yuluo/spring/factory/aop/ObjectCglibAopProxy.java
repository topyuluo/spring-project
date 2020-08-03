package com.yuluo.spring.factory.aop;

import com.yuluo.spring.util.ClassUtils;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import net.sf.cglib.transform.impl.UndeclaredThrowableStrategy;

import javax.swing.*;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/8/2 0:20
 * @Version V1.0
 */
public class ObjectCglibAopProxy {
    private  MyAdvisedSupport advised = null;

    public ObjectCglibAopProxy(MyAdvisedSupport config) {
        this.advised = config;
    }

    public Object getProxy() {
        //获取要代理的class信息
        Class<?> rootClass = this.advised.getTargetClass();
        if (rootClass == null){
            throw new IllegalArgumentException("rootClass not null");
        }

        Class proxySuperClass = rootClass;
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(ClassUtils.getDefaultClassLoader());
        enhancer.setSuperclass(proxySuperClass);
        enhancer.setCallback(new DynamicAdvisedInterceptor(this.advised));
        return enhancer.create();
    }


    private static class DynamicAdvisedInterceptor implements MethodInterceptor , Serializable{

        private final MyAdvisedSupport advised ;
        public DynamicAdvisedInterceptor(MyAdvisedSupport advised){
            this.advised = advised;
        }

        //代理类的执行的方法， 类似 InvocationHandler中的invoke方法
        @Override
        public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            //获取代理对象
            Object target = this.advised.getTargetObject();
            //获取代理对象的class对象
            Class<?> targetClass = this.advised.getTargetClass();

            //获取代理对象方法执行之前的拦截方法列表
            List<Advisor> chain = this.advised.getAdviceList(method, targetClass);
//            Advisor advisor = Optional.ofNullable(chain.get(0)).get();
//            execProxyMethod(advisor);
            aopMethod(chain , "before");
            Object invoke = method.invoke(target, args);
            aopMethod(chain , "after") ;
            return invoke;
        }

        private void aopMethod(List<Advisor> chain , String methodName) {
            List<Advisor> before = chain.stream().filter(a -> a.getMethod().getName().equals(methodName)).collect(Collectors.toList());
            before.forEach( b -> {
                try {
                    b.getMethod().invoke(b.getAspect());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            });
        }

        /**
         * 执行代理的方法
         * @param object
         */
        private void execProxyMethod(Advisor  advisor){
            try {
                advisor.getMethod().invoke(advisor.getAspect());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }


}
