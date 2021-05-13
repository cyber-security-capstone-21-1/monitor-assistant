package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
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

    private static String TEST_CRAWL_DATA_URL = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document doc = Jsoup.connect(TEST_CRAWL_DATA_URL + keyword).get();
        Elements elements = doc.select(".bx._svp_item");
        for (Element el : elements) {
            PostEntity postEntity = PostEntity.builder()
                    .author(el.select(".elss.etc_dsc_inner").text())
                    .site("네이버")
                    .title(el.select(".api_txt_lines.total_tit").text())
                    .created_at(el.select(".sub_time.sub_txt").text())
                    .url(el.select(".api_txt_lines.total_tit").attr("href"))
                    .content(el.select(".total_wrap.api_ani_send").html())
                    .type("블로그")
                    .build();
            list.add(postEntity);
        }
        return list;
    }
}