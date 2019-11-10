//@@author SakuraBlossom
package seedu.address.model.events.predicates;

import java.util.function.Predicate;

import seedu.address.model.ReferenceId;
import seedu.address.model.events.Event;


/**
 * Tests that a {@code Event}'s {@code ReferenceId} matches the given {@code ReferenceId}.
 */
public class EventContainsKeywordAndAcknowledgedPredicate implements Predicate<Event> {
    private final ReferenceId referenceId;

    public EventContainsKeywordAndAcknowledgedPredicate(ReferenceId referenceId) {
        this.referenceId = referenceId;
    }

    @Override
    public boolean test(Event event) {
        return referenceId.equals(event.getPersonId())
                && (event.getStatus().isApproved() || event.getStatus().isAcked());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventContainsKeywordAndAcknowledgedPredicate)) {
            return false;
        }

        EventContainsKeywordAndAcknowledgedPredicate o =
                (EventContainsKeywordAndAcknowledgedPredicate) other;
        return referenceId.equals(o.referenceId);
    }

    @Override
    public String toString() {
        return String.format("Suggesting event(s) which involves '%1$s':\n%2$s",
                referenceId.toString());
    }
}
