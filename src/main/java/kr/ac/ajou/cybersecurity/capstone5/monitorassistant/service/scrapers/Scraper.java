package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.ChangeDate;

public interface Scraper {
    List<PostEntity> getPosts(String keyword) throws IOException, ParseException;
}
