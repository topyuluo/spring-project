package com.yuluo.spring.factory;

import com.yuluo.spring.annoation.*;
import com.yuluo.spring.bean.MyBeanDefinition;
import com.yuluo.spring.factory.aop.Advisor;
import com.yuluo.spring.factory.aop.MyAdvisedSupport;
import com.yuluo.spring.factory.aop.MyAnnoationAwareAspectJAtuoProxyCreator;
import com.yuluo.spring.factory.aop.MyProxyFactory;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/8/1 19:59
 * @Version V1.0
 */
public abstract class MyAbstractAutowireCapableBeanFactory extends MyAbstractBeanFactory {

    private final Set<String> targetSourcedBeans = Collections.newSetFromMap(new ConcurrentHashMap<>(16));

    private final Map<Object, Object> earlyProxyReferences = new ConcurrentHashMap<>(16);

    private final Map<Object, Class<?>> proxyTypes = new ConcurrentHashMap<>(16);

    private final Map<String, Boolean> advisedBeans = new ConcurrentHashMap<>(256);

    private volatile List<String> aspectBeanNames;

    private MyDefaultListableBeanFactory beanFactory;

    private final Map<Method, List<Advisor>> advisorsCache = new ConcurrentHashMap<>();


    private static final Class<?>[] ASPECTJ_ANNOTATION_CLASSES = new Class<?>[]{
            MyPointcut.class, MyBefore.class, MyAfter.class};

    public MyAbstractAutowireCapableBeanFactory() {
    }

    public void setBeanFactory(MyDefaultListableBeanFactory beanFactory){
        this.beanFactory = beanFactory;
    }

    @Override
    protected Object createBean(String beanName, MyBeanDefinition mbd) {
        Class<?> resolvedClass = mbd.getBeanClasses();
        Object beanInstance = doCreateBean(resolvedClass, mbd);
        //初始化bean
        addSingleton(beanName, beanInstance);
        return beanInstance;
    }

    /**
     * 执行具体的创建bean操作
     * 1。 如果是@MyService 注解， 直接使用class的反射创建实例
     * 2. 如果是@Bean 注解， 则需要执行 @Bean注解的方法，拿到配置的Bean实例
     *
     * @param resolvedClass
     * @param mbd
     * @return
     */
    private Object doCreateBean(Class<?> resolvedClass, MyBeanDefinition mbd) {
        String beanName = mbd.getBeanName();
        Object instanceWrapper = createBeanInstance(resolvedClass, mbd);
        Object exposedObject = instanceWrapper;
        populateBean(beanName, mbd, exposedObject);
        exposedObject = initializeBean(beanName, instanceWrapper, mbd);
        return exposedObject;
    }

    private Object createBeanInstance(Class<?> resolvedClass, MyBeanDefinition mbd) {
        Object obj = null;
        try {
            if (resolvedClass != null) {
                obj = resolvedClass.newInstance();
            } else {
                String factBeanName = mbd.getFactoryBean();
                Object bean = getBean(factBeanName);
                String factoryBeanMethod = mbd.getFactoryMethod();
                Method method = bean.getClass().getMethod(factoryBeanMethod);
                obj = method.invoke(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }


    protected Object initializeBean(final String name, Object singleton, MyBeanDefinition mbd) {
        Object wrapperBean = singleton;
        wrapperBean = applyBeanPostProcessBeforeInitialization(wrapperBean, name);

        wrapperBean = applyBeanPostProcessAfterInitialization(wrapperBean, name);

        return wrapperBean;
    }

    /**
     * 执行afterInitialzation方法
     *
     * @param wrapperBean
     * @param name
     * @return
     */
    private Object applyBeanPostProcessAfterInitialization(Object wrapperBean, String name) {
        Object result = wrapperBean;
        MyAnnoationAwareAspectJAtuoProxyCreator autoProxy = new MyAnnoationAwareAspectJAtuoProxyCreator(beanFactory);
        Object current = autoProxy.postProcessAfterInitialization(result, name);
        if (current == null) {
            return result;
        }
        return current;
    }

    /**
     * 执行beforInitialzation方法
     *
     * @param wrapperBean
     * @param name
     * @return
     */
    private Object applyBeanPostProcessBeforeInitialization(Object wrapperBean, String name) {
        return wrapperBean;
    }


    /**
     * 属性注入
     *
     * @param beanName
     * @param mbd
     */
    protected void populateBean(String beanName, MyBeanDefinition mbd, Object obj) {
        // 执行beanPostProcessor
        Class<?> clazz = mbd.getBeanClasses();
        if (clazz == null) {
            clazz = obj.getClass();
        }
        Field[] fields = clazz.getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(MyAutowired.class)) {
                try {
                    field.set(obj, getValue(field.getType()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 如果它适合被代理，则需要封装制定的bean
     *
     * @param obj
     * @param name
     * @return
     */
    protected Object wrapIfNecessary(Object obj, String name) {
        if (targetSourcedBeans.contains(name)) {
            return obj;
        }
        if (Boolean.FALSE.equals(this.advisedBeans.get(name))){
            return obj;
        }
        Object[] specificIntef = getAdvicesAndAdvisorsForBean(obj.getClass(), name);
        if (specificIntef != null && specificIntef.length > 0) {
            for (Map.Entry<Method, List<Advisor>> methodListEntry : this.advisorsCache.entrySet()) {
                List<Advisor> value = methodListEntry.getValue();
                for (Advisor advisor : value) {
                    if (advisor.getAspect() == null){
                        Object bean = getBean(advisor.getBeanName());
                        advisor.setAspect(bean);
                    }
                }
            }
            return crateProxy(obj.getClass(), name, specificIntef ,obj);
        }
        this.advisedBeans.put(name, Boolean.FALSE);
        return obj;
    }

    /**
     * 创建代理
     *
     * @param aClass
     * @param name
     * @param specificIntef
     */
    protected Object crateProxy(Class<?> aClass, String name, Object[] specificIntef , Object obj) {
        MyProxyFactory proxyFactory = new MyProxyFactory();
        MyAdvisedSupport support =new MyAdvisedSupport(aClass , obj , this.advisorsCache);
        return proxyFactory.getProxy(support);
    }

    /**
     * 如果存在增强方法则创建代理
     * 1.  获取增强方法或者增强器
     * 2. 根据获取的增强进行代理
     *
     * @param aClass
     * @param name
     * @return
     */
    protected Object[] getAdvicesAndAdvisorsForBean(Class<?> aClass, String name) {
        List<Advisor> advisors = findEligibleAdvisors(aClass, name);
        if (advisors.isEmpty()) {
            return null;
        }
        return advisors.toArray();
    }

    private List<Advisor> findEligibleAdvisors(Class<?> aClass, String name) {
        List<Advisor> advisors = findCandidateAdvisors();
        List<Advisor> advisorsThatCanApply = findAdvisorsThatCanApply(advisors, aClass);
        return advisorsThatCanApply;
    }

    protected  List<Advisor> findAdvisorsThatCanApply(List<Advisor> advisors , Class<?> clazz){
        if (advisors.isEmpty()){
            return advisors;
        }
        Method[] declaredMethods = clazz.getDeclaredMethods();
        List<Advisor> list = new ArrayList<>();
        for (Method declaredMethod : declaredMethods) {
//            OperLog annotation = declaredMethod.getAnnotation(OperLog.class);
            Annotation[] annotations = declaredMethod.getAnnotations();
            for (Annotation annotation : annotations) {
                if (annotation.annotationType().getSimpleName().equals(advisors.get(0).getExpression())) {
                    list.addAll(advisors);
                    advisorsCache.put(declaredMethod, advisors);
                }
            }
        }
        return list;
    }

    protected List<Advisor> findCandidateAdvisors() {
        List<Advisor> advisors = new ArrayList<>();
        advisors.addAll(this.buildAspectJAdvisors());
        return advisors;
    }

    /**
     * 获取注解信息
     * 1. 获取所有的BeanName , 从beanFactor中进行提取
     * 2. 便利所有的beanName ,并找出声明AspectJ注解的类，进行进一步的处理
     * 3. 对标记AspectJ注解的类进行增强的提起操作
     * 4. 将提取的结果加入到缓存中
     *
     * @return
     */
    private List<Advisor> buildAspectJAdvisors() {
        List<String> aspectNames = this.aspectBeanNames;
        // 双重检验锁 保证线程安全
        if (aspectNames == null) {
            synchronized (this) {
                aspectNames = this.aspectBeanNames;
                if (aspectNames == null) {
                    List<Advisor> advisors = new ArrayList<>();
                    aspectNames = new ArrayList<>();
                    String[] beanNames = this.beanFactory.getBeanDefinitionNames();
                    for (String beanName : beanNames) {
                        MyBeanDefinition beanDefinition = this.beanFactory.getBeanDefinition(beanName);
                        Class<?> clazz = beanDefinition.getBeanClasses();
                        // 此处判断是否切面类
                        if (isAspect(clazz)) {
                            aspectNames.add(beanName);
                            List<Advisor> advisorsList = getAdvisors(clazz, beanName );
                            advisors.addAll(advisorsList);
                        }
                    }
                    this.aspectBeanNames = aspectNames;
                    return advisors;
                }
            }
        }
        return null;
    }

    /**
     * 获取织入方法的列表
     *
     * @param clazz
     * @param beanName
     * @return
     */
    private List<Advisor> getAdvisors(Class<?> clazz, String beanName ) {
        List<Advisor> advisors = new ArrayList<>();
        String pointcut = null;
        for (Method advisorMethod : getAdvisorMethods(clazz, beanName)) {
            String point = getPointcut(advisorMethod);
            if (point != null){
                pointcut = point;
            }

        }
        for (Method advisorMethod : getAdvisorsMethod(clazz)) {

            Advisor advisor = getAdvisor(advisorMethod, beanName, clazz , pointcut);
            if (advisor != null) {
                advisors.add(advisor);
            }
        }
//        this.advisorsCache.put(beanName, advisors);
        return advisors;
    }

    private String getPointcut(Method advisorMethod) {
        if (null != advisorMethod.getAnnotation(MyPointcut.class)) {
            MyPointcut annotation = advisorMethod.getAnnotation(MyPointcut.class);
            String value = annotation.value();
            int start = value.indexOf("(");
            int end = value.indexOf(")");
            return value.substring(start+1, end);
        }
        return null;
    }

    private List<Method> getAdvisorMethods(Class<?> clazz, String beanName) {
//        List<Method> advisorsMethod = getAdvisorsMethod(clazz);
//        final List<Method> methods = new ArrayList<>();
        Method[] methodss = clazz.getDeclaredMethods();
//        for (Method method : methodss) {
//            if (null == method.getAnnotation(MyPointcut.class)) {
//                methods.add(method);
//            }
//        }
        return Arrays.asList(methodss);
    }

    /**
     * 获取织入要执行的方法，出去pointcut()注解
     *
     * @param clazz
     * @return
     */
    private List<Method> getAdvisorsMethod(Class<?> clazz) {
        final List<Method> methods = new ArrayList<>();
        Method[] methodss = clazz.getDeclaredMethods();
        for (Method method : methodss) {
            if (null == method.getAnnotation(MyPointcut.class)) {
                methods.add(method);
            }
        }
        return methods;
    }

    /**
     * 获取织入 方法
     *
     * @param method
     * @param beanName
     * @param clazz
     * @return
     */
    protected Advisor getAdvisor(Method method, String beanName, Class<?> clazz ,String pointcut) {
        return  new Advisor(method , beanName , clazz , pointcut);
    }



    /**
     * 判断类中是否有Aspect 注解
     *
     * @param clazz
     * @return
     */
    private boolean isAspect(Class<?> clazz) {
        if (clazz == null){
            return Boolean.FALSE;
        }
        MyAspect annoation = clazz.getDeclaredAnnotation(MyAspect.class);
        if (annoation != null) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }



}
