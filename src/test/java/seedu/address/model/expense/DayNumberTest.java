package seedu.address.model.expense;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

public class DayNumberTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DayNumber(null));
    }

    @Test
    public void constructor_invalidDayNumber_throwsIllegalArgumentException() {
        String invalidDayNumber = "";
        assertThrows(IllegalArgumentException.class, () -> new DayNumber(invalidDayNumber));
    }

    @Test
    public void isValidDayNumber() {
        // null day number
        assertThrows(NullPointerException.class, () -> DayNumber.isValidDayNumber(null));

        // invalid day numbers
        assertFalse(DayNumber.isValidDayNumber("")); // empty string
        assertFalse(DayNumber.isValidDayNumber(" ")); // spaces only
        assertFalse(DayNumber.isValidDayNumber("dayNumber")); // non-numeric
        assertFalse(DayNumber.isValidDayNumber("9011p041")); // alphabets within digits
        assertFalse(DayNumber.isValidDayNumber("9312 1534")); // spaces within digits

        // valid day numbers
        assertTrue(DayNumber.isValidDayNumber("1")); // 1 digit
        assertTrue(DayNumber.isValidDayNumber("99"));
        assertTrue(DayNumber.isValidDayNumber("124293842033123")); // long day numbers
    }
}
