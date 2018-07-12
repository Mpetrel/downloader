package io.lovs.downloader.controller.search;

import io.lovs.downloader.common.Result;
import io.lovs.downloader.service.search.impl.MagnetSearchServiceImpl;
import io.lovs.downloader.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Map;

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
    public Result searchMagnet(@RequestParam String keyword, @RequestParam Integer page, Map<String, Object> map) {
        map.put("page", page);
        map.put("list", magnetSearchService.search(keyword, page, 10));
        return ResultUtil.success(map);
    }

}
