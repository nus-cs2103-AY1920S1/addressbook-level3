package seedu.address.model.event;

import java.time.YearMonth;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class EventContainsKeyYearMonthPredicate implements Predicate<Event> {
    private final YearMonth yearMonth;

    public EventContainsKeyYearMonthPredicate(YearMonth yearMonth) {
        this.yearMonth = yearMonth;
    }

    /**
     * Checks if a date is currently within the range of the Event's start and end date.
     *
     * @param event Event object that is referenced
     * @return Boolean
     */
    @Override
    public boolean test(Event event) {
        return YearMonth.from(event.getStartDate().getDate()).equals(yearMonth);
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }

}

