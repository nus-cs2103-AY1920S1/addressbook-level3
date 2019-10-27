package com.typee.model.engagement;

import java.time.LocalDateTime;

public class TimeSlot {

    private static final String MESSAGE_NON_NULL_CONSTRAINT = "%s should be non-null.";

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        assert startTime != null && endTime != null : String.format(MESSAGE_NON_NULL_CONSTRAINT,
                "The start and end times");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        assert startTime != null : String.format(MESSAGE_NON_NULL_CONSTRAINT, "The start time");
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        assert endTime != null : String.format(MESSAGE_NON_NULL_CONSTRAINT, "The end time");
        this.endTime = endTime;
    }
}
