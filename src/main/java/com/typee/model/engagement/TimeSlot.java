package com.typee.model.engagement;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * Represents the time slot during which an engagement is held.
 */
public class TimeSlot {

    public static final String MESSAGE_CONSTRAINTS = "The start and end times should conform to the DD/MM/YYYY HHMM"
            + " format. The dates and times must be semantically valid and"
            + " the start time should occur before the end time";
    private static final String MESSAGE_NON_NULL_CONSTRAINT = "%s should be non-null.";

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    /**
     * Constructs a {@code TimeSlot} from a given {@code LocalDateTime startTime}
     * and {@code LocalDateTime endTime}.
     * @param startTime starting time of the time slot.
     * @param endTime ending time of the time slot.
     */
    public TimeSlot(LocalDateTime startTime, LocalDateTime endTime) {
        assert startTime != null && endTime != null : String.format(MESSAGE_NON_NULL_CONSTRAINT,
                "The start and end times");
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns a {@code TimeSlot} constructed from a {@code String}.
     * @param timeSlot String representation of the time-slot.
     * @return a {@code TimeSlot}.
     */
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

    /**
     * Returns true if the start occurs simultaneously with the end, or after the end.
     * @return false if start is before end.
     */
    public boolean isStartAfterEnd() {
        return startTime.isAfter(endTime) || startTime.equals(endTime);
    }

    /**
     * Returns true if the {@code TimeSlot} overlaps with the input {@code TimeSlot}.
     * @param timeSlot timeSlot to check overlap with.
     * @return true if there is an overlap.
     */
    public boolean overlapsWith(TimeSlot timeSlot) {
        if (this.getStartTime().isBefore(timeSlot.getStartTime())) {
            return this.getEndTime().isAfter(timeSlot.getStartTime());
        } else if (timeSlot.getStartTime().isBefore(this.getStartTime())) {
            return timeSlot.getEndTime().isAfter(this.getStartTime());
        } else {
            return true;
        }
    }

    /**
     * Returns true if the {@code String} representation of a {@code TimeSlot} is valid.
     * @param timeSlot {@code String} representation of a {@code TimeSlot}.
     * @return true if the string form is valid.
     */
    public static boolean isValid(String timeSlot) {
        try {
            String[] tokens = tokenize(timeSlot);
            TimeSlot newTimeSlot = makeTimeSlotFromTokens(tokens);
            return !newTimeSlot.startTime.isAfter(newTimeSlot.endTime);
        } catch (DateTimeException e) {
            return false;
        }
    }

    /**
     * Returns a {@code TimeSlot} constructed from {@code String} tokens.
     * @param tokens String tokens containing start and end times.
     * @return the corresponding {@code TimeSlot}.
     * @throws DateTimeException if the times are invalid.
     */
    private static TimeSlot makeTimeSlotFromTokens(String[] tokens) throws DateTimeException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm");
        LocalDateTime startTime = LocalDateTime.parse(tokens[0].replace("T", "/"), formatter);
        LocalDateTime endTime = LocalDateTime.parse(tokens[1].replace("T", "/"), formatter);
        return new TimeSlot(startTime, endTime);
    }

    private static String[] tokenize(String timeSlot) {
        return timeSlot.split(" - ");
    }

    @Override
    public String toString() {
        return startTime.toString() + " - " + endTime.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            // short-circuit
            return true;
        } else if (o instanceof TimeSlot) {
            TimeSlot newTimeSlot = (TimeSlot) o;
            return newTimeSlot.startTime.equals(startTime) && newTimeSlot.endTime.equals(endTime);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime);
    }
}
