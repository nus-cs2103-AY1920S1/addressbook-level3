package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DateUtilTest {

    @Test
    public void extendDate_validDays_returnsTrue() {
        LocalDate today = LocalDate.now();

        assertTrue(DateUtil.extendDate(today, 14).equals(today.plusDays(14)));
        assertTrue(DateUtil.extendDate(today, 789).equals(today.plusDays(789)));
        assertTrue(DateUtil.extendDate(today, 0).equals(today));
    }

    @Test
    public void extendDate_validDays_returnsFalse() {
        LocalDate today = LocalDate.now();

        assertFalse(DateUtil.extendDate(today, 123).equals(today.plusDays(456)));
        assertFalse(DateUtil.extendDate(today, 123).equals(today.plusDays(-123)));
    }

    @Test
    public void extendDate_invalidDays_throwsAssertionError() {
        LocalDate today = LocalDate.now();

        assertThrows(AssertionError.class, () -> DateUtil.extendDate(today, -23));
    }

    @Test
    public void extendDate_invalidDate_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> DateUtil.extendDate(null, 23));
    }

    @Test
    public void getTodayPlusDays_validDays_returnsTrue() {
        LocalDate today = LocalDate.now();

        assertTrue(DateUtil.getTodayPlusDays(30).equals(today.plusDays(30)));
        assertTrue(DateUtil.getTodayPlusDays(567).equals(today.plusDays(567)));
        assertTrue(DateUtil.getTodayPlusDays(0).equals(today));
    }

    @Test
    public void getTodayPlusDays_validDays_returnsFalse() {
        LocalDate today = LocalDate.now();

        assertFalse(DateUtil.getTodayPlusDays(9876).equals(today.plusDays(9877)));
        assertFalse(DateUtil.getTodayPlusDays(123).equals(today.plusDays(0)));
    }

    @Test
    public void getNumOfDaysBetween_validDates_returnsTrue() {
        LocalDate today = LocalDate.now();

        assertTrue(DateUtil.getNumOfDaysBetween(DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(40)) == 40);
        assertTrue(DateUtil.getNumOfDaysBetween(today.plusDays(23), today.plusDays(50)) == 27);

        assertTrue(DateUtil.getNumOfDaysBetween(today.minusDays(10), today) != 11);
        assertTrue(DateUtil.getNumOfDaysBetween(today, DateUtil.extendDate(today, 100)) != 99);
    }

    @Test
    public void getNumOfDaysBetween_invalidDates_throwsAssertionError() {
        LocalDate today = LocalDate.now();

        assertThrows(AssertionError.class, ()
            -> DateUtil.getNumOfDaysBetween(DateUtil.extendDate(today, 30), today));
    }

    @Test
    public void getNumOfDaysBetween_invalidDates_throwsNullPointerException() {
        LocalDate today = DateUtil.getTodayDate();

        assertThrows(NullPointerException.class, () -> DateUtil.getNumOfDaysBetween(null, today));
    }
}
