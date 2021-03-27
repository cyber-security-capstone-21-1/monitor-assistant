package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ScraperService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/monitor")
@AllArgsConstructor
public class ScrapedPostController {
    @GetMapping("/")
    public List<PostEntity> load(@RequestParam("keyword") String keyword) throws IOException {
        ScraperService s = new ScraperService(keyword);
        return s.getPostEntityList();
    }
}
