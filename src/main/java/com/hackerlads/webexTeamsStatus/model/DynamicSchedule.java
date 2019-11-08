package com.hackerlads.webexTeamsStatus.model;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedList;

public class DynamicSchedule {
    public static final long MILLISECONDS_IN_A_SECOND = 1000;

    private LinkedList<ScheduleNode> model;

    @Getter
    @Setter
    private Date scheduledStartTime;

    public DynamicSchedule() {
        this.model = new LinkedList<>();
    }

    public ScheduleNode add( String name, long durationInSeconds ) {
        ScheduleNode node = new ScheduleNode();
        node.setName( name );
        node.setScheduledDurationInSeconds( durationInSeconds );
        for( ScheduleNode element: this.model ) {
            if( element.getName().equals( node.getName() ) ) {
                return element;
            }
        }
        this.model.addLast( node );
        return this.get( name );
    }

    public ScheduleNode get( String nodeName ) {
        LinkedList<ScheduleNode> expectedSchedule = generateExpectedSchedule();
        for ( ScheduleNode element: expectedSchedule ) {
            if( element.getName().equals( nodeName ) ) {
                return element;
            }
        }
        return null;
    }

    public LinkedList<ScheduleNode> getUpcoming( long upcomingTimeIntervalInSeconds ) {
        LinkedList<ScheduleNode> predictedSchedule = generateExpectedSchedule();
        LinkedList<ScheduleNode> output = new LinkedList<>();
        Date now = new Date();
        Date upcomingDate = new Date( now.getTime() + upcomingTimeIntervalInSeconds * MILLISECONDS_IN_A_SECOND);
        for( ScheduleNode element: predictedSchedule) {
            if( element.getExpectedStartTime().before( upcomingDate ) && !element.hasSentUpcomingAlert ) {
                output.addLast( element );
            }
        }
        return output;
    }

    public ScheduleNode cancelScheduleNode( String nodeName ) {
        ScheduleNode target = null;
        for( ScheduleNode element: this.model ) {
            if( element.getName().equals( nodeName ) ) {
                target = element;
            }
        }

        if( target != null ) {
            this.model.remove( target );
        }

        return target;
    }

    public ScheduleNode markAsStarted( String nodeName ) {
        for ( ScheduleNode element: this.model ) {
            if( element.getName().equals( nodeName )) {
                if( element.getActualStartTime() == null ) {
                    element.setActualStartTime( new Date() );
                }
                return element;
            }
        }
        return null;
    }

    public ScheduleNode markAsFinished( String nodeName ) {
        for ( ScheduleNode element: this.model ) {
            if( element.getName().equals( nodeName )) {
                if( element.getActualEndTime() == null ) {
                    element.setActualEndTime( new Date() );
                }
                if( element.getActualStartTime() == null ) {
                    element.setActualStartTime( element.getActualEndTime() );
                }
                return element;
            }
        }
        return null;
    }

    public ScheduleNode markAsNotified( String nodeName ) {
        for ( ScheduleNode element: this.model ) {
            if( element.getName().equals( nodeName )) {
                element.setHasSentUpcomingAlert( true );
                return element;
            }
        }
        return null;
    }

    public LinkedList<ScheduleNode> generateExpectedSchedule() {
        long offsetInSeconds = 0;
        LinkedList<ScheduleNode> output = new LinkedList<ScheduleNode>();

        for( ScheduleNode element: this.model ) {
            ScheduleNode expectedMeetingSchedule = element.deepCopy();
            offsetInSeconds += meetingTimeLeft( expectedMeetingSchedule );
            expectedMeetingSchedule = setExpectedTimes( expectedMeetingSchedule, offsetInSeconds );
            output.addLast( expectedMeetingSchedule );
        }

        return output;
    }

    public void resetMeeting() {
        this.model.clear();
        this.scheduledStartTime = null;
    }

    private static ScheduleNode setExpectedTimes( ScheduleNode element, long offsetInSeconds ) {
        Date now = new Date();
        ScheduleNode output = element.deepCopy();

        if ( output.getActualStartTime() == null ) {
            output.setExpectedStartTime( new Date( now.getTime() + offsetInSeconds * MILLISECONDS_IN_A_SECOND )  );
            output.setExpectedEndTime( new Date( output.getExpectedStartTime().getTime() + output.getScheduledDurationInSeconds() * MILLISECONDS_IN_A_SECOND ));
        }
        else if ( output.getActualStartTime() != null && output.getActualEndTime() == null ) {
            long elapsedTimeInMeetingInSeconds = ( now.getTime() - element.getActualStartTime().getTime() ) / MILLISECONDS_IN_A_SECOND;
            long timeLeftInMeetingInSeconds = Math.max( 0, element.getScheduledDurationInSeconds() - elapsedTimeInMeetingInSeconds);
            output.setExpectedStartTime( output.getActualStartTime() );
            output.setExpectedEndTime( new Date( now.getTime() + timeLeftInMeetingInSeconds * MILLISECONDS_IN_A_SECOND ) );
        }
        else {
            output.setExpectedStartTime( output.getActualStartTime() );
            output.setExpectedEndTime( output.getActualEndTime() );
        }

        return output;
    }

    private static long meetingTimeLeft( ScheduleNode node ) {
        if( node.getActualStartTime() == null ) {
            return node.getScheduledDurationInSeconds();
        }
        else if( node.getActualStartTime() != null && node.getActualEndTime() == null ) {
            Date now = new Date();
            long elapsedTimeInMeetingInSeconds = ( now.getTime() - node.getActualStartTime().getTime() ) / MILLISECONDS_IN_A_SECOND;
            return Math.max( 0, node.getScheduledDurationInSeconds() - elapsedTimeInMeetingInSeconds);
        }
        else {
            return 0;
        }

    }
}