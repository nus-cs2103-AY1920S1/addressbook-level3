package seedu.algobase.model.problem;

import java.util.function.Predicate;

/**
 * Tests that a {@code Problem}'s {@code Author} matches the given keyword exactly.
 */
public class AuthorMatchesKeywordPredicate implements Predicate<Problem> {
    private final String keyword;

    public AuthorMatchesKeywordPredicate(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean test(Problem problem) {
        return problem.getAuthor().equals(new Author(keyword));
    }
}
