/*
@@author shihaoyap
 */

package seedu.address.model.event;

import java.util.function.Predicate;

/**
 * Tests that an Event start and end dates is is within a date range
 * {@code Event}'s {@code Date} matches the given date in dd/MM/yyyy format.
 */
public class EventContainsKeyDateRangePredicate implements Predicate<Event> {
    private final EventDate startDate;
    private final EventDate endDate;

    public EventContainsKeyDateRangePredicate(EventDate start, EventDate end) {
        this.startDate = start;
        this.endDate = end;
    }

    @Override
    public boolean test(Event event) {
        EventDate eventStartDate = event.getStartDate();
        EventDate eventEndDate = event.getEndDate();
        boolean eventStartWithinRange = eventStartDate.isAfter(startDate) || eventStartDate.equals(startDate);
        boolean eventEndWithinRange = eventEndDate.isBefore(endDate) || eventEndDate.equals(endDate);

        return eventStartWithinRange && eventEndWithinRange;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeyDateRangePredicate
                && startDate.equals(((EventContainsKeyDateRangePredicate) other).startDate))
                && endDate.equals(((EventContainsKeyDateRangePredicate) other).endDate);
    }
}

