package com.hypertars.neighborChat.exception;

import com.hypertars.neighborChat.enums.NBCResultCodeEnum;
import lombok.Data;

@Data
public class NBCException extends RuntimeException {

    /** 错误码 */
    private NBCResultCodeEnum errorCode;

    /**
     * 常用构造器
     * @param message 异常信息
     * @param errorCode 错误码
     */
    public NBCException(String message, NBCResultCodeEnum errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}