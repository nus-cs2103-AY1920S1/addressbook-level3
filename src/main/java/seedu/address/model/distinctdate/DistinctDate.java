package seedu.address.model.distinctdate;

import java.util.List;
import java.util.Objects;

import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;

/**
 * Represents a DistinctDate Object, which will be stored in the DistinctDateList
 * Guarantees: details are present and not null, field values are validated.
 * Each DistinctDate Object contains a list of events and a EventDate which it represents.
 */
public class DistinctDate {

    // Identity fields
    private List<Event> listOfEvents;
    private EventDate date;

    /**
     * Every field must be present and not null.
     */
    public DistinctDate(EventDate date, List<Event> events) {
        this.date = date;
        listOfEvents = events;
    }

    /**
     * returns the list of events which is on the specific date
     *
     * @return a list of events which corresponds to the specific date
     */
    public List<Event> getListOfEvents() {
        return listOfEvents;
    }

    /**
     * returns the EventDate of the DistinctDate object
     */
    public EventDate getDate() {
        return date;
    }

    /**
     * Returns true if both DistinctDate object refer to the same object, or share the same EventDate.
     * This defines a weaker notion of equality between two DistinctDate.
     */
    public boolean isSameDate(DistinctDate otherDistinctDate) {
        if (otherDistinctDate == this) {
            return true;
        }

        return otherDistinctDate != null
                && otherDistinctDate.getDate().equals(getDate());
    }

    /**
     * Returns true if both DistinctDate have the same identity and data fields.
     * This defines a stronger notion of equality between two DistinctDate.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DistinctDate)) {
            return false;
        }

        DistinctDate otherDistinctDate = (DistinctDate) other;
        return otherDistinctDate.getDate().equals(getDate())
                && otherDistinctDate.getListOfEvents().equals(getListOfEvents());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(date, listOfEvents);
    }
}
