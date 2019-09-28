package seedu.address.commons.util;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

class DateUtilTest {

    @Test
    public void extendDate() {
        LocalDate today = LocalDate.now();

        assertTrue(DateUtil.extendDate(today, 14).equals(today.plusDays(14)));
        assertTrue(DateUtil.extendDate(today, 789).equals(today.plusDays(789)));

        assertFalse(DateUtil.extendDate(today, 123).equals(today.plusDays(456)));
        assertFalse(DateUtil.extendDate(today, 123).equals(today.plusDays(-123)));
    }

    @Test
    public void getTodayPlusDays() {
        LocalDate today = LocalDate.now();

        assertTrue(DateUtil.getTodayPlusDays(30).equals(today.plusDays(30)));
        assertTrue(DateUtil.getTodayPlusDays(567).equals(today.plusDays(567)));

        assertFalse(DateUtil.getTodayPlusDays(9876).equals(today.plusDays(9877)));
        assertFalse(DateUtil.getTodayPlusDays(123).equals(today.plusDays(0)));
    }

    @Test
    public void getNumOfDaysBetween() {
        LocalDate today = LocalDate.now();

        assertTrue(DateUtil.getNumOfDaysBetween(DateUtil.getTodayDate(), DateUtil.getTodayPlusDays(40)) == 40);
        assertTrue(DateUtil.getNumOfDaysBetween(today.plusDays(23), today.plusDays(50)) == 27);

        assertFalse(DateUtil.getNumOfDaysBetween(today.minusDays(10), today) != 10);
        assertFalse(DateUtil.getNumOfDaysBetween(DateUtil.getTodayDate(),
                DateUtil.extendDate(today, 100)) != 100);
    }
}
