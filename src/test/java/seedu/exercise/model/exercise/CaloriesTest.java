package seedu.exercise.model.exercise;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.exercise.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CaloriesTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Calories(null));
    }

    @Test
    public void constructor_invalidCalories_throwsIllegalArgumentException() {
        String invalidCalories = "";
        assertThrows(IllegalArgumentException.class, () -> new Calories(invalidCalories));
    }

    @Test
    public void isValidCalories() {
        // null Calories
        assertThrows(NullPointerException.class, () -> Calories.isValidCalories(null));

        // blank Calories
        assertFalse(Calories.isValidCalories("")); // empty string
        assertFalse(Calories.isValidCalories(" ")); // spaces only

        // invalid parts
        assertFalse(Calories.isValidCalories("32.?")); // contains punctuation
        assertFalse(Calories.isValidCalories("a332")); // contains alphabet

        // valid Calories
        assertTrue(Calories.isValidCalories("342"));
        assertTrue(Calories.isValidCalories("3223"));
        assertTrue(Calories.isValidCalories("0"));
        assertTrue(Calories.isValidCalories("32323"));

    }
}
