package com.sogong.sogong.entity;

import java.io.Serializable;

public class ResultEntity<T> implements Serializable {
    private static final long serialVersionUID = -3682758295121500390L;

    private String code = "200";
    private String message = "Success";
    private T data;

    public ResultEntity() {
    }

    public ResultEntity(final T data) {
        this.data = data;
    }

    public ResultEntity(String code, String message, final T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultEntity(String code, String message) {
        this.code = code;
        this.message = message;
    }

//    public ResultEntity(ApiResult apiResult) {
//        this.code = apiResult.getCode();
//        this.message = apiResult.getMessage();
//    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
