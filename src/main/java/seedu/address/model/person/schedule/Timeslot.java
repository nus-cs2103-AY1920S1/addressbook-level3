package seedu.address.model.person.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Timeslot of an Event.
 */
public class Timeslot {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm");
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Venue venue;

    public Timeslot(LocalDateTime startTime, LocalDateTime endTime, Venue venue) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    /**
     * Converts to String.
     * @return String
     */
    public String toString() {
        String output = "";
        output += startTime.format(DATE_TIME_FORMATTER) + "hrs - ";
        output += endTime.format(DATE_TIME_FORMATTER) + "hrs at ";
        output += venue.toString() + " ";
        return output;
    }
}
