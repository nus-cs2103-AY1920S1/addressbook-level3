package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class DescriptionContainsKeywordsPredicateTest {

    private static final DescriptionContainsKeywordsPredicate VALID_PREDICATE_ONE =
        new DescriptionContainsKeywordsPredicate(Arrays.asList(new Keyword("Steven Halim")));
    private static final DescriptionContainsKeywordsPredicate VALID_PREDICATE_ONE_COPY =
        new DescriptionContainsKeywordsPredicate(Arrays.asList(new Keyword("Steven Halim")));
    private static final DescriptionContainsKeywordsPredicate VALID_PREDICATE_TWO =
        new DescriptionContainsKeywordsPredicate(Arrays.asList(new Keyword("Tan Sun Teck")));

    @Test
    void equals() {
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE);
        assertNotEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_TWO);
        assertNotEquals(5, VALID_PREDICATE_ONE);
        assertNotEquals(null, VALID_PREDICATE_ONE);
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE_COPY);
    }

}
