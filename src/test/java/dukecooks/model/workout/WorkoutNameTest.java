package dukecooks.model.workout;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.Assert;

public class WorkoutNameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new WorkoutName(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new WorkoutName(invalidName));
    }

    @Test
    public void isValidName() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> WorkoutName.isValidName(null));

        // invalid name
        assertFalse(WorkoutName.isValidName("")); // empty string
        assertFalse(WorkoutName.isValidName(" ")); // spaces only
        assertFalse(WorkoutName.isValidName("^")); // only non-alphanumeric characters
        assertFalse(WorkoutName.isValidName("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(WorkoutName.isValidName("peter jack")); // alphabets only
        assertTrue(WorkoutName.isValidName("12345")); // numbers only
        assertTrue(WorkoutName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(WorkoutName.isValidName("Capital Tan")); // with capital letters
        assertTrue(WorkoutName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void testWorkoutNameToString() {
        WorkoutName workoutName = new WorkoutName("A");
        String expected = "A";
        assertEquals(workoutName.toString(), expected);
    }
}

