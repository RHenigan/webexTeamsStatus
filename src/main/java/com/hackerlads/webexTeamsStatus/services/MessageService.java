package com.hackerlads.webexTeamsStatus.services;

import com.hackerlads.webexTeamsStatus.clients.WebexClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageService {

    private WebexClient webexClient;

    public MessageService(WebexClient webexClient) {
        this.webexClient = webexClient;
    }

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
                startStatusMeeting();
            } else if (command.equalsIgnoreCase(":addteam")) {
                addTeam();
            } else if (command.equalsIgnoreCase(":teamstart")) {
                startTeam();
            } else if (command.equalsIgnoreCase(":teamfinish")) {
                finishTeam();
            } else if (command.equalsIgnoreCase(":agenda")) {
                getMeetingAgenda();
            } else {
                System.out.println("Not a Valid Command");
            }
        } else {
            System.out.println("Not a Command Message");
        }
    }

    private void startStatusMeeting() {
        //TODO call meetingService to start meeting
        webexClient.createMessage("Your Meeting has been started");
    }

    private void addTeam() {
        //TODO call meetingService to start meeting
        webexClient.createMessage("Team has been added");
    }

    private void startTeam() {
        //TODO call meetingService to start team presenting
        webexClient.createMessage("A team has started presenting");
    }

    private void finishTeam() {
        //TODO call meetingService to finish presenting
        webexClient.createMessage("A team has finished presenting");
    }

    private void getMeetingAgenda() {
        //TODO call meetingService get meeting agenda
        webexClient.createMessage("Your Agenda will be displayed here");
    }

    private String parseOutCommand(String message) {
        String command = "";
        if (message.contains(" ")) {
            command = message.substring(0, message.indexOf(" "));
        } else {
            command = message;
        }
        return command;
    }
}
