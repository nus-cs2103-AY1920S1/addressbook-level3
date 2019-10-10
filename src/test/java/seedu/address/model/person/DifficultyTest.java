package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DifficultyTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Difficulty(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Difficulty(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Difficulty.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Difficulty.isValidPhone("")); // empty string
        assertFalse(Difficulty.isValidPhone(" ")); // spaces only
        assertFalse(Difficulty.isValidPhone("91")); // less than 3 numbers
        assertFalse(Difficulty.isValidPhone("phone")); // non-numeric
        assertFalse(Difficulty.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Difficulty.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Difficulty.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Difficulty.isValidPhone("93121534"));
        assertTrue(Difficulty.isValidPhone("124293842033123")); // long phone numbers
    }
}
