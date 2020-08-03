package com.yuluo.spring.service.impl;

import com.yuluo.spring.service.MessageService;
import com.yuluo.spring.service.MyOper;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/25 7:07
 * @Version V1.0
 */
public class MessageServiceImpl implements MessageService {
    @Override
    @MyOper
    public void sayHello() {
        System.out.println("messageServiceImpl 中的 sayHello 方法");
    }
}
