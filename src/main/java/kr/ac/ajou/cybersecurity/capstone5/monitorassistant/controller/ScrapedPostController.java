package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.PostAdapter;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.PostResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ScrapeHumor;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ScrapeNate;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ScraperService;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ScraperServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test/monitor")
public class ScrapedPostController {

    @Autowired
    private ScrapeNate scrapeNate;
    @Autowired
    private ScrapeHumor scrapeHumor;
    @Autowired
    private ScraperService scrapeNaver;

    @GetMapping("/naver")
    public PostResponse scrapeNaver(@RequestParam String keyword) {
        List<String> errors = new ArrayList<>();
        List<PostEntity> postEntities = null;
        try {
            postEntities = scrapeNaver.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/nate")
    public PostResponse scrapeNate(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeNate.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/humor")
    public PostResponse scrapeHumor(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeHumor.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
}
