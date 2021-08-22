package com.sogong.sogong.entity.common;


import com.sogong.sogong.entity.common.CriteriaOrderBy;

public class Criteria {
    private int page = 0;
    private int size = 20;
    private int startIndex = 0;
    private int maxCount = 0;
    private CriteriaOrderBy orderBy = CriteriaOrderBy.DESC;
    private String searchCondition = "";
    private String searchKeyword = "";
    private String searchPeriodCondition = "";
    private String searchPeriodFrom = "";
    private String searchPeriodTo = "";

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }

    public CriteriaOrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(CriteriaOrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getSearchCondition() {
        return searchCondition;
    }

    public void setSearchCondition(String searchCondition) {
        this.searchCondition = searchCondition;
    }

    public String getSearchKeyword() {
        return searchKeyword;
    }

    public void setSearchKeyword(String searchKeyword) {
        this.searchKeyword = searchKeyword;
    }

    public String getSearchPeriodCondition() {
        return searchPeriodCondition;
    }

    public void setSearchPeriodCondition(String searchPeriodCondition) {
        this.searchPeriodCondition = searchPeriodCondition;
    }

    public String getSearchPeriodFrom() {
        return searchPeriodFrom;
    }

    public void setSearchPeriodFrom(String searchPeriodFrom) {
        this.searchPeriodFrom = searchPeriodFrom;
    }

    public String getSearchPeriodTo() {
        return searchPeriodTo;
    }

    public void setSearchPeriodTo(String searchPeriodTo) {
        this.searchPeriodTo = searchPeriodTo;
    }
}
