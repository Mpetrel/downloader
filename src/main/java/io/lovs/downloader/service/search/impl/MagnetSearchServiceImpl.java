package io.lovs.downloader.service.search.impl;

import com.github.kevinsawicki.http.HttpRequest;
import io.lovs.downloader.entity.search.MagnetSearchDetailEntity;
import io.lovs.downloader.entity.search.MagnetSearchEntity;
import io.lovs.downloader.entity.search.SearchEntity;
import io.lovs.downloader.entity.search.SearchResultEntity;
import io.lovs.downloader.service.search.SearchService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @program: downloader
 * @description:
 * @author: Larry
 * @create: 2018-07-12 14:03
 **/
@Service
@Slf4j
public class MagnetSearchServiceImpl implements SearchService {

    private static final String source = "http://www.btyunsou.co/search/%s_%s_%s.html";
    private static final String detailUrl = "http://www.btyunsou.co/%s.html";
    private static final List<String> sortType = Arrays.asList("ctime", "length", "click");
    private static final Map<String, String> headers = new HashMap<>();

    static {
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
    }

    @Override
    public SearchResultEntity search(@NonNull String keyword, String sort, Integer page, Integer size) {
        if (page < 0 || page > 200) {
            page = 1;
        }
        if (sort == null || !sortType.contains(sort)) {
            sort = sortType.get(0);
        }
        String url = String.format(source, keyword, sort, page);
        String body = HttpRequest.get(url).headers(headers).body();
        SearchResultEntity resultEntity = new SearchResultEntity();
        resultEntity.setList(parse(body));
        resultEntity.setTotalPage(pageInfo(body));
        resultEntity.setCurrentPage(page);
        return resultEntity;
    }

    /**
     * 解析具体详情
     * @param key
     * @return
     */
    @Override
    public SearchEntity detail(@NonNull String key) {
        String body = HttpRequest.get(String.format(detailUrl, key)).headers(headers).body();
        return parseDetail(body);
    }


    /**
     * 解析网页信息
     * @param body
     * @return
     */
    private List<? extends SearchEntity> parse(String body) {
        Document doc = Jsoup.parse(body);
        List<MagnetSearchEntity> magnets = new ArrayList<>();
        Elements elements = doc.select("div[class=media-body]");
        if (elements != null && !elements.isEmpty()) {
            elements.forEach(element -> {
                MagnetSearchEntity entity = new MagnetSearchEntity();
                Element a = element.selectFirst(".title");
                entity.setName(a.text());
                entity.setHash(a.attr("href").substring(1)
                        .replace(".html", ""));
                entity.setUrl("magnet:?xt=urn:btih:" + entity.getHash());
                entity.setSizeName(element.selectFirst(".label-warning").text());
                entity.setHot(element.selectFirst(".label-primary").text());
                entity.setRate(calculateRate(entity.getHot()));
                entity.setCreateDate(element.selectFirst(".label-success").text());
                magnets.add(entity);
            });
        }
        return magnets;
    }


    /**
     * 计算评分
     * @param hot
     * @return
     */
    private Integer calculateRate(String hot) {
        try{
            int val = Integer.valueOf(hot);
            if (val > 1000) return 5;
            if (val > 500) return 4;
            if (val > 100) return 3;
            if (val > 50) return 2;
        }catch (Exception e) {
            log.error("Rate Calculate: error", e);
        }
        return 1;
    }

    /**
     * 解析总页数
     * @param body
     * @return
     */
    private Integer pageInfo(String body) {
        Document doc = Jsoup.parse(body);
        Element element = doc.selectFirst("a:containsOwn(末页)");
        if (element != null) {
            // 解析末页数量
            String href = element.attr("href");
            Pattern pattern = Pattern.compile(".*_(\\d*).html");
            Matcher matcher = pattern.matcher(href);
            if (matcher.find()) {
                return Integer.valueOf(matcher.group(1));
            }
        }
        return 0;
    }

    /**
     * 解析详情
     * @param body
     * @return
     */
    private MagnetSearchDetailEntity parseDetail(String body) {
        MagnetSearchDetailEntity detail = new MagnetSearchDetailEntity();
        Document doc = Jsoup.parse(body);
        Element nameElement = doc.selectFirst("div[class*=tor-title] > h2");
        detail.setName(nameElement.text());
        // 其他属性
        Element otherInfo = doc.selectFirst("table[class*=detail]");
        detail.setHash(otherInfo.selectFirst("td:containsOwn(Hash:) + td").text());
        detail.setFileNumber(otherInfo.selectFirst("td:containsOwn(数量:) + td").text());
        detail.setSizeName(otherInfo.selectFirst("td:containsOwn(大小:) + td").text());
        detail.setCreateDate(otherInfo.selectFirst("td:containsOwn(日期:) + td").text());
        detail.setHot(otherInfo.selectFirst("td:containsOwn(热度:) + td").text());

        // 包含文件详情
        List<Map<String, String>> fileList = new ArrayList<>();
        Element table = doc.selectFirst("table[class=table]");
        Elements trs = table.select("tbody > tr");
        if (trs != null) {
            trs.forEach(tr -> {
                Map<String, String> file = new HashMap<>();
                file.put("name", tr.child(0).text());
                file.put("sizeName", tr.child(1).text());
                fileList.add(file);
            });
        }

        detail.setSeedItems(fileList);
        return detail;
    }


}
