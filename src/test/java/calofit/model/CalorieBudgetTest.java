package calofit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.Map;
import java.util.OptionalInt;

import org.junit.jupiter.api.Test;

class CalorieBudgetTest {

    public static final LocalDate TEST_DATE = LocalDate.of(2019, Month.NOVEMBER, 11);

    @Test
    public void checkEquals() {
        CalorieBudget b1 = new CalorieBudget(Map.of(TEST_DATE, 1400));
        CalorieBudget b2 = new CalorieBudget(Map.of(TEST_DATE, 1400));
        assertEquals(b1, b1);
        assertEquals(b1, b2);
        CalorieBudget diffVal = new CalorieBudget(Map.of(TEST_DATE, 1800));
        CalorieBudget diffDate = new CalorieBudget(Map.of(TEST_DATE.minus(Period.ofDays(1)), 1400));
        CalorieBudget diffContent = new CalorieBudget();
        assertNotEquals(b1, diffVal);
        assertNotEquals(b1, diffDate);
        assertNotEquals(b1, diffContent);
    }

    @Test
    public void testGet() {
        CalorieBudget b1 = new CalorieBudget(Map.of(TEST_DATE, 1400));
        b1.todayProperty().set(TEST_DATE);
        assertEquals(b1.getCurrentBudget(), OptionalInt.of(1400));
        assertEquals(b1.currentBudget().get(), 1400);
        assertEquals(b1.getBudgetAt(TEST_DATE), OptionalInt.of(1400));
        assertEquals(b1.getBudgetAt(TEST_DATE.minus(Period.ofDays(1))), OptionalInt.empty());

        CalorieBudget b2 = new CalorieBudget();
        b2.todayProperty().set(TEST_DATE);
        assertEquals(b2.getCurrentBudget(), OptionalInt.empty());
        assertEquals(b2.currentBudget().get(), Double.POSITIVE_INFINITY);


    }
}
