//@@author le0tan
package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import org.junit.jupiter.api.Test;

import seedu.algobase.model.searchrule.problemsearchrule.AuthorMatchesKeywordPredicate;
import seedu.algobase.model.searchrule.problemsearchrule.Keyword;

class AuthorMatchesKeywordPredicateTest {

    @Test
    public void test_authorMatches_returnTrue() {
        AuthorMatchesKeywordPredicate predicate = new AuthorMatchesKeywordPredicate(
            new Keyword(QUICK_SORT.getAuthor().value));
        assertTrue(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_authorDoesNotMatch_returnFalse() {
        AuthorMatchesKeywordPredicate predicate =
            new AuthorMatchesKeywordPredicate(new Keyword(QUICK_SORT.getAuthor().value.toUpperCase()));
        assertFalse(predicate.test(QUICK_SORT));
    }

}
