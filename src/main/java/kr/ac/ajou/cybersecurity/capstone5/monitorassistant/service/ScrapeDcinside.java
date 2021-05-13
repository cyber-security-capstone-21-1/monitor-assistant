package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import lombok.Getter;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScrapeDcinside implements ScraperServiceInterface {

    private static String DC_CRAWL_DATA_URL = "https://search.dcinside.com/post/p/";

    @Getter
    private List<PostEntity> postEntityList;

    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        for(int i=0;i<3;i++) {
            Document[] doc = new Document[3];
//            doc[i] =
//                    Jsoup.connect(DC_CRAWL_DATA_URL +(i+1)+"/sort/latest/q/"+ keyword)
//                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
//                                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
//                            .referrer("www.google.com")
//                            .get();

            Connection.Response response =
                    Jsoup.connect(DC_CRAWL_DATA_URL +(i+1)+"/sort/latest/q/"+ keyword)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                                    "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .execute();
            System.out.println("dc:"+ response.statusCode());
            doc[i] = response.parse();
            Elements elements = doc[i].select(".sch_result_list li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("dcinside")
                        .title(el.select(".tit_txt").text())
                        .created_at(el.select(".date_time").text())
                        .url(el.select(".tit_txt").attr("href"))
                        .type(el.select(".sub_txt").text())
                        .build();
                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                String view = doc2.select("span.gall_count").text();
                postEntity.setContent(doc2.select(".writing_view_box").html());
                postEntity.setAuthor(doc2.select(".nickname.in").attr("title"));
                postEntity.setView(view.replaceAll("[^0-9]", ""));

                postEntityList.add(postEntity);
            }
        }
        return postEntityList;
    }
}