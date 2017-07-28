package com.kaishengit.crm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author
 */
public class ChanceRecod implements Serializable {
    private Integer id;

    /**
     * 机会ID
     */
    private Integer chanceId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 客户id
     */
    private Integer custId;

    /**
     * 跟进记录内容
     */
    private String text;

    /**
     * 机会
     */
    private Chance chance;

    public Chance getChance() {
        return chance;
    }

    public void setChance(Chance chance) {
        this.chance = chance;
    }

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getChanceId() {
        return chanceId;
    }

    public void setChanceId(Integer chanceId) {
        this.chanceId = chanceId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}