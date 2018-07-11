package io.lovs.downloader.controller.data;

import io.lovs.downloader.common.Result;
import io.lovs.downloader.entity.data.MagnetDataEntity;
import io.lovs.downloader.service.data.MagnetDataService;
import io.lovs.downloader.utils.MagnetDownloader;
import io.lovs.downloader.utils.ResultUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:12
 **/
@RestController
@RequestMapping(value = "/api/magnet")
public class MagnetDataController {

    @Resource
    private MagnetDataService magnetDataService;

    @GetMapping
    public Result findAll() {
        List<MagnetDataEntity> list= magnetDataService.findAll();
        return ResultUtil.success(list);
    }

    @PostMapping
    public Result save(MagnetDataEntity entity) {
        magnetDataService.save(entity);
        return ResultUtil.success();
    }

    @PostMapping(value = "/download")
    public Result startDownload() {
        MagnetDownloader.download("magnet:?xt=urn:btih:af0d9aa01a9ae123a73802cfa58ccaf355eb19f1", "./");
        return ResultUtil.success();
    }

}
