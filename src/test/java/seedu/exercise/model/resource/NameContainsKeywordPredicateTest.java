package seedu.exercise.model.resource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.exercise.testutil.CommonTestData;
import seedu.exercise.testutil.typicalutil.TypicalExercises;

public class NameContainsKeywordPredicateTest {

    private NameContainsKeywordsPredicate keywordsInExercise;
    private NameContainsKeywordsPredicate keywordsNotInExercise;

    @BeforeEach
    public void setUp() {
        List<String> keywordsIn = new ArrayList<String>();
        keywordsIn.add(CommonTestData.VALID_NAME_BASKETBALL);
        List<String> keywordsNotIn = new ArrayList<String>();
        keywordsNotIn.add(CommonTestData.VALID_NAME_AEROBICS);

        keywordsInExercise = new NameContainsKeywordsPredicate(keywordsIn);
        keywordsNotInExercise = new NameContainsKeywordsPredicate(keywordsNotIn);
    }

    @Test
    public void test_nameInExercise_returnsTrue() {
        assertTrue(keywordsInExercise.test(TypicalExercises.BASKETBALL));
    }

    @Test
    public void test_nameNotInExercise_returnsFalse() {
        assertFalse(keywordsNotInExercise.test(TypicalExercises.BASKETBALL));
    }

    @Test
    public void equals_variousScenarios_success() {
        // null -> false
        assertFalse(keywordsInExercise.equals(null));

        // same obj -> true
        assertTrue(keywordsInExercise.equals(keywordsInExercise));

        // diff obj instance variables -> false
        assertFalse(keywordsInExercise.equals(keywordsNotInExercise));
    }
}
