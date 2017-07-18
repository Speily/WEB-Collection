package com.kaishengit.result;

/**
 * Created by SPL on 2017/7/17 0017.
 */
public class AjaxResult {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String state;
    private String message;
    private Object data;

    public AjaxResult(){}

    public static AjaxResult success(){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.SUCCESS);
        return ajaxResult;
    }
    public static AjaxResult success(Object data){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.SUCCESS);
        ajaxResult.setData(data);
        return ajaxResult;
    }
    public static AjaxResult error(){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.ERROR);
        return ajaxResult;
    }
    public static AjaxResult error(Object data){
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.ERROR);
        ajaxResult.setData(data);
        return ajaxResult;
    }


    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
