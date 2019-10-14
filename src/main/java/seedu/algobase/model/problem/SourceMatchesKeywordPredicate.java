package seedu.algobase.model.problem;

import java.util.function.Predicate;

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
    private final String keyword;

    public SourceMatchesKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }
    private SourceMatchesKeywordPredicate() {
        this.keyword = null;
    }

    @Override
    public boolean test(Problem problem) {
        return problem.getSource().equals(new Source(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SourceMatchesKeywordPredicate // instanceof handles nulls
                && keyword.equals(((SourceMatchesKeywordPredicate) other).keyword)); // state check
    }
}
