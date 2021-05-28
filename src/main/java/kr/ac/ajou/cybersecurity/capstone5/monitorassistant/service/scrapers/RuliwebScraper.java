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
public class RuliwebScraper implements Scraper {

    private static String RULIWEB_CRAWL_DATA_URL = "https://bbs.ruliweb.com/community/board/300148?search_type=subject_content&search_key=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document doc;
        Connection.Response response =
                Jsoup.connect(RULIWEB_CRAWL_DATA_URL + keyword + "&page=" + (1))
                        .userAgent("Mozilla")
                        .referrer("www.google.com")
                        .followRedirects(false)
                        .execute();
        doc = response.parse();

        Elements elements = doc.select("tr.table_body");
        for (Element el : elements) {
            if (el.select("td.divsn.text_over").text().equals("")) continue;
            ChangeDate date = new ChangeDate(el.select("td.time").text(), 9);
            PostEntity postEntity = PostEntity.builder()
                    .author(el.select(".name").text())
                    .site("루리웹")
                    .title(el.select("a.deco").text())
                    .url(el.select("a.deco").attr("href"))
                    .author(el.select("td.writer.text_over").text())
                    .view(el.select("td.hit span").text())
                    .created_at(date.getLocalDateTime())
                    .type(el.select("td.divsn.text_over").text())
                    .build();

            list.add(postEntity);
        }
        return list;
    }
}