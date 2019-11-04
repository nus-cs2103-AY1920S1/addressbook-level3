package seedu.address.model.event;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s {@code Date} matches the given date in dd/MM/yyyy format.
 */
public class EventContainsKeyDateRangePredicate implements Predicate<Event> {
    private final LocalDate startDate;
    private final LocalDate endDate;

    public EventContainsKeyDateRangePredicate(LocalDate start, LocalDate end) {
        this.startDate = start;
        this.endDate = end;
    }

    public String getStartDate() {
        return startDate.toString();
    }

    public String getEndDate() {
        return endDate.toString();
    }

    /**
     * Checks if a date is currently within the range of the Event's start and end date.
     *
     * @param event Event object that is referenced
     * @return Boolean
     */
    @Override
    public boolean test(Event event) {
        LocalDate start = event.getStartDate().getDate();
        LocalDate end = event.getEndDate().getDate();
        List<LocalDate> eventDates = new ArrayList<>();
        while (!start.isAfter(end)) {
            eventDates.add(start);
            start = start.plusDays(1);
        }
        LocalDate startRange = startDate;
        LocalDate endRange = endDate;
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
                && startDate.equals(((EventContainsKeyDateRangePredicate) other).getStartDate()))
                && endDate.equals(((EventContainsKeyDateRangePredicate) other).getEndDate());
    }
}

