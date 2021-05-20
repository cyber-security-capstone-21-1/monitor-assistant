package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ChangeDate;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ChangeImgSrc;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PpomppuScraper implements Scraper {

    private static String PPOMPPU_CRAWL_DATA_URL = "http://www.ppomppu.co.kr/search_bbs.php?page_size=50&bbs_cate=2&order_type=date&search_type=sub_memo&keyword=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document doc = Jsoup.connect(PPOMPPU_CRAWL_DATA_URL + keyword)
                .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                .referrer("www.google.com")
                .get();

        Elements elements = doc.select(".results_board div div.content");
        for (Element el : elements) {
            PostEntity postEntity = PostEntity.builder()
                    .site("뽐뿌")
                    .title(el.select("span.title").text())
                    .url("http://www.ppomppu.co.kr/" + el.select("span.title > a").attr("href"))
                    .build();
            String str = el.select(".desc").text();
            postEntity.setType(str.substring(1,str.indexOf("]")));
            postEntity.setView(str.substring(str.indexOf(":") + 1, str.indexOf(":") + 3));
            Document doc2 = Jsoup.connect(postEntity.getUrl()).get();
           // postEntity.setContent(doc2.select(".pic_bg").html());
            postEntity.setAuthor(doc2.select(".view_name").text());
            /* **** IMG SRC 변환  */
            String content=doc2.select(".pic_bg").html();
            ChangeImgSrc tmp = new ChangeImgSrc(content,2);
            postEntity.setContent(tmp.getHtml());
            /* **** IMG SRC 변환  */

            /* **** DATE 객체 변환  */
            String str2 = doc2.select(".sub-top-text-box").text();
            if(!str2.equals("")) {
                ChangeDate date = new ChangeDate(str2.substring(str2.indexOf("등록일:") + 5, str2.indexOf("조회수:") - 1), 2);
                postEntity.setCreated_at(date.getLocalDateTime());
            }
            /* **** DATE 객체 변환  */
            list.add(postEntity);
        }
        return list;
    }
}