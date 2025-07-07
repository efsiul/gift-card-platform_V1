package com.pt.lfc.giftcardmicroservice.adapter.in.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PingController {
    @GetMapping("/ping")
    public String pong() {
        return "pong";
    }
}