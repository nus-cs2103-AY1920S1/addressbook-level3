package seedu.address.model.event;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.person.*;
import seedu.address.model.tag.Tag;

public class Event {

    //Identity Fields
    //private final EventID id;
    private final EventName name;

    //Data Fields
    /*private final EventManpowerNeeded manpowerNeeded;
    private final EventVenue venue;
    private final EventHoursNeeded hoursNeeded;
    private final EventStartDate startDate;
    private final EventEndDate endDate;
    private final EventManpowerAllocatedList manpowerAllocatedList;
    private final Set<Tag> tags = new HashSet<>();

    public Event(EventID id, EventName name, EventVenue venue, EventHoursNeeded hoursNeeded,
                 EventManpowerNeeded manpowerNeeded, EventStartDate startDate, EventEndDate endDate, Set<Tag> tags) {
        this.id = id;
        this.name = name;
        this.venue = venue;
        this.hoursNeeded = hoursNeeded;
        this.manpowerNeeded = manpowerNeeded;
        this.startDate = startDate;
        this.endDate = endDate;
        this.manpowerAllocatedList = new EventManpowerAllocatedList();
        this.tags.addAll(tags);
    }*/

    public Event(EventName name) {
        this.name = name;
    }

    public EventName getName() {
        return name;
    }



    /*

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     *
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two persons.
     *
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getName().equals(getName())
                && (otherEvent.getPhone().equals(getPhone()) || otherEvent.getEmail().equals(getEmail()));
    }



    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getTags().equals(getTags());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        return builder.toString();
    }
    */
}
