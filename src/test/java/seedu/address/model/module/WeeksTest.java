package seedu.address.model.module;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.testutil.moduleutil.TypicalWeeks;

class WeeksTest {
    private Weeks weeksWeekNumbersAllWeeks;
    private Weeks weeksStartEndWeekNumbersAllWeeks;
    private Weeks weeksStartEndWeekInterval1;
    private Weeks weeksStartEndWeekInterval2;

    @BeforeEach
    void setup() {
        weeksWeekNumbersAllWeeks = TypicalWeeks.generateWeeks_weekNumbers_allWeeks();
        weeksStartEndWeekNumbersAllWeeks = TypicalWeeks.generateWeeks_startEndWeekNumbers_allWeeks();
        weeksStartEndWeekInterval1 = TypicalWeeks.generateWeeks_startEndWeekInterval(1);
        weeksStartEndWeekInterval2 = TypicalWeeks.generateWeeks_startEndWeekInterval(2);
    }

    @Test
    void testGetWeekNumbers() {
        assertEquals(TypicalWeeks.WEEK_NUMBERS_ALL, weeksWeekNumbersAllWeeks.getWeekNumbers());
    }

    @Test
    void testGetStartDate() {
        assertEquals(TypicalWeeks.START_DATE_1, weeksStartEndWeekNumbersAllWeeks.getStartDate());
    }

    @Test
    void testGetEndDate() {
        assertEquals(TypicalWeeks.END_DATE_1, weeksStartEndWeekNumbersAllWeeks.getEndDate());
    }

    @Test
    void testGetWeekInterval() {
        assertEquals(1, weeksStartEndWeekInterval1.getWeekInterval());
        assertEquals(2, weeksStartEndWeekInterval2.getWeekInterval());
    }

    @Test
    void testGetType() {
        assertEquals(WeeksType.WEEK_NUMBERS, weeksWeekNumbersAllWeeks.getType());
        assertEquals(WeeksType.START_END_WEEK_NUMBERS, weeksStartEndWeekNumbersAllWeeks.getType());
        assertEquals(WeeksType.START_END_WEEK_INTERVAL, weeksStartEndWeekInterval1.getType());
    }

    @Test
    void testToString() {
        assertEquals("Week Numbers: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]",
                weeksWeekNumbersAllWeeks.toString());
        assertEquals("Start Date: 2019-08-12 End Date: 2019-10-28 Week Numbers: "
                + "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13]", weeksStartEndWeekNumbersAllWeeks.toString());
        assertEquals("Start Date: 2019-08-12 End Date: 2019-10-28 Week Interval: 1",
                weeksStartEndWeekInterval1.toString());
    }

    @Test
    void testEquals() {
        assertTrue(weeksStartEndWeekInterval1.equals(weeksStartEndWeekInterval1));
        assertTrue(weeksStartEndWeekInterval1.equals(TypicalWeeks.generateWeeks_startEndWeekInterval(1)));
        assertFalse(weeksWeekNumbersAllWeeks.equals(weeksStartEndWeekInterval1));
    }

    @Test
    void testHashCode() {
        assertEquals(weeksStartEndWeekInterval1.hashCode(), weeksStartEndWeekInterval1.hashCode());
        assertNotEquals(weeksStartEndWeekInterval1.hashCode(), weeksStartEndWeekInterval2.hashCode());
    }
}
