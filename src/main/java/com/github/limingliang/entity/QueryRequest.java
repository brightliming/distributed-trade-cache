package com.github.limingliang.entity;

import org.hibernate.validator.constraints.Range;

import java.io.Serializable;

/**
 * @author limingliang
 */
public class QueryRequest implements Serializable {

    private static final long serialVersionUID = -4869594085374385813L;

    /**
     * 当前页面数据量
     */
    @Range(min = 1L,max = 20L)
    private int pageSize = 10;
    /**
     * 当前页码
     */
    @Range(min = 1L,max = 999L)
    private int pageNum = 1;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
}
