package seedu.address.commons.util;

import org.junit.jupiter.api.Test;
import seedu.address.logic.parser.exceptions.ParseException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;


public class DateTimeUtilTest {

    private static String VALID_DATE_TIME_FORMAT = "10/10/2018 18:00";
    private static String INVALID_DATE_TIME_FORMAT_DASH = "10-10-2018 18-00";
    private static String INVALID_DATE_TIME_FORMAT_ZERO = "1/1/2019 18:00";

    private static String EXCEEDING_DAY_DATE_TIME_FORMAT = "32/10/2018 18:00";
    private static String EXCEEDING_TIME_DATE_TIME_FORMAT = "22/10/2018 25:00";
    private static String EXCEEDING_MONTH_DATE_TIME_FORMAT = "22/15/2018 15:00";

    private static String FALL_SHORT_DAY_DATE_TIME_FORMAT = "0/10/2018 18:00";
    private static String FALL_SHORT_MONTH_DATE_TIME_FORMAT = "22/00/2018 15:00";

    private static String DUE_SOON_DATE_TIME = "31/01/2020 10:00";

    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTimeUtil.parseDateTime((String) null));
    }

    @Test
    public void parseDateTime_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(INVALID_DATE_TIME_FORMAT_DASH));
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(INVALID_DATE_TIME_FORMAT_ZERO));
    }

    @Test
    public void parseDateTime_validFormat_success() throws ParseException {
        assertEquals(LocalDateTime.parse(VALID_DATE_TIME_FORMAT, DateTimeUtil.defaultFormatter),
                DateTimeUtil.parseDateTime(VALID_DATE_TIME_FORMAT));
    }

    // EP empty string
    @Test
    public void parseDateTime_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(""));
    }

    // EP day/month/time exceeds calendar
    @Test
    public void parseDateTime_dateTimeExceeded_ParseException() {
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(EXCEEDING_DAY_DATE_TIME_FORMAT));
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(EXCEEDING_TIME_DATE_TIME_FORMAT));
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(EXCEEDING_MONTH_DATE_TIME_FORMAT));
    }

    // EP day/month falls short
    @Test
    public void parseDateTime_dateTimeFallsShort_ParseException() {
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(FALL_SHORT_DAY_DATE_TIME_FORMAT));
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(FALL_SHORT_MONTH_DATE_TIME_FORMAT));
    }

    @Test
    public void checkIfDueSoon_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTimeUtil.checkIfDueSoon(100, (LocalDateTime) null));
    }

    @Test
    public void checkIfDueSoon_dueSoon_success() throws ParseException {
        assertTrue(DateTimeUtil.checkIfDueSoon(100, DateTimeUtil.parseDateTime(DUE_SOON_DATE_TIME)));
        assertTrue(DateTimeUtil.checkIfDueSoon(2, LocalDateTime.now().plusWeeks(2)));
    }

}
