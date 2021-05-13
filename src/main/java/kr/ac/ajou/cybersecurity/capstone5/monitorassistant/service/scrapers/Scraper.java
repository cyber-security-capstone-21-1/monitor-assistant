package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;

import java.io.IOException;
import java.util.List;

public interface Scraper {
    List<PostEntity> getPosts(String keyword) throws IOException;
}
