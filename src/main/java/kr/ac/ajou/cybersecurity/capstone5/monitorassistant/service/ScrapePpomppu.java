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
public class ScrapePpomppu implements ScraperServiceInterface {

    private static String PPOMPPU_CRAWL_DATA_URL = "http://www.ppomppu.co.kr/search_bbs.php?page_size=50&bbs_cate=2&" +
            "order_type=date&search_type=sub_memo&keyword=";

    @Getter
    private List<PostEntity> postEntityList;

    //작가 없음
    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document doc ;

            doc = Jsoup.connect(PPOMPPU_CRAWL_DATA_URL + keyword)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) " +
                            "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                    .referrer("www.google.com")
                    .get();
            Elements elements = doc.select(".results_board div div.content");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("ppomppu")
                        .title(el.select("span.title").text())
                        .url("http://www.ppomppu.co.kr/" + el.select("span.title > a").attr("href"))
                        .build();
                String str=el.select(".desc").text();
                postEntity.setType(str.substring(1,str.indexOf("]")));
                postEntity.setView(str.substring(str.indexOf(":")+1,str.indexOf(":")+3));
                Document doc2= Jsoup.connect(postEntity.getUrl()).get();
                postEntity.setContent(doc2.select(".pic_bg").html());
                postEntity.setAuthor(doc2.select(".view_name").text());
                String str2=doc2.select(".sub-top-text-box").text();
                postEntity.setCreated_at(str2.substring(str2.indexOf("등록일:")+5,str2.indexOf("조회수:")-1));


                postEntityList.add(postEntity);
            }
        return postEntityList;
    }
}