package dukecooks.model.profile.person;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class WeightTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Weight(null));
    }

    @Test
    public void constructor_invalidWeight_throwsIllegalArgumentException() {
        String invalidWeight = "";
        assertThrows(IllegalArgumentException.class, () -> new Weight(invalidWeight));
    }

    @Test
    void isValidNumber() {
        // invalid numbers
        assertFalse(Weight.isValidNumber("")); // empty string
        assertFalse(Weight.isValidNumber(" ")); // spaces only
        assertFalse(Weight.isValidNumber("weight")); // non-numeric
        assertFalse(Weight.isValidNumber("9011p041")); // alphabets within digits
        assertFalse(Weight.isValidNumber("9312 1534")); // spaces within digits

        // valid numbers
        assertTrue(Weight.isValidNumber("150")); // exactly 3 numbers
        assertTrue(Weight.isValidNumber("93121534"));
        assertTrue(Weight.isValidNumber("124293842033123")); // long weight numbers
    }

    @Test
    void isValidWeight() {
        // invalid weight numbers
        assertFalse(Weight.isValidWeight(0)); // zero
        assertFalse(Weight.isValidWeight(501)); // out of range
        assertFalse(Weight.isValidWeight(123934343)); // large value
        assertFalse(Weight.isValidWeight(-12121212)); // negative value

        // valid weight numbers
        assertTrue(Weight.isValidWeight(1)); // the smallest number
        assertTrue(Weight.isValidWeight(50));
        assertTrue(Weight.isValidWeight(199));
        assertTrue(Weight.isValidWeight(500)); // the largest number
    }

    @Test
    void testToString() {
        Weight weight = new Weight("90");
        String expected = "90.0";
        assertEquals(weight.toString(), expected);
    }
}
