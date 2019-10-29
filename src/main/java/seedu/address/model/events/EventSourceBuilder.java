package seedu.address.model.events;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.model.DateTime;

/**
 * Represents a Builder responsible for creating {@link EventSource}.
 */
public class EventSourceBuilder {

    // Required
    private final String description;
    private final DateTime start;

    // Optional
    private DateTime end;
    private Set<String> tags;
    private DateTime remind;

    EventSourceBuilder(String description, DateTime start) {
        this.description = Objects.requireNonNull(description);
        this.start = Objects.requireNonNull(start);
    }

    @JsonCreator
    private EventSourceBuilder(@JsonProperty("description") String description,
                               @JsonProperty("start") DateTime start,
                               @JsonProperty("end") DateTime end,
                               @JsonProperty("remind") DateTime remind,
                               @JsonProperty("tags") Set<String> tags) {
        this.description = description;
        this.start = start;
        this.end = end;
        this.remind = remind;
        this.tags = tags;
    }

    public EventSourceBuilder setEnd(DateTime end) {
        this.end = end;
        return this;
    }

    public EventSourceBuilder setTags(Collection<String> tags) {
        this.tags = new HashSet<>(tags);
        return this;
    }

    public EventSourceBuilder setRemind(DateTime remind) {
        this.remind = remind;
        return this;
    }

    public EventSource build() {
        return new EventSource(this);
    }

    String getDescription() {
        return description;
    }

    DateTime getStart() {
        return start;
    }

    DateTime getEnd() {
        return end;
    }

    Set<String> getTags() {
        return tags;
    }

    DateTime getRemind() {
        return remind;
    }
}
