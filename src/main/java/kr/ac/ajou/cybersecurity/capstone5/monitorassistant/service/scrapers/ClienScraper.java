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
                long beforeTime = System.currentTimeMillis();

        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[3];
        for (int i = 0; i < 3; i++) {
            Connection.Response response =
                    Jsoup.connect(Clien_CRAWL_DATA_URL + keyword +
                            "&sort=recency&p=" + i + "&boardCd=&isBoard=false")
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .ignoreHttpErrors(true)
                            .execute();
            doc[i] = response.parse();
            Elements elements = doc[i].select(".list_item.symph_row.jirum");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .author(el.select(".nickname").text())
                        .site("clien")
                        .title(el.select(".subject_fixed").text())
                        .url("https://www.clien.net" + el.select(".subject_fixed").attr("href"))
                        .content(el.select(".preview").text())
                        .type(el.select(".shortname.fixed").text())
                        .view(el.select(".hit").text())
                        .build();
                ChangeDate fun= new ChangeDate(el.select(".timestamp").text(),1);
                postEntity.setCreated_at(fun.getLocalDateTime());
                if (postEntity.getAuthor().equals("")) {
                    postEntity.setAuthor(el.select(".nickname img").attr("alt"));
                }
                Document doc2 = Jsoup.connect(postEntity.getUrl())
                        .ignoreHttpErrors(true)
                        .get();
                postEntity.setContent(doc2.select("div.post_article").html());
                list.add(postEntity);
            }
        }

        long afterTime = System.currentTimeMillis();
        long secDiffTime = (afterTime - beforeTime)/1000;
        System.out.println("클리앙 걸리는 시간 : "+secDiffTime);
        return list;
    }
}