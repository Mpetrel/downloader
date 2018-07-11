package io.lovs.downloader.entity.data;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-11 15:01
 **/
@Entity
@Data
public class MagnetDataEntity {
    @Id
    private String id;

    private String name;

    private String url;

    private String hash;

    private String filePath;

    private Integer downloaded;

    private Integer hot;

    private Long size;

    private Long createTime;
}
