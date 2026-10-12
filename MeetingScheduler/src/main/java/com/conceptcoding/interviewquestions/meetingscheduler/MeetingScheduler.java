package com.conceptcoding.interviewquestions.meetingscheduler;

import java.sql.SQLException;
import java.util.Map;

public class MeetingScheduler {
    private final MeetingDao meetingDao;

    public MeetingScheduler(MeetingDao meetingDao) {
        this.meetingDao = meetingDao;
    }

    public Meeting scheduleMeeting(User organizer,
                                   Map<User, ParticipantResponse> participants,
                                   String subject,
                                   TimeInterval interval) throws SQLException {
        int capacity = participants.size() + 1;
        MeetingRoom room = meetingDao.findAvailableRoom(capacity, interval);
        if (room == null) return null;

        String meetingId = "MTS-" + System.currentTimeMillis();
        Meeting meeting = new Meeting(meetingId, subject, interval, organizer, participants, room);
        meetingDao.saveMeeting(meeting);

        Notification notification = new Notification(1, "Meeting Invitation");
        for (User user : participants.keySet()) {
            notification.sendMeetingInviteNotification(user, meeting);
        }
        return meeting;
    }

    public Meeting getMeeting(String meetingId) throws SQLException {
        return meetingDao.findById(meetingId);
    }

    public boolean cancelMeeting(String meetingId) throws SQLException {
        Meeting meeting = meetingDao.findById(meetingId);
        if (meeting == null || !meetingDao.cancelMeeting(meetingId)) return false;

        Notification notification = new Notification(2, "Meeting Cancelled");
        for (User user : meeting.getAttendees().keySet()) {
            notification.sendCancelMeetingNotification(user, meeting);
        }
        return true;
    }
}
