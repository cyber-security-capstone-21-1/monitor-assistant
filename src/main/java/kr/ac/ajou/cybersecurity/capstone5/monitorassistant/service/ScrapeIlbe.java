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
public class ScrapeIlbe implements ScraperServiceInterface {

    private static String ILBE_CRAWL_DATA_URL =
            "https://www.ilbe.com/search?docType=doc&searchType=title_content&q=";

    @Getter
    private List<PostEntity> postEntityList;

    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document doc[] = new Document[3];
        for(int i=0;i<3;i++) {
            doc[i]=Jsoup.connect(ILBE_CRAWL_DATA_URL + keyword + "&page=" + (i+1))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .referrer("www.google.com")
                    .get();
            Elements elements = doc[i].select("div.search-list ul li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("ilbe")
                        .title(el.select("a.title").text())
                        .url("https://www.ilbe.com"+el.select("a.title").attr("href"))
                        .author(el.select("span.nick").text())
                        .created_at(el.select("span.date").text())
                        .build();
                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                postEntity.setContent(doc2.select("div.post-content").html());
                postEntity.setType(doc2.select("div.board-view > div.board-header").text());
                postEntity.setView(doc2.select("em.color-ibred").text());
                postEntityList.add(postEntity);
            }
        }
        return postEntityList;
    }
}