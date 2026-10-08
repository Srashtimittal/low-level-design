package com.conceptcoding.interviewquestions.meetingscheduler.api;

import com.conceptcoding.interviewquestions.meetingscheduler.MeetingRoom;
import com.conceptcoding.interviewquestions.meetingscheduler.MeetingScheduler;
import com.sun.net.httpserver.HttpServer;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;

public class ApiMain {
    public static void main(String[] args) throws Exception {

        List<MeetingRoom> rooms = new ArrayList<>();
        rooms.add(new MeetingRoom(1, "Room 1", 4));
        rooms.add(new MeetingRoom(2, "Room 2", 8));
        rooms.add(new MeetingRoom(3, "Room 3", 12));

        MeetingScheduler meetingScheduler =
                new MeetingScheduler(rooms);

        HttpServer server = HttpServer.create(
                new InetSocketAddress(8080),
                0);

        MeetingSchedulerApiHandler handler =
                new MeetingSchedulerApiHandler(meetingScheduler);

        handler.register(server);
        server.start();

        System.out.println(
                "Meeting Scheduler API running on http://localhost:8080");
    }
}
