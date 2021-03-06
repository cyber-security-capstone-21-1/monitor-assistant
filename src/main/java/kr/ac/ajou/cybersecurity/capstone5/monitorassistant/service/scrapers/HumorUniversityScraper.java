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
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@Service
public class HumorUniversityScraper implements Scraper {

    private static String HUMORUNIVERSITY_CRAWL_DATA_URL = "http://web.humoruniv.com/search/search.html?search_text=";

    @Override
    public List<PostEntity> getPosts(String keyword) throws IOException, ParseException {
        List<PostEntity> list = new ArrayList<>();
        Document doc[] = new Document[2];
        String encodeKeyword = URLEncoder.encode(keyword, "EUC-KR");


        for (int i = 0; i < 2; i++) {
            Connection.Response response =
                    Jsoup.connect(HUMORUNIVERSITY_CRAWL_DATA_URL + encodeKeyword + "&search_type=&x=0&y=0&page=" + i + 1)
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .followRedirects(true)
                            .execute();

            doc[i] = response.parse();

            Elements elements = doc[i].select("div[style=margin:10px 0 0 0px;*margin: 0 0 0 0;] > table");
            for (Element el : elements) {
                if (!el.select("a[style=text-decoration: underline;]").text().isEmpty()) {
                    PostEntity postEntity = PostEntity.builder()
                            .site("μκΈ΄λν")
                            .title(el.select("a[style=text-decoration: underline;]").text())
                            .url(el.select("a[style=text-decoration: underline;]").attr("href"))
                            .build();
                    Document doc2 = Jsoup.connect(postEntity.getUrl())
                            .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36")
                            .followRedirects(true)
                            .get();
                    String time = doc2.select("div#if_date").text();
                    String author = doc2.select("span[style=cursor:pointer;cursor:hand] > span.hu_nick_txt").text();
                    String view = doc2.select("div#content_info").text();

                    if (!view.isEmpty()) {
                        postEntity.setView(view.substring(view.indexOf("μ‘°ν") + 3, view.indexOf("μμ±") - 1));
                        postEntity.setType(view.substring(view.indexOf("μΆμ²") + 3, view.indexOf("μΆμ²") - 1));
                    }

                    if (!time.isEmpty()) {
                        if (time.contains("μ΄λμκ°")) {
                            ChangeDate fun = new ChangeDate(time.substring(time.indexOf(" ") + 1, time.indexOf("μ΄λ") - 1), 1);
                            postEntity.setCreated_at(fun.getLocalDateTime());
                        } else {
                            ChangeDate fun = new ChangeDate(time.substring(time.indexOf(" ") + 1), 1);
                            postEntity.setCreated_at(fun.getLocalDateTime());
                        }
                    }

                    if (author.contains(" ")) {
                        postEntity.setAuthor(author.substring(0, author.indexOf(" ")));
                    } else {
                        postEntity.setAuthor(author);
                    }

                    list.add(postEntity);
                }
            }
        }
        return list;
    }
}
