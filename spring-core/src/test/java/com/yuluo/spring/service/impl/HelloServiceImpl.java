package com.yuluo.spring.service.impl;

import com.yuluo.spring.annoation.MyService;
import com.yuluo.spring.service.HelloService;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 0:06
 * @Version V1.0
 */

@MyService
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return null;
    }
}
