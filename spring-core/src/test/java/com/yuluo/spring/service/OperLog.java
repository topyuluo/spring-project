package com.yuluo.spring.service;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * describe
 *
 * @author: PanMW
 * @date: 2019/8/13
 * @since: JDK 1.8
 * @version: v1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OperLog {
	String methodName() default "";

	String[] arg1() default "";

	String desc() default "";
}
