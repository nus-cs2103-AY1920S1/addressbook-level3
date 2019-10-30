package seedu.algobase.model.searchrule.problemsearchrule;

import static org.junit.jupiter.api.Assertions.assertFalse;
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
        assertTrue(NAME_SEQUENCES.equals(NAME_SEQUENCES_DUPLICATE));

        // same object -> returns true
        assertTrue(ALL_PREDICATE.equals(ALL_PREDICATE));

        // null -> returns false
        assertFalse(ALL_PREDICATE.equals(null));

        // different types -> returns false
        assertFalse(ALL_PREDICATE.equals(5));

        // different objects -> returns false
        assertFalse(ALL_PREDICATE.equals(NAME_SEQUENCES));

        // same name but different predicates -> returns false
        assertFalse(NAME_SEQUENCES.equals(NAME_SEQUENCES_SAME_NAME_DIFFERENT_PREDICATE));

        // same content -> returns true
        assertTrue(NAME_SEQUENCES.equals(NAME_SEQUENCES_DUPLICATE));
    }

}
