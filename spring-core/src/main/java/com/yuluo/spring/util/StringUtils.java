package com.yuluo.spring.util;

/**
 * @Description
 * @Author 蚂蚁不是ant
 * @Date 2020/7/24 0:07
 * @Version V1.0
 */
public class StringUtils {

    /**
     * 将字符串首字母转换成大写
     * @param content
     * @return
     */
    public static String toLowerFirstCase(String content) {
        char[] chars = content.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    /**
     * 采用截取方式将字符串首字母转换为小写
     * @param content
     * @return
     */
    public static String lowerFirstCase(String content) {
        return content.substring(0, 1).toLowerCase() + content.substring(1);
    }


}
