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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class IlbeScraper implements Scraper {

    private static String ILBE_CRAWL_DATA_URL = "https://www.ilbe.com/search?docType=doc&searchType=title_content&q=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException, ParseException {

        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[2];
        for (int i = 0; i < 2; i++) {
            Connection.Response response =
                    Jsoup.connect(ILBE_CRAWL_DATA_URL + keyword + "&page=" + (i + 1))
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .execute();
            doc[i] = response.parse();

            Elements elements = doc[i].select("div.search-list ul li");

            for (Element el : elements) {

                PostEntity postEntity = PostEntity.builder()
                        .site("일간베스트")
                        .title(el.select("a.title").text())
                        .url("https://www.ilbe.com" + el.select("a.title").attr("href"))
                        .author(el.select("span.nick").text())
                        .build();
                ChangeDate fun = new ChangeDate(el.select("span.date").text(), 1);
                postEntity.setCreated_at(fun.getLocalDateTime());
                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                postEntity.setType(doc2.select("div.board-view > div.board-header").text());
                postEntity.setView(doc2.select("em.color-ibred").text());

                list.add(postEntity);
            }
        }

        return list;
    }
}