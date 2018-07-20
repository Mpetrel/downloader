package io.lovs.downloader.controller.data;

import io.lovs.downloader.common.Result;
import io.lovs.downloader.entity.data.MagnetDataEntity;
import io.lovs.downloader.service.data.MagnetDataService;
import io.lovs.downloader.utils.MagnetDownloader;
import io.lovs.downloader.utils.ResultUtil;
import org.springframework.web.bind.annotation.*;

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
       /* new Thread(() -> {
            MagnetDownloader.download("magnet:?xt=urn:btih:54dca0477d74d88ed051a9cd62fe5359151e7823", "G:\\tunder_download");
        }).start();*/

        return ResultUtil.success();
    }


    @GetMapping(value = "/download/{key}")
    public Result getDownloadInfo(@PathVariable String key) {
        return ResultUtil.success(MagnetDownloader.getDownloadInfo(key));
    }

}
