package com.hackerlads.webexTeamsStatus.services;

import com.hackerlads.webexTeamsStatus.controllers.StatusBotController;
import com.hackerlads.webexTeamsStatus.model.DynamicSchedule;
import com.hackerlads.webexTeamsStatus.model.ScheduleNode;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.LinkedList;

@Service
public class MeetingService {

    public LinkedList getMeetingAgenda() {
        return StatusBotController.dynamicSchedule.generateExpectedSchedule();
    }

    public void startMeeting() {
        StatusBotController.dynamicSchedule.setScheduledStartTime(new Date());
    }

//    public void addTeam(String teamName) {
//        ScheduleNode team = new ScheduleNode();
//        Date date = new Date();
//
//        team.setActualStartTime(StatusBotController.dynamicSchedule.getScheduledStartTime());
//        team.setExpectedEndTime(date);
////        getMeetingAgenda().add();
//    }
}
