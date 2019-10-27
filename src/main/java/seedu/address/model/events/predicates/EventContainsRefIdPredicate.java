//@@author SakuraBlossom
package seedu.address.model.events.predicates;

import java.util.function.Predicate;

import seedu.address.model.ReferenceId;
import seedu.address.model.events.Event;
import seedu.address.model.person.predicates.ContainsKeywordsPredicate;

/**
 * Tests that a {@code Event}'s {@code ReferenceId} matches the given {@code ReferenceId}.
 */
public class EventContainsRefIdPredicate implements Predicate<Event> {
    private final ReferenceId referenceId;

    public EventContainsRefIdPredicate(ReferenceId referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public boolean test(Event event) {
        return event.getStatus().isApproved()
                && referenceId.equals(event.getPersonId());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EventContainsRefIdPredicate // instanceof handles nulls
            && referenceId.equals(((EventContainsRefIdPredicate) other).referenceId)); // state check
    }
}
