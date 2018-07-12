package io.lovs.downloader.service.search.impl;

import com.github.kevinsawicki.http.HttpRequest;
import io.lovs.downloader.entity.search.MagnetSearchEntity;
import io.lovs.downloader.entity.search.SearchEntity;
import io.lovs.downloader.service.search.SearchService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private static final String[] sortType = {"ctime", "length", "click"};
    private static final Map<String, String> headers = new HashMap<>();

    static {
        headers.put("X-Requested-With", "XMLHttpRequest");
        headers.put("User-Agent", "Mozilla/5.0 (Windows NT 6.1; rv:6.0.2) Gecko/20100101 Firefox/6.0.2");
    }

    @Override
    public List<? extends SearchEntity> search(@NonNull String keyword, Integer page, Integer size) {
        if (page < 0 || page > 200) {
            page = 1;
        }
        String url = String.format(source, keyword, sortType[2], page);
        return parse(HttpRequest.get(url).headers(headers).body());
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
                entity.setUrl("magnet:?xt=urn:btih:" + a.attr("href").substring(1).replace(".html", ""));
                entity.setSizeName(element.selectFirst(".label-warning").text());
                entity.setHot(element.selectFirst(".label-primary").text());
                magnets.add(entity);
            });
        }
        return magnets;
    }


}
