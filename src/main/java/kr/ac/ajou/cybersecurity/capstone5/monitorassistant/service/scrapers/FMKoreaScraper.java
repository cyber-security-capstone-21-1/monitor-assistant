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
public class FMKoreaScraper implements Scraper {

    private static String FMKOREA_CRAWL_DATA_URL = "https://www.fmkorea.com/index.php?act=IS&is_keyword=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();

        Document doc;

        Connection.Response response =
                Jsoup.connect(FMKOREA_CRAWL_DATA_URL + keyword + "&mid=home&where=document&page=&page=" + (1))
                        .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                        .referrer("www.google.com")
                        .execute();
        doc = response.parse();

        Elements elements = doc.select(".searchResult li");
        for (Element el : elements) {
            PostEntity postEntity = PostEntity.builder()
                    .author(el.select("strong").text())
                    .site("에펨코리아")
                    .title(el.select("dt a").text())
                    .url("https://www.fmkorea.com" + el.select("dt a").attr("href"))
                    .build();
            ChangeDate fun = new ChangeDate(el.select(".time").text(), 2);
            postEntity.setCreated_at(fun.getLocalDateTime());

            Document doc2 = Jsoup.connect(postEntity.getUrl())
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .get();
            String str = doc2.select(".side.fr").text();
            String str2 = postEntity.getTitle();
            postEntity.setView(str.substring(5, str.indexOf("추천") - 1));
            postEntity.setType(str2.substring(1, str2.indexOf("]")));

            list.add(postEntity);
        }
        return list;
    }
}