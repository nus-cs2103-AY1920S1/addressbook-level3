package cs.f10.t1.nursetraverse.model.datetime;

import static cs.f10.t1.nursetraverse.testutil.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class DateTimeTest {

    public static final String INVALID_INPUT = "10-30-2017 1200";
    public static final String VALID_INPUT_1200 = "10-01-2017 1200";
    public static final String VALID_INPUT_1300 = "10-01-2017 1300";
    public static final DateTime DATE_TIME_1200 = new DateTime(VALID_INPUT_1200);
    public static final DateTime DATE_TIME_1300 = new DateTime(VALID_INPUT_1300);
    public static final DateTime NOW_DATE_TIME = new DateTime(new Date());
    public static final String DATE_1200_EXPECTED_JACKSON_OUTPUT = "10-01-2017 1200";
    public static final String DATE_1200_EXPECTED_STRING_OUTPUT = "10-01-2017 1200";

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
        assertDoesNotThrow(() -> new DateTime(VALID_INPUT_1300));
        assertDoesNotThrow(() -> new DateTime(new Date()));
    }

    @Test
    public void parseDateTime_null_nullPointerException() {
        assertThrows(NullPointerException.class, () -> DateTime.parseDateTime(null));
    }

    @Test
    public void parseDateTime_invalid_illegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DateTime.parseDateTime(""));
        assertThrows(IllegalArgumentException.class, () -> DateTime.parseDateTime("string"));
        assertThrows(IllegalArgumentException.class, () -> DateTime.parseDateTime("1/1/1 12:00"));
        assertThrows(IllegalArgumentException.class, () -> DateTime.parseDateTime("1-1-1000 1200"));
        assertThrows(IllegalArgumentException.class, () -> DateTime.parseDateTime("10-1-0001 12:00"));
        assertThrows(IllegalArgumentException.class, () -> DateTime.parseDateTime("10-20-0001 0000"));
    }

    @Test
    public void parseDateTime_valid_noException() {
        assertDoesNotThrow(() -> DateTime.parseDateTime("10-01-2017 1200"));
        assertDoesNotThrow(() -> DateTime.parseDateTime("30-12-2900 0100"));
    }

    @Test
    public void testEquals() {
        //Create a local date time and pass it in as a date
        LocalDateTime localDateTime = LocalDateTime.parse("10-01-2017 1300", DateTime.DATE_PARSER_VALIDATOR);
        ZonedDateTime zdt = localDateTime.atZone(ZoneId.systemDefault());
        Date output = Date.from(zdt.toInstant());
        DateTime dateTime1300 = new DateTime(output);

        //false
        assertNotEquals(DATE_TIME_1200, DATE_TIME_1300);
        assertNotEquals(DATE_TIME_1200, dateTime1300);

        //true
        assertEquals(DATE_TIME_1300, dateTime1300);
        assertEquals(DATE_TIME_1300, DATE_TIME_1300);
        assertEquals(NOW_DATE_TIME, NOW_DATE_TIME);
    }

    @Test
    public void testToJacksonJsonString() {
        assertEquals(DATE_TIME_1200.toJacksonJsonString(), DATE_1200_EXPECTED_JACKSON_OUTPUT);
    }

    @Test
    public void testToString() {
        assertEquals(DATE_TIME_1200.toString(), DATE_1200_EXPECTED_STRING_OUTPUT);
    }

}
