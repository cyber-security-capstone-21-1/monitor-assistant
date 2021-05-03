package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapeMlbpark implements ScraperServiceInterface {

    private static String MLBPARRK_CRAWL_DATA_URL = "http://mlbpark.donga.com/mp/b.php?p=1&m=search&b=bullpen&select=sct&query=";

    @Getter
    private List<PostEntity> postEntityList;

    //작가 없음
    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document doc ;

            doc = Jsoup.connect( MLBPARRK_CRAWL_DATA_URL + keyword).get();
            Elements elements = doc.select(".tbl_type01 tbody tr");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("mlbpark")
                        .title(el.select("div.tit").text())
                        .url(el.select("div.tit > a").attr("href"))
                        .type(el.select(".list_word").text())
                        .author(el.select("span.nick").text())
                        .view(el.select("span.viewV").text())
                        .build();
                if(!postEntity.getAuthor().equals("엠팍제휴팀")) {
                    Document doc2= Jsoup.connect(postEntity.getUrl()).get();
                   postEntity.setCreated_at(doc2.select("div.text3 > span.val").text());
                   postEntity.setContent(doc2.select("div.view_context").html());
                    postEntityList.add(postEntity);
                }
              //  Document doc2= Jsoup.connect(postEntity.getUrl()).get();
//                postEntity.setContent();
//                postEntity.setCreated_at();



            }
        return postEntityList;
    }
}