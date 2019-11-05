package seedu.address.model.events.predicates;

import java.util.Date;
import java.util.function.Predicate;

import seedu.address.model.events.Event;

/**
 * Tests whether an {@code Event}'s {@code Timing} is in conflict with the given event's timing.
 */
public class EventsInConflictPredicate implements Predicate<Event> {
    private final Date currentTime;

    public EventsInConflictPredicate() {
        this.currentTime = new Date();
    }

    @Override
    public boolean test(Event event) {
        return false;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof EventsInConflictPredicate; // instanceof handles nulls
    }
}
