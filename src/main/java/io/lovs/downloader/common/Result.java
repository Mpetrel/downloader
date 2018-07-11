package io.lovs.downloader.common;

import lombok.Data;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:34
 **/

@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public Result(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
