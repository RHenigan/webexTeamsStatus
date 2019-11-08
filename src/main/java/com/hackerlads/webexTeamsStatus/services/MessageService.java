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
        if (null == message) {
            return;
        }
        System.out.println("Message Details: " + message);
        message = message.replace("<div>", "").replace("</div>", "");
        System.out.println("Message divs removed: " + message);
        if (message.charAt(0) == ':') {
            String command = parseOutCommand(message);
            System.out.println("Command: " + command);
            if (command.equalsIgnoreCase(":help")) {
                webexClient.createMessage("You have reached the help menu!");
            } else if (command.equalsIgnoreCase(":startmeeting")) {
                webexClient.createMessage(startStatusMeeting());
            } else if (command.equalsIgnoreCase(":teamstart")) {
                webexClient.createMessage(startTeam());
            } else if (command.equalsIgnoreCase(":teamfinish")) {
                webexClient.createMessage(finishTeam());
            } else if (command.equalsIgnoreCase(":agenda")) {
                webexClient.createMessage(getMeetingAgenda());
            } else {
                System.out.println("Not a Valid Command");
            }
        } else {
            System.out.println("Not a Command Message");
        }
    }

    private String startStatusMeeting() {
        //TODO call meetingService to start meeting
        return "Your Meeting has been started";
    }

    private String startTeam() {
        //TODO call meetingService to start team presenting
        return "A team has started presenting";
    }

    private String finishTeam() {
        //TODO call meetingService to finish presenting
        return "A team has finished presenting";
    }

    private String getMeetingAgenda() {
        //TODO call meetingService get meeting agenda
        return "Your Agenda will be displayed here";
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
