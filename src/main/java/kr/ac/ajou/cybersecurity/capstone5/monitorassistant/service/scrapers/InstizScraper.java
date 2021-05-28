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
public class InstizScraper implements Scraper {

    private static String INSTIZ_CRAWL_DATA_URL = "https://www.instiz.net/name?page=";

    //"name?page=1&category=1&k="+"&stype=9#greentop"
    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document doc;

        doc = Jsoup.connect(INSTIZ_CRAWL_DATA_URL + (1) + "&category=1&k=" + keyword + "&stype=9#greentop")
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                .referrer("www.google.com")
                .get();
        Elements elements = doc.select("#mainboard > tbody > tr");
        for (Element el : elements) {
            if (!el.select("span#subject").text().equals("")) {
                PostEntity postEntity = PostEntity.builder()
                        .site("인스티즈")
                        .title(el.select("span#subject").text())
                        .author("익명")
                        .url("https://www.instiz.net" + el.select("span#subject a").attr("href").substring(2))
                        .type("일상")
                        .build();
                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                String time = doc2.select("meta[property=article:published_time]").attr("content");
                ChangeDate date = new ChangeDate(time.substring(0, 19), 8);

                postEntity.setCreated_at(date.getLocalDateTime());

                list.add(postEntity);
            }
        }

        return list;
    }
}