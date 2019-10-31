package seedu.ichifund.model.date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class MonthTest {
    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Month(null));
    }

    @Test
    public void constructor_invalidAmount_throwsIllegalArgumentException() {
        String invalidMonth = "";
        assertThrows(IllegalArgumentException.class, () -> new Month(invalidMonth));
    }

    @Test
    public void isValidMonth() {
        // null month
        assertThrows(NullPointerException.class, () -> Month.isValidMonth(null));

        // invalid month
        assertFalse(Month.isValidMonth("13"));
        assertFalse(Month.isValidMonth("0"));
        assertFalse(Month.isValidMonth(""));
        assertFalse(Month.isValidMonth(" "));
        assertFalse(Month.isValidMonth("Jan"));


        assertTrue(Month.isValidMonth("1"));
        assertTrue(Month.isValidMonth("12"));
        assertTrue(Month.isValidMonth("6"));
    }

    @Test
    public void has30Days() {
        assertFalse(new Month("1").has30Days());
        assertFalse(new Month("2").has30Days());
        assertFalse(new Month("12").has30Days());

        assertTrue(new Month("9").has30Days());
        assertTrue(new Month("4").has30Days());
        assertTrue(new Month("11").has30Days());
    }

    @Test
    public void has31Days() {
        assertFalse(new Month("2").has31Days());
        assertFalse(new Month("4").has31Days());
        assertFalse(new Month("6").has31Days());

        assertTrue(new Month("1").has31Days());
        assertTrue(new Month("8").has31Days());
        assertTrue(new Month("12").has31Days());
    }

    @Test
    public void compareTo() {
        assertTrue(new Month("12").compareTo(new Month("6")) < 0);
    }
}
