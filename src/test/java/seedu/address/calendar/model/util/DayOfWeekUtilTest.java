package seedu.address.calendar.model.util;

import org.junit.jupiter.api.Test;
import seedu.address.calendar.model.date.DayOfWeek;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class DayOfWeekUtilTest {

    @Test
    public void of_invalidInt_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> DayOfWeekUtil.of(Integer.MAX_VALUE));
        assertThrows(IllegalArgumentException.class, () -> DayOfWeekUtil.of(Integer.MIN_VALUE));
        assertThrows(IllegalArgumentException.class, () -> DayOfWeekUtil.of(-1));
        assertThrows(IllegalArgumentException.class, () -> DayOfWeekUtil.of(7));
    }

    @Test
    public void of() {
        assertEquals(DayOfWeek.SUN, DayOfWeekUtil.of(0));
        assertEquals(DayOfWeek.MON, DayOfWeekUtil.of(1));
        assertEquals(DayOfWeek.TUE, DayOfWeekUtil.of(2));
        assertEquals(DayOfWeek.WED, DayOfWeekUtil.of(3));
        assertEquals(DayOfWeek.THU, DayOfWeekUtil.of(4));
        assertEquals(DayOfWeek.FRI, DayOfWeekUtil.of(5));
        assertEquals(DayOfWeek.SAT, DayOfWeekUtil.of(6));
    }
}
