package seedu.sugarmummy.model.time;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class YearMonthTest {
    private java.time.YearMonth validYearMonth = java.time.YearMonth.of(2020, 12);

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new YearMonth((String) null));
    }

    @Test
    public void isValidYearMonth_false() {
        assertFalse(YearMonth.isValidYearMonth("2020-13"));
        assertFalse(YearMonth.isValidYearMonth("2019-1"));
        assertFalse(YearMonth.isValidYearMonth("201-12"));
        assertFalse(YearMonth.isValidYearMonth("2222"));
    }

    @Test
    public void isValidDateTime_true() {
        assertTrue(YearMonth.isValidYearMonth("2020-12"));
        assertTrue(YearMonth.isValidYearMonth("2020-01"));
        assertTrue(YearMonth.isValidYearMonth("2222-02"));
    }

    @Test
    public void constructor_string() {
        assertEquals(new YearMonth(2020, 12), new YearMonth("2020-12"));
    }
}
