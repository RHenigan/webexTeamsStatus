package com.hackerlads.webexTeamsStatus.controllers;

import com.hackerlads.webexTeamsStatus.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("webhooks")
public class WebhookController {

    private MessageService messageService;

    public WebhookController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("/messageCreated")
    public void test(@RequestBody Object msgCreatedJSONBody) {

        System.out.println("HOOK MESSAGE: " + msgCreatedJSONBody);

        LinkedHashMap webhookMessage = (LinkedHashMap) msgCreatedJSONBody;
        LinkedHashMap msgData  = (LinkedHashMap) webhookMessage.get("data");
        String msgId = (String) msgData.get("id");

        System.out.println("MESSAGE ID: " + msgId);

        messageService.messageHandler(msgId);
    }
}
