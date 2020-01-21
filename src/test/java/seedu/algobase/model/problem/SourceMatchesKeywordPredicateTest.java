//@@author le0tan
package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import org.junit.jupiter.api.Test;

import seedu.algobase.model.searchrule.problemsearchrule.Keyword;
import seedu.algobase.model.searchrule.problemsearchrule.SourceMatchesKeywordPredicate;

class SourceMatchesKeywordPredicateTest {

    @Test
    public void test_sourcerMatches_returnTrue() {
        SourceMatchesKeywordPredicate predicate = new SourceMatchesKeywordPredicate(
            new Keyword(QUICK_SORT.getSource().value));
        assertTrue(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_sourceDoesNotMatch_returnFalse() {
        SourceMatchesKeywordPredicate predicate =
                new SourceMatchesKeywordPredicate(new Keyword(QUICK_SORT.getSource().value.toUpperCase()));
        assertFalse(predicate.test(QUICK_SORT));
    }

}
