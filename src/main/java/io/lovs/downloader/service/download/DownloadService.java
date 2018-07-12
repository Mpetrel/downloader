package io.lovs.downloader.service.download;

import io.lovs.downloader.entity.downloader.DownloaderEntity;
import io.lovs.downloader.service.download.callback.DownloadCallback;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-12 13:39
 **/
public interface DownloadService {
    boolean download(String url, String path);
    DownloaderEntity status(String key);
    default void registerCallback(DownloadCallback callback) {}
}
