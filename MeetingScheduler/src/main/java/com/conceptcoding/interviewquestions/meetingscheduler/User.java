package com.conceptcoding.interviewquestions.meetingscheduler;

public class User {
    private final int id;
    private String name;
    private String email;
    private ParticipantType participantType;

    public User(int id, String name, String email, ParticipantType participantType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.participantType = participantType;
    }

    public User(String name, String email, ParticipantType participantType) {
        this(0, name, email, participantType);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public void respondToInvitation(Meeting meeting, ParticipantResponse response) {
        meeting.getAttendees().put(this, response);
    }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
