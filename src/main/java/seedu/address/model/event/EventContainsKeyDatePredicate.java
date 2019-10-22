package seedu.address.model.event;

import java.time.LocalDate;
import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class EventContainsKeyDatePredicate implements Predicate<Event> {
    private final LocalDate date;

    public EventContainsKeyDatePredicate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean test(Event event) {
        return (event.getStartDate().date.isBefore(date) || event.getStartDate().date.equals(date))
                && (event.getEndDate().date.isAfter(date) || event.getEndDate().date.equals(date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }

}

