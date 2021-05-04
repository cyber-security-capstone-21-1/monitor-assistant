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
public class ScrapeRuliweb implements ScraperServiceInterface {

    private static String RULIWEB_CRAWL_DATA_URL = "https://bbs.ruliweb.com/search?q=하이&page=2#gsc.tab=0&gsc.q=하이&gsc.page=1";

    @Getter
    private List<PostEntity> postEntityList;

    //작가 없음
    @Override
    public List<PostEntity> scrape(String keyword) throws IOException {
        postEntityList = new ArrayList<>();
        Document doc ;

            doc = Jsoup.connect(RULIWEB_CRAWL_DATA_URL).get();
           Elements elements = doc.select("div.search_result_title");
            for (Element el : elements) {

//                PostEntity postEntity = PostEntity.builder()
//                        .site("ruliweb")
//                        .title(el.select("span.title").text())
//                        .url("http://www.ppomppu.co.kr/" + el.select("span.title > a").attr("href"))
//                        .build();
//                String str=el.select(".desc").text();
//                postEntity.setType(str.substring(1,str.indexOf("]")));
//                postEntity.setView(str.substring(str.indexOf(":")+1,str.indexOf(":")+3));
//                Document doc2= Jsoup.connect(postEntity.getUrl()).get();
//                postEntity.setContent(doc2.select(".pic_bg").html());
//                postEntity.setAuthor(doc2.select(".view_name").text());
//                String str2=doc2.select(".sub-top-text-box").text();
//                postEntity.setCreated_at(str2.substring(str2.indexOf("등록일:")+5,str2.indexOf("조회수:")-1));
//

//                postEntityList.add(postEntity);
           }
        return postEntityList;
    }
}