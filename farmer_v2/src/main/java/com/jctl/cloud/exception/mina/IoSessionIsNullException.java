package com.jctl.cloud.exception.mina;

/**
 * 处理节点信息异常类
 * Created by lewKay on 2017/2/28.
 */
public class IoSessionIsNullException extends Exception {

    private static final long serialVersionUID = 533854535537735838L;


    public IoSessionIsNullException(String message){
        super(message);
    }
}
