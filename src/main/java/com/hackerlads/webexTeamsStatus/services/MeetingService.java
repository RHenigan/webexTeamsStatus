package com.hackerlads.webexTeamsStatus.services;

import com.hackerlads.webexTeamsStatus.model.DynamicSchedule;
import com.hackerlads.webexTeamsStatus.model.ScheduleNode;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class MeetingService {

    private DynamicSchedule dynamicSchedule = new DynamicSchedule();

    public LinkedList getMeetingAgenda() {
        return dynamicSchedule.generateExpectedSchedule();
    }

    public LinkedList<ScheduleNode> startMeeting() {
        return new LinkedList<>();
    }

}
