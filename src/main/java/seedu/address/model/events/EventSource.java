package seedu.address.model.events;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_END_DATE;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_EVENT_REMIND_DATE;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.DateTime;
import seedu.address.model.exceptions.InvalidEventSourceException;

/**
 * Represents an EventSource in Horo.
 * It is immutable.
 */
public class EventSource {

    // Required
    private final String description;
    private final DateTime start;

    // Optional
    private final DateTime end;
    private final Set<String> tags;
    private final DateTime remind;

    /**
     * Creates an EventSource from an EventSourceBuilder.
     * All fields must be non null.
     */
    EventSource(EventSourceBuilder builder) {
        this.description = builder.getDescription();
        this.start = builder.getStart();
        this.end = builder.getEnd();
        if (builder.getTags() == null) {
            this.tags = new HashSet<>();
        } else {
            this.tags = builder.getTags();
        }
        this.remind = builder.getRemind();

        // Ensure EventSource date time are valid
        if (this.end != null && this.start.compareTo(this.end) >= 0) {
            throw new InvalidEventSourceException(MESSAGE_INVALID_EVENT_END_DATE);
        }

        if (this.remind != null && this.start.compareTo(this.remind) < 0) {
            throw new InvalidEventSourceException(MESSAGE_INVALID_EVENT_REMIND_DATE);
        }
    }

    /**
     * Copy constructor.
     * Creates a deep-copy of an EventSource.
     * @param eventSource the eventSource to deep-copy.
     */
    public EventSource(EventSource eventSource) {
        this.description = eventSource.description;
        this.start = eventSource.start;
        this.end = eventSource.end;
        this.tags = eventSource.tags;
        this.remind = eventSource.remind;
    }

    public static EventSourceBuilder newBuilder(String description, DateTime start) {
        return new EventSourceBuilder(description, start);
    }

    @JsonProperty("description")
    public String getDescription() {
        return this.description;
    }

    @JsonProperty("start")
    public DateTime getStartDateTime() {
        return this.start;
    }

    @JsonProperty("end")
    public DateTime getEndDateTime() {
        return this.end;
    }

    @JsonProperty("remind")
    public DateTime getRemindDateTime() {
        return this.remind;
    }

    @JsonProperty("tags")
    public Set<String> getTags() {
        return this.tags;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof EventSource) {
            EventSource e = (EventSource) object;
            return Objects.equals(this.description, e.description)
                && Objects.equals(this.start, e.start)
                && Objects.equals(this.end, e.end)
                && Objects.equals(this.remind, e.remind)
                && Objects.equals(this.tags, e.tags);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.description,
            this.start,
            this.end,
            this.remind,
            this.tags);
    }
}
