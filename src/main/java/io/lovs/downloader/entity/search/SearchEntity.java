package io.lovs.downloader.entity.search;

import lombok.Data;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-12 13:57
 **/
@Data
public class SearchEntity {

    private String url;

    private String name;

    private Long size;

    private String sizeName;

}
