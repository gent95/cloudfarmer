package com.jctl.cloud.exception.mina;

/**
 * 数据解释异常
 * Created by lewKay on 2017/3/10.
 */
public class DataParsingFailedException extends Exception {

    public DataParsingFailedException(String message){
        super(message);
    }
}
