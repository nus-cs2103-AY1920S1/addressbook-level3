package seedu.algobase.model.problem;

import java.util.List;
import java.util.function.Predicate;

import seedu.algobase.commons.util.StringUtil;

/**
 * Tests that a {@code Problem}'s {@code Name} matches any of the keywords given.
 */
public class NameContainsKeywordsPredicate implements Predicate<Problem> {
    public static final NameContainsKeywordsPredicate DEFAULT_NAME_PREDICATE =
        new NameContainsKeywordsPredicate() {
            @Override
            public boolean test(Problem problem) {
                return true;
            }
        };
    private final List<String> keywords;

    public NameContainsKeywordsPredicate(List<String> keywords) {
        this.keywords = keywords;
    }

    private NameContainsKeywordsPredicate() {
        this.keywords = null;
    }

    @Override
    public boolean test(Problem problem) {
        return keywords.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(problem.getName().fullName, keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NameContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((NameContainsKeywordsPredicate) other).keywords)); // state check
    }

}
