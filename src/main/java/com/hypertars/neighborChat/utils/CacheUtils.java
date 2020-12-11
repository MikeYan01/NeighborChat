package com.hypertars.neighborChat.utils;

import com.alibaba.fastjson.JSONObject;
/**
 * 缓存序列化
 */
public class CacheUtils {

    /**
     * obj 2 string
     *
     * @param obj obj
     * @return string
     */
    public static String obj2String(Object obj) {
        if (obj == null) {
            return "";
        }
        return JSONObject.toJSONString(obj);
    }

    /**
     * string 2 obj
     *
     * @param str      string
     * @param objClass 转换的类
     * @return obj
     */
    public static Object string2Obj(String str, Class<?> objClass) {
        if (!StringUtils.isNotEmpty(str)) {
            return null;
        }
        if (StringUtils.equal(objClass.getName(), String.class.getName())) {
            return str;
        }
        return JSONObject.parseObject(str, objClass);
    }
}