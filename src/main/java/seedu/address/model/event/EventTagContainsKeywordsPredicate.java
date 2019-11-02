package seedu.address.model.event;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Tests that a {@code Event}'s {@code EventTag} matches any of the keywords given.
 */
public class EventTagContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public EventTagContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        List<String> eventTag = event.getTags().stream().map(Objects::toString)
                .map(x -> x.substring(1, x.length() - 1)).collect(Collectors.toList());
        return eventTag.containsAll(keywords);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EventTagContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((EventTagContainsKeywordsPredicate) other).keywords)); // state check
    }

}
