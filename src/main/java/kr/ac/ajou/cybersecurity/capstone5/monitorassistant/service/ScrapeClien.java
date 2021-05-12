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
public class ScrapeClien implements ScraperServiceInterface {

    private static String Clien_CRAWL_DATA_URL = "https://www.clien.net/service/search?&sort=recency&boardCd=&isBoard=false&q=";

    @Getter
    private List<PostEntity> postEntityList;

    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document[] doc = new Document[3];

        for (int i = 0; i < 3; i++) {
            doc[i] = Jsoup.connect(Clien_CRAWL_DATA_URL + keyword + "&p=" + (i)).get();//3page까지 긁어오기
            Elements elements = doc[i].select(".list_item.symph_row.jirum");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .author(el.select(".nickname").text())
                        .site("clien")
                        .title(el.select(".subject_fixed").text())
                        .created_at(el.select(".timestamp").text())
                        .url("https://www.clien.net" + el.select(".subject_fixed").attr("href"))
                        .content(el.select(".preview").text())
                        .type(el.select(".shortname.fixed").text())
                        .view(el.select(".hit").text())
                        .build();
                if(postEntity.getAuthor().equals("")) {
                    postEntity.setAuthor(el.select(".nickname img").attr("alt"));
                }
                Document doc2= Jsoup.connect(postEntity.getUrl()).get();
                postEntity.setContent(doc2.select("div.post_article").html());
                postEntityList.add(postEntity);
            }
        }
        return postEntityList;
    }
}