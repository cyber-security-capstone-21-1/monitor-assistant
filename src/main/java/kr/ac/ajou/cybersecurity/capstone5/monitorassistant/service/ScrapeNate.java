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
import java.util.List;

@Service
public class ScrapeNate implements ScraperServiceInterface {

    private static String NATE_CRAWL_DATA_URL = "https://pann.nate.com/search/talk?q=";

    @Getter
    private List<PostEntity> postEntityList;

    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document[] doc = new Document[3];
        for (int i = 0; i < 3; i++) {
            doc[i] = Jsoup.connect(NATE_CRAWL_DATA_URL + keyword + "&page=" + (i + 1))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .referrer("www.google.com")
                    .get();//3page까지 긁어오기
            Elements elements = doc[i].select(".s_list li");
            for (Element el : elements) {
                Document doc_detail = Jsoup.connect("https://pann.nate.com" + el.select(".subject").attr("href")).get();
                String content_all = doc_detail.select("#contentArea").html();
                String view=doc_detail.select("div.info > span.count").text();

                PostEntity postEntity = PostEntity.builder().author(el.select(".writer").text())
                        .site("nate")
                        .title(el.select(".subject").text())
                        .created_at(doc_detail.select("span.date").text())
                        .url("https://pann.nate.com" + el.select(".subject").attr("href"))
                        .content(content_all)
                        .view(view.replaceAll("[^0-9]", ""))
                        .type(el.select(".t_talk").text())
                        .build();
                postEntityList.add(postEntity);
            }
        }
        return postEntityList;
    }
}