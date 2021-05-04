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
public class ScrapeDcinside implements ScraperServiceInterface {

    private static String DC_CRAWL_DATA_URL = "https://search.dcinside.com/post/p/1/sort/latest/q/";

    @Getter
    private List<PostEntity> postEntityList;

    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();

        Document doc;
            doc = Jsoup.connect(DC_CRAWL_DATA_URL + keyword).get();
            Elements elements = doc.select(".sch_result_list li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("dcinside")
                        .title(el.select(".tit_txt").text())
                        .created_at(el.select(".date_time").text())
                        .url(el.select(".tit_txt").attr("href"))
                        .type(el.select(".sub_txt").text())
                        .build();
                Document doc2= Jsoup.connect(postEntity.getUrl()).get();
                String view = doc2.select("span.gall_count").text();
                postEntity.setContent(doc2.select(".gallview_contents").html());
                postEntity.setAuthor(doc2.select(".nickname.in").attr("title"));
                postEntity.setView(view.replaceAll("[^0-9]", ""));

                postEntityList.add(postEntity);
            }

        return postEntityList;
    }
}