package seedu.address.calendar.model.date;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class DayOfWeekTest {

    @Test
    public void getNumericalVal() {
        // ensures that {@code SUN} is represented by the {@code int} 0
        assertEquals(DayOfWeek.SUN.getNumericalVal(), 0);
        // ensures that {@code MON} is represented by the {@code int} 1
        assertEquals(DayOfWeek.MON.getNumericalVal(), 1);
        // ensures that {@code TUE} is represented by the {@code int}r 2
        assertEquals(DayOfWeek.TUE.getNumericalVal(), 2);
        // ensures that {@code WED} is represented by the {@code int} 3
        assertEquals(DayOfWeek.WED.getNumericalVal(), 3);
        // ensures that {@code THU} is represented by the {@code int} 4
        assertEquals(DayOfWeek.THU.getNumericalVal(), 4);
        // ensures that {@code FRI} is represented by the {@code int} 5
        assertEquals(DayOfWeek.FRI.getNumericalVal(), 5);
        // ensures that {@code SUN} is represented by the {@code int} 6
        assertEquals(DayOfWeek.SAT.getNumericalVal(), 6);
    }

    @Test
    public void to_string() {
        // ensures that {@code SUN} is represented by the {@code String} "Sun"
        assertEquals(DayOfWeek.SUN.toString(), "Sun");
        // ensures that {@code MON} is represented by the {@code String} "Mon'
        assertEquals(DayOfWeek.MON.toString(), "Mon");
        // ensures that {@code TUE} is represented by the {@code String} "Tue"
        assertEquals(DayOfWeek.TUE.toString(), "Tue");
        // ensures that {@code WED} is represented by the {@code String} "Wed"
        assertEquals(DayOfWeek.WED.toString(), "Wed");
        // ensures that {@code THU} is represented by the {@code String} "Thu"
        assertEquals(DayOfWeek.THU.toString(), "Thu");
        // ensures that {@code FRI} is represented by the {@code String} "Fri"
        assertEquals(DayOfWeek.FRI.toString(), "Fri");
        // ensures that {@code SAT} is represented by the {@code String} "Sat"
        assertEquals(DayOfWeek.SAT.toString(), "Sat");
        // ensures that {@code SUN} is represented by the {@code String} "Sun"
        assertEquals(DayOfWeek.SUN.toString(), "Sun");
    }
}
