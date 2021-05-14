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
public class Cook82Scraper implements Scraper {

    private static String COOK82_CRAWL_DATA_URL = "https://www.82cook.com/entiz/enti.php?bn=15&searchType=search&search1=1&keys=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[3];
        for(int i = 0; i < 3; i++) {
            Connection.Response response =
                    Jsoup.connect(COOK82_CRAWL_DATA_URL + keyword + "&page=" + (i + 1))
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .execute();
            doc[i] = response.parse();
            Elements elements = doc[i].select("div#bbs table tbody tr");
            for (Element el : elements) {
                if (!el.select("td.user_function").text().equals("82cook")) {
                    String t=el.select("td.numbers").text();
                    PostEntity postEntity = PostEntity.builder()
                            .author(el.select("td.user_function").text())
                            .site("82cook")
                            .view(t.substring(t.lastIndexOf(" "))+1)
                            .title(el.select("td.title a").text())
                            .url("https://www.82cook.com/entiz/" + el.select("td.title a").attr("href"))
                            .type("자유게시판")
                            .build();
                    ChangeDate date= new ChangeDate(el.select("td.regdate.numbers").attr("title"),1);
                    postEntity.setCreated_at(date.getLocalDateTime());
                    Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                    postEntity.setContent(doc2.select("div#articleBody").html());
                    list.add(postEntity);
                }
            }
        }
        return list;
    }
}