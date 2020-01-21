//@@author le0tan
package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class SourceMatchesKeywordPredicateTest {

    private static final SourceMatchesKeywordPredicate VALID_PREDICATE_ONE =
        new SourceMatchesKeywordPredicate(new Keyword("Kattis"));
    private static final SourceMatchesKeywordPredicate VALID_PREDICATE_ONE_COPY =
        new SourceMatchesKeywordPredicate(new Keyword("Kattis"));
    private static final SourceMatchesKeywordPredicate VALID_PREDICATE_TWO =
        new SourceMatchesKeywordPredicate(new Keyword("Onlinejudge.org"));

    @Test
    void equals() {
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE);
        assertNotEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_TWO);
        assertNotEquals(5, VALID_PREDICATE_ONE);
        assertNotEquals(null, VALID_PREDICATE_ONE);
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE_COPY);
    }

}
