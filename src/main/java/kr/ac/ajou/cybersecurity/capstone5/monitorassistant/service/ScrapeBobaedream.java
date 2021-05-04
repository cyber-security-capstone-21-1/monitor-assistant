package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import lombok.Getter;
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
public class ScrapeBobaedream implements ScraperServiceInterface {

    private static String BOBAEDREAM_CRAWL_DATA_URL = "";

    @Getter
    private List<PostEntity> postEntityList;

    //작가 없음
    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        for(int i=0;i<2;i++) {
            Document[] doc = new Document[2];
            Connection.Response response =
                    Jsoup.connect("https://www.bobaedream.co.kr/search")
                            .userAgent("Mozilla/5.0")
                            .timeout(15000)
                            .method(Connection.Method.POST)
                            .data("colle", "community")
                            .data("searchField", "ALL")
                            .data("page", Integer.toString(i+1))
                            .data("sort", "DATE")
                            .data("startDate", "")
                            .data("keyword", keyword)
                            .followRedirects(true)
                            .execute();
            doc[i] = response.parse();
            Elements elements = doc[i].select(".search_Community ul li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("bobaedream")
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
                postEntityList.add(postEntity);
            }
        }
       return postEntityList;
    }
}