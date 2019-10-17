package seedu.billboard.model.expense;

import java.util.List;
import java.util.function.Predicate;

import seedu.billboard.commons.util.StringUtil;

/**
 * Tests that a {@code Expense}'s {@code Name} matches any of the keywords given.
 */
public class AllContainsKeywordsPredicate implements Predicate<Expense> {
    public static final String FINDTYPE = "all";
    private final List<String> keywords;

    public AllContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Expense expense) {
        return keywords.stream()
                .anyMatch(keyword ->
                        StringUtil.containsWordIgnoreCase(expense.getName().name, keyword)
                        || StringUtil.containsWordIgnoreCase(expense.getDescription().description, keyword)
                );
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AllContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((AllContainsKeywordsPredicate) other).keywords)); // state check
    }

}
