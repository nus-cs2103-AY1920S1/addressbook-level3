package seedu.moolah.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.moolah.model.statistics.TrendStatistics.INTERVAL_COUNT;
import static seedu.moolah.testutil.Assert.assertThrows;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.statistics.StatsTrendCommand;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.general.Timestamp;
import seedu.moolah.testutil.BudgetBuilder;
import seedu.moolah.testutil.TypicalMooLah;


class TrendStatisticsTest {

    public static final Timestamp DAY_FIRST_WINDOW_DATE =
            Timestamp.createTimestampIfValid("05-09-2016").get();
    public static final Timestamp DAY_FIRST_END_DATE =
            DAY_FIRST_WINDOW_DATE.createForwardTimestamp(BudgetPeriod.DAY,
                    StatsTrendCommand.HALF_OF_PERIOD_NUMBER);
    public static final Timestamp DAY_FIRST_START_DATE =
            DAY_FIRST_WINDOW_DATE.createBackwardTimestamp(BudgetPeriod.DAY,
                    StatsTrendCommand.HALF_OF_PERIOD_NUMBER);

    public static final String DAY_BUDGET_TITLE =
            String.format("Periodic trendline from %s to %s in the unit of %ss",
                    DAY_FIRST_START_DATE.showDate(), DAY_FIRST_END_DATE.showDate(),
                    BudgetPeriod.DAY);

    public static final List<List<Double>> DAY_BUDGET_CATEGORY_MODE_RESULT = List.of(
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    5.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 30.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50.0, 0.0, 0.0, 70.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 240.0, 0.0, 0.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 480.0, 0.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));


    public static final Timestamp WEEK_FIRST_END_DATE =
            Timestamp.createTimestampIfValid("25-09-2016").get();
    public static final Timestamp WEEK_FIRST_START_DATE =
            WEEK_FIRST_END_DATE.createBackwardTimestamp(BudgetPeriod.WEEK,
                    2 * StatsTrendCommand.HALF_OF_PERIOD_NUMBER);

    public static final String WEEK_BUDGET_TITLE =
            String.format("Periodic trendline from %s to %s in the unit of %ss",
                    WEEK_FIRST_START_DATE.showDate(), WEEK_FIRST_END_DATE.showDate(),
                    BudgetPeriod.WEEK);

    public static final List<List<Double>> WEEK_BUDGET_CATEGORY_MODE_RESULT = List.of(
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.0, 10.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 30.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50.0, 70.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 240.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 480.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
            List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                    0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));




    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new TrendStatistics(null, DAY_FIRST_END_DATE,
                        new BudgetBuilder().build(), false));
        assertThrows(NullPointerException.class, () ->
                new TrendStatistics(DAY_FIRST_START_DATE, null,
                        new BudgetBuilder().build(), false));
        assertThrows(NullPointerException.class, () ->
                new TrendStatistics(DAY_FIRST_START_DATE, DAY_FIRST_END_DATE,
                        null, false));
    }


    @Test
    void trendStats_dayPeriodWithNoDatesCategoryMode_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedDayBudget();
        TrendStatistics statistics = new TrendStatistics(DAY_FIRST_START_DATE, DAY_FIRST_END_DATE, budget, false);

        statistics.populateData();
        assertEquals(statistics.getTitle(), DAY_BUDGET_TITLE);

        List<Timestamp> dates = statistics.getDates();
        assertTrue(dates.size() <= INTERVAL_COUNT);
        assertEquals(statistics.getDates().get(0).getDate(),
                DAY_FIRST_START_DATE.getDate());

        assertEquals(statistics.getPeriodicCategoricalExpenses(), DAY_BUDGET_CATEGORY_MODE_RESULT);

    }


    @Test
    void trendStats_weekPeriodWithOnlyEndDateCategoryMode_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedWeekBudget();
        TrendStatistics statistics = new TrendStatistics(WEEK_FIRST_START_DATE, WEEK_FIRST_END_DATE, budget, false);
        statistics.populateData();
        assertEquals(statistics.getTitle(), WEEK_BUDGET_TITLE);
        int numPoints = statistics.getDates().size();
        assertTrue(numPoints <= INTERVAL_COUNT);
        assertEquals(statistics.getDates().get(numPoints - 1).showDate(),
                Timestamp.createTimestampIfValid("19-09-2016").get().showDate());
        assertEquals(statistics.getPeriodicCategoricalExpenses(), WEEK_BUDGET_CATEGORY_MODE_RESULT);
    }
}


