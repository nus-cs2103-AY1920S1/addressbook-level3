package seedu.eatme.model.eatery;

import java.util.List;
import java.util.function.Predicate;

import seedu.eatme.commons.util.StringUtil;

/**
 * Tests that a {@code Eatery}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Eatery> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Eatery eatery) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(eatery.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }
}
