/*
@@author shihaoyap
 */

package seedu.address.model.event;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
        EventDate startOfEvent = event.getStartDate();
        EventDate endOfEvent = event.getEndDate();
        List<EventDate> eventDates = startOfEvent.getListOfDatesUntil(endOfEvent);
        List<EventDate> specifiedDateRange = startDate.getListOfDatesUntil(endDate);
        Collection<EventDate> set = new HashSet<>(specifiedDateRange);
        boolean result = false;
        for (EventDate date: eventDates) {
            result |= set.contains(date);
        }
        return result;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeyDateRangePredicate
                && startDate.equals(((EventContainsKeyDateRangePredicate) other).startDate))
                && endDate.equals(((EventContainsKeyDateRangePredicate) other).endDate);
    }
}

