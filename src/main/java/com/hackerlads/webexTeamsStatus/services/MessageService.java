package com.hackerlads.webexTeamsStatus.services;

import com.hackerlads.webexTeamsStatus.clients.WebexClient;
import com.hackerlads.webexTeamsStatus.controllers.StatusBotController;
import com.hackerlads.webexTeamsStatus.model.DynamicSchedule;
import com.hackerlads.webexTeamsStatus.model.ScheduleNode;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class MessageService {

    private WebexClient webexClient;
    private MeetingService meetingService;

    public MessageService(WebexClient webexClient, MeetingService meetingService) {
        this.webexClient = webexClient;
        this.meetingService = meetingService;
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
                sendHelpMessage();
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

    private void sendHelpMessage() {
        webexClient.createMessage(":startmeeting will start you status meeting\n:addteam <teamname> will add a team to the meeting\n:teamstart <teamname> will start a teams presentation time window\n:teamfinish <teamfinish> will end a teams presentation time window\n:agenda will present the current meeting agenda");
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
        webexClient.createMessage(meetingService.getMeetingAgenda().toString());
    }

    public void notifyTeamsOfUpcommingPresentation() {
        LinkedList<ScheduleNode> upcomingSchedule = StatusBotController.dynamicSchedule.generateExpectedSchedule();
        for( ScheduleNode element: upcomingSchedule ) {
            webexClient.createMessage( element.getName() + " is on soon (" + element.getExpectedStartTime().toString() + "-" + element.getExpectedEndTime() );
            StatusBotController.dynamicSchedule.markAsNotified( element.getName() );
        }
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
