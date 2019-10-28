package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class TagIncludesKeywordsPredicateTest {
    private static final TagIncludesKeywordsPredicate VALID_PREDICATE_ONE =
        new TagIncludesKeywordsPredicate(Arrays.asList(new Keyword("Steven Halim")));
    private static final TagIncludesKeywordsPredicate VALID_PREDICATE_ONE_COPY =
        new TagIncludesKeywordsPredicate(Arrays.asList(new Keyword("Steven Halim")));
    private static final TagIncludesKeywordsPredicate VALID_PREDICATE_TWO =
        new TagIncludesKeywordsPredicate(Arrays.asList(new Keyword("Tan Sun Teck")));

    @Test
    void equals() {
        assertTrue(VALID_PREDICATE_ONE.equals(VALID_PREDICATE_ONE));
        assertFalse(VALID_PREDICATE_ONE.equals(VALID_PREDICATE_TWO));
        assertFalse(VALID_PREDICATE_ONE.equals(5));
        assertFalse(VALID_PREDICATE_ONE.equals(null));
        assertTrue(VALID_PREDICATE_ONE.equals(VALID_PREDICATE_ONE_COPY));
    }
}
