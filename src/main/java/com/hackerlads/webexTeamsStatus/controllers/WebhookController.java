package com.hackerlads.webexTeamsStatus.controllers;

import com.hackerlads.webexTeamsStatus.clients.WebexClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("webhooks")
public class WebhookController {

    @Autowired
    WebexClient webexClient;

    @PostMapping("/messageCreated")
    public void test(@RequestBody Object msgCreatedJSONBody) {
        System.out.println("HOOK MESSAGE: " + msgCreatedJSONBody);
        LinkedHashMap mappedMsg = (LinkedHashMap) msgCreatedJSONBody;
        LinkedHashMap mappedMsg1  = (LinkedHashMap) mappedMsg.get("data");
        String msgId = (String) mappedMsg1.get("id");
        System.out.println("MESSAGE ID: " + msgId);

        System.out.println(webexClient.getMessageDetails(msgId));
        webexClient.createMessage("Hello I am a bot to help you with status meetings");
    }
}
