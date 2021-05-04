package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.PostAdapter;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.PostResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/monitor")
public class ScrapedPostController {

    /*
    * service 주입 하는거 interface이용해서 list or map 으로 주입하는 방법 찾아보기(SpringBoot)
    *  좀 더 깔끔하게,,
    * */
    @Autowired
    private ScrapeNate scrapeNate;
    @Autowired
    private ScrapeRuliweb scrapeRuliweb;
    @Autowired
    private ScrapeDogdrip scrapeDogdrip;
    @Autowired
    private ScrapeBobaedream scrapeBobaedream;
    @Autowired
    private ScrapeYgosu scrapeYgosu;
    @Autowired
    private ScrapeMlbpark scrapeMlbpark;
    @Autowired
    private ScrapeHumor scrapeHumor;
    @Autowired
    private ScraperServiceInterface scrapeClien;
    @Autowired
    private ScrapeFmkorea scrapeFmkorea;
    @Autowired
    private ScrapeDcinside scrapeDcinside;
    @Autowired
    private ScrapePpomppu scrapePpomppu;
    @Autowired
    private ScrapeNaver scrapeNaver;

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
    @GetMapping("/clien")
    public PostResponse scrapeClien(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeClien.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/fmkorea")
    public PostResponse scrapeFmkorea(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeFmkorea.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/ppomppu")
    public PostResponse scrapePpomppu(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapePpomppu.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/dcinside")
    public PostResponse scrapeDcinside(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeDcinside.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/mlbpark")
    public PostResponse scrapeMlbpark(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeMlbpark.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/ygosu")
    public PostResponse scrapeYgosu(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeYgosu.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/bobaedream")
    public PostResponse scrapeRBobaedream(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeBobaedream.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/dogdrip")
    public PostResponse scrapeDogdrip(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeDogdrip.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
    @GetMapping("/ruliweb")
    public PostResponse scrapeRuriweb(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeRuliweb.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
}
