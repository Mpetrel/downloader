package io.lovs.downloader.entity.search;

import lombok.Data;

import java.util.List;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-13 13:47
 **/
@Data
public class SearchResultEntity {

    private Integer totalPage;
    private Integer currentPage;
    private List<? extends SearchEntity> list;
}
