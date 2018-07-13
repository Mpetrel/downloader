package io.lovs.downloader.service.search.impl;

import io.lovs.downloader.entity.search.MagnetSearchDetailEntity;
import io.lovs.downloader.entity.search.SearchEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class MagnetSearchServiceImplTest {
    @Resource
    private MagnetSearchServiceImpl magnetSearchService;

    @Test
    public void parseDetailTest() {
        SearchEntity entity = magnetSearchService.detail("485529707BA9C4D084DB7A79A657379FBDC3D8AF");
        MagnetSearchDetailEntity detailEntity = (MagnetSearchDetailEntity)entity;
        log.info(" --------------> " + detailEntity.getHash());
        Assert.notNull(detailEntity.getName(), "error");
    }

}