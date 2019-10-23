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

    /**
     * Checks if a date is currently within the range of the Event's start and end date.
     *
     * @param event Event object that is referenced
     * @return Boolean
     */
    @Override
    public boolean test(Event event) {
        return (event.getStartDate().getDate().isBefore(date) || event.getStartDate().getDate().equals(date))
                && (event.getEndDate().getDate().isAfter(date) || event.getEndDate().getDate().equals(date));
    }

    @Override
    public boolean equals(Object other) {
        return other == this; // short circuit if same object
    }

}

