package com.yuluo.spring;

import static org.junit.Assert.assertTrue;

import com.yuluo.spring.factory.MyAnnotationConfigApplicationContext;
import com.yuluo.spring.service.impl.MessageServiceImpl;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {

        MyAnnotationConfigApplicationContext context = new MyAnnotationConfigApplicationContext(MainConfig.class);
        MessageServiceImpl messageService = (MessageServiceImpl) context.getBean("messageService");
        messageService.sayHello();

    }
}
