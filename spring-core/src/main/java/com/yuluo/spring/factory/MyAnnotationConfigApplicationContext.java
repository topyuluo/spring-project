package com.yuluo.spring.factory;

import com.yuluo.spring.reader.MyAnnotatedBeanDefinitionReader;
import com.yuluo.spring.reader.MyClassPathBeanDefinitionScanner;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 15:02
 * @Version V1.0
 */
public class MyAnnotationConfigApplicationContext extends MyGenericApplicationContext {
    private final MyAnnotatedBeanDefinitionReader reader ;
    private final MyClassPathBeanDefinitionScanner scanner ;


    public MyAnnotationConfigApplicationContext(){
        this.reader = new MyAnnotatedBeanDefinitionReader(this);
        this.scanner = new MyClassPathBeanDefinitionScanner();
    }

    public MyAnnotationConfigApplicationContext(Class<?>... annotatedClasses){
        this();
        register(annotatedClasses);
        refresh();
    }

    private void register(Class<?>... annotatedClasses) {
        this.reader.register(annotatedClasses);
    }


}
