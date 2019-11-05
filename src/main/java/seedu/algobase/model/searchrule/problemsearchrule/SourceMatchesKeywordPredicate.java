package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.algobase.model.problem.Problem;
import seedu.algobase.model.problem.Source;

/**
 * Tests that a {@code Problem}'s {@code Source} matches the given keyword exactly.
 */
public class SourceMatchesKeywordPredicate implements Predicate<Problem> {

    public static final SourceMatchesKeywordPredicate DEFAULT_SOURCE_PREDICATE =
        new SourceMatchesKeywordPredicate() {
            @Override
            public boolean test(Problem problem) {
                return true;
            }
        };
    private final Keyword keyword;

    public SourceMatchesKeywordPredicate(Keyword keyword) {
        requireNonNull(keyword);
        this.keyword = keyword;
    }

    private SourceMatchesKeywordPredicate() {
        this.keyword = null;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    @Override
    public boolean test(Problem problem) {
        if (!Source.isValidSource(keyword.toString())) {
            return false;
        }
        return problem.getSource().equals(new Source(keyword.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SourceMatchesKeywordPredicate // instanceof handles nulls
                && keyword.equals(((SourceMatchesKeywordPredicate) other).keyword)); // state check
    }
}
