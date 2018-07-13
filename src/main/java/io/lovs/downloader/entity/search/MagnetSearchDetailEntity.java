package io.lovs.downloader.entity.search;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-13 17:36
 **/
@Data
public class MagnetSearchDetailEntity extends SearchEntity {
    private String hot;
    private String hash;
    private String fileNumber;
    private String createDate;
    private List<Map<String, String>> seedItems;
}
