package com.kaishengit.crm.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author 
 */
public class Chance implements Serializable {
    /**
     * 机会主键
     */
    private Integer id;

    /**
     * 机会名称
     */
    private String name;

    /**
     * 客户id
     */
    private Integer custId;

    /**
     * 员工id
     */
    private Integer userId;

    /**
     * 机会价值
     */
    private BigDecimal price;

    /**
     * 当前进度
     */
    private String currentState;

    /**
     * 详情
     */
    private String mark;

    /**
     * 客户
     */
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }



    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }
}