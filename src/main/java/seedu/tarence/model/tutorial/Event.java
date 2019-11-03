package seedu.tarence.model.tutorial;

import static seedu.tarence.commons.util.AppUtil.checkArgument;
import static seedu.tarence.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Date;
import java.util.Objects;

/**
 * Represents an Event in a Tutorial.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Event implements Comparable<Event> {

    public static final String MESSAGE_CONSTRAINTS_EVENT_NAME =
            "Name should not be blank and should contain only alphanumeric characters.";
    public static final String MESSAGE_CONSTRAINTS_START_END_TIME =
            String.format("Time should be of the format %s. "
            + "Start time should be earlier than end time.", Tutorial.DATE_FORMAT);
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String eventName;
    public final Date startTime;
    public final Date endTime;

    /**
     * Every field must be present and not null.
     */
    public Event(String eventName, Date startTime, Date endTime) {
        requireAllNonNull(startTime, endTime);
        checkArgument(isValidEventName(eventName), MESSAGE_CONSTRAINTS_EVENT_NAME);
        checkArgument(isValidStartEndTime(startTime, endTime), MESSAGE_CONSTRAINTS_START_END_TIME);
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns true if event name is valid.
     */
    public static boolean isValidEventName(String name) {
        return name.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if given parameters are valid.
     */
    public static boolean isValidStartEndTime(Date startTime, Date endTime) {
        return startTime.before(endTime);
    }

    /**
     * Returns true two events overlap.
     */
    public boolean overlap(Event event) {
        return !(endTime.compareTo(event.startTime) < 0
                || startTime.compareTo(event.endTime) > 0);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(eventName)
                .append(" Start Time: ")
                .append(startTime)
                .append(" End Time: ")
                .append(endTime);

        return stringBuilder.toString();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Event // instanceof handles nulls
                && eventName.equals(((Event) other).eventName)
                && startTime.equals(((Event) other).startTime)
                && endTime.equals(((Event) other).endTime)); // state check
    }

    @Override
    public int compareTo(Event event1) {
        return startTime.compareTo(event1.startTime) != 0
                ? startTime.compareTo(event1.startTime)
                : endTime.compareTo(event1.endTime) != 0
                ? endTime.compareTo(event1.endTime)
                : eventName.compareTo(event1.eventName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, startTime, endTime);
    }
}
