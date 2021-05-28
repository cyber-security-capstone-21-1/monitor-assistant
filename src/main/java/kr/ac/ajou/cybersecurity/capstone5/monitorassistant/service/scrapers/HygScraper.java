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
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HygScraper implements Scraper {

    private static String HYG_CRAWL_DATA_URL = "https://hygall.com/index.php?mid=hy&search_target=title_content&search_keyword=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException, ParseException {
        List<PostEntity> list = new ArrayList<>();
        Document doc[] = new Document[2];

        for (int i = 0; i < 2; i++) {
            Connection.Response response =
                    Jsoup.connect(HYG_CRAWL_DATA_URL + keyword + "&page=" + (i + 1))
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .execute();
            doc[i] = response.parse();

            Elements elements = doc[i].select("tr.docList.exBg0");

            for (Element el : elements) {
                if (!el.select("td.title").text().equals("")) {
                    PostEntity postEntity = PostEntity.builder()
                            .site("해연갤")
                            .title(el.select("td.title").text())
                            .url(el.select("a.exJsHotTrackA").attr("href"))
                            .author("ㅇㅇ")
                            .view(el.select("td.readed_count").text())
                            .build();
                    Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                    ChangeDate date = new ChangeDate(doc2.select("div.dayACut.exBg1 > div.date").text(), 4);
                    postEntity.setCreated_at(date.getLocalDateTime());
                    list.add(postEntity);
                }
            }
        }
        return list;
    }
}