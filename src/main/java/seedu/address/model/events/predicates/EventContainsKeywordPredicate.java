//@@author SakuraBlossom
package seedu.address.model.events.predicates;

import java.util.function.Predicate;

import seedu.address.commons.exceptions.ForceThreadInterruptException;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.events.Event;

/**
 * Tests that a {@code Event}'s {@code ReferenceId} matches the given {@code ReferenceId}.
 */
public class EventContainsKeywordPredicate implements Predicate<Event> {
    private final String keyword;

    public EventContainsKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Event event) {
        if (Thread.currentThread().interrupted()) {
            throw new ForceThreadInterruptException();
        }
        return event.getStatus().isApproved()
                && event.getPersonId().toString().contains(keyword);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EventContainsKeywordPredicate // instanceof handles nulls
                && StringUtil.containsIgnoreCase(keyword, ((EventContainsKeywordPredicate) other).keyword));
    }
}
