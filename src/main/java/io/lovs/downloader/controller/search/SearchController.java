package io.lovs.downloader.controller.search;

import io.lovs.downloader.common.Result;
import io.lovs.downloader.service.search.impl.MagnetSearchServiceImpl;
import io.lovs.downloader.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-12 14:44
 **/
@RestController
@RequestMapping(value = "/api/search")
public class SearchController {

    @Resource
    private MagnetSearchServiceImpl magnetSearchService;


    @GetMapping(value = "/magnet")
    public Result searchMagnet(@RequestParam String keyword, @RequestParam Integer page,
                               @RequestParam(required = false) String sort) {
        return ResultUtil.success(magnetSearchService.search(keyword, sort, page, 10));
    }

    @GetMapping(value = "/magnet/{key}")
    public Result magnetDetail(@PathVariable String key) {
        return ResultUtil.success(magnetSearchService.detail(key));
    }

}
