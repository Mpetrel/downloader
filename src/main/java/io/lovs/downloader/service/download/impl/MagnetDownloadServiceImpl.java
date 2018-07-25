package io.lovs.downloader.service.download.impl;

import com.github.kevinsawicki.http.HttpRequest;
import io.lovs.downloader.entity.downloader.DownloaderEntity;
import io.lovs.downloader.service.download.DownloadService;
import io.lovs.downloader.service.download.callback.DownloadCallback;
import lombok.extern.slf4j.Slf4j;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-12 13:45
 **/
@Slf4j
public class MagnetDownloadServiceImpl implements DownloadService {

    private DownloadCallback downloadCallback;

    @Override
    public boolean download(String url, String path) {
        HttpRequest.get("").contentLength();
        return false;
    }

    @Override
    public DownloaderEntity status(String key) {
        return null;
    }

    @Override
    public void registerCallback(DownloadCallback callback) {

    }
}
