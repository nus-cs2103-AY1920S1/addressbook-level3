/*
@@author shihaoyap
 */

package seedu.address.model.event;

import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s {@code Date} is within the range of the {code Event}'s Start & End Dates.
 */
public class EventContainsKeyDatePredicate implements Predicate<Event> {
    private final EventDate eventDate;

    public EventContainsKeyDatePredicate(EventDate date) {
        this.eventDate = date;
    }

    public String getDate() {
        return eventDate.toString();
    }

    @Override
    public boolean test(Event event) {
        return (event.getStartDate().isBefore(eventDate) || event.getStartDate().equals(eventDate))
                && (event.getEndDate().isAfter(eventDate) || event.getEndDate().equals(eventDate));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeyDatePredicate
                && eventDate.equals(((EventContainsKeyDatePredicate) other).eventDate));
    }
}
