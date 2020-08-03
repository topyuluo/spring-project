package com.yuluo.spring.annoation;

import java.lang.annotation.*;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/31 23:26
 * @Version V1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface MyComponent {
}
