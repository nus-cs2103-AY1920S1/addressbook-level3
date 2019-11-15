package dukecooks.model.workout.exercise.components;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.testutil.Assert;



public class MuscleTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new MuscleType(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        Assert.assertThrows(IllegalArgumentException.class, () -> new MuscleType(invalidName));
    }

    @Test
    public void isValidMuscleType() {
        // null name
        Assert.assertThrows(NullPointerException.class, () -> MuscleType.isValidMuscleType(null));

        // invalid name
        assertFalse(MuscleType.isValidMuscleType("")); // empty string
        assertFalse(MuscleType.isValidMuscleType(" ")); // spaces only
        assertFalse(MuscleType.isValidMuscleType("^")); // only non-alphanumeric characters
        assertFalse(MuscleType.isValidMuscleType("peter*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(MuscleType.isValidMuscleType("peter jack")); // alphabets only
        assertTrue(MuscleType.isValidMuscleType("12345")); // numbers only
        assertTrue(MuscleType.isValidMuscleType("peter the 2nd")); // alphanumeric characters
        assertTrue(MuscleType.isValidMuscleType("Capital Tan")); // with capital letters
        assertTrue(MuscleType.isValidMuscleType("David Roger Jackson Ray Jr 2nd")); // long names
    }

    @Test
    public void testMuscleTypeToString() {
        MuscleType muscleType = new MuscleType("A");
        String expected = "A";
        String error = "[A]";
        assertEquals(muscleType.toString(), expected);
        assertNotEquals(muscleType.toString(), error);
    }

    @Test
    public void testMuscleTypeToView() {
        MuscleType muscleType = new MuscleType("A");
        String expected = "[A]";
        String error = "A";
        assertEquals(muscleType.toView(), expected);
        assertNotEquals(muscleType.toView(), error);
    }
}
