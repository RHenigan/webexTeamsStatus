package com.hackerlads.webexTeamsStatus.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Getter
@NoArgsConstructor
public class ScheduleNode {
    private String name;
    private long scheduledDurationInSeconds;
    private Date actualStartTime;
    private Date actualEndTime;
    private Date expectedStartTime;
    private Date expectedEndTime;
    public boolean hasSentUpcomingAlert;

    public ScheduleNode deepCopy() {
        ScheduleNode output = new ScheduleNode();
        output.setName( this.getName() );
        output.setScheduledDurationInSeconds( this.getScheduledDurationInSeconds() );
        output.setActualStartTime( this.getActualStartTime() );
        output.setActualEndTime( this.getActualEndTime() );
        output.setExpectedStartTime( this.getExpectedStartTime() );
        output.setExpectedEndTime( this.getExpectedEndTime() );
        output.hasSentUpcomingAlert = this.hasSentUpcomingAlert;
        return output;
    }
}
