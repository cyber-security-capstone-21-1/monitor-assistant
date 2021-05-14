package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ChangeDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class NaverScraper implements Scraper {

    private static String NAVER_CRAWL_DATA_URL = "https://search.naver.com/search.naver?where=article&sm=tab_opt&query=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();

        Document doc = Jsoup.connect(NAVER_CRAWL_DATA_URL + keyword).get();
        Elements elements = doc.select("ul.lst_total li");
        for (Element el : elements) {
            PostEntity postEntity = PostEntity.builder()
                    .site("네이버")
                    .title(el.select(".api_txt_lines.total_tit").text())
                    .url(el.select(".api_txt_lines.total_tit").attr("href"))
                    .type(el.select(".sub_txt.sub_name").text())
                    .content(el.select("div.total_wrap.api_ani_send").html())
                    .build();
            list.add(postEntity);
        }
        return list;
    }
}