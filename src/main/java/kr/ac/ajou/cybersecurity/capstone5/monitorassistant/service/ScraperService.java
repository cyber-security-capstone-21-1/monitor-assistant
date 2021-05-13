package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers.Scraper;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ScraperService {

    @Setter
    private Scraper scraperType;

    public ScraperService() {}

    public List<PostEntity> getPosts(String keyword) throws IOException {
        return scraperType.getPosts(keyword);
    }
}
