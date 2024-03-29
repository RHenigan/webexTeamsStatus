package com.hackerlads.webexTeamsStatus.services;

import com.hackerlads.webexTeamsStatus.controllers.StatusBotController;
import com.hackerlads.webexTeamsStatus.model.DynamicSchedule;
import com.hackerlads.webexTeamsStatus.model.ScheduleNode;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;

@Service
public class MeetingService {


    public String getMeetingAgenda() {
        String agenda = "";
        for (ScheduleNode scheduleNode : StatusBotController.dynamicSchedule.generateExpectedSchedule()) {
            agenda = agenda + scheduleNode.getName() + " " +
                    DateFormat.getTimeInstance().format(scheduleNode.getExpectedStartTime() ) + "-" +
                    DateFormat.getTimeInstance().format(scheduleNode.getExpectedEndTime() ) + "\n";
        }
        return agenda;
    }

    public void startMeeting() {
        StatusBotController.dynamicSchedule.setScheduledStartTime(new Date());
    }

    public void addTeam(String teamName) {
        StatusBotController.dynamicSchedule.add(teamName, 900);
    }

    public String teamStart(String teamName) {
        ScheduleNode node = StatusBotController.dynamicSchedule.markAsStarted(teamName);
        if (null != node) {
            return node.getName() + " has started presenting";
        } else {
            return "Failed to find team";
        }
    }

    public String teamFinish(String teamName) {
        ScheduleNode node = StatusBotController.dynamicSchedule.markAsFinished(teamName);
        if (null != node) {
            return node.getName() + " has finished presenting";
        } else {
            return "Failed to find team";
        }
    }

}
