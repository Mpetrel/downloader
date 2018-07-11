package io.lovs.downloader.common;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:36
 **/
public enum  ResultEnum {
    SUCCESS(100, "OK"),
    ERROR(150, "Server error");



    private Integer code;
    private String message;
    ResultEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
