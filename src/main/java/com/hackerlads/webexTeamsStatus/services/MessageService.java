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
        System.out.println("Message Details: " + message);
        message = message.replace("<div>", "").replace("</div>", "");
        System.out.println("Message divs removed: " + message);
        if (message.charAt(0) == ':') {
            String command = parseOutCommand(message);
            System.out.println("Command: " + command);
            if (command.equalsIgnoreCase("help")) {
                webexClient.createMessage("You have reached the help menu!");
            } else {
                System.out.println("Not a Valid Command");
            }
        } else {
            System.out.println("Not a Command Message");
        }
    }

    private String parseOutCommand (String message) {
        String command = "";
        if (message.contains(" ")) {
            command = message.substring(0, message.indexOf(" "));
        } else {
            command = message;
        }
        return command;
    }
}
