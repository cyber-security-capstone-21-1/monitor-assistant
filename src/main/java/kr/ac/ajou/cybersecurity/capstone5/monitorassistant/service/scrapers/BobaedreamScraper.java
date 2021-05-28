package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ChangeDate;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BobaedreamScraper implements Scraper {

    private static String BOBAEDREAM_CRAWL_DATA_URL = "https://www.bobaedream.co.kr/search";
    Map<String, String> data = new HashMap<>();

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException {
        List<PostEntity> list = new ArrayList<>();
        Document[] doc = new Document[3];
                 data.put("colle", "community");
                data.put("searchField", "ALL");
                data.put("sort", "DATE");
                data.put("startDate", "");
                data.put("keyword", keyword);
        for(int i = 0; i < 3; i++) {
            data.put("page", Integer.toString(i + 1));
            Connection.Response response =
                    Jsoup.connect(BOBAEDREAM_CRAWL_DATA_URL)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .referrer("https://www.bobaedream.co.kr/search")
                            .header("Connection","keep-alive")
                            .header("Content-Type","application/x-www-form-urlencoded")
                            .header("Origin","https://www.bobaedream.co.kr")
                            .header("Host","www.bobaedream.co.kr")
                            .ignoreHttpErrors(true)
                            .method(Connection.Method.POST)
                            .followRedirects(true)
                            .data(data)
                            .execute();
            //System.out.println("보배드림 : " +response.statusCode()+ response.statusMessage());
            doc[i] = response.parse();
            Elements elements = doc[i].select(".search_Community ul li");
            for (Element el : elements) {
                PostEntity postEntity = PostEntity.builder()
                        .site("보배드림")
                        .title(el.select("dl dt").text())
                        .url("https://www.bobaedream.co.kr" + el.select("dl > dt > a").attr("href"))
                        .type(el.select("span.first").text())
                        .build();

                Document doc2 = Jsoup.connect(postEntity.getUrl())
                        .ignoreHttpErrors(true)
                        .get();
                String str = doc2.select("span.countGroup").text();
                String time=str.substring(str.lastIndexOf("|") + 2);
                StringBuffer str2= new StringBuffer(time);
                str2.delete(11,14);
                ChangeDate date=new ChangeDate(str2.toString(),6);
                postEntity.setCreated_at(date.getLocalDateTime());
                postEntity.setAuthor(doc2.select("a.nickname").text());
                postEntity.setView(str.substring(3, str.indexOf("|") - 1));
                postEntity.setContent(doc2.select("div.content02").html());


                list.add(postEntity);
            }
        }
        return list;
    }
}