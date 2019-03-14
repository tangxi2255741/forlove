package com.txr.forlove.domain.rpc;

import java.io.Serializable;

/**
 * @author: T.X
 * @create: 2018-12-22 14:41
 **/
public class BasePageQuery implements Serializable {
	private static final long serialVersionUID = 4758475436020171796L;
	private int pageSize = 10;
    private int currentPage = 1;
    /**
     * 数据库排序字段
     */
    private String orderBy;

    private String sortType;

    public BasePageQuery() {
    }

    public BasePageQuery(int currentPage) {
        this.currentPage = currentPage;
    }

    public BasePageQuery(int currentPage, int pageSize) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }

    public int getStartIndex() {
        return (this.getCurrentPage() - 1) * this.pageSize;
    }

    public int getEndIndex() {
        return this.getCurrentPage() * this.pageSize;
    }

    public int getCurrentPage() {
        if (this.currentPage <= 0) {
            this.currentPage = 1;
        }
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }
}
