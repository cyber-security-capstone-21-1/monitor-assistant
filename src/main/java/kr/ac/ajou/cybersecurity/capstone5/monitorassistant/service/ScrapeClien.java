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
public class ScrapeClien implements ScraperServiceInterface {

    private static String Clien_CRAWL_DATA_URL = "https://www.clien.net/service/search?q=";

    @Getter
    private List<PostEntity> postEntityList;

    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document[] doc = new Document[3];
        for (int i = 0; i < 3; i++) {
            Connection.Response response =
                    Jsoup.connect(Clien_CRAWL_DATA_URL + keyword +
                    "&sort=recency&p=" +(i)+"&boardCd=&isBoard=false")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .execute();
            System.out.println("clien:  "+i+" "+response.statusCode()+response.statusMessage());
            doc[i] = response.parse();
            Elements elements = doc[i].select(".list_item.symph_row.jirum");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .author(el.select(".nickname").text())
                        .site("clien")
                        .title(el.select(".subject_fixed").text())
                        .created_at(el.select(".timestamp").text())
                        .url("https://www.clien.net" + el.select(".subject_fixed").attr("href"))
                        .content(el.select(".preview").text())
                        .type(el.select(".shortname.fixed").text())
                        .view(el.select(".hit").text())
                        .build();
                if(postEntity.getAuthor().equals("")) {

                    postEntity.setAuthor(el.select(".nickname img").attr("alt"));
                }
                Document doc2= Jsoup.connect(postEntity.getUrl()).get();
                postEntity.setContent(doc2.select("div.post_article").html());
               // System.out.println("clien title: "+postEntity.getTitle());
                postEntityList.add(postEntity);
            }
        }
        return postEntityList;
    }
}