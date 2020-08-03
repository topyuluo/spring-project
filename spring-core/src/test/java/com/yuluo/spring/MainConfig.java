package com.yuluo.spring;

import com.yuluo.spring.annoation.MyBean;
import com.yuluo.spring.annoation.MyComponentScan;
import com.yuluo.spring.annoation.MyConfiguration;
import com.yuluo.spring.service.MessageService;
import com.yuluo.spring.service.impl.MessageServiceImpl;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 14:01
 * @Version V1.0
 */

@MyConfiguration
@MyComponentScan("com.yuluo.spring.service")
public class MainConfig {

    @MyBean
    public MessageService messageService() {
        return new MessageServiceImpl();
    }

}
