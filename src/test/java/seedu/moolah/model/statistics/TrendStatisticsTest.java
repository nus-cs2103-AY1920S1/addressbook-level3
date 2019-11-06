package seedu.moolah.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moolah.logic.commands.statistics.StatsTrendCommand;
import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Timestamp;
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

    public static final List<List<Double>> DAY_BUDGET_CATEGORY_MODE_RESULT =
            List.of(
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            5.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 10.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 30.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0, 0.0, 0.0, 0.0, 0.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50.0, 0.0, 0.0, 70.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 240.0, 0.0, 0.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 480.0, 0.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));





    public static final Timestamp WEEK_FIRST_END_DATE =
            Timestamp.createTimestampIfValid("25-09-2016").get();
    public static final Timestamp WEEK_FIRST_START_DATE =
            WEEK_FIRST_END_DATE.createBackwardTimestamp(BudgetPeriod.WEEK,
            2 * StatsTrendCommand.HALF_OF_PERIOD_NUMBER);

    public static final String WEEK_BUDGET_TITLE =
            String.format("Periodic trendline from %s to %s in the unit of %ss",
                    WEEK_FIRST_START_DATE.showDate(), WEEK_FIRST_END_DATE.showDate(),
                    BudgetPeriod.WEEK);

    public static final List<List<Double>> WEEK_BUDGET_CATEGORY_MODE_RESULT =
            List.of(
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 5.0, 10.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 30.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 20.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 60.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 50.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 240.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0),
                    List.of(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                            0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0));


    //dates
    //2D List

    //dates
    //budget limit
    //total expenditure


    //switch window testing
    @Test
    void trendStats_dayPeriodWithNoDatesCategoryMode_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedDayBudget();
        TrendStatistics statistics = TrendStatistics.run(Category.getValidCategories(),
                null, null, budget, false);

        assertEquals(statistics.getTitle(), DAY_BUDGET_TITLE);
        assertEquals(statistics.getDates().get(0).getDate(),
                Timestamp.createTimestampIfValid("20-08-2016").get().getDate());
        assertEquals(statistics.getPeriodicCategoricalExpenses(), DAY_BUDGET_CATEGORY_MODE_RESULT);
        //hard to see what points, just count number of trend lines
        //assertEquals(statistics.getPeriodicCategoricalExpenses())

        //may require stubs and ModelManager to test that the code will still work even after shifting windows

    }



    @Test
    void trendStats_dayPeriodWithNoDatesBudgetMode_correctOutput() {
    }

    @Test
    void trendStats_weekPeriodWithOnlyEndDateCategoryMode_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedWeekBudget();
        TrendStatistics statistics = TrendStatistics.run(Category.getValidCategories(),
                null, WEEK_FIRST_END_DATE, budget, false);

        assertEquals(statistics.getTitle(), WEEK_BUDGET_TITLE);
        int numPoints = statistics.getDates().size();
        assertEquals(statistics.getDates().get(numPoints - 1).showDate(),
                Timestamp.createTimestampIfValid("12-09-2016").get().showDate());
        assertEquals(statistics.getPeriodicCategoricalExpenses(), WEEK_BUDGET_CATEGORY_MODE_RESULT);

    }


    @Test
    void trendStats_weekPeriodWithOnlyEndDateBudgetMode_correctOutput() {
    }

    @Test
    void trendStats_monthPeriodWithBothDateCategoryMode_correctOutput() {
    }

    @Test
    void trendStats_monthPeriodWithBothDateBudgetMode_correctOutput() {
    }

    //shift window into future if possible
    @Test
    void trendStats_yearPeriodWithOnlyStartDateCategoryMode_correctOutput() {
    }

    @Test
    void trendStats_yearPeriodWithOnlyStartDateBudgetMode_correctOutput() {
    }
}
