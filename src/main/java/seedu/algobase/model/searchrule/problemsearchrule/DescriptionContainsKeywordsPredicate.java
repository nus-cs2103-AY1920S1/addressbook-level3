package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;
import static seedu.algobase.commons.util.AppUtil.isTwoListsEqual;

import java.util.List;
import java.util.function.Predicate;

import seedu.algobase.commons.util.StringUtil;
import seedu.algobase.model.problem.Problem;

/**
 * Tests that a {@code Problem}'s {@code Description} includes all of the given keywords (ignoring cases).
 */
public class DescriptionContainsKeywordsPredicate implements Predicate<Problem> {
    public static final DescriptionContainsKeywordsPredicate DEFAULT_DESCRIPTION_PREDICATE =
        new DescriptionContainsKeywordsPredicate() {
            @Override
            public boolean test(Problem problem) {
                return true;
            }
        };
    private final List<Keyword> keywords;

    public DescriptionContainsKeywordsPredicate(List<Keyword> keywords) {
        requireNonNull(keywords);
        this.keywords = keywords;
    }

    private DescriptionContainsKeywordsPredicate() {
        this.keywords = null;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    @Override
    public boolean test(Problem problem) {
        requireNonNull(problem);
        return keywords.stream()
                .allMatch(keyword ->
                    StringUtil.containsWordIgnoreCase(problem.getDescription().value, keyword.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DescriptionContainsKeywordsPredicate // instanceof handles nulls
                && isTwoListsEqual(keywords, ((DescriptionContainsKeywordsPredicate) other).keywords)); // state check
    }
}
