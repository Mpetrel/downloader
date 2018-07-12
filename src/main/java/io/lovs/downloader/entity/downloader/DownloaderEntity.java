package io.lovs.downloader.entity.downloader;

import lombok.Data;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-12 11:18
 **/
@Data
public class DownloaderEntity {
    private Long downloaded;
    private Integer piecesComplete;
    private Integer piecesInComplete;
    private Integer piecesNotSkipped;
    private Integer piecesRemaining;
    private Integer piecesSkipped;
    private Integer piecesTotal;
    private Long uploaded;
}
