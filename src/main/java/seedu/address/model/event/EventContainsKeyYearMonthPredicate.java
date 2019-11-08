/*
@@author shihaoyap
 */

package seedu.address.model.event;

import java.time.YearMonth;
import java.util.function.Predicate;

/**
 * Tests that a {@code Event}'s {@code Date} matches the given Key Year Month in MM/yyyy format.
 */
public class EventContainsKeyYearMonthPredicate implements Predicate<Event> {
    private final YearMonth yearMonth;

    public EventContainsKeyYearMonthPredicate(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    public String getYearMonth() {
        return yearMonth.toString();
    }

    /**
     * Checks if a YearMonth is currently within the range of the Event's start.
     *
     * @param event Event object that is referenced
     * @return Boolean result of whether it is within the Year Month
     */
    @Override
    public boolean test(Event event) {
        YearMonth start = YearMonth.of(event.getStartDate().getDate().getYear(),
                event.getStartDate().getDate().getMonth());
        YearMonth end = YearMonth.of(event.getEndDate().getDate().getYear(),
                event.getEndDate().getDate().getMonth());
        return yearMonth.equals(start) || yearMonth.equals(end)
                || yearMonth.isAfter(start) && yearMonth.isBefore(end);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventContainsKeyYearMonthPredicate
                && yearMonth.equals(((EventContainsKeyYearMonthPredicate) other).yearMonth));
    }

}
