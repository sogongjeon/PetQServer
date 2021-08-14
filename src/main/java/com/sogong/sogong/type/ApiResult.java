package com.sogong.sogong.type;

public enum ApiResult  {
    SUCCESS("0000", "Success"),
    BAD("1400", "Bad Request"),
    NOT_FOUND("1404", "Not Found"),
    EXIST("1403", "Already Exist"),
    INTERNAL("1500", "Internal Error")
    ;

    ApiResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
