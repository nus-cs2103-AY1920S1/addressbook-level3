package seedu.address.model.event;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents an Event in the AddMin app.
 */
public class Event {

    //Identity Fields
    private final EventId eventId;

    //Data Fields
    private final EventName name;
    private final EventVenue venue;
    private final EventManpowerNeeded manpowerNeeded;
    private final EventHoursNeeded hoursNeeded;
    private final EventStartDate startDate;
    private final EventEndDate endDate;
    private final EventManpowerAllocatedList manpowerAllocatedList;
    private final Set<Tag> tags = new HashSet<>();

    public Event(EventId id, EventName name, EventVenue venue, EventHoursNeeded hoursNeeded,
                 EventManpowerNeeded manpowerNeeded, EventStartDate startDate,
                 EventEndDate endDate, Set<Tag> tags) {
        this.eventId = id;
        this.name = name;
        this.venue = venue;
        this.hoursNeeded = hoursNeeded;
        this.manpowerNeeded = manpowerNeeded;
        this.manpowerAllocatedList = new EventManpowerAllocatedList();
        this.startDate = startDate;
        this.endDate = endDate;
        this.tags.addAll(tags);
    }

    /**
     * Temporary Constructor
     */
    public Event() {
        this.eventId = null;
        this.name = null;
        this.venue = null;
        this.hoursNeeded = null;
        this.manpowerNeeded = null;
        this.manpowerAllocatedList = new EventManpowerAllocatedList();
        this.startDate = null;
        this.endDate = null;
        this.tags.addAll(tags);
    }

    public EventId getEventId() {
        return eventId;
    }

    public EventName getName() {
        return name;
    }

    public EventVenue getVenue() {
        return venue;
    }

    public EventManpowerNeeded getManpowerNeeded() {
        return manpowerNeeded;
    }

    public EventHoursNeeded getHoursNeeded() {
        return hoursNeeded;
    }

    public EventStartDate getStartDate() {
        return startDate;
    }

    public EventEndDate getEndDate() {
        return endDate;
    }

    public EventManpowerAllocatedList getManpowerAllocatedList() {
        return manpowerAllocatedList;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventId().equals(getEventId());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return otherEvent.getEventId().equals(getEventId())
                && otherEvent.getName().equals(getName())
                && otherEvent.getVenue().equals(getVenue())
                && otherEvent.getManpowerNeeded().equals(getManpowerNeeded())
                && otherEvent.getHoursNeeded().equals(getHoursNeeded())
                && otherEvent.getStartDate().equals(getStartDate())
                && otherEvent.getEndDate().equals(getEndDate())
                && otherEvent.getManpowerAllocatedList().equals(getManpowerAllocatedList())
                && otherEvent.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventId, name, venue, manpowerNeeded, hoursNeeded,
            startDate, endDate, manpowerAllocatedList, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName());
        builder.append(" Event ID: ").append(getEventId());
        builder.append(" Event Venue: ").append(getVenue());
        builder.append(" Event Manpower Needed: ").append(getManpowerNeeded());
        builder.append(" Event Hours Needed: ").append(getHoursNeeded());
        builder.append(" Event Start Date: ").append(getStartDate());
        builder.append(" Event End Date: ").append(getEndDate());
        builder.append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
}
