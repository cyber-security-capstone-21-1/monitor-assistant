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
public class TodayhumorScraper implements Scraper {

    private static String TodayHumor_CRAWL_DATA_URL = "http://www.todayhumor.co.kr/board/list.php?kind=search&keyfield=subject&keyword=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[3];
        for(int i = 0; i < 3; i++) {
            Connection.Response response =
                    Jsoup.connect(TodayHumor_CRAWL_DATA_URL + keyword + "&page=" + (i + 1))
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .execute();
            doc[i] = response.parse();
            Elements elements = doc[i].select(".table_list tbody tr");
            for (Element el : elements) {
                if (!el.select(".name").text().equals("")) {
                    PostEntity postEntity = PostEntity.builder()
                            .author(el.select(".name").text())
                            .site("오늘의유머")
                            .title(el.select(".subject a").text())
                            .url("http://www.todayhumor.co.kr" + el.select(".subject a").attr("href"))
                            .view(el.select(".hits").text())
                            .type(el.attr("class").substring(13))
                            .build();
                    ChangeDate fun= new ChangeDate(el.select(".date").text(),3);
                    postEntity.setCreated_at(fun.getLocalDateTime());
                    list.add(postEntity);
                }
            }
        }
        return list;
    }
}