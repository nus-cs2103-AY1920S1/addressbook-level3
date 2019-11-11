package seedu.sugarmummy.model.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class YearMonthDayTest {
    private LocalDate validDate = LocalDate.of(2020, 1, 20);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new YearMonthDay((String) null));
        assertThrows(NullPointerException.class, () -> new YearMonthDay((LocalDate) null));
    }

    @Test
    public void isValidYearMonthDay_false() {
        assertFalse(YearMonthDay.isValidYearMonthDay("2019-02-30"));
        assertFalse(YearMonthDay.isValidYearMonthDay("2019-11-31"));
        assertFalse(YearMonthDay.isValidYearMonthDay("2000-13-31"));
        assertFalse(YearMonthDay.isValidYearMonthDay("2019-1-31"));
        assertFalse(YearMonthDay.isValidYearMonthDay("2019-11-1"));
        assertFalse(YearMonthDay.isValidYearMonthDay("200-11-31"));
        assertFalse(YearMonthDay.isValidYearMonthDay("2000-11-"));
        assertFalse(YearMonthDay.isValidYearMonthDay("200000--"));
    }

    @Test
    public void isValidDateTime_true() {
        assertTrue(YearMonthDay.isValidYearMonthDay("2019-11-30"));
        assertTrue(YearMonthDay.isValidYearMonthDay("2019-12-01"));
        assertTrue(YearMonthDay.isValidYearMonthDay("2020-02-29"));
    }

    @Test
    public void constructor_string() {
        assertEquals(new YearMonthDay(validDate), new YearMonthDay("2020-01-20"));
    }
}
