package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class SourceMatchesKeywordPredicateTest {

    private static final SourceMatchesKeywordPredicate VALID_PREDICATE_ONE =
        new SourceMatchesKeywordPredicate(new Keyword("Steven Halim"));
    private static final SourceMatchesKeywordPredicate VALID_PREDICATE_ONE_COPY =
        new SourceMatchesKeywordPredicate(new Keyword("Steven Halim"));
    private static final SourceMatchesKeywordPredicate VALID_PREDICATE_TWO =
        new SourceMatchesKeywordPredicate(new Keyword("Tan Sun Teck"));

    @Test
    void equals() {
        assertTrue(VALID_PREDICATE_ONE.equals(VALID_PREDICATE_ONE));
        assertFalse(VALID_PREDICATE_ONE.equals(VALID_PREDICATE_TWO));
        assertFalse(VALID_PREDICATE_ONE.equals(5));
        assertFalse(VALID_PREDICATE_ONE.equals(null));
        assertTrue(VALID_PREDICATE_ONE.equals(VALID_PREDICATE_ONE_COPY));
    }

}
