package io.lovs.downloader.service.search;


import io.lovs.downloader.entity.search.SearchEntity;
import io.lovs.downloader.entity.search.SearchResultEntity;

public interface SearchService {

    SearchResultEntity search(String keyword, String sort, Integer page, Integer size);

    SearchEntity detail(String key);

}
