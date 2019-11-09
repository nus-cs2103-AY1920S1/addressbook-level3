package dukecooks.model.workout.exercise.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.Assert;

public class ExerciseNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new ExerciseName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new ExerciseName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> ExerciseName.isValidName(null));

        // invalid name
        assertFalse(ExerciseName.isValidName("")); // empty string
        assertFalse(ExerciseName.isValidName(" ")); // spaces only
        assertFalse(ExerciseName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(ExerciseName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(ExerciseName.isValidName("peter jack")); // alphabets only
        assertTrue(ExerciseName.isValidName("12345")); // numbers only
        assertTrue(ExerciseName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(ExerciseName.isValidName("Capital Tan")); // with capital letters
        assertTrue(ExerciseName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void testExerciseNameToString() {
        ExerciseName exerciseName = new ExerciseName("A");
        String expected = "A";
        assertEquals(exerciseName.toString(), expected);
    }
}
