package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.PostAdapter;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.PostResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/test/monitor")
public class ScrapedPostController {

    @Autowired
    private ScraperService scraperService;


    @GetMapping("/")
    @ResponseBody
    public PostResponse scrape(@RequestParam String keyword) {
        List<String> errors = new ArrayList<>();
        List<PostEntity> postEntities = null;
        try {
            postEntities = scraperService.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }

        return PostAdapter.postResponse(postEntities, errors);
    }
}
