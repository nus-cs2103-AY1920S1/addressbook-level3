package seedu.ichifund.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.ichifund.storage.JsonAdaptedDate.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.ichifund.testutil.Assert.assertThrows;
import static seedu.ichifund.testutil.TypicalFundBook.TRANSACTION_BUS;

import org.junit.jupiter.api.Test;

import seedu.ichifund.commons.exceptions.IllegalValueException;
import seedu.ichifund.model.date.Date;
import seedu.ichifund.model.date.Day;
import seedu.ichifund.model.date.Month;
import seedu.ichifund.model.date.Year;

public class JsonAdaptedDateTest {
    private static final String INVALID_DAY = "40";
    private static final String INVALID_MONTH = "15";
    private static final String INVALID_YEAR = "1999";

    private static final String INVALID_DATE_DAY = "30";
    private static final String INVALID_DATE_MONTH = "2";
    private static final String INVALID_DATE_YEAR = "2001";

    private static final String VALID_DAY = TRANSACTION_BUS.getDay().toString();
    private static final String VALID_MONTH = TRANSACTION_BUS.getMonth().toString();
    private static final String VALID_YEAR = TRANSACTION_BUS.getYear().toString();

    @Test
    public void toModelType_validDateDetails_returnsDate() throws Exception {
        JsonAdaptedDate date = new JsonAdaptedDate(TRANSACTION_BUS.getDate());
        assertEquals(TRANSACTION_BUS.getDate(), date.toModelType());
    }

    @Test
    public void toModelType_invalidDay_throwsIllegalValueException() {
        JsonAdaptedDate date =
                new JsonAdaptedDate(INVALID_DAY, VALID_MONTH, VALID_YEAR);
        String expectedMessage = Day.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_nullDay_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate(null, VALID_MONTH, VALID_YEAR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Day.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_invalidMonth_throwsIllegalValueException() {
        JsonAdaptedDate date =
                new JsonAdaptedDate(VALID_DAY, INVALID_MONTH, VALID_YEAR);
        String expectedMessage = Month.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_nullMonth_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate(VALID_DAY, null, VALID_YEAR);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Month.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_invalidYear_throwsIllegalValueException() {
        JsonAdaptedDate date =
                new JsonAdaptedDate(VALID_DAY, VALID_MONTH, INVALID_YEAR);
        String expectedMessage = Year.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_nullYear_throwsIllegalValueException() {
        JsonAdaptedDate date = new JsonAdaptedDate(VALID_DAY, VALID_MONTH, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Year.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedDate date =
                new JsonAdaptedDate(INVALID_DATE_DAY, INVALID_DATE_MONTH, INVALID_DATE_YEAR);
        String expectedMessage = Date.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, date::toModelType);
    }
}
