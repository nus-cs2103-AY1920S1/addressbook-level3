package dukecooks.model.profile.person;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class HeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Height(null));
    }

    @Test
    public void constructor_invalidHeight_throwsIllegalArgumentException() {
        String invalidHeight = "";
        assertThrows(IllegalArgumentException.class, () -> new Height(invalidHeight));
    }

    @Test
    void isValidNumber() {
        // invalid numbers
        assertFalse(Height.isValidNumber("")); // empty string
        assertFalse(Height.isValidNumber(" ")); // spaces only
        assertFalse(Height.isValidNumber("height")); // non-numeric
        assertFalse(Height.isValidNumber("9011p041")); // alphabets within digits
        assertFalse(Height.isValidNumber("9312 1534")); // spaces within digits

        // valid numbers
        assertTrue(Height.isValidNumber("150")); // exactly 3 numbers
        assertTrue(Height.isValidNumber("93121534"));
        assertTrue(Height.isValidNumber("124293842033123")); // long height numbers
    }

    @Test
    void isValidHeight() {
        // invalid height numbers
        assertFalse(Height.isValidHeight(0)); // zero
        assertFalse(Height.isValidHeight(301)); // out of range
        assertFalse(Height.isValidHeight(123934343)); // large value
        assertFalse(Height.isValidHeight(-12121212)); // negative value

        // valid height numbers
        assertTrue(Height.isValidHeight(1)); // the smallest number
        assertTrue(Height.isValidHeight(50));
        assertTrue(Height.isValidHeight(199));
        assertTrue(Height.isValidHeight(300)); // the largest number
    }

    @Test
    void testToString() {
        Height height = new Height("190");
        String expected = "190.0";
        assertEquals(height.toString(), expected);
    }
}
