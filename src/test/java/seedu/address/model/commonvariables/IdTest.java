package seedu.address.model.commonvariables;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

class IdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Id(null));
    }

    @Test
    public void constructor_invalidPhone_throwsIllegalArgumentException() {
        String invalidId = "";
        assertThrows(IllegalArgumentException.class, () -> new Id(invalidId));
    }

    @Test
    void isValidId() {
        // null id
        assertThrows(NullPointerException.class, () -> Id.isValidId(null));

        // invalid phone numbers
        assertFalse(Id.isValidId("")); // empty string
        assertFalse(Id.isValidId(" ")); // spaces only
        assertFalse(Id.isValidId("id")); // non-numeric
        assertFalse(Id.isValidId("3p01")); // alphabets within digits
        assertFalse(Id.isValidId("423 113")); // spaces within digits

        // valid phone numbers
        assertTrue(Id.isValidId("1"));
        assertTrue(Id.isValidId("124341234567890"));
    }

    @Test
    void incrementId() {
        assertEquals(Id.getIdCount() + 1 + "", Id.incrementId().value);
    }
}
