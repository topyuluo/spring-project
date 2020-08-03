package com.yuluo.spring.service.impl;

import com.yuluo.spring.annoation.MyAutowired;
import com.yuluo.spring.annoation.MyService;
import com.yuluo.spring.service.IService;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/26 22:04
 * @Version V1.0
 */
@MyService
public class IServiceImpl {
    @MyAutowired
    private IService iService ;


    public void sayHello(){
        System.out.println();
    }
}
