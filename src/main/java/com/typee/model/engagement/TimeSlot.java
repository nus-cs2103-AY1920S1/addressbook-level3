package com.typee.model.engagement;

import java.sql.Time;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeSlot {

    private static final String MESSAGE_NON_NULL_CONSTRAINT = "%s should be non-null.";
    public static final String MESSAGE_CONSTRAINTS = "The start and end times should conform to the DD/MM/YYYY HHMM"
            + " format. The dates and times must be semantically valid and"
            + " the start time should occur before the end time";

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        assert startTime != null && endTime != null : String.format(MESSAGE_NON_NULL_CONSTRAINT,
                "The start and end times");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static TimeSlot of(String timeSlot) {
        String[] tokens = tokenize(timeSlot);
        TimeSlot newTimeSlot = makeTimeSlotFromTokens(tokens);
        return newTimeSlot;
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

    public boolean isStartAfterEnd() {
        return startTime.isAfter(endTime) || startTime.equals(endTime);
    }

    public boolean overlapsWith(TimeSlot timeSlot) {
        if (this.getStartTime().isBefore(timeSlot.getStartTime())) {
            return this.getEndTime().isAfter(timeSlot.getStartTime());
        } else if (timeSlot.getStartTime().isBefore(this.getStartTime())) {
            return timeSlot.getEndTime().isAfter(this.getStartTime());
        } else {
            return true;
        }
    }

    public static boolean isValid(String timeSlot) {
        try {
            String[] tokens = tokenize(timeSlot);
            TimeSlot newTimeSlot = makeTimeSlotFromTokens(tokens);
            return !newTimeSlot.startTime.isAfter(newTimeSlot.endTime);
        } catch (DateTimeException e) {
            return false;
        }
    }

    private static TimeSlot makeTimeSlotFromTokens(String[] tokens) throws DateTimeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm");
        LocalDateTime startTime = LocalDateTime.parse(tokens[0], formatter);
        LocalDateTime endTime = LocalDateTime.parse(tokens[1], formatter);
        return new TimeSlot(startTime, endTime);
    }

    private static String[] tokenize(String timeSlot) {
        return timeSlot.split(" - ");
    }

    @Override
    public String toString() {
        return startTime.toString() + " - " + endTime.toString();
    }
}
