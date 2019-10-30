package dukecooks.model.recipe;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static dukecooks.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import dukecooks.model.recipe.components.Calories;

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
        // null calories number
        assertThrows(NullPointerException.class, () -> Calories.isValidCalories(null));

        // invalid calories numbers
        assertFalse(Calories.isValidCalories("")); // empty string
        assertFalse(Calories.isValidCalories(" ")); // spaces only
        assertFalse(Calories.isValidCalories("calories")); // non-numeric
        assertFalse(Calories.isValidCalories("9011p041")); // alphabets within digits
        assertFalse(Calories.isValidCalories("9312 1534")); // spaces within digits

        // valid calories numbers
        assertTrue(Calories.isValidCalories("911")); // exactly 3 numbers
        assertTrue(Calories.isValidCalories("93121534"));
        assertTrue(Calories.isValidCalories("124293842033123")); // long calories numbers
    }
}
