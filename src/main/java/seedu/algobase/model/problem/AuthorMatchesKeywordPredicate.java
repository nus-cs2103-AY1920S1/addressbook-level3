package seedu.algobase.model.problem;

import java.util.function.Predicate;

/**
 * Tests that a {@code Problem}'s {@code Author} matches the given keyword exactly.
 */
public class AuthorMatchesKeywordPredicate implements Predicate<Problem> {
    public static final AuthorMatchesKeywordPredicate DEFAULT_AUTHOR_PREDICATE =
        new AuthorMatchesKeywordPredicate() {
            @Override
            public boolean test(Problem problem) {
                return true;
            }
        };
    private final String keyword;

    public AuthorMatchesKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }
    private AuthorMatchesKeywordPredicate() {
        this.keyword = null;
    }

    @Override
    public boolean test(Problem problem) {
        return problem.getAuthor().equals(new Author(keyword));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AuthorMatchesKeywordPredicate // instanceof handles nulls
                && keyword.equals(((AuthorMatchesKeywordPredicate) other).keyword)); // state check
    }
}
