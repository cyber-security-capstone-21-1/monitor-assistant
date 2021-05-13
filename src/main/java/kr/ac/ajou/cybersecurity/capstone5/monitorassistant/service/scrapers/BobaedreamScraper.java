package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
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
public class BobaedreamScraper implements Scraper {

    private static String BOBAEDREAM_CRAWL_DATA_URL = "https://www.bobaedream.co.kr/search";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[3];
        for(int i = 0; i < 3; i++) {
            Connection.Response response =
                    Jsoup.connect(BOBAEDREAM_CRAWL_DATA_URL)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .method(Connection.Method.POST)
                            .data("colle", "community")
                            .data("searchField", "ALL")
                            .data("page", Integer.toString(i + 1))
                            .data("sort", "DATE")
                            .data("startDate", "")
                            .data("keyword", keyword)
                            .followRedirects(true)
                            .execute();
            doc[i] = response.parse();
            Elements elements = doc[i].select(".search_Community ul li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("보배드림")
                        .title(el.select("dl dt").text())
                        .url("https://www.bobaedream.co.kr" + el.select("dl > dt > a").attr("href"))
                        .type(el.select("span.first").text())
                        .build();
                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                String str = doc2.select("span.countGroup").text();
                postEntity.setAuthor(doc2.select("a.nickname").text());
                postEntity.setView(str.substring(3, str.indexOf("|") - 1));
                postEntity.setCreated_at(str.substring(str.lastIndexOf("|") + 2));
                postEntity.setContent(doc2.select("div.content02").html());
                list.add(postEntity);
            }
        }
        return list;
    }
}