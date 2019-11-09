//@@author le0tan
package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.TypicalProblemSearchRules.ALL_PREDICATE;
import static seedu.algobase.testutil.TypicalProblemSearchRules.MEDIUM_DIFFICULTY;
import static seedu.algobase.testutil.TypicalProblemSearchRules.NAME_SEQUENCES;
import static seedu.algobase.testutil.TypicalProblemSearchRules.NAME_SEQUENCES_DUPLICATE;
import static seedu.algobase.testutil.TypicalProblemSearchRules.NAME_SEQUENCES_SAME_NAME_DIFFERENT_PREDICATE;

import org.junit.jupiter.api.Test;

class ProblemSearchRuleTest {

    @Test
    void isSameProblemSearchRule() {
        // same object -> returns true
        assertTrue(ALL_PREDICATE.isSameProblemSearchRule(ALL_PREDICATE));

        // null -> returns false
        assertFalse(ALL_PREDICATE.isSameProblemSearchRule(null));

        // different names -> returns true
        assertFalse(MEDIUM_DIFFICULTY.isSameProblemSearchRule(NAME_SEQUENCES));

        // different names -> returns true
        assertFalse(NAME_SEQUENCES.isSameProblemSearchRule(MEDIUM_DIFFICULTY));

        // same names but different content -> returns true
        assertTrue(NAME_SEQUENCES.isSameProblemSearchRule(NAME_SEQUENCES_SAME_NAME_DIFFERENT_PREDICATE));
    }

    @Test
    void testEquals() {
        // same values -> returns true
        assertEquals(NAME_SEQUENCES, NAME_SEQUENCES_DUPLICATE);
        // same object -> returns true
        assertEquals(ALL_PREDICATE, ALL_PREDICATE);
        // null -> returns false
        assertNotEquals(null, ALL_PREDICATE);
        // different types -> returns false
        assertNotEquals(5, ALL_PREDICATE);
        // different objects -> returns false
        assertNotEquals(ALL_PREDICATE, NAME_SEQUENCES);
        // same name but different predicates -> returns false
        assertNotEquals(NAME_SEQUENCES, NAME_SEQUENCES_SAME_NAME_DIFFERENT_PREDICATE);
        // same content -> returns true
        assertEquals(NAME_SEQUENCES, NAME_SEQUENCES_DUPLICATE);
    }

}
