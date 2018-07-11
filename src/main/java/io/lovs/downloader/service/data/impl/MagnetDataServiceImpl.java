package io.lovs.downloader.service.data.impl;

import io.lovs.downloader.entity.data.MagnetDataEntity;
import io.lovs.downloader.repository.data.MagnetDataRepository;
import io.lovs.downloader.service.data.MagnetDataService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:08
 **/
@Service
public class MagnetDataServiceImpl implements MagnetDataService {
    @Resource
    private MagnetDataRepository magnetDataRepository;


    @Override
    public void save(MagnetDataEntity entity) {
        magnetDataRepository.save(entity);
    }

    @Override
    public List<MagnetDataEntity> findAll() {
        return magnetDataRepository.findAll();
    }
}
