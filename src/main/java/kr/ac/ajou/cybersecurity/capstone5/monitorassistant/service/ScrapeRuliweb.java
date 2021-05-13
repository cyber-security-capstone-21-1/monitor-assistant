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
public class ScrapeRuliweb implements ScraperServiceInterface {

    private static String RULIWEB_CRAWL_DATA_URL =
            "https://bbs.ruliweb.com/community/board/300148?search_type=subject_content&search_key=";

    @Getter
    private List<PostEntity> postEntityList;

    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document doc[] = new Document[3];
        for(int i=0;i<3;i++) {
//            doc[i]=Jsoup.connect(RULIWEB_CRAWL_DATA_URL + keyword + "&page="+(i+1))
//                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
//                            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
//                    .referrer("www.google.com")
//                    .get();

        Connection.Response response =
                Jsoup.connect(RULIWEB_CRAWL_DATA_URL + keyword + "&page="+(i+1))
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                        .referrer("www.google.com")
                        .execute();
        System.out.println("ruliweb: "+i+" "+response.statusCode()+response.statusMessage());
        doc[i] = response.parse();
            Elements elements = doc[i].select("tr.table_body");
           // System.out.println(doc[i]);
            for (Element el : elements) {
                if (el.select("td.divsn.text_over").text().equals(""))
                    continue;
                PostEntity postEntity = PostEntity.builder()
                        .author(el.select(".name").text())
                        .site("ruliweb")
                        .title(el.select("a.deco").text())
                        .url(el.select("a.deco").attr("href"))
                        .author(el.select("td.writer.text_over").text())
                        .view(el.select("td.hit span").text())
                        .type(el.select("td.divsn.text_over").text())
                        .build();
                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                postEntity.setContent(doc2.select("div.view_content").html());
                postEntity.setCreated_at(doc2.select("span.regdate").text());
                postEntityList.add(postEntity);
            }
        }
        return postEntityList;
    }
}