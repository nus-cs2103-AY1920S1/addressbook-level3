package seedu.tarence.model.tutorial;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tarence.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class WeekTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Week(null));
    }

    @Test
    public void constructor_invalidWeek_throwsIllegalArgumentException() {
        int invalidWeek = 0;
        assertThrows(IllegalArgumentException.class, () -> new Week(invalidWeek));
    }

    @Test
    public void isValidWeek() {
        // null Week
        assertThrows(NullPointerException.class, () -> Week.isValidWeek(null));

        // invalid Week
        assertFalse(Week.isValidWeek(0)); // values not between 1 and 13
        assertFalse(Week.isValidWeek(14));

        // valid Week
        assertTrue(Week.isValidWeek(1));
    }

    @Test
    public void compareTo() {
        assertTrue(0 > new Week(1).compareTo(new Week(2)));
        assertTrue(0 == new Week(1).compareTo(new Week(1)));
        assertTrue(0 < new Week(2).compareTo(new Week(1)));
    }
}
