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
public class ClienScraper implements Scraper {

    private static String Clien_CRAWL_DATA_URL = "https://www.clien.net/service/search?q=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {

        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[2];
        for (int i = 0; i < 2; i++) {
            Connection.Response response =
                    Jsoup.connect(Clien_CRAWL_DATA_URL + keyword + "&sort=recency&p=" + (i + 1) + "&boardCd=&isBoard=false")
                            .userAgent("Mozilla")
                            .referrer("www.google.com")
                            .followRedirects(false)
                            .ignoreHttpErrors(true)
                            .execute();
            doc[i] = response.parse();
            System.out.println("클리앙 : " +response.statusCode());
            System.out.println("클리앙 : " +response.headers());
            System.out.println("클리앙 : " +response.url());
            System.out.println("클리앙 : " +doc[i].html());
            Elements elements = doc[i].select(".list_item.symph_row.jirum");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .author(el.select(".nickname").text())
                        .site("클리앙")
                        .title(el.select(".subject_fixed").text())
                        .url("https://www.clien.net" + el.select(".subject_fixed").attr("href"))
                        .type(el.select(".shortname.fixed").text())
                        .view(el.select(".hit").text())
                        .build();
                ChangeDate fun = new ChangeDate(el.select(".timestamp").text(), 1);
                postEntity.setCreated_at(fun.getLocalDateTime());
                if (postEntity.getAuthor().equals("")) {
                    postEntity.setAuthor(el.select(".nickname img").attr("alt"));
                }
                list.add(postEntity);
            }
        }
        return list;
    }
}