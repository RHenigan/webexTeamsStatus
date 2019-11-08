package com.hackerlads.webexTeamsStatus.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("webhooks")
public class webhookController {

    @GetMapping()
    public String test() {
        return "Hello World";
    }
}
