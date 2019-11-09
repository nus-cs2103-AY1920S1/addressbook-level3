package dukecooks.model.workout.exercise.detail;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.workout.exercise.details.Sets;
import dukecooks.testutil.Assert;


public class SetsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Sets(null));
    }

    @Test
    public void constructor_invalidSets_throwsIllegalArgumentException() {
        String invalidTagName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new Sets(Integer.valueOf(invalidTagName)));
    }

    @Test
    public void isValidExerciseName() {
        // null tag name
        Assert.assertThrows(NullPointerException.class, () -> Sets.isValidExerciseDetail(null));

        // invalid Sets numbers
        assertFalse(Sets.isValidExerciseDetail("")); // empty string
        assertFalse(Sets.isValidExerciseDetail(" ")); // spaces only

        // valid Repetition numbers
        assertTrue(Sets.isValidExerciseDetail("911")); // exactly 3 numbers
        assertTrue(Sets.isValidExerciseDetail("93121534"));
        assertTrue(Sets.isValidExerciseDetail("124293842033123")); // long calories numbers
    }

    @Test
    public void testToString() {
        String expected = "[Number of sets: 5]";
        String error = "Number of sets: 5";
        Sets sets = new Sets(5);
        assertEquals(sets.toString(), expected);
        assertNotEquals(sets.toString(), error);
    }
}
