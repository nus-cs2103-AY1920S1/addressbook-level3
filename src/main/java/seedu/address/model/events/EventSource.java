package seedu.address.model.events;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Event in Horo.
 * It is immutable.
 */
public class EventSource {

    // Identity fields
    private final String description;
    private final Time time;
    private Time end;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public EventSource(String description, Time time, Set<Tag> tags) {
        requireAllNonNull(description, time, tags);
        this.description = description;
        this.time = time;
        this.tags.addAll(tags);
    }

    public String getDescription() {
        return description;
    }

    public Time getTime() {
        return time;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
}
