package seedu.address.model.distinctdate;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import seedu.address.model.event.Event;

/**
 * Represents a DistinctDate Object in the DistinctDateList
 * Guarantees: details are present and not null, field values are validated.
 */
public class DistinctDate {

    // Identity fields
    private List<Event> listOfEvents;
    private LocalDate date;

    /**
     * Every field must be present and not null.
     */
    public DistinctDate(LocalDate date, List<Event> events) {
        this.date = date;
        listOfEvents = events;
    }

    public DistinctDate() {
        this.date = null;
        this.listOfEvents = null;
    }

    public List<Event> getListOfEvents() {
        return listOfEvents;
    }

    public LocalDate getDate() {
        return date;
    }

    /**
     * Returns true if both employees of the same employeeName have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two employees.
     */
    public boolean isSameDate(DistinctDate otherDistinctDate) {
        if (otherDistinctDate == this) {
            return true;
        }

        return otherDistinctDate != null
                && otherDistinctDate.getDate().equals(getDate());
    }

    /**
     * Returns true if both employees have the same identity and data fields.
     * This defines a stronger notion of equality between two employees.
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
