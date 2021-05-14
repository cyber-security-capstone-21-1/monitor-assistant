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
public class MLBParkScraper implements Scraper {

    private static String MLBPARRK_CRAWL_DATA_URL = "http://mlbpark.donga.com/mp/b.php?p=1&m=search&b=bullpen&select=sct&query=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document doc = Jsoup.connect( MLBPARRK_CRAWL_DATA_URL + keyword)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                .referrer("www.google.com")
                .get();

        Elements elements = doc.select(".tbl_type01 tbody tr");
        for (Element el : elements) {
            PostEntity postEntity = PostEntity.builder()
                    .site("MLB파크")
                    .title(el.select("div.tit").text())
                    .url(el.select("div.tit > a").attr("href"))
                    .type(el.select(".list_word").text())
                    .author(el.select("span.nick").text())
                    .view(el.select("span.viewV").text())
                    .build();

            if (!postEntity.getAuthor().equals("엠팍제휴팀")) {
                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                String time=doc2.select("div.text3 > span.val").text();
                postEntity.setContent(doc2.select("div.view_context").html());
                if(!time.equals("")){
               ChangeDate fun= new ChangeDate(time,2);
               postEntity.setCreated_at(fun.getLocalDateTime());
                }
                list.add(postEntity);
            }
        }
        return list;
    }
}