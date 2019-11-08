package com.hackerlads.webexTeamsStatus.services;

import com.hackerlads.webexTeamsStatus.clients.WebexClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    @Autowired
    WebexClient webexClient;

    public void messageHandler(String msgId) {
        String message = (String) webexClient.getMessageDetails(msgId);
        message = message.replace("<div>", "").replace("</div>", "");
        if (message.charAt(0) == ':') {
            String command = message.substring(0, message.indexOf(' '));
            if (command.equalsIgnoreCase("help")) {
                webexClient.createMessage("You have reached the help menu!");
            }
        }
    }
}
