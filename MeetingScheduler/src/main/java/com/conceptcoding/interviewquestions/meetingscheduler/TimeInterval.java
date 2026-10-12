package com.conceptcoding.interviewquestions.meetingscheduler;

import java.time.LocalDateTime;

public class TimeInterval {
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public TimeInterval(LocalDateTime startTime, LocalDateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public boolean overlapsWith(TimeInterval other) {
        return startTime.isBefore(other.endTime) && other.startTime.isBefore(endTime);
    }
}
