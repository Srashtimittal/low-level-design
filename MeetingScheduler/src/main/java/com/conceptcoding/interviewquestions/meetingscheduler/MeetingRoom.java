package com.conceptcoding.interviewquestions.meetingscheduler;

public class MeetingRoom {
    private final int id;
    private String name;
    private final int capacity;

    public MeetingRoom(int id, String name, int capacity) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
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

    public int getCapacity() {
        return capacity;
    }
}
