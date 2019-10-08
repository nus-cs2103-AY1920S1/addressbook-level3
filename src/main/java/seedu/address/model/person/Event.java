package seedu.address.model.person;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Represents an Item's Event in ELISA.
 * Duration of Event is 0 by default.
 * Priority of Event is medium by default.
 * Guarantees: immutable;
 */
public class Event {

    public final LocalDateTime startDateTime;
    public final LocalDateTime endDateTime;
    
    //Duration chosen over Period as Events are unlikely to exceed a day.
    public final Duration duration;
    public final Priority priority;

    /**
     * Constructs an {@code Event}.
     *
     * @param startDateTimeString A valid LocalDateTime String that denotes the start of the event.
     */
    public Event(String startDateTimeString, Duration duration, Priority priority) throws IllegalArgumentException{
        requireNonNull(startDateTimeString);
        
        if (duration != null) {
            this.duration = duration;
        } else {
            this.duration = Duration.ZERO;
        }

        if (priority != null) {
            this.priority = priority;
        } else {
            this.priority = Priority.MEDIUM;
        }
        
        
        try {
            //Check validity of startDateTimeString by parsing
            this.startDateTime = LocalDateTime.parse(startDateTimeString);
            this.endDateTime = startDateTime.plus(duration);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("DateTimeString Invalid! It should follow the ISO_LOCAL_DATE_TIME format:" +
                    " YYYY-MM-DDTHH:MM. Eg.2019-10-09T02:00");
        }
    }
    
    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }
    
    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public Duration getDuration() {
        return duration;
    }

    public Priority getPriority() {
        return priority;
    }
    
    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Start DateTime: ")
                .append(getStartDateTime().toString())
                .append(" End DateTime: ")
                .append(getEndDateTime().toString())
                .append(" Duration: ")
                .append(getDuration().toString())
                .append(" Priority: ")
                .append(getPriority().toString());
        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Item)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime())
                && otherEvent.getDuration().equals(getDuration())
                && otherEvent.getPriority().equals(getPriority());
                
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, endDateTime, duration, priority);
    }

}
