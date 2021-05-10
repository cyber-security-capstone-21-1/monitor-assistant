package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class APIRedirectController {

    @GetMapping("/")
    public String RedirectAPIPage() {
        return "redirect:/api";
    }
    
}
