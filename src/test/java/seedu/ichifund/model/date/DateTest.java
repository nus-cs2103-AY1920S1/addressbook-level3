package seedu.ichifund.model.date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.ichifund.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {
    @Test
    public void constructor_nullArguments_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Date(null, new Month("12"), new Year("2009")));
        assertThrows(NullPointerException.class, () -> new Date(new Day("28"), null, new Year("2009")));
        assertThrows(NullPointerException.class, () -> new Date(new Day("28"), new Month("12"), null));
    }

    @Test
    public void isValidDate() {
        // invalid: February in non-leap years
        assertFalse(Date.isValidDate(new Day("29"), new Month("2"), new Year("2005")));
        assertFalse(Date.isValidDate(new Day("29"), new Month("2"), new Year("2100")));

        // invalid: 31 days in months with 30 days
        assertFalse(Date.isValidDate(new Day("31"), new Month("4"), new Year("2219")));
        assertFalse(Date.isValidDate(new Day("31"), new Month("9"), new Year("9999")));

        // valid: February in leap years
        assertTrue(Date.isValidDate(new Day("29"), new Month("2"), new Year("2000")));
        assertTrue(Date.isValidDate(new Day("29"), new Month("2"), new Year("2400")));
        assertTrue(Date.isValidDate(new Day("29"), new Month("2"), new Year("2008")));

        // valid: months with 31 days
        assertTrue(Date.isValidDate(new Day("31"), new Month("1"), new Year("5487")));
        assertTrue(Date.isValidDate(new Day("31"), new Month("10"), new Year("6283")));

        // valid: months with 30 days
        assertTrue(Date.isValidDate(new Day("30"), new Month("6"), new Year("2219")));
        assertTrue(Date.isValidDate(new Day("30"), new Month("11"), new Year("9999")));

        // valid: other cases
        assertTrue(Date.isValidDate(new Day("1"), new Month("7"), new Year("3489")));
        assertTrue(Date.isValidDate(new Day("15"), new Month("5"), new Year("3102")));
        assertTrue(Date.isValidDate(new Day("27"), new Month("12"), new Year("8409")));
        assertTrue(Date.isValidDate(new Day("7"), new Month("3"), new Year("8293")));
    }

    @Test
    public void compareTo() {
        assertTrue(new Date(new Day("1"), new Month("12"), new Year("2019"))
                .compareTo(new Date(new Day("5"), new Month("10"), new Year("3000"))) > 0);
    }
}
