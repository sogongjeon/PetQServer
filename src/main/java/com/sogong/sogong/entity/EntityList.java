package com.sogong.sogong.entity;


import java.util.ArrayList;
import java.util.List;

public class EntityList<T> {
    private long totalCount = 0;
    private List<T> elements = new ArrayList<>();

    public EntityList(long totalCount, List<T> elements) {
        this.totalCount = totalCount;
        this.elements = elements;
    }

    public EntityList() {
    }

    public EntityList(List<T> elements) {
        this.totalCount = elements.size();
        this.elements = elements;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getElements() {
        return elements;
    }

    public void setElements(List<T> elements) {
        this.elements = elements;
    }
}
