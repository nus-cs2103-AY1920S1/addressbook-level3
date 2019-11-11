package seedu.guilttrip.model.entry;

import java.util.List;
import java.util.function.Predicate;

import seedu.guilttrip.commons.util.StringUtil;

/**
 * Tests that a {@code Budget}'s {@code Description} matches any of the keywords given.
 */
public class BudgetDescriptionContainsKeywordsPredicate implements Predicate<Budget> {
    private final List<String> keywords;

    public BudgetDescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Budget entry) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(entry.getDesc().fullDesc, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetDescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((BudgetDescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
