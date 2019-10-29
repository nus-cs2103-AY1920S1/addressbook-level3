//@@author woon17
package seedu.address.model.events.predicates;

import java.util.function.Predicate;

import seedu.address.model.events.Event;
/**
 * Tests that a {@code Event}'s {@code ReferenceId} matches the given {@code ReferenceId}.
 */
public class EventContainsApprovedStatusPredicate implements Predicate<Event> {
    public EventContainsApprovedStatusPredicate() {
    }

    @Override
    public boolean test(Event event) {
        return event.getStatus().isApproved();
    }
}
