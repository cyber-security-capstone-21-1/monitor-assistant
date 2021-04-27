package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperService implements ScraperServiceInterface {


        private static String TEST_CRAWL_DATA_URL = "https://search.naver.com/search.naver?where=nexearch&sm=top_hty&fbm=0&ie=utf8&query=";

        @Getter
        private List<PostEntity> postEntityList;

        @Override
        public List<PostEntity> scrape(String keyword) throws IOException{
            postEntityList=new ArrayList<>();
             Document doc;
            doc = Jsoup.connect(TEST_CRAWL_DATA_URL + keyword).get();
            Elements elements = doc.select(".bx._svp_item");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder().author(el.select(".elss.etc_dsc_inner").text())
                        .site("naver")
                        .title(el.select(".api_txt_lines.total_tit").text())
                        .created_at(el.select(".sub_time.sub_txt").text())
                        .url(el.select(".api_txt_lines.total_tit").attr("href"))
                        .content(el.select(".total_wrap.api_ani_send").html())
                        .type("blog")
                        .build();
                postEntityList.add(postEntity);
            }
            return postEntityList;
        }
}