package seedu.algobase.model.problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.TypicalProblems.QUICK_SORT;

import org.junit.jupiter.api.Test;

class DifficultyIsInRangePredicateTest {
    @Test
    public void test_difficultyInRange_returnTrue() {
        DifficultyIsInRangePredicate predicate =
            new DifficultyIsInRangePredicate(QUICK_SORT.getDifficulty().value, QUICK_SORT.getDifficulty().value);
        assertTrue(predicate.test(QUICK_SORT));
    }

    @Test
    public void test_difficultyOutOfRange_returnFalse() {
        DifficultyIsInRangePredicate predicate =
                new DifficultyIsInRangePredicate(QUICK_SORT.getDifficulty().value * 2,
                    QUICK_SORT.getDifficulty().value * 3);
        assertFalse(predicate.test(QUICK_SORT));
    }
}
