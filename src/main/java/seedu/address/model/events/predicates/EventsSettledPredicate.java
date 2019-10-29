package seedu.address.model.events.predicates;

import java.util.Date;
import java.util.function.Predicate;

import seedu.address.model.events.Event;

/**
 * Tests whether an {@code Event} has been settled.
 */
public class EventsSettledPredicate implements Predicate<Event> {
    private final Date currentTime;

    public EventsSettledPredicate() {
        this.currentTime = new Date();
    }

    @Override
    public boolean test(Event event) {
        return event.getStatus().isSettled();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof EventsSettledPredicate; // instanceof handles nulls
    }
}
