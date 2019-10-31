package seedu.sugarmummy.model.bio;

import java.util.List;
import java.util.function.Predicate;

import seedu.sugarmummy.commons.util.StringUtil;

/**
 * Tests that a {@code User}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<User> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(User user) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(user.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
