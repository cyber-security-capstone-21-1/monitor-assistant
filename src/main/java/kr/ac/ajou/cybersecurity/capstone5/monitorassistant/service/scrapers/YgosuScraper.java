package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ChangeDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class YgosuScraper implements Scraper {

    private static String Ygosu_CRAWL_DATA_URL = "https://www.ygosu.com/all_search/?type=board&add_search_log=Y&order=1&keyword=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[2];
        for (int i = 0; i < 2; i++) {
            doc[i] = Jsoup.connect(Ygosu_CRAWL_DATA_URL + keyword + "&page=" + (i + 1))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .referrer("www.google.com")
                    .get();
            Elements elements = doc[i].select(".type_board2 > li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("와이고수")
                        .title(el.select(".subject").text())
                        .url(el.select(".subject").attr("href"))
                        .type(el.select("dd.etc > a.category").text())
                        .build();

                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                postEntity.setAuthor(doc2.select("div.nickname > a").text());
                String str = doc2.select("div.date").text();
                if (!str.isEmpty()) {
                    String[] str2 = str.split(" /");
                    if (!str2[0].isEmpty()) {
                        ChangeDate fun = new ChangeDate(str2[0].substring(7), 1);
                        postEntity.setCreated_at(fun.getLocalDateTime());
                    }
                    if (!str2[1].isEmpty()) {
                        postEntity.setView(str2[1].substring(7));
                    }
                }
                list.add(postEntity);
            }
        }
        return list;
    }
}