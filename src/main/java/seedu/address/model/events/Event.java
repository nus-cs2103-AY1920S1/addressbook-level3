package seedu.address.model.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.common.ReferenceId;

/**
 * Represents an event involving a single Person.
 * Guarantees: Reference Id to a patient, the event timing and status are present, validated and immutable.
 */
public class Event implements Comparable<Event> {

    // Identity fields
    private final ReferenceId personId;
    private final Timing timing;
    private Status status;

    /**
     * Every field must be present and not null.
     */
    public Event(ReferenceId personId, Timing timing, Status status) {
        requireAllNonNull(personId, timing, status);
        this.personId = personId;
        this.timing = timing;
       this.status = status;
    }

    public ReferenceId getPersonId() {
        return personId;
    }

    public Timing getEventTiming() {
        return timing;
    }

    public Status getStatus() {
        return status;
    }

   public void setStausAsAck() {
       status.setAckStatus();
   }

   public void setStausAsMISS() {
       status.setMissStatus();
   }

   public void setStausAsSettle() {
       status.setSettleStatus();
   }

   public void setStausAsCancell() {
       status.setCancelStatus();
   }


    public boolean conflictsWith(Event otherEvent) {
        return getEventTiming().conflictsWith(otherEvent.getEventTiming());
    }

    /**
     * Returns true if both Event of the same patient and timing.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getPersonId().equals(getPersonId())
                && otherEvent.getEventTiming().equals(getEventTiming());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
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
        return otherEvent.getPersonId().equals(getPersonId())
                && otherEvent.getEventTiming().equals(getEventTiming());
    }

    @Override
    public int compareTo(Event o) {
        requireNonNull(o);
        int cmpTimingResult = getEventTiming().compareTo(o.getEventTiming());
        if (cmpTimingResult != 0) {
            return cmpTimingResult;
        }

        return getPersonId().compareTo(o.getPersonId());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(personId, timing, status);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Event - Person ID: ")
                .append(getPersonId())
                .append(" Timing: ")
                .append(getEventTiming())
                .append(" status: ")
                .append(getStatus());
        return builder.toString();
    }
}
