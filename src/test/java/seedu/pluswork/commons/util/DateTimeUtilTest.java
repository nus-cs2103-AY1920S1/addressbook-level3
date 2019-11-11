package seedu.pluswork.commons.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.pluswork.testutil.Assert.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.pluswork.logic.parser.exceptions.ParseException;


public class DateTimeUtilTest {

    private static final String VALID_DATE_TIME_FORMAT = "10-10-2025 18:00";

    private static final String INVALID_DATE_TIME_FORMAT_PAST = "10-10-2019 18:00";
    private static final String INVALID_DATE_TIME_FORMAT_SLASH = "10/10/2025 18-00";
    private static final String INVALID_DATE_TIME_FORMAT_ZERO = "1-1-2019 18:00";

    private static final String EXCEEDING_DAY_DATE_TIME_FORMAT = "32-10-2025 18:00";
    private static final String EXCEEDING_TIME_DATE_TIME_FORMAT = "22-10-2025 25:00";
    private static final String EXCEEDING_MONTH_DATE_TIME_FORMAT = "22-15-2025 15:00";

    private static final String FALL_SHORT_DAY_DATE_TIME_FORMAT = "0-10-2025 18:00";
    private static final String FALL_SHORT_MONTH_DATE_TIME_FORMAT = "22-00-2025 15:00";

    private static final String VALID_LEAP_YEAR = "29-02-2020 20:00";
    private static final String INVALID_LEAP_YEAR = "29-02-2019 10:00";

    @Test
    public void parseDateTime_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTimeUtil.parseDateTime((String) null));
    }

    @Test
    public void parseDateTime_invalidFormat_throwsParseException() {
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(INVALID_DATE_TIME_FORMAT_SLASH));
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(INVALID_DATE_TIME_FORMAT_ZERO));
    }

    @Test
    public void parseDateTime_validFormat_success() throws ParseException {
        assertEquals(LocalDateTime.parse(VALID_DATE_TIME_FORMAT, DateTimeUtil.getDefaultFormatter()),
                DateTimeUtil.parseDateTime(VALID_DATE_TIME_FORMAT));
    }

    // EP deadline has passed
    @Test
    public void parseDateTime_passedDeadline_throwsParseException() throws ParseException {
        assert (DateTimeUtil.parseDateTime(INVALID_DATE_TIME_FORMAT_PAST).isBefore(LocalDateTime.now()));
    }

    // EP empty string
    @Test
    public void parseDateTime_emptyString_throwsParseException() {
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(""));
    }

    // EP day/month/time exceeds calendar
    @Test
    public void parseDateTime_dateTimeExceeded_throwsParseException() {
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(EXCEEDING_DAY_DATE_TIME_FORMAT));
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(EXCEEDING_TIME_DATE_TIME_FORMAT));
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(EXCEEDING_MONTH_DATE_TIME_FORMAT));
    }

    // EP day/month falls short
    @Test
    public void parseDateTime_dateTimeFallsShort_throwsParseException() {
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(FALL_SHORT_DAY_DATE_TIME_FORMAT));
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(FALL_SHORT_MONTH_DATE_TIME_FORMAT));
    }

    // EP leap days in leap and non-leap years
    @Test
    public void parseDateTime_dateTimeLeapYear_throwsParseExceptionWhenInvalid() throws ParseException {
        assertEquals(LocalDateTime.parse(VALID_LEAP_YEAR, DateTimeUtil.getDefaultFormatter()),
                DateTimeUtil.parseDateTime(VALID_LEAP_YEAR));
        assertThrows(ParseException.class, () -> DateTimeUtil.parseDateTime(INVALID_LEAP_YEAR));
    }

    @Test
    public void checkIfDueSoon_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTimeUtil.checkIfDueSoon((LocalDateTime) null));
    }

}
