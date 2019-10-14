package seedu.address.model.person;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Person}'s {@code Name} matches any of the keywords given.
 */
public class WishDescriptionContainsKeywordsPredicate implements Predicate<Wish> {
    private final List<String> keywords;

    public WishDescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Wish entry) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(entry.getDesc().fullDesc, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof WishDescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((WishDescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
