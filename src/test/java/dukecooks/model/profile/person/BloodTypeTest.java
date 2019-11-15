package dukecooks.model.profile.person;

import static dukecooks.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class BloodTypeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BloodType(null));
    }

    @Test
    public void constructor_invalidBloodType_throwsIllegalArgumentException() {
        String invalidBloodType = "";
        assertThrows(IllegalArgumentException.class, () -> new BloodType(invalidBloodType));
    }

    @Test
    void isValidBloodType() {
        // null blood type
        assertThrows(NullPointerException.class, () -> BloodType.isValidBloodType(null));

        // invalid blood types
        assertFalse(BloodType.isValidBloodType("")); // empty string
        assertFalse(BloodType.isValidBloodType(" ")); // spaces only
        assertFalse(BloodType.isValidBloodType("123")); // digits
        assertFalse(BloodType.isValidBloodType("aa")); // more than 1 alphabet
        assertFalse(BloodType.isValidBloodType("1011p041")); // alphabets within digits
        assertFalse(BloodType.isValidBloodType("aaaa aaaa")); // long string

        // invalid blood type
        assertFalse(BloodType.isValidBloodType("a")); // alphabets only
        assertFalse(BloodType.isValidBloodType("a -")); // spaces within alphabets and +/-

        // valid blood type
        assertTrue(BloodType.isValidBloodType("a-")); // small letters
        assertTrue(BloodType.isValidBloodType("A-")); // proper
    }

    @Test
    void testBloodTypeToString() {
        BloodType bloodType = new BloodType("a+");
        String expected = "A+";
        assertEquals(bloodType.toString(), expected);
    }
}
