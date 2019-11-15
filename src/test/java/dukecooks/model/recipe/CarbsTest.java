package dukecooks.model.recipe;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.recipe.components.Carbs;

public class CarbsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Carbs(null));
    }

    @Test
    public void constructor_invalidCarbs_throwsIllegalArgumentException() {
        String invalidCarbs = "";
        assertThrows(IllegalArgumentException.class, () -> new Carbs(invalidCarbs));
    }

    @Test
    public void isValidCarbs() {
        // null carbs number
        assertThrows(NullPointerException.class, () -> Carbs.isValidCarbs(null));

        // invalid carbs numbers
        assertFalse(Carbs.isValidCarbs("")); // empty string
        assertFalse(Carbs.isValidCarbs(" ")); // spaces only
        assertFalse(Carbs.isValidCarbs("carbs")); // non-numeric
        assertFalse(Carbs.isValidCarbs("9011p041")); // alphabets within digits
        assertFalse(Carbs.isValidCarbs("9312 1534")); // spaces within digits

        // valid carbs numbers
        assertTrue(Carbs.isValidCarbs("911")); // exactly 3 numbers
        assertTrue(Carbs.isValidCarbs("93121534"));
        assertTrue(Carbs.isValidCarbs("124293842033123")); // long carbs numbers
    }

    @Test
    public void testCarbsToString() {
        Carbs carbs = new Carbs("0");
        String expected = "0";
        assertEquals(expected, carbs.toString());
    }
}
