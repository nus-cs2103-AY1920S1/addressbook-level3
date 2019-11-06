package seedu.moolah.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.expense.Timestamp;

class BudgetWindowTest {
    private static final Timestamp NOV_1 = new Timestamp(
            LocalDateTime.of(2019, 11, 1, 0, 0));
    private static final Timestamp NOV_30 = new Timestamp(
            LocalDateTime.of(2019, 11, 30, 23, 59)).toEndOfDay();
    private static final Timestamp NOV_15 = new Timestamp(
            LocalDateTime.of(2019, 11, 15, 0, 0));
    private static final Timestamp OCT_15 = new Timestamp(
            LocalDateTime.of(2019, 10, 15, 0, 0));
    private static final Timestamp OCT_1 = new Timestamp(
            LocalDateTime.of(2019, 10, 1, 0, 0));
    private static final Timestamp OCT_31 = new Timestamp(
            LocalDateTime.of(2019, 10, 31, 23, 59)).toEndOfDay();
    private static final BudgetWindow NOV_1_TO_NOV_30 = new BudgetWindow(NOV_1, BudgetPeriod.MONTH);
    private static final String NOV_1_TO_NOV_30_SG = " Period: month Start date: 1 Nov 2019, 12:00:00 AM "
            + "End date: 30 Nov 2019, 11:59:59 PM";
    private static final String NOV_1_TO_NOV_30_US = " Period: month Start date: Nov 1, 2019, 12:00:00 AM "
            + "End date: Nov 30, 2019, 11:59:59 PM";

    @Test
    void getStartDate() {
        assertEquals(NOV_1, NOV_1_TO_NOV_30.getStartDate());
    }

    @Test
    void getEndDate() {
        assertEquals(NOV_30, NOV_1_TO_NOV_30.getEndDate());
    }

    @Test
    void getPeriod() {
        assertEquals(BudgetPeriod.MONTH, NOV_1_TO_NOV_30.getBudgetPeriod());
    }

    @Test
    void contains() {
        assertTrue(NOV_1_TO_NOV_30.contains(NOV_15));
        assertFalse(NOV_1_TO_NOV_30.contains(OCT_15));
    }

    @Test
    void normalize() {
        BudgetWindow copy = new BudgetWindow(NOV_1, BudgetPeriod.MONTH);
        copy.normalize(OCT_15);
        assertEquals(OCT_1, copy.getStartDate());
        assertEquals(OCT_31, copy.getEndDate());
        assertEquals(BudgetPeriod.MONTH, copy.getBudgetPeriod());
    }

    @Test
    void testEquals() {
        assertFalse(NOV_1_TO_NOV_30.equals(1));
        assertTrue(NOV_1_TO_NOV_30.equals(NOV_1_TO_NOV_30));
        BudgetWindow copy = new BudgetWindow(NOV_1, BudgetPeriod.MONTH);
        copy.normalize(OCT_15);
        assertFalse(NOV_1_TO_NOV_30.equals(copy));
    }

    @Test
    void testToString() {
        assertTrue(NOV_1_TO_NOV_30.toString().equals(NOV_1_TO_NOV_30_SG)
                || NOV_1_TO_NOV_30.toString().equals(NOV_1_TO_NOV_30_US));
    }
}
