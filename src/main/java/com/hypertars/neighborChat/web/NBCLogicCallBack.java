package com.hypertars.neighborChat.web;

public interface NBCLogicCallBack {

    /**
     * 实现execute方法以处理controller请求
     * @return 处理结果
     * @throws Exception 需要异常处理
     */
    NBCResult<Object> execute() throws Exception;


}
