package com.kaishengit.exception;

/**
 * 登录状态异常
 * Created by SPL on 2017/7/19 0019.
 */
public class SessionException extends ServiceException{
    public SessionException(){};
    public SessionException(String message){
        super(message);
    }
    public SessionException(String message,Throwable th){
        super(message,th);
    }
}
