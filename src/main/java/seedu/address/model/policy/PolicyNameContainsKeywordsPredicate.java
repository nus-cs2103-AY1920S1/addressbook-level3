package seedu.address.model.policy;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.StringUtil;

/**
 * Tests that a {@code Policy}'s {@code Name} matches any of the keywords given.
 */
public class PolicyNameContainsKeywordsPredicate implements Predicate<Policy> {
    private final List<String> keywords;

    public PolicyNameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Policy policy) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(policy.getName().toString(), keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PolicyNameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PolicyNameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
