package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ScrapeYgosu implements ScraperServiceInterface {

    private static String Ygosu_CRAWL_DATA_URL = "https://www.ygosu.com/all_search/?type=board&add_search_log=Y&order=1&keyword=";

    @Getter
    private List<PostEntity> postEntityList;

    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document[] doc = new Document[3];
        for (int i = 0; i < 3; i++) {
            doc[i] = Jsoup.connect(Ygosu_CRAWL_DATA_URL + keyword + "&page=" + (i + 1)).get();//3page까지 긁어오기
            Elements elements = doc[i].select(".type_board2 > li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("ygosu")
                        .title(el.select(".subject").text())
                        .url(el.select(".subject").attr("href"))
                        .type(el.select("dd.etc > a.category").text())
                        .build();
                    Document doc2=Jsoup.connect(postEntity.getUrl()).get();
//                    String author=doc2.select("div.nickname > a").text();
                 String str=doc2.select("div.date").text();
                 String[] str2=str.split("/");
                    postEntity.setContent(doc2.select("div.container").html());
                postEntity.setAuthor(doc2.select("div.nickname > a").text());
                       postEntity.setView(str2[1].substring(7));
                      postEntity.setCreated_at(str2[0].substring(8));
                postEntityList.add(postEntity);
            }
        }
        return postEntityList;
    }
}