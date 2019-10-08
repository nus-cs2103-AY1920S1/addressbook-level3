package seedu.address.model.events;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an EventSource in Horo.
 * It is immutable.
 */
public class EventSource {

    // Required
    private final String description;
    private final DateTime start;

    // Optional
    private DateTime end;
    private Set<Tag> tags = new HashSet<>();

    /**
     * Creates an EventSource.
     * All fields must be non null.
     */
    public EventSource(String description, DateTime start) {
        requireAllNonNull(description, start);
        this.description = description;
        this.start = start;
    }

    /**
     * Returns a deep-copy of an EventSource.
     *
     * @param oldEventSource the eventSource to deep-copy.
     */
    public EventSource(EventSource oldEventSource) {
        this.description = oldEventSource.description;
        this.start = oldEventSource.start;
        this.end = oldEventSource.end;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getStartDateTime() {
        return start;
    }
}
