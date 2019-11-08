package com.hackerlads.webexTeamsStatus.services;

import com.hackerlads.webexTeamsStatus.controllers.StatusBotController;
import com.hackerlads.webexTeamsStatus.model.DynamicSchedule;
import com.hackerlads.webexTeamsStatus.model.ScheduleNode;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class MeetingService {


    public String getMeetingAgenda() {
        String agenda = "";
        for (ScheduleNode scheduleNode : StatusBotController.dynamicSchedule.generateExpectedSchedule()) {
            agenda = agenda + scheduleNode.getName() + " " + scheduleNode.getExpectedStartTime() + "-" + scheduleNode.getExpectedEndTime() + "\n";
        }
        return agenda;
    }

    public LinkedList<ScheduleNode> startMeeting() {
        return new LinkedList<>();
    }

}
