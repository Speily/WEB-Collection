package com.kaishengit.util;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * 查找条件类
 */
public class Condition {

    private String propertyName ;
    private Object value;
    private String type;

    public Condition(){}

    public Condition(String propertyName , Object value, String type){
        this.propertyName  = propertyName ;
        this.value = value;
        this.type = type;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /**
     * 根据页面查询条件，生产Condention条件数组
     * @param request HttpRequest请求
     * @return Condition[]
     */
    public static Condition[] builderConditionList(HttpServletRequest request) {

        List<Condition> conditionList = new ArrayList<Condition>();

        Enumeration<String> keys = request.getParameterNames();//获取所有查询条件 key

        //迭代筛选
        while (keys.hasMoreElements()){
            String key = keys.nextElement();
            String value = request.getParameter(key);

            //只处理以req_开头并且值存在的键值对
            if(key.startsWith("req_") && StringUtils.isNotEmpty(value)){

                // 页面传值格式：String userName_or_age = "req_like_s_userName_or_age"
                //              String name = "req_eq_s_user.name"
                //              Integer id = "req_like_i_custId"
                try {
                    value = new String(value.getBytes("ISO8859-1"),"UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                String[] array = key.split("_",4);

                //处理value值的参数类型
                String valueType = array[2];
                Object resultValue = converValueType(value,valueType);


                Condition condition = new Condition();
                condition.setPropertyName(array[3]);
                condition.setValue(resultValue);
                condition.setType(array[1]);

                conditionList.add(condition);

                request.setAttribute(key,value);
            }
        }

        return conditionList.toArray(new Condition[]{});
    }

    private static Object converValueType(String value, String valueType) {
        if("s".equalsIgnoreCase(valueType)) {
            return value;
        } else if("i".equalsIgnoreCase(valueType)) {
            return Integer.valueOf(value);
        } else if("d".equalsIgnoreCase(valueType)) {
            return Double.valueOf(value);
        } else if("f".equalsIgnoreCase(valueType)) {
            return Float.valueOf(value);
        } else if("b".equalsIgnoreCase(valueType)) {
            return Boolean.valueOf(value);
        } else if("l".equalsIgnoreCase(valueType))  {
            return Long.valueOf(value);
        }
        return null;
    }
}
