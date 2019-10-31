package seedu.ichifund.model.budget;

import java.util.List;
import java.util.function.Predicate;

import seedu.ichifund.commons.util.StringUtil;

/**
 * Tests that a {@code Budget}'s {@code Description} matches any of the keywords given.
 */
public class BudgetDescriptionPredicate implements Predicate<Budget> {
    private final List<String> keywords;

    public BudgetDescriptionPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Budget budget) {
        if (keywords.isEmpty()) {
            return true;
        }

        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(budget.getDescription().description, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BudgetDescriptionPredicate // instanceof handles nulls
                && keywords.equals(((BudgetDescriptionPredicate) other).keywords)); // state check
    }

}
