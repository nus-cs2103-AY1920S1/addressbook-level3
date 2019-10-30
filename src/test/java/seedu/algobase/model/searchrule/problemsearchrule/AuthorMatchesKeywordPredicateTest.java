package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class AuthorMatchesKeywordPredicateTest {

    private static final AuthorMatchesKeywordPredicate VALID_PREDICATE_ONE =
        new AuthorMatchesKeywordPredicate(new Keyword("Steven Halim"));
    private static final AuthorMatchesKeywordPredicate VALID_PREDICATE_ONE_COPY =
        new AuthorMatchesKeywordPredicate(new Keyword("Steven Halim"));
    private static final AuthorMatchesKeywordPredicate VALID_PREDICATE_TWO =
        new AuthorMatchesKeywordPredicate(new Keyword("Tan Sun Teck"));

    @Test
    void equals() {
        assertTrue(VALID_PREDICATE_ONE.equals(VALID_PREDICATE_ONE));
        assertFalse(VALID_PREDICATE_ONE.equals(VALID_PREDICATE_TWO));
        assertFalse(VALID_PREDICATE_ONE.equals(5));
        assertFalse(VALID_PREDICATE_ONE.equals(null));
        assertTrue(VALID_PREDICATE_ONE.equals(VALID_PREDICATE_ONE_COPY));
    }

}
