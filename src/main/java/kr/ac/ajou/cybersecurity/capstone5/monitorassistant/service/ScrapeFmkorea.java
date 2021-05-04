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
public class ScrapeFmkorea implements ScraperServiceInterface {

    private static String FMKOREA_CRAWL_DATA_URL = "https://www.fmkorea.com/index.php?act=IS&mid=home&where=document&is_keyword=";

    @Getter
    private List<PostEntity> postEntityList;

    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document[] doc = new Document[3];

        for (int i = 0; i < 3; i++) {
            doc[i] = Jsoup.connect(FMKOREA_CRAWL_DATA_URL + keyword + "&page=" + (i+1)).get();
            Elements elements = doc[i].select(".searchResult li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .author(el.select("strong").text())
                        .site("fmkorea")
                        .title(el.select("dt a").text())
                        .created_at(el.select(".time").text())
                        .url("https://www.fmkorea.com" + el.select("dt a").attr("href"))
                        .content(el.select("dd").text())
                        .build();
                Document doc2= Jsoup.connect(postEntity.getUrl()).get();
                String str=doc2.select(".side.fr").text();
                String str2=postEntity.getTitle();
                postEntity.setView(str.substring(5,str.indexOf("추천")-1));
                postEntity.setContent(doc2.select("div.rd_body.clear").html());
                postEntity.setType(str2.substring(1,str2.indexOf("]")));
                postEntityList.add(postEntity);
            }
        }
        return postEntityList;
    }
}