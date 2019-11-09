//@@author SakuraBlossom
package seedu.address.model.events.predicates;

import java.util.function.Predicate;

import seedu.address.model.ReferenceId;
import seedu.address.model.events.Event;

/**
 * Tests that a {@code Event}'s {@code ReferenceId} matches the given {@code ReferenceId}.
 */
public class EventContainsKeywordOrRecentlyAcknowledgedPredicate implements Predicate<Event> {
    private final ReferenceId referenceId;
    private final Event acknowledgedEvent;

    public EventContainsKeywordOrRecentlyAcknowledgedPredicate(ReferenceId referenceId, Event acknowledgedEvent) {
        this.referenceId = referenceId;
        this.acknowledgedEvent = acknowledgedEvent;
    }

    @Override
    public boolean test(Event event) {
        return referenceId.equals(event.getPersonId())
                && (event.getStatus().isApproved()
                || event.equals(acknowledgedEvent));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof EventContainsKeywordOrRecentlyAcknowledgedPredicate)) {
            return false;
        }

        EventContainsKeywordOrRecentlyAcknowledgedPredicate o =
                (EventContainsKeywordOrRecentlyAcknowledgedPredicate) other;
        return referenceId.equals(o.referenceId) && acknowledgedEvent.equals(o.acknowledgedEvent);
    }

    @Override
    public String toString() {
        return String.format("Suggesting event(s) which involves '%1$s':\n%2$s",
                referenceId.toString(), acknowledgedEvent.getPersonName().toString());
    }
}
