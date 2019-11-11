package seedu.moolah.model.general;

import java.util.List;
import java.util.function.Predicate;

import seedu.moolah.commons.util.StringUtil;
import seedu.moolah.model.expense.Expense;

/**
 * Tests that a {@code Expense}'s {@code Description} matches any of the keywords given.
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Expense> {
    private final List<String> keywords;

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Expense expense) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil
                        .containsWordIgnoreCase(expense.getDescription().fullDescription, keyword));
    }

    public List<String> getKeywords() {
        return keywords;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }

}
