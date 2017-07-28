package com.kaishengit.crm.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 
 */
public class Disk implements Serializable {

    public static final String FILE = "file";//文件
    public static final String DIR = "dir";//文件夹

    private Integer id;

    /**
     * 原始文件名
     */
    private String name;

    /**
     * 磁盘保存文件名
     */
    private String saveName;

    /**
     * 文件大小
     */
    private String fileSize;

    /**
     * 父id
     */
    private Integer pId;

    /**
     * 类型：文件|文件夹
     */
    private String type;

    private Date updateTime;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 下载次数
     */
    private Integer downloadCount;

    /**
     * 加密密码
     */
    private String password;

    /**
     * md5
     */
    private String md5;

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

    public String getSaveName() {
        return saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(Integer downloadCount) {
        this.downloadCount = downloadCount;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}