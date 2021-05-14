package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.BasicResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.CommonResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.ErrorResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.*;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitor")
public class ScraperController {

    @RequestMapping(value = "/{site}", method = RequestMethod.GET)
    public ResponseEntity<? extends BasicResponse> getPostsByKeyword(
            @PathVariable(name = "site") String siteId,
            @RequestParam(value = "keyword") String keyword) {

        List<PostEntity> posts = null;
        String statusText;

        ScraperService scraper = new ScraperService();
        if (siteId.equals("CS01")) {
            scraper.setScraperType(new NaverScraper());
        } else if (siteId.equals("CS02")) {
            scraper.setScraperType(new IlbeScraper());
        } else if (siteId.equals("CS03")) {
            scraper.setScraperType(new BobaedreamScraper());
        } else if (siteId.equals("CS04")) {
            scraper.setScraperType(new ClienScraper());
        } else if (siteId.equals("CS05")) {
            scraper.setScraperType(new DCInsideScraper());
        } else if (siteId.equals("CS06")) {
            scraper.setScraperType(new DogdripScraper());
        } else if (siteId.equals("CS07")) {
            scraper.setScraperType(new TodayhumorScraper());
        } else if (siteId.equals("CS08")) {
            scraper.setScraperType(new PpomppuScraper());
        } else if (siteId.equals("CS09")) {
            scraper.setScraperType(new YgosuScraper());
        } else if (siteId.equals("CS10")) {
            scraper.setScraperType(new NatePannScraper());
        } else if (siteId.equals("CS11")) {
            scraper.setScraperType(new RuliwebScraper());
        } else if (siteId.equals("CS12")) {
            scraper.setScraperType(new FMKoreaScraper());
        } else if (siteId.equals("CS13")) {
            scraper.setScraperType(new MLBParkScraper());
        } else if (siteId.equals("CS14")) {
            scraper.setScraperType(new InstizScraper());
        } else if (siteId.equals("CS15")) {
            scraper.setScraperType(new HygScraper());
        } else if (siteId.equals("CS16")) {
            scraper.setScraperType(new InvenScraper());
        } else if (siteId.equals("CS17")) {
            scraper.setScraperType(new Cook82Scraper());
        }
        else {
            return ResponseEntity.notFound().build();
        }

        try {
            posts = scraper.getPosts(keyword);
            statusText = "ok";
        } catch (javax.net.ssl.SSLHandshakeException e) {
            e.printStackTrace();
            statusText = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponse(statusText, 417));
        } catch (java.net.SocketTimeoutException e) {
            e.printStackTrace();
            statusText = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponse(statusText, 417));
        } catch (Exception e) {
            e.printStackTrace();
            statusText = e.getMessage();
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ErrorResponse(statusText, 417));
        }

        return ResponseEntity.ok()
                .body(new CommonResponse<List<PostEntity>>(posts, statusText));
    }
}
