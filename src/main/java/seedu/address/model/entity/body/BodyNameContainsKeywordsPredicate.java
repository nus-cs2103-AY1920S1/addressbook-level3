package seedu.address.model.entity.body;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Entity}'s {@code Name} matches any of the keywords given.
 */
public class BodyNameContainsKeywordsPredicate implements Predicate<Body> {
    private final List<String> keywords;

    public BodyNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Body body) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(body.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BodyNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((BodyNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
