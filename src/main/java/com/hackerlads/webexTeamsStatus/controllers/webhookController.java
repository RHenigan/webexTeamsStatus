package com.hackerlads.webexTeamsStatus.controllers;

import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("webhooks")
public class webhookController {

    @PostMapping("/messageCreated")
    public void test(@RequestBody Object msgCreatedJSONBody) {
        System.out.println("HOOK MESSAGE: " + msgCreatedJSONBody);
        LinkedHashMap mappedMsg = (LinkedHashMap) msgCreatedJSONBody;
        LinkedHashMap mappedMsg1  = (LinkedHashMap) mappedMsg.get("data");
        String msgId = (String) mappedMsg1.get("id");
        System.out.println("MESSAGE ID: " + msgId);
    }
}
