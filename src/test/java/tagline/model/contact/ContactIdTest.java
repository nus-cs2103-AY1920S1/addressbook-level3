package tagline.model.contact;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static tagline.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import tagline.model.contact.exceptions.InvalidIdException;

public class ContactIdTest {
    @Test
    public void constructor_nonIntegerString_throwsNumberFormatException() {
        assertThrows(NumberFormatException.class, () -> new ContactId("a"));
        assertThrows(NumberFormatException.class, () -> new ContactId("1a"));
    }

    @Test
    public void constructor_invalidId_throwsInvalidIdException() {
        int tooBigId = (int) Math.pow(10, ContactId.getDigit());
        assertThrows(InvalidIdException.class, () -> new ContactId(tooBigId));

        int negativeId = -1;
        assertThrows(InvalidIdException.class, () -> new ContactId(negativeId));
    }

    @Test
    public void isValidId() {
        // invalid id
        assertFalse(ContactId.isValidId("")); // empty string
        assertFalse(ContactId.isValidId("a")); // non-integer string
        assertFalse(ContactId.isValidId("1a1")); // non-integer string

        // valid id
        assertTrue(ContactId.isValidId("1")); // integer string
        assertTrue(ContactId.isValidId("0000000000000001")); // integer string with leading zeros
    }
}
