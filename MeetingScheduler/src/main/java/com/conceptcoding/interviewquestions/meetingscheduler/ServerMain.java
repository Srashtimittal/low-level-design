package com.conceptcoding.interviewquestions.meetingscheduler;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class ServerMain {
    public static void main(String[] args) throws IOException {
        MeetingDao dao = new MeetingDao();
        MeetingScheduler scheduler = new MeetingScheduler(dao);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/meetings", new MeetingHandler(dao, scheduler));
        server.start();

        System.out.println("Server running at http://localhost:8080");
    }
}
