package dukecooks.model.workout.exercise.detail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.details.Repetitions;
import dukecooks.testutil.Assert;


public class RepetitionsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Repetitions(null));
    }

    @Test
    public void constructor_invalidRepetitions_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Repetitions(Integer.valueOf(invalidTagName)));
    }

    @Test
    public void isValidExerciseName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Repetitions.isValidExerciseDetail(null));

        // invalid Repetitions numbers
        assertFalse(Repetitions.isValidExerciseDetail("")); // empty string
        assertFalse(Repetitions.isValidExerciseDetail(" ")); // spaces only

        // valid Repetition numbers
        assertTrue(Repetitions.isValidExerciseDetail("911")); // exactly 3 numbers
        assertTrue(Repetitions.isValidExerciseDetail("93121534"));
        assertTrue(Repetitions.isValidExerciseDetail("124293842033123")); // long calories numbers
    }

    @Test
    public void testToString() {
        String expected = "[Number of repetitions: 5]";
        String error = "Number of repetitions: 5";
        Repetitions repetitions = new Repetitions(5);
        assertEquals(repetitions.toString(), expected);
        assertNotEquals(repetitions.toString(), error);
    }
}
