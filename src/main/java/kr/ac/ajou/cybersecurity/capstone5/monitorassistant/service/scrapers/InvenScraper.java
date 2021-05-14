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
import java.util.ArrayList;
import java.util.List;

@Service
public class InvenScraper implements Scraper {

    private static String INVEN_CRAWL_DATA_URL = "http://www.inven.co.kr/search/webzine/article/";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException, ParseException {
        List<PostEntity> list = new ArrayList<>();

        Document doc[] = new Document[3];

        for(int i = 0; i < 3; i++) {
            Connection.Response response =
                    Jsoup.connect(INVEN_CRAWL_DATA_URL + keyword + "/" + (i + 1)+"?sort=recency")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .execute();
            doc[i] = response.parse();

            Elements elements = doc[i].select("div.section_body ul li");
            for (Element el : elements) {
                    PostEntity postEntity = PostEntity.builder()
                            .site("인벤")
                            .title(el.select("a.name").text())
                            .url(el.select("a.name").attr("href"))
                            .build();
                    Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                    postEntity.setContent(doc2.select("div.articleMain").html());
                String time=doc2.select("div.articleDate").text();
                    postEntity.setAuthor(doc2.select("div.articleWriter").text());
                if(!time.equals("")) {
                    ChangeDate fun = new ChangeDate(time,2);
                    postEntity.setCreated_at(fun.getLocalDateTime());
                }
                    list.add(postEntity);

            }
        }
        return list;
    }
}