package com.malith.mysystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    record Malith(String result){}

    @GetMapping("/malith")
    public Malith getString(){
        return new Malith("Test Success again...55591 huu");
    }
}
