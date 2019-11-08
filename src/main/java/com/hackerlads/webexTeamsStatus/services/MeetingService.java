package com.hackerlads.webexTeamsStatus.services;

import com.hackerlads.webexTeamsStatus.model.DynamicSchedule;
import org.springframework.stereotype.Service;

import java.util.LinkedList;

@Service
public class MeetingService {

    private DynamicSchedule dynamicSchedule;

    public MeetingService() {
        DynamicSchedule dynamicSchedule = new DynamicSchedule();
    }

    public LinkedList getMeetingAgenda() {
        return dynamicSchedule.generateExpectedSchedule();
    }

}
