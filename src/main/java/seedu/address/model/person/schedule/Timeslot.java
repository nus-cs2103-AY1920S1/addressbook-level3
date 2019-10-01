package seedu.address.model.person.schedule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Timeslot of an Event.
 */
public class Timeslot {
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd MMM uuuu HH:mm");
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Venue venue;

    public Timeslot(LocalDateTime startTime, LocalDateTime endTime, Venue venue) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
    }

    /**
     * Compares if it is equal to another timeslot object.
     * @param timeslot to be compared
     * @return boolean
     */
    public boolean equals(Timeslot timeslot) {
        if (startTime.compareTo(timeslot.getStartTime()) != 0) {
            return false;
        } else if (endTime.compareTo(timeslot.getEndTime()) != 0) {
            return false;
        } else if (!venue.equals(timeslot.getVenue())) {
            return false;
        } else if (timeslot == null) {
            return false;
        } else {
            return true;
        }
    }

    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    public Venue getVenue() {
        return this.venue;
    }

    /**
     * Converts to String.
     *
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
