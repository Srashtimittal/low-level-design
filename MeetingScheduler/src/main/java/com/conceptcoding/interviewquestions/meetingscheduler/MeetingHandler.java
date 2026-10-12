package com.conceptcoding.interviewquestions.meetingscheduler;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MeetingHandler implements HttpHandler {
    private final Gson gson = new Gson();
    private final MeetingDao dao;
    private final MeetingScheduler scheduler;

    public MeetingHandler(MeetingDao dao, MeetingScheduler scheduler) {
        this.dao = dao;
        this.scheduler = scheduler;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();

            if (method.equals("POST") && path.equals("/meetings")) {
                create(exchange);
                return;
            }

            if (!path.startsWith("/meetings/")) {
                send(exchange, 404, "Route not found");
                return;
            }

            String id = path.substring("/meetings/".length());
            if (method.equals("GET")) get(exchange, id);
            else if (method.equals("DELETE")) cancel(exchange, id);
            else send(exchange, 405, "Method not allowed");

        } catch (Exception e) {
            e.printStackTrace();
            send(exchange, 500, "Internal server error");
        }
    }

    private void create(HttpExchange exchange) throws Exception {
        String body = new String(exchange.getRequestBody().readAllBytes(), StandardCharsets.UTF_8);
        MeetingRequest req = gson.fromJson(body, MeetingRequest.class);

        if (req == null || req.subject() == null || req.startTime() == null || req.endTime() == null) {
            send(exchange, 400, "Invalid request");
            return;
        }

        LocalDateTime start = LocalDateTime.parse(req.startTime());
        LocalDateTime end = LocalDateTime.parse(req.endTime());
        if (!start.isBefore(end)) {
            send(exchange, 400, "Invalid time interval");
            return;
        }

        User organizer = dao.findUserById(req.organizerId(), ParticipantType.ORGANIZER);
        if (organizer == null) {
            send(exchange, 404, "Organizer not found");
            return;
        }

        Map<User, ParticipantResponse> participants = new HashMap<>();
        for (int id : req.participantIds() == null ? List.<Integer>of() : req.participantIds()) {
            User user = dao.findUserById(id, ParticipantType.ATTENDEE);
            if (user == null) {
                send(exchange, 404, "Participant not found");
                return;
            }
            participants.put(user, ParticipantResponse.NOT_RESPONDED);
        }

        Meeting meeting = scheduler.scheduleMeeting(
                organizer, participants, req.subject(), new TimeInterval(start, end));

        if (meeting == null) {
            send(exchange, 409, "No room available");
            return;
        }

        send(exchange, 201, toResponse(meeting));
    }

    private void get(HttpExchange exchange, String id) throws Exception {
        Meeting meeting = scheduler.getMeeting(id);
        if (meeting == null) {
            send(exchange, 404, "Meeting not found");
            return;
        }
        send(exchange, 200, toResponse(meeting));
    }

    private void cancel(HttpExchange exchange, String id) throws Exception {
        if (!scheduler.cancelMeeting(id)) {
            send(exchange, 404, "Meeting not found or already cancelled");
            return;
        }
        send(exchange, 200, "Meeting cancelled");
    }

    private MeetingResponse toResponse(Meeting m) {
        List<Integer> participantIds = m.getAttendees().keySet().stream().map(User::getId).toList();
        return new MeetingResponse(
                m.getMeetingId(), m.getSubject(), m.getOrganizer().getId(),
                m.getMeetingRoom().getId(), m.getMeetingRoom().getName(),
                m.getTimeInterval().getStartTime().toString(),
                m.getTimeInterval().getEndTime().toString(), m.getStatus(), participantIds);
    }

    private void send(HttpExchange exchange, int status, Object body) throws IOException {
        byte[] response = gson.toJson(body).getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    private record MeetingRequest(int organizerId, List<Integer> participantIds,
                                  String subject, String startTime, String endTime) {}

    private record MeetingResponse(String meetingId, String subject, int organizerId,
                                   int roomId, String roomName, String startTime, String endTime,
                                   String status, List<Integer> participantIds) {}
}
