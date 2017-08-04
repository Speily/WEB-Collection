package com.kaishengit.util;

import java.util.List;

public class Page<T> {

    private Integer pageTotal;

    private Integer pageSize;

    private Integer pageNo;

    private Integer pageStart;

    private Integer preSize;

    private List<T> items;

    public Page(){};

    public Page(Integer pageN, Integer preSize, Integer total) {

        pageTotal = total;

        pageSize = total / preSize;

        if(total%preSize > 0){
            pageSize++;
        }

        if(pageN < 1){
            pageNo = 1;
        }
        if(pageN > pageSize){
            pageNo = pageSize;
        }
        if(pageSize == 0){
            pageNo = 1;
        }

        pageStart = (pageN-1)*preSize;

    }


    public Integer getPageTotal() {
        return pageTotal;
    }

    public void setPageTotal(Integer pageTotal) {
        this.pageTotal = pageTotal;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageStart() {
        return pageStart;
    }

    public void setPageStart(Integer pageStart) {
        this.pageStart = pageStart;
    }

    public Integer getPreSize() {
        return preSize;
    }

    public void setPreSize(Integer preSize) {
        this.preSize = preSize;
    }

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
