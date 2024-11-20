package com.malmitas.backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WelcomController {

    @GetMapping("/")
    public String welcome() {
        return "Hello World";
    }

    @GetMapping("/user")
    public String user() {
        return "Authorize user";
    }

    @GetMapping("/admin")
    public String admin() {
        return "Authorize admin";
    }
}
