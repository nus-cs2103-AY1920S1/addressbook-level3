package dukecooks.model.workout.history;

import static dukecooks.testutil.exercise.ExerciseBuilder.DEFAULT_EXERCISE_RUN_1;
import static dukecooks.testutil.exercise.ExerciseBuilder.DEFAULT_EXERCISE_RUN_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExerciseRunTest {

    @Test
    public void getTotalTimeTakenStringTest() {
        String expected = "1:00:00";
        assertEquals(DEFAULT_EXERCISE_RUN_1.getTotalTimeTakenString(), expected);
    }

    @Test
    public void equals() {

        // same object -> returns true
        assertTrue(DEFAULT_EXERCISE_RUN_1.equals(DEFAULT_EXERCISE_RUN_1));

        // same value -> returns true
        assertTrue(DEFAULT_EXERCISE_RUN_1.equals(DEFAULT_EXERCISE_RUN_1.clone()));

        // null -> returns false
        assertFalse(DEFAULT_EXERCISE_RUN_1.equals(null));

        // different type -> returns false
        assertFalse(DEFAULT_EXERCISE_RUN_1.equals(false));

        // different values -> returns false
        assertFalse(DEFAULT_EXERCISE_RUN_1.equals(DEFAULT_EXERCISE_RUN_2));
    }
}
