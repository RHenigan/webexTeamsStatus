package com.hackerlads.webexTeamsStatus.services;

import com.hackerlads.webexTeamsStatus.model.DynamicSchedule;

import java.util.LinkedList;

public class MeetingService {

    private DynamicSchedule dynamicSchedule;

    public MeetingService() {
        DynamicSchedule dynamicSchedule = new DynamicSchedule();
    }

    public LinkedList getMeetingAgenda() {
        return dynamicSchedule.generateExpectedSchedule();
    }

}
