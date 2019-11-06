package seedu.moolah.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Period;

import org.junit.jupiter.api.Test;

class BudgetPeriodTest {

    @Test
    void getPeriod() {
        assertEquals(Period.ofDays(1), BudgetPeriod.DAY.getPeriod());
        assertEquals(Period.ofWeeks(1), BudgetPeriod.WEEK.getPeriod());
        assertEquals(Period.ofMonths(1), BudgetPeriod.MONTH.getPeriod());
        assertEquals(Period.ofYears(1), BudgetPeriod.YEAR.getPeriod());
        assertEquals(Period.ofYears(100), BudgetPeriod.INFINITY.getPeriod());
    }

    @Test
    void testToString() {
        assertEquals("day", BudgetPeriod.DAY.toString());
        assertEquals("week", BudgetPeriod.WEEK.toString());
        assertEquals("month", BudgetPeriod.MONTH.toString());
        assertEquals("year", BudgetPeriod.YEAR.toString());
        assertEquals("infinity", BudgetPeriod.INFINITY.toString());
    }
}
