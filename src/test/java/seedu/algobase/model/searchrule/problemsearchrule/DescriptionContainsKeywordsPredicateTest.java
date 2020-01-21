//@@author le0tan
package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;

class DescriptionContainsKeywordsPredicateTest {

    private static final DescriptionContainsKeywordsPredicate VALID_PREDICATE_ONE =
        new DescriptionContainsKeywordsPredicate(
            Collections.singletonList(new Keyword("This is a valid description")));
    private static final DescriptionContainsKeywordsPredicate VALID_PREDICATE_ONE_COPY =
        new DescriptionContainsKeywordsPredicate(
            Collections.singletonList(new Keyword("This is a valid description")));
    private static final DescriptionContainsKeywordsPredicate VALID_PREDICATE_TWO =
        new DescriptionContainsKeywordsPredicate(Collections.singletonList(
            new Keyword("This is a valid description, but different from last one.")));

    @Test
    void equals() {
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE);
        assertNotEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_TWO);
        assertNotEquals(5, VALID_PREDICATE_ONE);
        assertNotEquals(null, VALID_PREDICATE_ONE);
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE_COPY);
    }

}
