package seedu.address.commons.core.item;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

import com.fasterxml.jackson.databind.JsonNode;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;

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

    /**
     * Constructs an {@code Event}.
     *
     * @param startDateTime A valid LocalDateTime object that denotes the start of the event.
     * @param duration A Duration of the event. Defaults to Duration.ZERO if null.
     */
    public Event(LocalDateTime startDateTime, Duration duration) throws IllegalArgumentException {
        requireNonNull(startDateTime);
        if (duration != null) {
            this.duration = duration;
        } else {
            this.duration = Duration.ZERO;
        }

        this.startDateTime = startDateTime;
        this.endDateTime = startDateTime.plus(this.duration);
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

    public Event changeStartDateTime(LocalDateTime newStartDateTime) {
        return new Event(newStartDateTime, getDuration());
    }

    public Event changeDuration(Duration newDuration) {
        return new Event(getStartDateTime(), newDuration);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("\nStart DateTime: ")
                .append(getStartDateTime().toString())
                .append("\nEnd DateTime: ")
                .append(getEndDateTime().toString())
                .append(" Duration: ")
                .append(getDuration().toString());

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
                && otherEvent.getDuration().equals(getDuration());
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

        return new Event(startDateTime, duration);
    }

}
