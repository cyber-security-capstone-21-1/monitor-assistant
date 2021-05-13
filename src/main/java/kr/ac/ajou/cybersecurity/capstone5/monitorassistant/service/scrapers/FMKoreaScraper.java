package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
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
public class FMKoreaScraper implements Scraper {

    private static String FMKOREA_CRAWL_DATA_URL = "https://www.fmkorea.com/index.php?act=IS&mid=home&where=document&is_keyword=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[3];

        for (int i = 0; i < 3; i++) {
            Connection.Response response =
                    Jsoup.connect(FMKOREA_CRAWL_DATA_URL + keyword + "&page=" + (i + 1))
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("www.google.com")
                            .execute();
            doc[i] = response.parse();

            Elements elements = doc[i].select(".searchResult li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .author(el.select("strong").text())
                        .site("에펨코리아")
                        .title(el.select("dt a").text())
                        .created_at(el.select(".time").text())
                        .url("https://www.fmkorea.com" + el.select("dt a").attr("href"))
                        .content(el.select("dd").text())
                        .build();

                Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
                String str = doc2.select(".side.fr").text();
                String str2 = postEntity.getTitle();
                postEntity.setView(str.substring(5,str.indexOf("추천")-1));
                postEntity.setContent(doc2.select("div.rd_body.clear").html());
                postEntity.setType(str2.substring(1,str2.indexOf("]")));
                list.add(postEntity);
            }
        }
        return list;
    }
}