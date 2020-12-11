package com.hypertars.neighborChat.utils;

import com.hypertars.neighborChat.enums.NBCResultCodeEnum;
import com.hypertars.neighborChat.exception.NBCException;

/**
 * 断言工具
 */
public class AssertUtils {

    /**
     * 对象不为空
     * @param object 对象
     */
    public static void assertNotNull(Object object) {
        if(object == null) {
            throw new NBCException("empty object", NBCResultCodeEnum.SYSTEM_ERROR);
        }
    }

    /**
     * 字符串不为空
     * @param s 字符串
     */
    public static void stringNotEmpty(String s) {
        if(!StringUtils.isNotEmpty(s)) {
            throw new NBCException("empty string", NBCResultCodeEnum.SYSTEM_ERROR);
        }
    }

}