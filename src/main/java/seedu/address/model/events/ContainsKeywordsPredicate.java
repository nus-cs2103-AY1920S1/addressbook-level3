package seedu.address.model.events;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;


/**
 * Tests that a {@code Person}'s {@code ReferenceId}, {@code Name} or {@code Phone} matches any of the keywords given.
 */
public class ContainsKeywordsPredicate implements Predicate<Event> {
    private final List<String> keywords;

    public ContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Event event) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(event.getPersonId().toString(), keyword)
                                || (keyword.length() >= 3
                                && StringUtil.containsIgnoreCase(event.getPersonId().toString(), keyword))
                                || (keyword.length() >= 4
                                && StringUtil.containsIgnoreCase(event.getPersonId().toString(), keyword)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ContainsKeywordsPredicate) other).keywords)); // state check
    }
}
