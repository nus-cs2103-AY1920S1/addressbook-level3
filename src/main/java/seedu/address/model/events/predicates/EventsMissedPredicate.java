//@@author SakuraBlossom
package seedu.address.model.events.predicates;

import java.time.LocalDateTime;
import java.util.function.Predicate;

import seedu.address.model.events.Event;

/**
 * Tests whether an {@code Event} was missed.
 */
public class EventsMissedPredicate implements Predicate<Event> {
    private final LocalDateTime currentTime;

    public EventsMissedPredicate() {
        this.currentTime = LocalDateTime.now();
    }

    @Override
    public boolean test(Event event) {
        return event.getStatus().isMissed()
            || (!event.getStatus().isSettled()
                && event.getEventTiming().getEndTime().getTime().isBefore(currentTime));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof EventsMissedPredicate; // instanceof handles nulls
    }

    @Override
    public String toString() {
        return String.format("Displaying missed appointment(s) which has yet to be settled");
    }
}
