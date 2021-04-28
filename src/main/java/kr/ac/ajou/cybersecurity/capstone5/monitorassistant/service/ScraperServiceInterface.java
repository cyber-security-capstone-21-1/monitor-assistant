package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;

import java.io.IOException;
import java.util.List;

public interface ScraperServiceInterface {
    List<PostEntity> scrape(String keyword) throws IOException;
}
