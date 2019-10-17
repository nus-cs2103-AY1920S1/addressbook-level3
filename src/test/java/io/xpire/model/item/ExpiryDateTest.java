package io.xpire.model.item;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ExpiryDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ExpiryDate(null));
    }

    @Test
    public void isValidFormatExpiryDate() {

        // valid input date
        assertTrue(ExpiryDate.isValidFormatExpiryDate("24/06/1999"));

        // valid input date with non-padded month
        assertTrue(ExpiryDate.isValidFormatExpiryDate("24/6/1999"));

        // valid input date with non-padded date
        assertTrue(ExpiryDate.isValidFormatExpiryDate("2/06/1999"));

        // invalid date
        assertFalse(ExpiryDate.isValidFormatExpiryDate("99/99/2"));

    }

    @Test
    public void isValidRangeExpiryDate() {

    }

}
