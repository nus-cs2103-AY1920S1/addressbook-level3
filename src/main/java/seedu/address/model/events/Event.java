//@@author SakuraBlossom
package seedu.address.model.events;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.model.ReferenceId;
import seedu.address.model.common.Identical;
import seedu.address.model.events.parameters.Status;
import seedu.address.model.events.parameters.Timing;
import seedu.address.model.person.parameters.Name;

/**
 * Represents an event involving a single Person.
 * Guarantees: Reference Id to a patient, the event timing and status are present, validated and immutable.
 */
public class Event implements Identical<Event> {

    // Identity fields
    private final ReferenceId personId;
    private final Name personName;
    private final Timing timing;
    private Status status;

    /**
     * Every field must be present and not null.
     */
    public Event(ReferenceId personId, Name personName, Timing timing, Status status) {
        requireAllNonNull(personId, personName, timing, status);

        this.personId = personId;
        this.personName = personName;
        this.timing = timing;

        if (!status.equals(Status.AppointmentStatuses.SETTLED) && timing.hasMissedTiming()) {
            this.status = new Status(Status.AppointmentStatuses.MISSED);
        } else {
            this.status = status;
        }
    }


    public ReferenceId getPersonId() {
        return personId;
    }

    public Name getPersonName() {
        return personName;
    }

    public Timing getEventTiming() {
        return timing;
    }

    public Status getStatus() {
        return status;
    }

    public boolean conflictsWith(Event otherEvent) {
        return getEventTiming().conflictsWith(otherEvent.getEventTiming());
    }

    /**
     * Returns true if both Event of the same patient and timing.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameAs(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getPersonId().equals(getPersonId())
                && otherEvent.getPersonName().equals(getPersonName())
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
                && otherEvent.getPersonName().equals(getPersonName())
                && otherEvent.getEventTiming().equals(getEventTiming())
                && otherEvent.getStatus().equals(status);
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
                .append(" Name: ")
                .append(getPersonName())
                .append(" Timing: ")
                .append(getEventTiming())
                .append(" status: ")
                .append(getStatus());
        return builder.toString();
    }
}
