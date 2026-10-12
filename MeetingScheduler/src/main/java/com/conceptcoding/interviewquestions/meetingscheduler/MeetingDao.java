package com.conceptcoding.interviewquestions.meetingscheduler;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class MeetingDao {

    public User findUserById(int id, ParticipantType type) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            return new User(id, rs.getString("name"), rs.getString("email"), type);
        }
    }

    public MeetingRoom findAvailableRoom(int capacity, TimeInterval interval) throws SQLException {
        String sql = """
                SELECT * FROM meeting_rooms r
                WHERE r.capacity >= ?
                AND NOT EXISTS (
                    SELECT 1 FROM meetings m
                    WHERE m.room_id = r.id
                    AND m.status = 'SCHEDULED'
                    AND m.start_time < ?
                    AND m.end_time > ?
                )
                LIMIT 1
                """;

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, capacity);
            ps.setTimestamp(2, Timestamp.valueOf(interval.getEndTime()));
            ps.setTimestamp(3, Timestamp.valueOf(interval.getStartTime()));
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;
            return new MeetingRoom(rs.getInt("id"), rs.getString("name"), rs.getInt("capacity"));
        }
    }

    public void saveMeeting(Meeting meeting) throws SQLException {
        String meetingSql = "INSERT INTO meetings VALUES (?, ?, ?, ?, ?, ?, ?)";
        String participantSql = "INSERT INTO meeting_participants VALUES (?, ?, ?)";

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(meetingSql)) {
            ps.setString(1, meeting.getMeetingId());
            ps.setString(2, meeting.getSubject());
            ps.setInt(3, meeting.getOrganizer().getId());
            ps.setInt(4, meeting.getMeetingRoom().getId());
            ps.setTimestamp(5, Timestamp.valueOf(meeting.getTimeInterval().getStartTime()));
            ps.setTimestamp(6, Timestamp.valueOf(meeting.getTimeInterval().getEndTime()));
            ps.setString(7, meeting.getStatus());
            ps.executeUpdate();
        }

        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(participantSql)) {
            for (Map.Entry<User, ParticipantResponse> entry : meeting.getAttendees().entrySet()) {
                ps.setString(1, meeting.getMeetingId());
                ps.setInt(2, entry.getKey().getId());
                ps.setString(3, entry.getValue().name());
                ps.executeUpdate();
            }
        }
    }

    public Meeting findById(String meetingId) throws SQLException {
        String meetingSql = """
                SELECT m.*, u.name organizer_name, u.email organizer_email,
                       r.name room_name, r.capacity room_capacity
                FROM meetings m
                JOIN users u ON u.id = m.organizer_id
                JOIN meeting_rooms r ON r.id = m.room_id
                WHERE m.id = ?
                """;

        String participantSql = """
                SELECT u.id, u.name, u.email, mp.response
                FROM meeting_participants mp
                JOIN users u ON u.id = mp.user_id
                WHERE mp.meeting_id = ?
                """;

        try (Connection con = Database.getConnection()) {
            PreparedStatement ps = con.prepareStatement(meetingSql);
            ps.setString(1, meetingId);
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) return null;

            User organizer = new User(
                    rs.getInt("organizer_id"), rs.getString("organizer_name"),
                    rs.getString("organizer_email"), ParticipantType.ORGANIZER);

            MeetingRoom room = new MeetingRoom(
                    rs.getInt("room_id"), rs.getString("room_name"), rs.getInt("room_capacity"));

            TimeInterval interval = new TimeInterval(
                    rs.getTimestamp("start_time").toLocalDateTime(),
                    rs.getTimestamp("end_time").toLocalDateTime());

            String subject = rs.getString("subject");
            String status = rs.getString("status");

            Map<User, ParticipantResponse> participants = new HashMap<>();
            ps = con.prepareStatement(participantSql);
            ps.setString(1, meetingId);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"),
                        rs.getString("email"), ParticipantType.ATTENDEE);
                participants.put(user, ParticipantResponse.valueOf(rs.getString("response")));
            }

            return new Meeting(meetingId, subject, interval, organizer, participants, room, status);
        }
    }

    public boolean cancelMeeting(String meetingId) throws SQLException {
        String sql = "UPDATE meetings SET status = 'CANCELLED' WHERE id = ? AND status = 'SCHEDULED'";
        try (Connection con = Database.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, meetingId);
            return ps.executeUpdate() > 0;
        }
    }
}
