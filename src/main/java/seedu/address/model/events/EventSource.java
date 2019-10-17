package seedu.address.model.events;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.IcsUtil.generateUid;
import static seedu.address.commons.util.IcsUtil.toIcsTimeStamp;

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
     * Creates an EventSource.
     * The description and start fields must be non null. The end field is optional.
     */
    public EventSource(String description, DateTime start, DateTime end) {
        requireAllNonNull(description, start);
        this.description = description;
        this.start = start;
        this.end = end;
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

    /**
     * Checks if a particular instance EventSource should have its notification posted now.
     *
     * @return <code>true</code> if the EventSource's notification timing matches the current notification timing.
     */
    public boolean notificationTimeMatchesCurrentTime() {
        return start.equalsPrecisionMinute(DateTime.now());
    }

    public String getDescription() {
        return description;
    }

    public DateTime getStartDateTime() {
        return start;
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
