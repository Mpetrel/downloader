package io.lovs.downloader.repository.data;

import io.lovs.downloader.entity.data.MagnetDataEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:04
 **/
public interface MagnetDataRepository extends JpaRepository<MagnetDataEntity, String> {
}
