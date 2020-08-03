package com.yuluo.spring.annoation;

import java.lang.annotation.*;

@Documented
@Target({ElementType.TYPE , ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MyRequestMapping {
    String value() default "";
}
