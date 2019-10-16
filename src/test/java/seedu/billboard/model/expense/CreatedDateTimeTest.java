package seedu.billboard.model.expense;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.billboard.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CreatedDateTimeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CreatedDateTime((String) null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate = "june";
        assertThrows(IllegalArgumentException.class, () -> new CreatedDateTime(invalidDate));
    }

    @Test
    public void isValidDate() {
        // null date
        assertThrows(NullPointerException.class, () -> CreatedDateTime.isValidDate(null));

        // invalid date
        assertFalse(CreatedDateTime.isValidDate("")); // empty string
        assertFalse(CreatedDateTime.isValidDate(" ")); // spaces only
        assertFalse(CreatedDateTime.isValidDate("2359 23/12/2019")); // wrong format
        assertFalse(CreatedDateTime.isValidDate("32/1/2019 1234")); // correct format, non existent date
        assertFalse(CreatedDateTime.isValidDate("31/1/2019 2401")); // correct format, non existent time
        assertFalse(CreatedDateTime.isValidDate("07/01/2019 12:34")); // slightly wrong format

        // valid date
        assertTrue(CreatedDateTime.isValidDate("23/12/2019 1234")); // normal date with time
        assertTrue(CreatedDateTime.isValidDate("23/12/2019")); // normal date no time
        assertTrue(CreatedDateTime.isValidDate("1/1/2019")); // date without leading zeros
        assertTrue(CreatedDateTime.isValidDate("01/01/2019")); // date with leading zeroes
    }
}
