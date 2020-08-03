package com.yuluo.spring.processor;

import com.yuluo.spring.annoation.MyConfiguration;
import com.yuluo.spring.bean.MyBeanDefinition;
import com.yuluo.spring.factory.MyBeanDefinitionRegistry;
import com.yuluo.spring.factory.MyConfigurableApplicationContext;
import com.yuluo.spring.parser.MyConfigClassParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 18:49
 * @Version V1.0
 */
public class MyConfigurationClassPostProcessor implements MyBeanDefinitionRegistryPostProcessor{

    /**
     * 全面扫描包路径下的配置信息
     * @param registry
     */
    @Override
    public void postProcessBeanDefinitionRegistry(MyBeanDefinitionRegistry registry) {
        processConfigBeanDefinitions(registry);
    }

    /**
     * 执行具体的扫描操作，扫描配置configuration注解的类
     * @param registry
     */
    private void processConfigBeanDefinitions(MyBeanDefinitionRegistry registry) {
        String[] definitionNames = registry.getBeanDefinitionNames();
        List<MyBeanDefinition> list = new ArrayList<>();
        for (String beanName : definitionNames) {
            MyBeanDefinition beanDefinition = registry.getBeanDefinition(beanName);
            Class<?> beanClasses = beanDefinition.getBeanClasses();
            if (beanClasses.isAnnotationPresent(MyConfiguration.class)){
                list.add(beanDefinition);
            }
        }
        // 如果没有扫描到到配置信息返回
        if (list.isEmpty()){
            return;
        }

        // 使用解析器解析
        MyConfigClassParser parser = new MyConfigClassParser(registry);
        parser.parser(list);
    }

    @Override
    public void postProcessBeanFactory(MyConfigurableApplicationContext beanFactory) {

    }
}
