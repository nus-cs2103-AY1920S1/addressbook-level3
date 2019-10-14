package seedu.address.model.events;

import java.util.HashSet;
import java.util.Objects;
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
        this.description = Objects.requireNonNull(description);
        this.start = Objects.requireNonNull(start);
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
    }

    public String getDescription() {
        return this.description;
    }

    public DateTime getStartDateTime() {
        return this.start;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof EventSource) {
            EventSource e = (EventSource) object;
            return this.description.equals(e.description)
                && this.start.equals(e.start)
                && this.end.equals(e.end)
                && this.tags.equals(e.tags);
        }
        return false;
    }
}
