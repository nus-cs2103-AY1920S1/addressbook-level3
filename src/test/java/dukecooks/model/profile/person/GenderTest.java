package dukecooks.model.profile.person;

import static dukecooks.testutil.Assert.assertThrows;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GenderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Gender(null));
    }

    @Test
    public void constructor_invalidGender_throwsIllegalArgumentException() {
        String invalidGender = "";
        assertThrows(IllegalArgumentException.class, () -> new Gender(invalidGender));
    }

    @Test
    void isValidGender() {
        // invalid gender
        assertFalse(Gender.isValidGender("")); // empty string
        assertFalse(Gender.isValidGender(" ")); // spaces only
        assertFalse(Gender.isValidGender("^")); // only non-alphanumeric characters
        assertFalse(Gender.isValidGender("female*")); // contains non-alphanumeric characters

        // valid gender
        assertTrue(Gender.isValidGender("female")); // alphabets only
        assertTrue(Gender.isValidGender("FEMALE")); // in caps
        assertTrue(Gender.isValidGender("fEmAle")); // both small and caps
    }

    @Test
    void testToString() {
        Gender gender = new Gender("FEMALE");
        String expected = "female";
        assertEquals(gender.toString(), expected);
    }
}
