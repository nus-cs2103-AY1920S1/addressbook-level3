package dukecooks.model.recipe;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dukecooks.model.recipe.components.Fats;

public class FatsTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Fats(null));
    }

    @Test
    public void constructor_invalidFats_throwsIllegalArgumentException() {
        String invalidFats = "";
        assertThrows(IllegalArgumentException.class, () -> new Fats(invalidFats));
    }

    @Test
    public void isValidFats() {
        // null fats number
        assertThrows(NullPointerException.class, () -> Fats.isValidFats(null));

        // invalid fats numbers
        assertFalse(Fats.isValidFats("")); // empty string
        assertFalse(Fats.isValidFats(" ")); // spaces only
        assertFalse(Fats.isValidFats("fats")); // non-numeric
        assertFalse(Fats.isValidFats("9011p041")); // alphabets within digits
        assertFalse(Fats.isValidFats("9312 1534")); // spaces within digits

        // valid fats numbers
        assertTrue(Fats.isValidFats("911")); // exactly 3 numbers
        assertTrue(Fats.isValidFats("93121534"));
        assertTrue(Fats.isValidFats("124293842033123")); // long fats numbers
    }

    @Test
    public void testFatsToString() {
        Fats fats = new Fats("0");
        String expected = "0";
        assertEquals(fats.toString(), expected);
    }
}
