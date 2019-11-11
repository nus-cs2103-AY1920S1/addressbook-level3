package seedu.tarence.model.student;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NusnetIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new NusnetId(null));
    }

    @Test
    public void constructor_invalidNusnetId_throwsIllegalArgumentException() {
        String invalidNusnetId = "";
        assertThrows(IllegalArgumentException.class, () -> new NusnetId(invalidNusnetId));
    }

    @Test
    public void isValidNusnetId() {
        // null MatricNum
        assertThrows(NullPointerException.class, () -> NusnetId.isValidNusnetId(null));

        // blank MatricNum
        assertFalse(NusnetId.isValidNusnetId("")); // empty string
        assertFalse(NusnetId.isValidNusnetId(" ")); // spaces only

        // missing parts
        assertFalse(NusnetId.isValidNusnetId("0123456")); // missing starting letter
        assertFalse(NusnetId.isValidNusnetId("e012345")); // missing 7 numbers

        // invalid parts
        assertFalse(NusnetId.isValidNusnetId("b0123456")); // invalid starting letter
        assertFalse(NusnetId.isValidNusnetId("e012_3456")); // underscore in nusnet id
        assertFalse(NusnetId.isValidNusnetId("e0123 456")); // spaces in nusnet id
        assertFalse(NusnetId.isValidNusnetId(" e0123456")); // leading space
        assertFalse(NusnetId.isValidNusnetId("e0123456 ")); // trailing space
        assertFalse(NusnetId.isValidNusnetId("ee0123456")); // double starting letter
        assertFalse(NusnetId.isValidNusnetId(".e0123456")); // nusnet id starts with a period
        assertFalse(NusnetId.isValidNusnetId("e0123456.")); // nusnet id ends with a period
        assertFalse(NusnetId.isValidNusnetId("-e0123456")); // nusnet id starts with a hyphen
        assertFalse(NusnetId.isValidNusnetId("0123456-")); // nusnet id ends with a hyphen

        // valid Matric
        assertTrue(NusnetId.isValidNusnetId("e0123456"));
    }
}
