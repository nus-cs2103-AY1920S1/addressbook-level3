package seedu.address.model.entity.body;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

//@@author ambervoong-unused
class BodyIdentificationNumberTest {

    @Test
    public void constructorBodyId_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new BodyIdentificationNumber(null));
    }

    @Test
    public void constructorBodyId_emptyString_throwsIllegalArgumentException() {
        String invalidId = "";

        assertThrows(IllegalArgumentException.class, () -> new BodyIdentificationNumber(invalidId));
    }

    @Test
    public void constructorBodyIdentificationNumber_invalidBodyId_throwsIllegalArgumentException() {
        String invalidId = "C00000000";

        assertThrows(IllegalArgumentException.class, () -> new BodyIdentificationNumber(invalidId));
    }

    @Test
    void getIdentificationNumber_true() {
        BodyIdentificationNumber testId = new BodyIdentificationNumber("B00000002");
        assertEquals("B00000002", testId.getIdentificationNumber());
    }

    @Test
    void testEquals_self_true() {
        BodyIdentificationNumber testId = new BodyIdentificationNumber("B00000002");
        BodyIdentificationNumber otherId = new BodyIdentificationNumber("B00000002");
        assertEquals(testId, testId);
        assertEquals(testId, otherId);
        assertEquals(testId.hashCode(), otherId.hashCode());
    }

    @Test
    void testEquals_differentAndNull_notEqual() {
        BodyIdentificationNumber testId = new BodyIdentificationNumber("B00000002");
        assertNotEquals(testId, new BodyIdentificationNumber("B00000111"));
        assertNotEquals(testId, null);
    }

    @Test
    void isValidIdentificationNumber() {
        // Null BodyIdentificationNumber
        assertThrows(NullPointerException.class, () -> BodyIdentificationNumber.isValidIdentificationNumber(null));

        // Invalid BodyIdentificationNumbers
        assertFalse(BodyIdentificationNumber.isValidIdentificationNumber("")); // empty string
        assertFalse(BodyIdentificationNumber.isValidIdentificationNumber(" ")); // spaces only
        assertFalse(BodyIdentificationNumber.isValidIdentificationNumber("B1234")); // Not enough digits
        assertFalse(BodyIdentificationNumber.isValidIdentificationNumber("B1234bp12")); // Alphabets in number.
        assertFalse(BodyIdentificationNumber.isValidIdentificationNumber("A12345678")); // Invalid starting digit
        assertFalse(BodyIdentificationNumber.isValidIdentificationNumber("@#!@4a&*(^")); // Not digit

        // Valid BodyIdentificationNumbers
        assertTrue(BodyIdentificationNumber.isValidIdentificationNumber("B00000002"));
        assertTrue(BodyIdentificationNumber.isValidIdentificationNumber("B00000072"));
        assertTrue(BodyIdentificationNumber.isValidIdentificationNumber("B97590072"));

    }
}
