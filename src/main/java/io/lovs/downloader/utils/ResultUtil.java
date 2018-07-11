package io.lovs.downloader.utils;

import io.lovs.downloader.common.Result;
import io.lovs.downloader.common.ResultEnum;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:36
 **/
public class ResultUtil {

    public static Result success(Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), data);
    }

    public static Result success() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMessage(), null);
    }

    public static Result error(Object data) {
        return new Result(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage(), data);
    }

}
