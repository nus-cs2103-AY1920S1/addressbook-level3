package seedu.moolah.model.budget;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.general.Timestamp;

class BudgetWindowTest {
    private static final Timestamp NOV_1_START = Timestamp.createTimestampIfValid("01-11-2019").get()
            .toStartOfDay();
    private static final Timestamp NOV_30_END = Timestamp.createTimestampIfValid("30-11-2019").get()
            .toEndOfDay();
    private static final Timestamp NOV_15_START = Timestamp.createTimestampIfValid("15-11-2019").get()
            .toStartOfDay();
    private static final Timestamp OCT_15_START = Timestamp.createTimestampIfValid("15-10-2019").get()
            .toStartOfDay();
    private static final Timestamp OCT_15_END = Timestamp.createTimestampIfValid("15-10-2019").get()
            .toEndOfDay();
    private static final Timestamp OCT_1_START = Timestamp.createTimestampIfValid("01-10-2019").get()
            .toStartOfDay();
    private static final Timestamp OCT_31_START = Timestamp.createTimestampIfValid("31-10-2019").get()
            .toStartOfDay();
    private static final Timestamp OCT_31_END = Timestamp.createTimestampIfValid("31-10-2019").get()
            .toEndOfDay();
    private static final Timestamp OCT_11_START = Timestamp.createTimestampIfValid("11-10-2019").get()
            .toStartOfDay();
    private static final Timestamp OCT_17_END = Timestamp.createTimestampIfValid("17-10-2019").get()
            .toEndOfDay();
    private static final Timestamp NOV_1_2018_START = Timestamp.createTimestampIfValid("01-11-2018").get()
            .toStartOfDay();
    private static final Timestamp JAN_31_START = Timestamp.createTimestampIfValid("31-01-2019").get()
            .toStartOfDay();
    private static final Timestamp FEB_6_END = Timestamp.createTimestampIfValid("06-02-2019").get()
            .toEndOfDay();
    private static final Timestamp FEB_28_END = Timestamp.createTimestampIfValid("28-02-2019").get()
            .toEndOfDay();
    private static final Timestamp MAR_31_START = Timestamp.createTimestampIfValid("31-03-2019").get()
            .toStartOfDay();
    private static final Timestamp APR_30_END = Timestamp.createTimestampIfValid("30-04-2019").get()
            .toEndOfDay();
    private static final Timestamp FEB_29_2000_START = Timestamp.createTimestampIfValid("29-02-2000").get()
            .toStartOfDay();
    private static final Timestamp FEB_28_2001_END = Timestamp.createTimestampIfValid("28-02-2001").get()
            .toEndOfDay();

    private static final BudgetWindow NOV_1_DAY = new BudgetWindow(NOV_1_START, BudgetPeriod.DAY);
    private static final BudgetWindow OCT_31_DAY = new BudgetWindow(OCT_31_START, BudgetPeriod.DAY);
    private static final BudgetWindow JAN_31_WEEK = new BudgetWindow(JAN_31_START, BudgetPeriod.WEEK);
    private static final BudgetWindow NOV_1_WEEK = new BudgetWindow(NOV_1_START, BudgetPeriod.WEEK);
    private static final BudgetWindow JAN_31_MONTH = new BudgetWindow(JAN_31_START, BudgetPeriod.MONTH);
    private static final BudgetWindow MAR_31_MONTH = new BudgetWindow(MAR_31_START, BudgetPeriod.MONTH);
    private static final BudgetWindow NOV_1_MONTH = new BudgetWindow(NOV_1_START, BudgetPeriod.MONTH);
    private static final BudgetWindow FEB_29_YEAR = new BudgetWindow(FEB_29_2000_START, BudgetPeriod.YEAR);
    private static final BudgetWindow NOV_1_YEAR = new BudgetWindow(NOV_1_START, BudgetPeriod.YEAR);
    private static final String NOV_1_MONTH_SG = " Period: month Start date: 1 Nov 2019, 12:00:00 AM "
            + "End date: 30 Nov 2019, 11:59:59 PM";
    private static final String NOV_1_MONTH_US = " Period: month Start date: Nov 1, 2019, 12:00:00 AM "
            + "End date: Nov 30, 2019, 11:59:59 PM";

    @Test
    void getStartDate() {
        assertEquals(NOV_1_START, NOV_1_MONTH.getStartDate());
    }

    @Test
    void getEndDate() {
        assertEquals(NOV_30_END, NOV_1_MONTH.getEndDate());
    }

    @Test
    void getPeriod() {
        assertEquals(BudgetPeriod.MONTH, NOV_1_MONTH.getBudgetPeriod());
    }

    @Test
    void contains() {
        assertTrue(NOV_1_MONTH.contains(NOV_15_START));
        assertFalse(NOV_1_MONTH.contains(OCT_15_START));
    }

    @Test
    void normalize() {
        //day
        NOV_1_DAY.normalize(OCT_15_START);
        assertEquals(OCT_15_START, NOV_1_DAY.getStartDate());
        assertEquals(OCT_15_END, NOV_1_DAY.getEndDate());
        assertEquals(BudgetPeriod.DAY, NOV_1_DAY.getBudgetPeriod());

        //week
        NOV_1_WEEK.normalize(OCT_15_START);
        assertEquals(OCT_11_START, NOV_1_WEEK.getStartDate());
        assertEquals(OCT_17_END, NOV_1_WEEK.getEndDate());
        assertEquals(BudgetPeriod.WEEK, NOV_1_WEEK.getBudgetPeriod());

        //month
        NOV_1_MONTH.normalize(OCT_15_START);
        assertEquals(OCT_1_START, NOV_1_MONTH.getStartDate());
        assertEquals(OCT_31_END, NOV_1_MONTH.getEndDate());
        assertEquals(BudgetPeriod.MONTH, NOV_1_MONTH.getBudgetPeriod());
        NOV_1_MONTH.normalize(NOV_1_START);

        //year
        NOV_1_YEAR.normalize(OCT_15_START);
        assertEquals(NOV_1_2018_START, NOV_1_YEAR.getStartDate());
        assertEquals(OCT_31_END, NOV_1_YEAR.getEndDate());
        assertEquals(BudgetPeriod.YEAR, NOV_1_YEAR.getBudgetPeriod());
    }

    @Test
    void testHashCode() {
        assertEquals(Objects.hash(NOV_1_START, NOV_30_END, BudgetPeriod.MONTH),
                NOV_1_MONTH.hashCode());
    }

    @Test
    void testEquals() {
        // null -> returns false
        assertFalse(NOV_1_MONTH.equals(null));

        // different types -> returns false
        assertFalse(NOV_1_MONTH.equals(1));

        // same object -> returns true
        assertTrue(NOV_1_MONTH.equals(NOV_1_MONTH));

        // different attributes -> returns false
        BudgetWindow copy = new BudgetWindow(NOV_1_START, BudgetPeriod.MONTH);
        copy.normalize(OCT_15_START);
        assertFalse(NOV_1_MONTH.equals(copy));
    }

    @Test
    void testToString() {
        assertTrue(NOV_1_MONTH.toString().equals(NOV_1_MONTH_SG)
                || NOV_1_MONTH.toString().equals(NOV_1_MONTH_US));
    }

    @Test
    void testCalculateEndDate() {
        //day
        assertEquals(OCT_31_END, OCT_31_DAY.getEndDate());

        //month
        assertEquals(FEB_28_END, JAN_31_MONTH.getEndDate());
        assertEquals(APR_30_END, MAR_31_MONTH.getEndDate());

        //week
        assertEquals(FEB_6_END, JAN_31_WEEK.getEndDate());

        //leap year
        assertEquals(FEB_28_2001_END, FEB_29_YEAR.getEndDate());
    }
}
