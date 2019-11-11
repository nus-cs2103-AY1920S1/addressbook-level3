package seedu.elisa.commons.core.item;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;

import seedu.elisa.commons.exceptions.IllegalValueException;
import seedu.elisa.commons.util.JsonUtil;
import seedu.elisa.model.AutoReschedulePeriod;

/**
 * Represents an Item's Event in ELISA.
 * Duration of Event is 0 by default.
 * Priority of Event is medium by default.
 * Guarantees: immutable;
 */
public class Event {

    private final LocalDateTime startDateTime;
    private final LocalDateTime endDateTime;
    //Duration chosen over Period as Events are unlikely to exceed a day.
    private final Duration duration;
    private final boolean isAutoReschedule;
    private final AutoReschedulePeriod period;

    /**
     * Constructs an {@code Event}.
     *
     * @param startDateTime A valid LocalDateTime object that denotes the start of the event.
     * @param duration A Duration of the event. Defaults to Duration.ZERO if null.
     */
    public Event(LocalDateTime startDateTime, Duration duration) throws IllegalArgumentException {
        this(startDateTime, duration, false, null);
    }

    public Event(LocalDateTime startDateTime, Duration duration, boolean isAutoReschedule)
            throws IllegalArgumentException {
        this(startDateTime, duration, isAutoReschedule, null);
    }

    public Event(LocalDateTime startDateTime, Duration duration, boolean isAutoReschedule,
                 AutoReschedulePeriod period) throws IllegalArgumentException {
        requireNonNull(startDateTime);
        if (duration != null) {
            this.duration = duration;
        } else {
            this.duration = Duration.ZERO;
        }

        this.startDateTime = startDateTime;
        this.endDateTime = startDateTime.plus(this.duration);
        this.isAutoReschedule = isAutoReschedule;
        this.period = period;
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

    public boolean hasAutoReschedule() {
        return isAutoReschedule;
    }

    /**
     * Set auto reschedule to true if the event should recur/auto-reschedule, false otherwise
     * @param bool true if event can be auto-rescheduled
     * @return a new Event object with the updated parameters
     */
    public Event setAutoReschedule(boolean bool) {
        return new Event(getStartDateTime(), getDuration(), bool);
    }

    /**
     * Get the reschedule period of this event
     * @return AutoReschedule period of this event
     */
    public AutoReschedulePeriod getPeriod() {
        return this.period;
    }

    /**
     * Set the reschedule period of this event. This would also set isAutoReschedule of this event to true.
     * @param period to set to this event
     * @return a new Event object with the updated parameters
     */
    public Event setReschedulePeriod(AutoReschedulePeriod period) {
        return new Event(getStartDateTime(), getDuration(), true, period);
    }

    public Event changeStartDateTime(LocalDateTime newStartDateTime) {
        return new Event(newStartDateTime, getDuration(), this.isAutoReschedule, this.period);
    }

    public Event changeDuration(Duration newDuration) {
        return new Event(getStartDateTime(), newDuration, this.isAutoReschedule, this.period);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nStart DateTime: ")
                .append(getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")));
        //Removed duration & endDateTime portion of string as requested in team meeting.

        return builder.toString();
    }

    /**
     * Creates a string for UI display.
     * @return A string containing only the start DateTime of the Event.
     */
    public String toDisplay() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nDateTime: ")
                .append(getStartDateTime().format(DateTimeFormatter.ofPattern("dd/MM/uuuu HH:mm")));

        return builder.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getStartDateTime().equals(getStartDateTime())
                && otherEvent.getEndDateTime().equals(getEndDateTime())
                && otherEvent.getDuration().equals(getDuration())
                && otherEvent.getPeriod().equals(getPeriod());
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDateTime, endDateTime, duration);
    }

    /**
     * Creates an event object from a JSON string.
     * @param jsonString the JSON string that represents the event
     * @return the event object that is created
     * @throws IOException when the jsonString is not in JSON format
     * @throws IllegalValueException when the JSON string contains incorrect value
     */
    public static Event fromJson(String jsonString) throws IOException {
        JsonNode node = JsonUtil.getObjectMapper().readTree(jsonString);

        String startDateTimeString = node.get("startDateTime").asText();
        LocalDateTime startDateTime = LocalDateTime.parse(startDateTimeString);

        String durationString = node.get("duration").asText();
        Duration duration = Duration.parse(durationString);

        String periodString = node.get("period").toString(); // in the format of {"period": 60000}
        if (!periodString.isEmpty() && !periodString.equals("null")) {
            periodString = node.get("period").get("period").asText(); // get the long value
            Long periodMillis = Long.valueOf(periodString);
            AutoReschedulePeriod period = new AutoReschedulePeriod(periodMillis);
            return new Event(startDateTime, duration, true, period);
        }

        return new Event(startDateTime, duration);
    }

}
