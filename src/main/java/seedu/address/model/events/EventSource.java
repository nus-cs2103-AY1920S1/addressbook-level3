package seedu.address.model.events;

import static seedu.address.commons.util.IcsUtil.generateUid;
import static seedu.address.commons.util.IcsUtil.toIcsTimeStamp;

import java.util.Objects;
import java.util.Set;

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

    /**
     * Creates an EventSource from an EventSourceBuilder.
     * All fields must be non null.
     */
    EventSource(EventSourceBuilder builder) {
        this.description = builder.getDescription();
        this.start = builder.getStart();
        this.end = builder.getEnd();
        this.tags = builder.getTags();
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

    public static EventSourceBuilder newBuilder(String description, DateTime start) {
        return new EventSourceBuilder(description, start);
    }

    /**
     * Checks if a particular instance EventSource should have its notification posted now.
     *
     * @return <code>true</code> if the EventSource's notification timing matches the current notification timing.
     */
    public boolean notificationTimeMatchesCurrentTime() {
        return start.equalsPrecisionMinute(DateTime.now());
    }

    public String getDescription() {
        return this.description;
    }

    public DateTime getStartDateTime() {
        return this.start;
    }

    public DateTime getEnd() {
        return end;
    }

    public Set<String> getTags() {
        return tags;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof EventSource) {
            EventSource e = (EventSource) object;
            return Objects.equals(this.description, e.description)
                && Objects.equals(this.start, e.start)
                && Objects.equals(this.end, e.end)
                && Objects.equals(this.tags, e.tags);
        }
        return false;
    }

    /**
     * @return The String representation of the EventSource in the Ics file format.
     */
    public String toIcsString() {
        DateTime now = DateTime.now();
        StringBuilder icsStringBuilder = new StringBuilder("BEGIN:VEVENT");

        String uid = generateUid();
        String dtStamp = toIcsTimeStamp(now);
        String start = toIcsTimeStamp(this.start);

        icsStringBuilder
                .append("\n").append("UID:").append(uid)
                .append("\n").append("DTSTAMP:").append(dtStamp)
                .append("\n").append("DTSTART:").append(start)
                .append("\n").append("DTSUMMARY:").append(this.getDescription());
        if (this.end != null) {
            String end = toIcsTimeStamp(this.end);
            icsStringBuilder
                    .append("\n").append("DTEND:").append(end);
        }

        icsStringBuilder.append("\n").append("END:VEVENT");
        return icsStringBuilder.toString();
    }

}
