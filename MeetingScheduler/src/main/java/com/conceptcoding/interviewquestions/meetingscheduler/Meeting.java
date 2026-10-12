package com.conceptcoding.interviewquestions.meetingscheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Meeting {
    private final String meetingId;
    private String subject;
    private final TimeInterval timeInterval;
    private final User organizer;
    private final MeetingRoom meetingRoom;
    private final Map<User, ParticipantResponse> attendees;
    private String status;

    public Meeting(String meetingId,
                   String subject,
                   TimeInterval timeInterval,
                   User organizer,
                   Map<User, ParticipantResponse> attendees,
                   MeetingRoom meetingRoom) {
        this(meetingId, subject, timeInterval, organizer, attendees, meetingRoom, "SCHEDULED");
    }

    public Meeting(String meetingId,
                   String subject,
                   TimeInterval timeInterval,
                   User organizer,
                   Map<User, ParticipantResponse> attendees,
                   MeetingRoom meetingRoom,
                   String status) {
        this.meetingId = meetingId;
        this.subject = subject;
        this.timeInterval = timeInterval;
        this.organizer = organizer;
        this.attendees = attendees;
        this.meetingRoom = meetingRoom;
        this.status = status;
    }

    public String getMeetingId() {
        return meetingId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public TimeInterval getTimeInterval() {
        return timeInterval;
    }

    public User getOrganizer() {
        return organizer;
    }

    public MeetingRoom getMeetingRoom() {
        return meetingRoom;
    }

    public Map<User, ParticipantResponse> getAttendees() {
        return attendees;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<User> getAcceptedParticipants() {
        List<User> result = new ArrayList<>();
        for (Map.Entry<User, ParticipantResponse> entry : attendees.entrySet()) {
            if (entry.getValue() == ParticipantResponse.ACCEPTED) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public List<User> getDeclinedParticipants() {
        List<User> result = new ArrayList<>();
        for (Map.Entry<User, ParticipantResponse> entry : attendees.entrySet()) {
            if (entry.getValue() == ParticipantResponse.DECLINED) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public List<User> getTentativeParticipants() {
        List<User> result = new ArrayList<>();
        for (Map.Entry<User, ParticipantResponse> entry : attendees.entrySet()) {
            if (entry.getValue() == ParticipantResponse.TENTATIVE) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Meeting{meetingId='" + meetingId + "', subject='" + subject + "', room='" +
                meetingRoom.getName() + "', status='" + status + "'}";
    }
}
