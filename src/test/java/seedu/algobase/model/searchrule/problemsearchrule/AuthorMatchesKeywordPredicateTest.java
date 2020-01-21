//@@author le0tan
package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

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
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE);
        assertNotEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_TWO);
        assertNotEquals(5, VALID_PREDICATE_ONE);
        assertNotEquals(null, VALID_PREDICATE_ONE);
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE_COPY);
    }

}
