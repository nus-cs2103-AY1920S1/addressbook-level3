package dukecooks.model.workout.history;

import static dukecooks.testutil.exercise.ExerciseBuilder.DEFAULT_EXERCISE_HISTORY;
import static dukecooks.testutil.exercise.ExerciseBuilder.DEFAULT_EXERCISE_RUN_1;
import static dukecooks.testutil.exercise.ExerciseBuilder.DEFAULT_EXERCISE_RUN_2;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.Assert;

public class ExerciseHistoryTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExerciseHistory(null));
    }

    @Test
    public void addRunTest() {

        // null -> throws Null Pointer Exception
        Assert.assertThrows(NullPointerException.class, () -> DEFAULT_EXERCISE_HISTORY.addRun(null));

        // Valid ExerciseRun -> add success
        ExerciseHistory expectedHistory = new ExerciseHistory(new ArrayList<>(Arrays
                .asList(DEFAULT_EXERCISE_RUN_1, DEFAULT_EXERCISE_RUN_2)));
        ExerciseHistory addedHistory = DEFAULT_EXERCISE_HISTORY.addRun(DEFAULT_EXERCISE_RUN_2);
        assertEquals(addedHistory, expectedHistory);
    }

    @Test
    public void getAverageRunTimeStringTest() {
        String expected = "1:00:00";
        assertEquals(DEFAULT_EXERCISE_RUN_1.getTotalTimeTakenString(), expected);
    }

    @Test
    public void equals() {

        // same object -> true
        assertTrue(DEFAULT_EXERCISE_HISTORY.equals(DEFAULT_EXERCISE_HISTORY));

        // same value -> true
        assertTrue(DEFAULT_EXERCISE_HISTORY.equals(DEFAULT_EXERCISE_HISTORY.clone()));

        // null -> false
        assertFalse(DEFAULT_EXERCISE_HISTORY.equals(null));

        // different type -> false
        assertFalse(DEFAULT_EXERCISE_HISTORY.equals(5));

        // different Values -> false
        ExerciseHistory newHistory = new ExerciseHistory(new ArrayList<>(Arrays
                .asList(DEFAULT_EXERCISE_RUN_1, DEFAULT_EXERCISE_RUN_2)));
        assertFalse(DEFAULT_EXERCISE_HISTORY.equals(newHistory));
    }

}
