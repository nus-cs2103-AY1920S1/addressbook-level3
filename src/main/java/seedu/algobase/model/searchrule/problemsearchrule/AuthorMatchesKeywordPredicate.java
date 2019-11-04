package seedu.algobase.model.searchrule.problemsearchrule;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.algobase.model.problem.Author;
import seedu.algobase.model.problem.Problem;

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
    private final Keyword keyword;

    public AuthorMatchesKeywordPredicate(Keyword keyword) {
        requireNonNull(keyword);
        this.keyword = keyword;
    }

    private AuthorMatchesKeywordPredicate() {
        this.keyword = null;
    }

    public Keyword getKeyword() {
        return keyword;
    }

    @Override
    public boolean test(Problem problem) {
        if (!Author.isValidAuthor(keyword.toString())) {
            return false;
        }
        return problem.getAuthor().equals(new Author(keyword.toString()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AuthorMatchesKeywordPredicate // instanceof handles nulls
                && keyword.equals(((AuthorMatchesKeywordPredicate) other).keyword)); // state check
    }
}
