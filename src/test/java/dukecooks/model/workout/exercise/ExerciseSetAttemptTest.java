package dukecooks.model.workout.exercise;

import static dukecooks.testutil.exercise.TypicalExercises.SET_ATTEMPT_WITH_DISTANCE_AND_TIMING;
import static dukecooks.testutil.exercise.TypicalExercises.SET_ATTEMPT_WITH_WEIGHT_AND_REPETITIONS;
import static dukecooks.testutil.exercise.TypicalExercises.SET_ATTEMPT_WITH_WEIGHT_AND_REPETITIONS_2;
import static dukecooks.testutil.exercise.TypicalExercises.SET_ATTEMPT_WITH_WEIGHT_AND_TIMING;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExerciseSetAttemptTest {

    @Test
    public void equals() {
        ExerciseSetAttempt attempt1 = SET_ATTEMPT_WITH_WEIGHT_AND_REPETITIONS;
        ExerciseSetAttempt attempt2 = SET_ATTEMPT_WITH_DISTANCE_AND_TIMING;
        ExerciseSetAttempt attempt3 = SET_ATTEMPT_WITH_WEIGHT_AND_REPETITIONS_2;
        ExerciseSetAttempt attempt4 = SET_ATTEMPT_WITH_WEIGHT_AND_TIMING;

        // same values -> returns true
        assertTrue(attempt1.equals(attempt1));

        // same object -> returns true
        assertTrue(attempt1.equals(attempt1.clone()));

        // null -> returns false
        assertFalse(attempt1.equals(null));

        // different type -> returns false
        assertFalse(attempt1.equals(5));

        // different person -> returns false
        assertFalse(attempt1.equals(attempt2));

        // different weight value -> returns false
        assertFalse(attempt1.equals(attempt3));

        // same weight value, different second value type -> returns false
        assertFalse(attempt1.equals(attempt4));
    }
}
