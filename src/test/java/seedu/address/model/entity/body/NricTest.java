package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author ambervoong
class NricTest {

    @Test
    public void constructorNric_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Nric(null));
    }

    @Test
    public void constructorNric_emptyString_throwsIllegalArgumentException() {
        String invalidNric = "";

        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    public void constructorNric_invalidNric_throwsIllegalArgumentException() {
        String invalidNric = "S12345678";

        assertThrows(IllegalArgumentException.class, () -> new Nric(invalidNric));
    }

    @Test
    void getNric_true() {
        Nric testNric = new Nric("S1234567A");
        assertEquals("S1234567A", testNric.getNric());
    }

    @Test
    void testEquals_self_true() {
        Nric testNric = new Nric("S1234567A");
        Nric otherNric = new Nric("S1234567A");
        assertEquals(testNric, testNric);
        assertEquals(testNric, otherNric);
        assertEquals(testNric.hashCode(), otherNric.hashCode());
    }

    @Test
    void testEquals_differentAndNull_notEqual() {
        Nric testNric = new Nric("S1234567A");
        assertNotEquals(testNric, new Nric("S7654321A"));
        assertNotEquals(testNric, null);
    }

    @Test
    void isValidNric() {
        // Null Nric
        assertThrows(NullPointerException.class, () -> Nric.isValidNric(null));

        // Invalid Nrics
        assertFalse(Nric.isValidNric("")); // empty string
        assertFalse(Nric.isValidNric(" ")); // spaces only
        assertFalse(Nric.isValidNric("S7643A")); // Not enough digits
        assertFalse(Nric.isValidNric("T94abcd0Z")); // Alphabets in number.
        assertFalse(Nric.isValidNric("A1234567A")); // Invalid starting digit
        assertFalse(Nric.isValidNric("@#!@4a&*(")); // Not digit

        // Valid Nrics
        assertTrue(Nric.isValidNric("F4320125A"));
        assertTrue(Nric.isValidNric("S5201443B"));
    }

    @Test
    void isDigitAndLengthSeven() {
        assertFalse(Nric.isDigitAndLengthSeven("abcdefg"));
        assertFalse(Nric.isDigitAndLengthSeven("123456z"));
        assertFalse(Nric.isDigitAndLengthSeven("@#!$%^&"));
        assertFalse(Nric.isDigitAndLengthSeven("abcdefg"));
        assertFalse(Nric.isDigitAndLengthSeven("aaaaaaaaaa"));
        assertFalse(Nric.isDigitAndLengthSeven("a3aa3a3atbfb1"));
        assertFalse(Nric.isDigitAndLengthSeven(""));

        assertTrue(Nric.isDigitAndLengthSeven("1212125"));
        assertTrue(Nric.isDigitAndLengthSeven("8734028"));
    }

    @Test
    void isValidStartEndAlphabet() {
        assertFalse(Nric.isValidStartEndAlphabet('W', '!'));
        assertFalse(Nric.isValidStartEndAlphabet('S', ')'));
        assertFalse(Nric.isValidStartEndAlphabet('F', '#'));
        assertFalse(Nric.isValidStartEndAlphabet('Z', 'A'));

        assertTrue(Nric.isValidStartEndAlphabet('S', 'A'));
        assertTrue(Nric.isValidStartEndAlphabet('F', 'Z'));
        assertTrue(Nric.isValidStartEndAlphabet('T', 'N'));
        assertTrue(Nric.isValidStartEndAlphabet('G', 'L'));

    }
}
