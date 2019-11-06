package seedu.address.calendar.model.date;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class YearTest {

    @Test
    public void constructor_invalidAddress_throwsIllegalArgumentException() {
        int lessThanLowerBound = Year.BOUND_LOWER - 1;
        int greaterThanUpperBound = Year.BOUND_UPPER + 1;

        assertThrows(IllegalArgumentException.class, () -> new Year(lessThanLowerBound));
        assertThrows(IllegalArgumentException.class, () -> new Year(greaterThanUpperBound));
    }

    @Test
    public void getNumericalValue() {
        Year beforeLeapYear = new Year(2135);
        Year leapYear = new Year(2136);
        Year afterLeapYear = new Year(2137);

        assertEquals(2135, beforeLeapYear.getNumericalValue());
        assertEquals(2136, leapYear.getNumericalValue());
        assertEquals(2137, afterLeapYear.getNumericalValue());
    }

    @Test
    public void copy() {
        Year beforeLeapYear = new Year(1983);
        Year leapYear = new Year(1984);
        Year afterLeapYear = new Year(1985);

        assertEquals(beforeLeapYear, beforeLeapYear.copy());
        assertEquals(leapYear, leapYear.copy());
        assertEquals(afterLeapYear, afterLeapYear.copy());
    }

    @Test
    public void compareTo() {
        Year earlierYear = new Year(2019);
        Year sameAsEarlierYear = new Year(2019);
        Year laterYear = new Year(2020);

        assertTrue(earlierYear.compareTo(laterYear) < 0);
        assertTrue(laterYear.compareTo(earlierYear) > 0);
        assertEquals(0, earlierYear.compareTo(earlierYear));
        assertEquals(0, earlierYear.compareTo(sameAsEarlierYear));
    }

    @Test
    public void to_string() {
        Year year = new Year(2119);
        assertEquals("2119", year.toString());
    }

    @Test
    public void isValidYear() {
        int lowerBoundYear = Year.BOUND_LOWER;
        int lowerBoundLessThanYear = Year.BOUND_LOWER - 1;
        int lowerBoundMoreThanYear = Year.BOUND_LOWER + 1;
        int upperBoundYear = Year.BOUND_UPPER;
        int upperBoundLessThanYear = Year.BOUND_UPPER - 1;
        int upperBoundMoreThanYear = Year.BOUND_UPPER + 1;

        assertTrue(Year.isValidYear(lowerBoundYear));
        assertTrue(Year.isValidYear(upperBoundYear));
        assertTrue(Year.isValidYear(lowerBoundMoreThanYear));
        assertTrue(Year.isValidYear(upperBoundLessThanYear));

        assertFalse(Year.isValidYear(lowerBoundLessThanYear));
        assertFalse(Year.isValidYear(upperBoundMoreThanYear));
    }

    @Test
    public void isLeapYear() {
        Year leapYear1 = new Year(2136);
        Year leapYearBefore1 = new Year(2135);
        Year leapYearAfter1 = new Year(2137);

        Year leapYear2 = new Year(2020);
        Year leapYearBefore2 = new Year(2019);
        Year leapYearAfter2 = new Year(2021);

        assertTrue(leapYear1.isLeapYear());
        assertTrue(leapYear2.isLeapYear());

        assertFalse(leapYearAfter1.isLeapYear());
        assertFalse(leapYearBefore1.isLeapYear());
        assertFalse(leapYearAfter2.isLeapYear());
        assertFalse(leapYearBefore2.isLeapYear());
    }
}
