package seedu.algobase.model.Problem;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.algobase.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AuthorTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Author(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidPhone = "";
        assertThrows(IllegalArgumentException.class, () -> new Author(invalidPhone));
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Author.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Author.isValidPhone("")); // empty string
        assertFalse(Author.isValidPhone(" ")); // spaces only
        assertFalse(Author.isValidPhone("91")); // less than 3 numbers
        assertFalse(Author.isValidPhone("phone")); // non-numeric
        assertFalse(Author.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Author.isValidPhone("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(Author.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Author.isValidPhone("93121534"));
        assertTrue(Author.isValidPhone("124293842033123")); // long phone numbers
    }
}
