package com.yuluo.spring.annoation;

import java.lang.annotation.*;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/31 23:29
 * @Version V1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface MyBefore {
    String value() default "";

    String argNames() default "";
}
