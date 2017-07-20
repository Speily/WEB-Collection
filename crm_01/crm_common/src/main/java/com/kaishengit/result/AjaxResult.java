package com.kaishengit.result;

/**
 * Ajax响应结果类
 * Created by SPL on 2017/7/17 0017.
 */
public class AjaxResult {

    public static final String SUCCESS = "success";
    public static final String ERROR = "error";

    private String state;
    private String message;
    private Object data;

    public AjaxResult() {
    }

    /**
     * 响应成功
     *
     * @return success
     */
    public static AjaxResult success() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.SUCCESS);
        return ajaxResult;
    }

    /**
     * 响应成功
     *
     * @return Object data
     */
    public static AjaxResult success(Object data) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.SUCCESS);
        ajaxResult.setData(data);
        return ajaxResult;
    }

    /**
     * 响应错误
     *
     * @return error
     */
    public static AjaxResult error() {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.ERROR);
        return ajaxResult;
    }
    /**
     * 响应错误
     * @return String message
     * @return Object data
     */
    public static AjaxResult error(String message) {
        AjaxResult ajaxResult = new AjaxResult();
        ajaxResult.setState(AjaxResult.ERROR);
        ajaxResult.setMessage(message);
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
