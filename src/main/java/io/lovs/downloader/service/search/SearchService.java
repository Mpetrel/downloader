package io.lovs.downloader.service.search;

import io.lovs.downloader.entity.search.SearchEntity;

import java.util.List;

public interface SearchService {

    List<? extends SearchEntity> search(String keyword, Integer page, Integer size);

}
