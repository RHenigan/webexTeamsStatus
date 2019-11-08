package com.hackerlads.webexTeamsStatus.controllers;

import com.hackerlads.webexTeamsStatus.clients.WebexClient;
import com.hackerlads.webexTeamsStatus.exceptions.MeetingNotFoundException;
import com.hackerlads.webexTeamsStatus.model.DynamicSchedule;
import com.hackerlads.webexTeamsStatus.model.ScheduleNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;

@RequestMapping(value = "/statusbot")
@RestController
public class StatusBotController {

    public static DynamicSchedule dynamicSchedule;
    private WebexClient webexClient;

    public StatusBotController(WebexClient webexClient){
        this.dynamicSchedule = new DynamicSchedule();
        this.webexClient = webexClient;
    }

    @PostMapping(value = "/testHooks", produces = "application/json")
    public void messageCreated(@RequestBody Object msgCreatedWebHook) {
        System.out.println("HOOK MESSAGE: " + msgCreatedWebHook);
        LinkedHashMap mappedMsg = (LinkedHashMap) msgCreatedWebHook;
        LinkedHashMap mappedMsg1  = (LinkedHashMap) mappedMsg.get("data");
        String msgId = (String) mappedMsg1.get("id");

        System.out.println(webexClient.getMessageDetails(msgId));
    }

    @GetMapping(value = "/find", produces = "application/json")
    public ResponseEntity<ScheduleNode> getMeetingInfo(String meetingName) {
        ScheduleNode meetingInfo = dynamicSchedule.get(meetingName);
        if(null == meetingInfo) {
            throw new MeetingNotFoundException(meetingName + " meeting not found");
        }
        return new ResponseEntity(meetingInfo, HttpStatus.OK);
    }

    @PostMapping(value = "/addMeeting", produces = "application/json")
    public ResponseEntity addMeeting(String teamName, long meetingDurationInSeconds) {
        dynamicSchedule.add(teamName, meetingDurationInSeconds);
        return new ResponseEntity("Meeting Successfully Added", HttpStatus.OK);
    }

    @PostMapping(value = "/meetingStarted", produces = "application/json")
    public ResponseEntity markMeetingAsStarted(String meetingName) {
        ScheduleNode meetingInfo = dynamicSchedule.get(meetingName);
        if(null == meetingInfo) {
            throw new MeetingNotFoundException(meetingName + " meeting not found. Cannot start meeting.");
        }
        return new ResponseEntity(dynamicSchedule.markAsStarted(meetingName), HttpStatus.OK);
    }

    @PostMapping(value = "/meetingEnded", produces = "application/json")
    public ResponseEntity markMeetingAsEnded(String meetingName) {
        ScheduleNode meetingInfo = dynamicSchedule.get(meetingName);
        if(null == meetingInfo) {
            throw new MeetingNotFoundException(meetingName + " meeting not found. Cannot end meeting.");
        }
        return new ResponseEntity(dynamicSchedule.markAsFinished(meetingName), HttpStatus.OK);
    }

    @PostMapping(value = "/meetingCancelled", produces = "application/json")
    public ResponseEntity cancelMeeting(String meetingName) {
        ScheduleNode meetingInfo = dynamicSchedule.get(meetingName);
        if(null == meetingInfo) {
            throw new MeetingNotFoundException(meetingName + " meeting not found. Cannot cancel meeting.");
        }
        return new ResponseEntity(dynamicSchedule.cancelScheduleNode(meetingName), HttpStatus.OK);
    }
}
