package seedu.address.commons.stub;

import seedu.address.commons.stub.*;

import java.util.Objects;


public class EventSourceStub {
    private final NameStub name;
    private final TimeStub startingTime;

    /*
    private final Venue venue;
    private final Duration duration;

    private final Recurrence recurrence;
    private final Set<Tag> tags = new HashSet<>();
    */

    public EventSourceStub(NameStub name, TimeStub startingTime) {
        this.name = name;
        this.startingTime = startingTime;
    }

    public NameStub getName() {
        return name;
    }

    public TimeStub getStartingTime() {
        return startingTime;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventSourceStub)) {
            return false;
        }

        EventSourceStub otherEventSource = (EventSourceStub) other;
        return otherEventSource.getName().equals(getName())
                && otherEventSource.getStartingTime().equals(getName());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, startingTime);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();

        sb.append(getName())
                .append(" Start Time: ")
                .append(getStartingTime());

        return sb.toString();
    }
}
