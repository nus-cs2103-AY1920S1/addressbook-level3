/*
@@author shihaoyap
 */

package seedu.address.model.event;

import java.time.LocalDate;
import java.util.ArrayList;
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
        LocalDate start = event.getStartDate().getDate();
        LocalDate end = event.getEndDate().getDate();
        List<LocalDate> eventDates = new ArrayList<>();
        while (!start.isAfter(end)) {
            eventDates.add(start);
            start = start.plusDays(1);
        }
        LocalDate startRange = startDate.getDate();
        LocalDate endRange = endDate.getDate();
        List<LocalDate> dateRange = new ArrayList<>();
        while (!startRange.isAfter(endRange)) {
            dateRange.add(startRange);
            startRange = startRange.plusDays(1);
        }
        Collection<LocalDate> set = new HashSet<>(dateRange);
        boolean result = false;
        for (LocalDate date: eventDates) {
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

