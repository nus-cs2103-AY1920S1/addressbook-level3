package seedu.address.model.events;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class EventSource {

    // Identity fields
    private final Name name;
    private final Time time;
    private final Duration duration;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */
    public EventSource(Name name, Time time, Duration duration, Set<Tag> tags) {
        requireAllNonNull(name, time, duration, tags);
        this.name = name;
        this.time = time;
        this.duration = duration;
        this.tags.addAll(tags);
    }

    public Name getName() {
        return name;
    }

    public Time getTime() {
        return time;
    }

    public Duration getDuration() {
        return duration;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
}
