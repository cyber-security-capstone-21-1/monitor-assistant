package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.SiteMetaEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.BasicResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.CommonResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.ErrorResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.*;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.scrapers.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/monitor")
public class ScraperController {

    @GetMapping("/")
    public ResponseEntity<? extends BasicResponse> getSiteList() {
        List<SiteMetaEntity> sites = new ArrayList<>();
        sites.add(SiteMetaEntity.builder().code("CS02").name("일간베스트").build());
        sites.add(SiteMetaEntity.builder().code("CS03").name("보배드림").build());
        sites.add(SiteMetaEntity.builder().code("CS04").name("클리앙").build());
        sites.add(SiteMetaEntity.builder().code("CS05").name("디씨인사이드").build());
        sites.add(SiteMetaEntity.builder().code("CS06").name("개드립").build());
        sites.add(SiteMetaEntity.builder().code("CS07").name("오늘의유머").build());
        sites.add(SiteMetaEntity.builder().code("CS08").name("뽐뿌").build());
        sites.add(SiteMetaEntity.builder().code("CS09").name("와이고수").build());
        sites.add(SiteMetaEntity.builder().code("CS10").name("네이트판").build());
        sites.add(SiteMetaEntity.builder().code("CS11").name("루리웹").build());
        sites.add(SiteMetaEntity.builder().code("CS13").name("MLB파크").build());
        sites.add(SiteMetaEntity.builder().code("CS14").name("인스티즈").build());
        sites.add(SiteMetaEntity.builder().code("CS15").name("해연갤").build());
        sites.add(SiteMetaEntity.builder().code("CS16").name("인벤").build());
        sites.add(SiteMetaEntity.builder().code("CS17").name("82cook").build());

        return ResponseEntity.ok().body(new CommonResponse<List<SiteMetaEntity>>(sites, "ok"));
    }

    @RequestMapping(value = "/{site}", method = RequestMethod.GET)
    public ResponseEntity<? extends BasicResponse> getPostsByKeyword(
            @PathVariable(name = "site") String siteId,
            @RequestParam(value = "keyword") String keyword) {

        List<PostEntity> posts = null;
        String statusText;

        ScraperService scraper = new ScraperService();
         if (siteId.equals("CS02")) {
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
        }  else if (siteId.equals("CS13")) {
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
