package seedu.algobase.model.plan;

import java.util.List;
import java.util.function.Predicate;

import seedu.algobase.commons.util.StringUtil;


/**
 * Tests that a {@code Plan}'s {@code PlanName} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Plan> {
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Plan plan) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(plan.getPlanName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
