package io.xpire.model.item;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

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
        assertFalse(ExpiryDate.isValidFormatExpiryDate("2/06/2"));

    }

    @Test
    public void isValidRangeExpiryDate() {

        // date after Y2S1 (will fail after 11/2/2020)
        assertTrue(ExpiryDate.isValidLowerRangeExpiryDate("11/2/2020"));
        assertTrue(ExpiryDate.isValidUpperRangeExpiryDate("11/2/2020"));

        // date before current date
        assertFalse(ExpiryDate.isValidLowerRangeExpiryDate("24/06/1999"));

        // date after 200 years
        assertFalse(ExpiryDate.isValidUpperRangeExpiryDate("24/06/4000"));

    }

    @Test
    public void getStatus_success() {
        LocalDate dateStub = LocalDate.of(2019, 8, 18);

        // XpireItem is not expired
        ExpiryDate validExpiryDate = new ExpiryDate("1/2/2020");
        assertEquals(validExpiryDate.getStatus(dateStub), "167 days left");

        // XpireItem has expired
        ExpiryDate pastDate = new ExpiryDate("15/7/2019");
        assertEquals(pastDate.getStatus(dateStub), "Expired!");
    }

}
