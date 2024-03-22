package com.carsharing.app.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/test")
    public String testAPI() {
        return "API is up and running!!";
    }
}