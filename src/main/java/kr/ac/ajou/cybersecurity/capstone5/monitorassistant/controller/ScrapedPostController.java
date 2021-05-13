package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.adapter.PostAdapter;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.entities.PostEntity;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.response.PostResponse;
import kr.ac.ajou.cybersecurity.capstone5.monitorassistant.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.SocketTimeoutException;
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
    // 일베는 조회수가 없어서 추천수로 뽑음
    @Autowired
    private ScrapeIlbe scrapeIlbe;
    @Autowired
    private ScrapeRuliweb scrapeRuliweb;
    // 개드립은  유저드립 게시판에서만 뽑음
    @Autowired
    private ScrapeDogdrip scrapeDogdrip;
    // 가끔씩 안될수도 있는데 추후 수정
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
        String status ="";
        try {
            postEntities = scrapeNaver.scrape(keyword);
        }catch (final NullPointerException e) {
            errors.add(e.getMessage());
            status="null";
        }
        catch (final SSLHandshakeException e) {
            errors.add(e.getMessage());
            status="SSL Error";
        }catch (final SocketTimeoutException e){
            errors.add(e.getMessage());
            status="timeout";
        } catch (final Exception e){
            errors.add(e.getMessage());
            status="?";
        }

        if(errors.isEmpty()) status="success";


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
    @RequestMapping(value = "/humor", method = {RequestMethod.GET,RequestMethod.POST})
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
    public PostResponse scrapeBobaedream(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        String status="";
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeBobaedream.scrape(keyword);
        } catch (final NullPointerException e) {
            errors.add(e.getMessage());
            status="null pointer";
        }
        catch (final SSLHandshakeException e) {
            errors.add(e.getMessage());
            status="SSL Error";
        }catch (final SocketTimeoutException e){
            errors.add(e.getMessage());
            status="timeout";
        } catch (final Exception e){
            errors.add(e.getMessage());
            status="?";
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
    @GetMapping("/ilbe")
    public PostResponse scrapeIlbe(@RequestParam String keyword) throws IOException {

        List<PostEntity> postEntities = null;
        List<String> errors = new ArrayList<>();
        try {
            postEntities = scrapeIlbe.scrape(keyword);
        } catch (final Exception e) {
            errors.add(e.getMessage());
        }
        return PostAdapter.postResponse(postEntities, errors);
    }
}
