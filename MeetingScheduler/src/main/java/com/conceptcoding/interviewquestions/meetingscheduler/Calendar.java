package com.conceptcoding.interviewquestions.meetingscheduler;

import java.util.List;

// Kept from the original LLD, but no longer stores meetings in memory.
// In the backend version, calendar data should be queried from MySQL when needed.
public class Calendar {

    public void viewMeetings(List<Meeting> meetings) {
        for (Meeting meeting : meetings) {
            System.out.println(meeting);
        }
    }
}
