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
public class NatePannScraper implements Scraper {

    private static String NATE_CRAWL_DATA_URL = "https://pann.nate.com/search/talk?q=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[3];
        for (int i = 0; i < 3; i++) {
            doc[i] = Jsoup.connect(NATE_CRAWL_DATA_URL + keyword + "&page=" + (i + 1))
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .referrer("www.google.com")
                    .get();

            Elements elements = doc[i].select(".s_list li");
            for (Element el : elements) {
                Document doc_detail = Jsoup.connect("https://pann.nate.com" + el.select(".subject").attr("href")).get();
                String content_all = doc_detail.select("#contentArea").html();
                String view = doc_detail.select("div.info > span.count").text();
                PostEntity postEntity = PostEntity.builder()
                        .author(el.select(".writer").text())
                        .site("네이트판")
                        .title(el.select(".subject").text())
                        .url("https://pann.nate.com" + el.select(".subject").attr("href"))
                        .content(content_all)
                        .view(view.replaceAll("[^0-9]", ""))
                        .type(el.select(".t_talk").text())
                        .build();
                if(!doc_detail.select("div.info > span.date").text().equals("")) {
                    ChangeDate fun = new ChangeDate(doc_detail.select("div.info > span.date").text(), 4);
                    postEntity.setCreated_at(fun.getLocalDateTime());
                }

                list.add(postEntity);
            }
        }
        return list;
    }
}