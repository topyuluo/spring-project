package com.yuluo.spring.parser;

import com.yuluo.spring.annoation.*;
import com.yuluo.spring.bean.MyBeanDefinition;
import com.yuluo.spring.factory.MyBeanDefinitionRegistry;
import com.yuluo.spring.util.ClassUtils;
import com.yuluo.spring.util.StringUtils;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 19:00
 * @Version V1.0
 */
public class MyConfigClassParser {

    private MyBeanDefinitionRegistry registry;

    public MyConfigClassParser(MyBeanDefinitionRegistry registry) {
        this.registry = registry;
    }

    /**
     * 配置信息解析
     * @param list
     */
    public void parser(List<MyBeanDefinition> list) {
        list.forEach(mbd -> {
            doProcessConfigurationClass(mbd);
        });
    }

    /**
     * 获取配置类上的所有注解
     *
     * @param mbd
     */
    private void doProcessConfigurationClass(MyBeanDefinition mbd) {
        Class<?> clazz = mbd.getBeanClasses();
        Annotation[] annotations = clazz.getAnnotations();
        boolean flag = false;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(MyConfiguration.class)) {
                flag = true;
                MyBeanDefinition mbdc = new MyBeanDefinition(StringUtils.toLowerFirstCase(clazz.getSimpleName()), clazz);
                this.registry.registerBeanDefinition(mbdc.getBeanName(), mbdc);
            }
            if (annotation.annotationType().equals(MyComponentScan.class)) {
                MyComponentScan scan = clazz.getAnnotation(MyComponentScan.class);
                String packagePath = scan.value();
                if (!"".equals(packagePath)) {
                    doScan(packagePath);
                }
            }

        }
        if (!flag) {
            return;
        }

        Method[] methods = clazz.getDeclaredMethods();
        MyBeanDefinition mbdm = null;
        for (Method method : methods) {
            if (method.isAnnotationPresent(MyBean.class)) {
                mbdm = new MyBeanDefinition();
                mbdm.setBeanName(method.getName());
                mbdm.setFactoryBean(StringUtils.toLowerFirstCase(clazz.getSimpleName()));
                mbdm.setFactoryMethod(method.getName());
                this.registry.registerBeanDefinition(mbdm.getBeanName(), mbdm);
            }
        }
    }

    /**
     * 扫描包路径下文件，并注入容器中保存
     *
     * @param path
     */
    public void doScan(String path) {
        ClassLoader classLoader = ClassUtils.getDefaultClassLoader();
        URL url = classLoader.getResource(path.replaceAll("\\.", "/"));
        File file = new File(url.getFile());
        MyBeanDefinition mbd = null;
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                doScan(path + "." + f.getName());
            } else {
                //只加载后缀为 .class的文件 此处和spring的处理方式不同，思路还是一样的
                if (f.getName().endsWith(".class")) {
                    String fileName = getFileName(f);
                    try {
                        Class<?> clazz = ClassUtils.getDefaultClassLoader().loadClass(path + "." + fileName);
                        if (clazz.isAnnotationPresent(MyController.class)
                                || clazz.isAnnotationPresent(MyService.class) || clazz.isAnnotationPresent(MyComponent.class)) {
                            mbd = new MyBeanDefinition(StringUtils.toLowerFirstCase(fileName), clazz);
                            registry.registerBeanDefinition(mbd.getBeanName(), mbd);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 获取文件名字
     *
     * @param f
     * @return
     */
    private String getFileName(File f) {
        if (f == null) {
            throw new IllegalArgumentException("f required not null !");
        }
        return f.getName().replace(".class", "");
    }
}
