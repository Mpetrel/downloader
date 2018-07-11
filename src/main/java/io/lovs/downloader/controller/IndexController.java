package io.lovs.downloader.controller;

import io.lovs.downloader.common.Result;
import io.lovs.downloader.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:12
 **/
@RestController
@RequestMapping(value = "/")
public class IndexController {

    @GetMapping
    public Result index() {
        return ResultUtil.success();
    }

}
