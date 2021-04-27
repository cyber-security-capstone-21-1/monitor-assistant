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
//오유는 url 형식 때문에 일단 1page 까지만+ 검색 목록에 content 없어서 따로 안가져옴
// 추후 설계 방법에 따라 content 추가 예정
public class ScrapeHumor implements ScraperServiceInterface {

    private static String TodayHumor_CRAWL_DATA_URL =
            "http://www.todayhumor.co.kr/board/list.php?kind=search&keyfield=subject&keyword=";

        @Getter
        private List<PostEntity> postEntityList;
@Override
    public List<PostEntity> scrape(String keyword) throws IOException{
        postEntityList=new ArrayList<>();
         Document doc;
        // for(int i=0;i<3;i++) {
             doc= Jsoup.connect(TodayHumor_CRAWL_DATA_URL + keyword).get();
             Elements elements = doc.select(".table_list tbody tr");
             for (Element el : elements) {
                 if(!el.select(".name").text().equals("")) {
                     PostEntity postEntity = PostEntity.builder()
                             .author(el.select(".name").text())
                             .site("todayhumor")
                             .title(el.select(".subject a").text())
                             .created_at(el.select(".date").text())
                             .url("http://www.todayhumor.co.kr" + el.select(".subject a").attr("href"))
                             .content(el.select(".txt").text())
                             .view(el.select(".hits").text())
                             .type(el.attr("class").substring(13))
                             .build();
                     postEntityList.add(postEntity);
                 }
             }
         //}
        return postEntityList;
   }
}