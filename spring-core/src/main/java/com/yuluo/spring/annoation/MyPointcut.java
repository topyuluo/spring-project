package com.yuluo.spring.annoation;

import java.lang.annotation.*;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/8/1 22:54
 * @Version V1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MyPointcut {
    String value() default "";
}
