package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ChangeDate;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ChangeImgSrc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DogdripScraper implements Scraper {

    private static String DOGDRIP_USER_CRAWL_DATA_URL = "https://www.dogdrip.net/index.php?_filter=search&mid=userdog&search_target=title_content&search_keyword=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[3];
        for (int i = 0; i < 3; i++) {
            doc[i] = Jsoup.connect(DOGDRIP_USER_CRAWL_DATA_URL + keyword + "&page=" + (i + 1))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .referrer("www.google.com")
                    .get();

            Elements elements = doc[i].select("table.ed.table.table-divider tbody tr");
            for (Element el : elements) {
                if (el.select(".author").text().equals("overflow")) continue;
                PostEntity postEntity = PostEntity.builder()
                        .site("개드립")
                        .type("유저개드립")
                        .title(el.select("span.ed.title-link").text())
                        .url("https://www.dogdrip.net" + el.select(".ed.link-reset").attr("href"))
                        .author(el.select(".author").text())
                        .build();
                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                String str = doc2.select("span.ed.margin-right-small > span.ed.text-xsmall.text-muted").text();
                ChangeDate date=new ChangeDate(str.substring(0,str.indexOf("  ")),7);
                postEntity.setCreated_at(date.getLocalDateTime());
               // postEntity.setContent(doc2.select("div#article_1").html());
                postEntity.setView(str.substring(str.indexOf("  ") + 2));
                /* **** IMG SRC 변환  */
                String content=doc2.select("div#article_1").html();
                ChangeImgSrc tmp = new ChangeImgSrc(content,1);
                postEntity.setContent(tmp.getHtml());
                /* **** IMG SRC 변환  */
                list.add(postEntity);
            }
        }
        return list;
    }
}