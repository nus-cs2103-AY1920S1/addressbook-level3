package seedu.moolah.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.testutil.TypicalMooLah;

class PieChartStatisticsTest {

    public static final String DAY_BUDGET_TITLE = String.format("Statistics Summary from %s to %s\n\n"
                    + "Total amount: $5.00",
            TypicalMooLah.DAY_BUDGET.getWindowStartDate().showDate(),
            TypicalMooLah.DAY_BUDGET.getWindowEndDate().showDate());

    public static final List<String> DAY_BUDGET_RESULTS = List.of("FOOD(100.00%)");

    public static final Timestamp WEEK_BUDGET_START_DATE =
            Timestamp.createTimestampIfValid("06-09-2016").get();
    public static final Timestamp WEEK_BUDGET_END_DATE =
            WEEK_BUDGET_START_DATE.createForwardTimestamp(BudgetPeriod.WEEK).minusDays(1);
    public static final String WEEK_BUDGET_TITLE = String.format("Statistics Summary from %s to %s\n\n"
                    + "Total amount: $0.00",
            WEEK_BUDGET_START_DATE.showDate(),
            WEEK_BUDGET_END_DATE.showDate());

    public static final List<String> WEEK_BUDGET_RESULTS = List.of();


    public static final Timestamp FUTURE_WEEK_BUDGET_START_DATE =
            Timestamp.createTimestampIfValid("06-09-2020").get();
    public static final Timestamp FUTURE_WEEK_BUDGET_END_DATE =
            FUTURE_WEEK_BUDGET_START_DATE.createForwardTimestamp(BudgetPeriod.WEEK).minusDays(1);
    public static final String FUTURE_WEEK_BUDGET_TITLE = String.format("Statistics Summary from %s to %s\n\n"
                    + "Total amount: $0.00",
            FUTURE_WEEK_BUDGET_START_DATE.showDate(),
            FUTURE_WEEK_BUDGET_END_DATE.showDate());
    public static final List<String> FUTURE_WEEK_BUDGET_RESULTS = List.of();



    public static final Timestamp MONTH_BUDGET_END_DATE =
            Timestamp.createTimestampIfValid("05-10-2016").get();
    public static final Timestamp MONTH_BUDGET_START_DATE =
            MONTH_BUDGET_END_DATE.createBackwardTimestamp(BudgetPeriod.MONTH).plusDays(1);
    public static final String MONTH_BUDGET_TITLE = String.format("Statistics Summary from %s to %s\n\n"
                    + "Total amount: $1920.00",
            MONTH_BUDGET_START_DATE.showDate(),
            MONTH_BUDGET_END_DATE.showDate());

    public static final List<String> MONTH_BUDGET_RESULTS =
            List.of("HEALTHCARE(12.50%)", "TRANSPORT(1.04%)", "TRAVEL(1.56%)",
            "ENTERTAINMENT(25.00%)", "UTILITIES(6.25%)", "SHOPPING(3.13%)", "EDUCATION(50.00%)", "FOOD(0.52%)");

    public static final Timestamp YEAR_BUDGET_START_DATE =
            Timestamp.createTimestampIfValid("07-09-2016").get();
    public static final Timestamp YEAR_BUDGET_END_DATE =
            YEAR_BUDGET_START_DATE.createForwardTimestamp(BudgetPeriod.YEAR).plusDays(2);

    public static final String YEAR_BUDGET_TITLE = String.format("Statistics Summary from %s to %s\n\n"
                    + "Total amount: $3840.00",
            YEAR_BUDGET_START_DATE.showDate(),
            YEAR_BUDGET_END_DATE.showDate());

    public static final List<String> YEAR_BUDGET_RESULTS = List.of("HEALTHCARE(6.25%)", "TRANSPORT(0.52%)",
            "TRAVEL(0.78%)", "ENTERTAINMENT(12.50%)", "OTHERS(50.00%)", "UTILITIES(3.13%)", "SHOPPING(1.56%)",
            "EDUCATION(25.00%)", "FOOD(0.26%)");


    @Test
    void pieChart_dayPeriodWithNoDates_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedDayBudget();
        PieChartStatistics statistics = PieChartStatistics.run(Category.getValidCategories(),
                null, null, budget);
        assertEquals(statistics.getTitle(), DAY_BUDGET_TITLE);
        assertEquals(statistics.getFormattedCategories(), DAY_BUDGET_RESULTS);
    }

    @Test
    void pieChart_weekPeriodWithOnlyStartDate_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedWeekBudget();
        PieChartStatistics statistics = PieChartStatistics.run(Category.getValidCategories(),
                WEEK_BUDGET_START_DATE, null, budget);
        assertEquals(statistics.getTitle(), WEEK_BUDGET_TITLE);
        assertEquals(statistics.getFormattedCategories(), WEEK_BUDGET_RESULTS);
    }


    @Test
    void pieChart_monthPeriodWithOnlyEndDate_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedMonthBudget();
        PieChartStatistics statistics = PieChartStatistics.run(Category.getValidCategories(),
                null, MONTH_BUDGET_END_DATE, budget);
        assertEquals(statistics.getTitle(), MONTH_BUDGET_TITLE);
        assertEquals(statistics.getFormattedCategories(), MONTH_BUDGET_RESULTS);
    }

    @Test
    void pieChart_yearPeriodWithBothDates_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedYearBudget();
        PieChartStatistics statistics = PieChartStatistics.run(Category.getValidCategories(),
                YEAR_BUDGET_START_DATE, YEAR_BUDGET_END_DATE, budget);
        assertEquals(statistics.getTitle(), YEAR_BUDGET_TITLE);
        assertEquals(statistics.getFormattedCategories(), YEAR_BUDGET_RESULTS);
    }

    @Test
    void pieChart_futureWeekPeriodWithOnlyStartDate_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedWeekBudget();
        PieChartStatistics statistics = PieChartStatistics.run(Category.getValidCategories(),
                FUTURE_WEEK_BUDGET_START_DATE, null, budget);
        assertEquals(statistics.getTitle(), FUTURE_WEEK_BUDGET_TITLE);
        assertEquals(statistics.getFormattedCategories(), FUTURE_WEEK_BUDGET_RESULTS);
    }
}
