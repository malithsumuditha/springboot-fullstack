package com.malith.mysystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    private static int COUNTER =0;
    record Malith(String result){}

    @GetMapping("/malith")
    public Malith getString(){
        return new Malith("Hello Malith Sumu %s".formatted(++COUNTER));
    }
}
