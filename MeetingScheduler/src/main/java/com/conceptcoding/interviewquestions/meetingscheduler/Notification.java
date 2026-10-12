package com.conceptcoding.interviewquestions.meetingscheduler;

public class Notification {
    private final int notificationId;
    private final String message;

    public Notification(int notificationId, String message) {
        this.notificationId = notificationId;
        this.message = message;
    }

    public void sendMeetingInviteNotification(User user, Meeting meeting) {
        System.out.println("Invite sent to " + user.getName() + " for " + meeting.getSubject());
    }

    public void sendCancelMeetingNotification(User user, Meeting meeting) {
        System.out.println("Cancellation sent to " + user.getName() + " for " + meeting.getSubject());
    }
}
