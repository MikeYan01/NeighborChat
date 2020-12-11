package com.hypertars.neighborChat.web;

import lombok.Data;

import java.io.Serializable;

/**
 * web result
 */
@Data
public class NBCResult<T> implements Serializable {

    /**
     * 序列号id
     */
    private static final long serialVersionUID = 761675137867578348L;

    /**
     * 请求结果
     */
    private boolean success = true;

    /**
     * 响应code
     */
    private String resultCode;

    /**
     * 响应描述
     */
    private String resultDesc;

    /**
     * 结果实体
     */
    private T resultObj;

    public void setResultObj(T resultObj) {
        this.resultObj = resultObj;
    }

    public T getResultObj() {
        return resultObj;
    }


}
