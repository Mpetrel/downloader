package io.lovs.downloader.entity.search;

import lombok.Data;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-12 14:00
 **/
@Data
public class MagnetSearchEntity extends SearchEntity {
    private String hash;
    private String hot;
    private Integer rate;
    private String createDate;
}
