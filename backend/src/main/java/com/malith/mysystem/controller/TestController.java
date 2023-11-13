package com.malith.mysystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    record Malith(String result){}

    @GetMapping("/malith")
    public Malith getString(){
        return new Malith("Hello CI CD malith... HIIIwaaa");
    }
}
