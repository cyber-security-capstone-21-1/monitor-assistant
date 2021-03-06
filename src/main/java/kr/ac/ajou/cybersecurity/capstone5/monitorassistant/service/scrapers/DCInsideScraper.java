package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ChangeDate;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DCInsideScraper implements Scraper {

    private static String DC_CRAWL_DATA_URL = "https://search.dcinside.com/post/p/";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document doc;

        Connection.Response response =
                Jsoup.connect(DC_CRAWL_DATA_URL + 1 + "/sort/latest/q/" + keyword)
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                        .referrer("www.google.com")
                        .execute();
        doc = response.parse();

        Elements elements = doc.select(".sch_result_list li");
        for (Element el : elements) {
            PostEntity postEntity = PostEntity.builder()
                    .site("디씨인사이드")
                    .title(el.select(".tit_txt").text())
                    .url(el.select(".tit_txt").attr("href"))
                    .type(el.select(".sub_txt").text())
                    .build();
            ChangeDate date = new ChangeDate(el.select(".date_time").text(), 4);
            postEntity.setCreated_at(date.getLocalDateTime());
            list.add(postEntity);
        }

        return list;
    }
}