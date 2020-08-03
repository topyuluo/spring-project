package com.yuluo.spring.service;

import com.yuluo.spring.annoation.*;

/**
 * describe
 *
 * @author: PanMW
 * @date: 2019/8/13
 * @since: JDK 1.8
 * @version: v1.0
 */

@MyAspect
@MyComponent
public class LogAspect {

	@MyPointcut("@annotation(MyOper)")
	public void pointcut() {

	}

	@MyBefore(value = "pointcut()")
	public void before() throws Throwable {
		System.out.println("方法之前执行的方法 ----- LogAspect");
	}
	@MyAfter(value = "pointcut()")
	public void after() throws Throwable {
		System.out.println("方法之前执行的方法 ---- LogAspect");
	}

}
