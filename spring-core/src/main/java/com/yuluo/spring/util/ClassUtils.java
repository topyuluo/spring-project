package com.yuluo.spring.util;

/**
 * @Description  工具类
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 14:38
 * @Version V1.0
 */
public abstract class ClassUtils {

    /**
     * 获取类加载器
     * @return
     */
    public static ClassLoader getDefaultClassLoader() {
        ClassLoader classLoader = null;
        try {
            if (classLoader == null) {
                classLoader = Thread.currentThread().getContextClassLoader();
            }
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
        if (classLoader == null) {
            classLoader = ClassUtils.class.getClassLoader();
            if (classLoader == null) {
                try {
                    classLoader = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                    ex.printStackTrace();
                }
            }
        }
        return classLoader;
    }

    /**
     * 获取泪加载路径
     * @param clazzName
     * @return
     */
    public static Class<?> forName(String clazzName){
        try {
            Class<?> aClass = Class.forName(clazzName);
            return aClass;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
