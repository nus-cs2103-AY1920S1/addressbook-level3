package seedu.ichifund.model.date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DayTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Day(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidDay = "";
        assertThrows(IllegalArgumentException.class, () -> new Day(invalidDay));
    }

    @Test
    public void isValidDay() {
        // null day
        assertThrows(NullPointerException.class, () -> Day.isValidDay(null));

        // invalid day
        assertFalse(Day.isValidDay("32"));
        assertFalse(Day.isValidDay("0"));
        assertFalse(Day.isValidDay(""));
        assertFalse(Day.isValidDay(" "));


        assertTrue(Day.isValidDay("1"));
        assertTrue(Day.isValidDay("31"));
        assertTrue(Day.isValidDay("15"));
    }

    @Test
    public void compareTo() {
        assertTrue(new Day("12").compareTo(new Day("6")) < 0);
    }
}
