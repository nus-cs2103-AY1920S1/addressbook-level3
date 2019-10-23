package seedu.address.model.datetime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.model.datetime.DateTimeTest.INVALID_INPUT;
import static seedu.address.model.datetime.DateTimeTest.VALID_INPUT_1300;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Date;

import org.junit.jupiter.api.Test;

public class StartDateTimeTest {

    public static final StartDateTime VALID_START_DATE_TIME = new StartDateTime(new Date());

    @Test
    public void constructorString_null_nullPointerException() {
        String nullString = null;
        assertThrows(NullPointerException.class, () -> new DateTime(nullString));
    }

    @Test
    public void constructorDate_null_nullPointerException() {
        Date nullDate = null;
        assertThrows(NullPointerException.class, () -> new DateTime(nullDate));
    }

    @Test
    public void constructor_valid_noException() {
        assertDoesNotThrow(() -> new StartDateTime(VALID_INPUT_1300));
        assertDoesNotThrow(() -> new StartDateTime(new Date()));
    }

    @Test
    public void isValidStartDateTime() {
        assertTrue(() -> StartDateTime.isValidStartDateTime(VALID_INPUT_1300));
        assertTrue(() -> StartDateTime.isValidStartDateTime(VALID_INPUT_1300));
        assertFalse(() -> StartDateTime.isValidStartDateTime(INVALID_INPUT));
    }
}
