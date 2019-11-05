package seedu.address.calendar.model.date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DayOfWeekTest {

    @Test
    public void getNumericalVal() {
        assertEquals(0, DayOfWeek.SUN.getNumericalVal());

        assertEquals(1, DayOfWeek.MON.getNumericalVal());

        assertEquals(2, DayOfWeek.TUE.getNumericalVal());

        assertEquals(3, DayOfWeek.WED.getNumericalVal());

        assertEquals(4, DayOfWeek.THU.getNumericalVal());

        assertEquals(5, DayOfWeek.FRI.getNumericalVal());

        assertEquals(6, DayOfWeek.SAT.getNumericalVal());
    }

    @Test
    public void to_string() {
        assertEquals("Sun", DayOfWeek.SUN.toString());

        assertEquals("Mon", DayOfWeek.MON.toString());

        assertEquals("Tue", DayOfWeek.TUE.toString());

        assertEquals("Wed", DayOfWeek.WED.toString());

        assertEquals("Thu", DayOfWeek.THU.toString());

        assertEquals("Fri", DayOfWeek.FRI.toString());

        assertEquals("Sat", DayOfWeek.SAT.toString());

        assertEquals("Sun", DayOfWeek.SUN.toString());
    }
}
