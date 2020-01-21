//@@author le0tan
package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collections;

import org.junit.jupiter.api.Test;

class NameContainsKeywordsPredicateTest {

    private static final NameContainsKeywordsPredicate VALID_PREDICATE_ONE =
        new NameContainsKeywordsPredicate(Collections.singletonList(new Keyword("Quick sort")));
    private static final NameContainsKeywordsPredicate VALID_PREDICATE_ONE_COPY =
        new NameContainsKeywordsPredicate(Collections.singletonList(new Keyword("Quick sort")));
    private static final NameContainsKeywordsPredicate VALID_PREDICATE_TWO =
        new NameContainsKeywordsPredicate(Collections.singletonList(new Keyword("Slow sort")));

    @Test
    void equals() {
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE);
        assertNotEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_TWO);
        assertNotEquals(5, VALID_PREDICATE_ONE);
        assertNotEquals(null, VALID_PREDICATE_ONE);
        assertEquals(VALID_PREDICATE_ONE, VALID_PREDICATE_ONE_COPY);
    }

}
