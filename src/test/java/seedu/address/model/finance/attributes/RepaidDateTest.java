package seedu.address.model.finance.attributes;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RepaidDateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TransactionDate(null));
    }

    @Test
    public void constructor_invalidRepaidDate_throwsIllegalArgumentException() {
        String invalidRepaidDate = "";
        assertThrows(IllegalArgumentException.class, () ->
                new RepaidDate(invalidRepaidDate, new TransactionDate().toString()));
    }

    @Test
    public void isValidRepaidDate() {
        // null repaid date
        assertThrows(NullPointerException.class, () -> RepaidDate.isValidRepaidDate(null, "01-01-2001"));

        // invalid repaid dates
        assertFalse(RepaidDate.isValidRepaidDate("", "18-09-2019")); // empty string
        assertFalse(RepaidDate.isValidRepaidDate(" ", "05-06-2018")); // spaces only
        assertFalse(RepaidDate.isValidRepaidDate("18/07/2019", "12-12-2018")); // backslashes
        assertFalse(RepaidDate.isValidRepaidDate("aa-bb-cccc", "11-11-2011")); // alphabets
        assertFalse(RepaidDate.isValidRepaidDate("31-02-2019", "23-01-2019")); // month without 31 days
        assertFalse(RepaidDate.isValidRepaidDate("15-10-2019", "30-10-2019")); // before transaction date


        // valid transaction dates
        // single digit, after transaction date
        assertTrue(RepaidDate.isValidRepaidDate("2-2-2019", "10-10-2018"));
        // double digit, same day as transaction date
        assertTrue(RepaidDate.isValidRepaidDate("20-08-2018", "20-08-2018"));
        // double digit, after as transaction date
        assertTrue(RepaidDate.isValidRepaidDate("20-08-2018", "03-03-2003"));
    }
}
