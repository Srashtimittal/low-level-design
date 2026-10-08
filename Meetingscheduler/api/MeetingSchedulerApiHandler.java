package com.conceptcoding.interviewquestions.meetingscheduler.api;

import com.conceptcoding.interviewquestions.meetingscheduler.Meeting;
import com.conceptcoding.interviewquestions.meetingscheduler.MeetingRoom;
import com.conceptcoding.interviewquestions.meetingscheduler.MeetingScheduler;
import com.conceptcoding.interviewquestions.meetingscheduler.ParticipantResponse;
import com.conceptcoding.interviewquestions.meetingscheduler.ParticipantType;
import com.conceptcoding.interviewquestions.meetingscheduler.TimeInterval;
import com.conceptcoding.interviewquestions.meetingscheduler.User;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MeetingSchedulerApiHandler {

    private final MeetingScheduler meetingScheduler;
    private final Map<String, User> usersByEmail = new HashMap<>();
    private final Map<String, Meeting> meetingsById = new HashMap<>();

    public MeetingSchedulerApiHandler(MeetingScheduler meetingScheduler) {
        this.meetingScheduler = meetingScheduler;
    }

    public void register(HttpServer server) {
        server.createContext("/meetings/schedule", this::handleScheduleMeeting);
        server.createContext("/meetings/cancel", this::handleCancelMeeting);
        server.createContext("/rooms/available", this::handleFindAvailableRoom);
        server.createContext("/users/calendar", this::handleGetUserCalendar);
    }

    private void handleScheduleMeeting(HttpExchange exchange)
            throws IOException {

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        try {
            Map<String, String> request =
                    SimpleJson.parse(readBody(exchange));

            String organizerEmail = request.get("organizerEmail");
            String subject = request.get("subject");
            String attendeeEmails = request.getOrDefault("attendeeEmails", "");
            String start = request.get("start");
            String end = request.get("end");

            if (organizerEmail == null || subject == null
                    || start == null || end == null) {
                send(exchange, 400,
                        "{\"error\":\"organizerEmail, subject, start and end are required\"}");
                return;
            }

            User organizer = usersByEmail.computeIfAbsent(
                    organizerEmail,
                    email -> new User(
                            email,
                            email,
                            ParticipantType.ORGANIZER));

            Map<User, ParticipantResponse> participants = new HashMap<>();

            if (!attendeeEmails.isBlank()) {
                for (String email : attendeeEmails.split("\\|")) {
                    String attendeeEmail = email.trim();
                    if (attendeeEmail.isEmpty()) continue;

                    User attendee = usersByEmail.computeIfAbsent(
                            attendeeEmail,
                            value -> new User(
                                    value,
                                    value,
                                    ParticipantType.ATTENDEE));

                    participants.put(
                            attendee,
                            ParticipantResponse.NOT_RESPONDED);
                }
            }

            TimeInterval interval = new TimeInterval(
                    LocalDateTime.parse(start),
                    LocalDateTime.parse(end));

            Meeting meeting = meetingScheduler.scheduleMeeting(
                    organizer,
                    participants,
                    subject,
                    interval);

            if (meeting == null) {
                send(exchange, 409,
                        "{\"error\":\"No meeting room available\"}");
                return;
            }

            meetingsById.put(meeting.getMeetingId(), meeting);

            send(exchange, 201,
                    "{\"meetingId\":\"" + meeting.getMeetingId()
                            + "\",\"room\":\"" + meeting.getMeetingRoom().getName()
                            + "\"}");

        } catch (Exception e) {
            send(exchange, 400,
                    "{\"error\":\"Invalid request\"}");
        }
    }

    private void handleCancelMeeting(HttpExchange exchange)
            throws IOException {

        if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        Map<String, String> request = SimpleJson.parse(readBody(exchange));
        String meetingId = request.get("meetingId");
        Meeting meeting = meetingsById.get(meetingId);

        if (meeting == null) {
            send(exchange, 404,
                    "{\"error\":\"Meeting not found\"}");
            return;
        }

        meetingScheduler.cancelMeeting(meeting);
        meetingsById.remove(meetingId);

        send(exchange, 200,
                "{\"message\":\"Meeting cancelled\"}");
    }

    private void handleFindAvailableRoom(HttpExchange exchange)
            throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        try {
            Map<String, String> query = parseQuery(exchange.getRequestURI());

            int capacity = Integer.parseInt(query.get("capacity"));
            TimeInterval interval = new TimeInterval(
                    LocalDateTime.parse(query.get("start")),
                    LocalDateTime.parse(query.get("end")));

            MeetingRoom room = meetingScheduler.checkForAvailableRooms(
                    capacity,
                    interval);

            if (room == null) {
                send(exchange, 404, "{\"available\":false}");
                return;
            }

            send(exchange, 200,
                    "{\"available\":true,\"roomId\":" + room.getId()
                            + ",\"roomName\":\"" + room.getName() + "\"}");

        } catch (Exception e) {
            send(exchange, 400,
                    "{\"error\":\"capacity, start and end are required\"}");
        }
    }

    private void handleGetUserCalendar(HttpExchange exchange)
            throws IOException {

        if (!"GET".equalsIgnoreCase(exchange.getRequestMethod())) {
            send(exchange, 405, "{\"error\":\"Method not allowed\"}");
            return;
        }

        Map<String, String> query = parseQuery(exchange.getRequestURI());
        User user = usersByEmail.get(query.get("email"));

        if (user == null) {
            send(exchange, 404,
                    "{\"error\":\"User not found\"}");
            return;
        }

        StringBuilder response = new StringBuilder("[");

        for (Meeting meeting : user.getCalendar().getMeetings()) {
            if (response.length() > 1) response.append(",");

            response.append("{")
                    .append("\"meetingId\":\"")
                    .append(meeting.getMeetingId())
                    .append("\",\"subject\":\"")
                    .append(meeting.getSubject())
                    .append("\",\"room\":\"")
                    .append(meeting.getMeetingRoom().getName())
                    .append("\"}");
        }

        response.append("]");
        send(exchange, 200, response.toString());
    }

    private Map<String, String> parseQuery(URI uri) {
        Map<String, String> result = new HashMap<>();
        String query = uri.getQuery();

        if (query == null || query.isBlank()) return result;

        for (String pair : query.split("&")) {
            String[] parts = pair.split("=", 2);
            if (parts.length == 2) result.put(parts[0], parts[1]);
        }
        return result;
    }

    private String readBody(HttpExchange exchange) throws IOException {
        return new String(
                exchange.getRequestBody().readAllBytes(),
                StandardCharsets.UTF_8);
    }

    private void send(HttpExchange exchange, int status, String response)
            throws IOException {

        byte[] bytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json");
        exchange.sendResponseHeaders(status, bytes.length);
        exchange.getResponseBody().write(bytes);
        exchange.close();
    }
}
