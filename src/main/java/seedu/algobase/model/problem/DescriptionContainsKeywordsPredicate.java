package seedu.algobase.model.problem;

import java.util.List;
import java.util.function.Predicate;

import seedu.algobase.commons.util.StringUtil;

/**
 * Tests that a {@code Problem}'s {@code Description} includes all of the given keywords (ignoring cases).
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Problem> {
    private final List<String> keywords;

    public DescriptionContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Problem problem) {
        return keywords.stream()
                .allMatch(keyword -> StringUtil.containsWordIgnoreCase(problem.getDescription().value, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
