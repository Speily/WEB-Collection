package com.kaishengit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by SPL on 2017/7/15 0015.
 */
public class StringUtils {

    static Logger logger = LoggerFactory.getLogger(StringUtils.class);

    public static String toUTF8(String str){
        if(org.apache.commons.lang3.StringUtils.isEmpty(str)){
            return null;
        }
        try {
            return new String(str.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            logger.error("{}转码发生异常",str);
            throw new RuntimeException("转码发生异常");
        }
    }


}
