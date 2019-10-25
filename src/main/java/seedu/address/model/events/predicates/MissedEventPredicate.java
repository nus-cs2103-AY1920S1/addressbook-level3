package seedu.address.model.events.predicates;

import java.util.Date;
import java.util.function.Predicate;

import seedu.address.model.events.Event;

/**
 * Tests that a {@code Event}'s {@code ReferenceId} matches the given {@code ReferenceId}.
 */
public class MissedEventPredicate implements Predicate<Event> {
    private final Date currentTime;

    public MissedEventPredicate() {
        this.currentTime = new Date();
    }

    @Override
    public boolean test(Event event) {
        return event.getStatus().isMissed()
            || (!event.getStatus().isSettled()
                && event.getEventTiming().getEndTime().getTime().before(currentTime));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof MissedEventPredicate; // instanceof handles nulls
    }
}
