package kr.ac.ajou.cybersecurity.capstone5.monitorassistant.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class FEErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "/404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
