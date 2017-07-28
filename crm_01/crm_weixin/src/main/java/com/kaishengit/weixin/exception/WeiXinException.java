package com.kaishengit.weixin.exception;

public class WeiXinException extends RuntimeException {

    public WeiXinException(){}

    public WeiXinException(String message) {
        super(message);
    }
    public WeiXinException(String message,Throwable th) {
        super(message,th);
    }
    public WeiXinException(Throwable th) {
        super(th);
    }
}
