package io.lovs.downloader.service.data;

import io.lovs.downloader.entity.data.MagnetDataEntity;

import java.util.List;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:07
 **/
public interface MagnetDataService {
    void save(MagnetDataEntity entity);
    List<MagnetDataEntity> findAll();
}
