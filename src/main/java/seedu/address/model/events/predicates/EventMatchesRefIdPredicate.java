//@@author SakuraBlossom
package seedu.address.model.events.predicates;

import java.util.function.Predicate;

import seedu.address.model.ReferenceId;
import seedu.address.model.events.Event;

/**
 * Tests that a {@code Event}'s {@code ReferenceId} matches the given {@code ReferenceId}.
 */
public class EventMatchesRefIdPredicate implements Predicate<Event> {
    private final ReferenceId referenceId;

    public EventMatchesRefIdPredicate(ReferenceId referenceId) {
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
            || (other instanceof EventMatchesRefIdPredicate // instanceof handles nulls
            && referenceId.equals(((EventMatchesRefIdPredicate) other).referenceId)); // state check
    }

    @Override
    public String toString() {
        return String.format(
                "Displaying event(s) which involves a person with a ref id of '%1$s'",
                referenceId.toString());
    }
}
