package com.kaishengit.util;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

public class EncodUtils {

    public static String toUTF8(String str){
        if(org.apache.commons.lang3.StringUtils.isEmpty(str)){
            return null;
        }
        try {
            return new String(str.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException("转码发生异常");
        }
    }
}
