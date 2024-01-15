package com.travelcompass.api.global.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller //-> login.html로 이동할 수 있음
@RequestMapping("/views")
public class ViewController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
