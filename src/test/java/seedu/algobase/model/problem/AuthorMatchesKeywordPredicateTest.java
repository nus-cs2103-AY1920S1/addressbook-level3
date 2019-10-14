package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import org.junit.jupiter.api.Test;

class AuthorMatchesKeywordPredicateTest {
    @Test
    public void test_authorMatches_returnTrue() {
        AuthorMatchesKeywordPredicate predicate = new AuthorMatchesKeywordPredicate(QUICK_SORT.getAuthor().value);
        assertTrue(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_authorDoesNotMatch_returnFalse() {
        AuthorMatchesKeywordPredicate predicate =
            new AuthorMatchesKeywordPredicate(QUICK_SORT.getAuthor().value.toUpperCase());
        assertFalse(predicate.test(QUICK_SORT));
    }
}
