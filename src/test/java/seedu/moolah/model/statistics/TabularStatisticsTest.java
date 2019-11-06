package seedu.moolah.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.moolah.model.budget.Budget;
import seedu.moolah.model.budget.BudgetPeriod;
import seedu.moolah.model.expense.Category;
import seedu.moolah.model.expense.Timestamp;
import seedu.moolah.testutil.TypicalMooLah;


class TabularStatisticsTest {

    public static final Timestamp DAY_BUDGET_FIRST_START_DATE =
            Timestamp.createTimestampIfValid("05-09-2016").get();

    public static final String DAY_BUDGET_TITLE =
            String.format("Statistics Summary: Comparing %s to %s with %s to %s\n",
            DAY_BUDGET_FIRST_START_DATE.showDate(), DAY_BUDGET_FIRST_START_DATE.showDate(),
            DAY_BUDGET_FIRST_START_DATE.showDate(), DAY_BUDGET_FIRST_START_DATE.showDate());

    public static final List<FiveElementTableEntry> DAY_BUDGET_RESULTS =
            TabularStatistics.replaceWith(TabularStatistics.createEmptyTableWithTotal(),
                    new FiveElementTableEntry(
                            "FOOD", 5, 1, 0, 0),
                    new FiveElementTableEntry("TOTAL", 5, 1, 0, 0));



    public static final Timestamp WEEK_BUDGET_FIRST_START_DATE =
            Timestamp.createTimestampIfValid("13-09-2016").get();
    public static final Timestamp WEEK_BUDGET_FIRST_END_DATE =
            WEEK_BUDGET_FIRST_START_DATE.createForwardTimestamp(BudgetPeriod.WEEK).minusDays(1);
    public static final Timestamp WEEK_BUDGET_SECOND_START_DATE =
            WEEK_BUDGET_FIRST_START_DATE.plusDays(1);
    public static final Timestamp WEEK_BUDGET_SECOND_END_DATE =
            WEEK_BUDGET_FIRST_END_DATE.plusDays(1);

    public static final String WEEK_BUDGET_TITLE =
            String.format("Statistics Summary: Comparing %s to %s with %s to %s\n",
            WEEK_BUDGET_FIRST_START_DATE.showDate(), WEEK_BUDGET_FIRST_END_DATE.showDate(),
            WEEK_BUDGET_SECOND_START_DATE.showDate(), WEEK_BUDGET_SECOND_END_DATE.showDate());

    public static final List<FiveElementTableEntry> WEEK_BUDGET_RESULTS =
            TabularStatistics.replaceWith(TabularStatistics.createEmptyTableWithTotal(),
                    new FiveElementTableEntry("FOOD", 0, 0, -10, -1),
                    new FiveElementTableEntry("TRAVEL", 30, 1, 0, 0),
                    new FiveElementTableEntry("TRANSPORT", 20, 1, 0, 0),
                    new FiveElementTableEntry("SHOPPING", 60, 1, 0, 0),
                    new FiveElementTableEntry("UTILITIES", 50, 1, 70, 1),
                    new FiveElementTableEntry("HEALTHCARE", 240, 1, 0, 0),
                    new FiveElementTableEntry("ENTERTAINMENT", 480, 1, 0, 0),
                    new FiveElementTableEntry("EDUCATION", 0, 0, 0, 0),
                    new FiveElementTableEntry("OTHERS", 0, 0, 0, 0),
                    new FiveElementTableEntry("TOTAL", 880, 6, 60, 0));



    public static final Timestamp MONTH_BUDGET_FIRST_START_DATE =
            Timestamp.createTimestampIfValid("13-09-2016").get();
    public static final Timestamp MONTH_BUDGET_FIRST_END_DATE =
            MONTH_BUDGET_FIRST_START_DATE.createForwardTimestamp(BudgetPeriod.MONTH).minusDays(1);
    public static final Timestamp MONTH_BUDGET_SECOND_START_DATE =
            Timestamp.createTimestampIfValid("13-09-2015").get();
    public static final Timestamp MONTH_BUDGET_SECOND_END_DATE =
            MONTH_BUDGET_SECOND_START_DATE.createForwardTimestamp(BudgetPeriod.MONTH).minusDays(1);

    public static final String MONTH_BUDGET_TITLE =
            String.format("Statistics Summary: Comparing %s to %s with %s to %s\n",
            MONTH_BUDGET_FIRST_START_DATE.showDate(), MONTH_BUDGET_FIRST_END_DATE.showDate(),
            MONTH_BUDGET_SECOND_START_DATE.showDate(), MONTH_BUDGET_SECOND_END_DATE.showDate());

    public static final List<FiveElementTableEntry> MONTH_BUDGET_RESULTS =
            TabularStatistics.replaceWith(TabularStatistics.createEmptyTableWithTotal(),
                    new FiveElementTableEntry("FOOD", 0, 0, 0, 1),
                    new FiveElementTableEntry("TRAVEL", 0, 0, 470, 0),
                    new FiveElementTableEntry("TRANSPORT", 0, 0, 0, 0),
                    new FiveElementTableEntry("SHOPPING", 0, 0, 0, 0),
                    new FiveElementTableEntry("UTILITIES", 0, 0, -100, -1),
                    new FiveElementTableEntry("HEALTHCARE", 0, 0, -240, -1),
                    new FiveElementTableEntry("ENTERTAINMENT", 0, 0, -480, -1),
                    new FiveElementTableEntry("EDUCATION", 0, 0, -960, -1),
                    new FiveElementTableEntry("OTHERS", 0, 0, -1920, -1),
                    new FiveElementTableEntry("TOTAL", 0, 0, -3230, -4));



    public static final Timestamp YEAR_BUDGET_FIRST_START_DATE =
            Timestamp.createTimestampIfValid("13-09-2016").get();
    public static final Timestamp YEAR_BUDGET_FIRST_END_DATE =
            YEAR_BUDGET_FIRST_START_DATE.createForwardTimestamp(BudgetPeriod.YEAR).minusDays(1);
    public static final Timestamp YEAR_BUDGET_SECOND_START_DATE =
            Timestamp.createTimestampIfValid("13-09-2020").get();
    public static final Timestamp YEAR_BUDGET_SECOND_END_DATE =
            YEAR_BUDGET_SECOND_START_DATE.createForwardTimestamp(BudgetPeriod.YEAR).minusDays(1);

    public static final String YEAR_BUDGET_TITLE =
            String.format("Statistics Summary: Comparing %s to %s with %s to %s\n",
            YEAR_BUDGET_FIRST_START_DATE.showDate(), YEAR_BUDGET_FIRST_END_DATE.showDate(),
            YEAR_BUDGET_SECOND_START_DATE.showDate(), YEAR_BUDGET_SECOND_END_DATE.showDate());

    public static final List<FiveElementTableEntry> YEAR_BUDGET_RESULTS =
            TabularStatistics.replaceWith(TabularStatistics.createEmptyTableWithTotal(),
                    new FiveElementTableEntry("FOOD", 0, 0, -10, -1),
                    new FiveElementTableEntry("TRAVEL", 0, 0, -30, -1),
                    new FiveElementTableEntry("TRANSPORT", 0, 0, -20, -1),
                    new FiveElementTableEntry("SHOPPING", 0, 0, -60, -1),
                    new FiveElementTableEntry("UTILITIES", 0, 0, -120, -2),
                    new FiveElementTableEntry("HEALTHCARE", 0, 0, -240, -1),
                    new FiveElementTableEntry("ENTERTAINMENT", 0, 0, -480, -1),
                    new FiveElementTableEntry("EDUCATION", 0, 0, -960, -1),
                    new FiveElementTableEntry("OTHERS", 0, 0, -1920, -1),
                    new FiveElementTableEntry("TOTAL", 0, 0, -3840, -10));


    @Test
    void tableView_dayPeriodSameInterval_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedDayBudget();
        TabularStatistics statistics = TabularStatistics.run(Category.getValidCategories(),
                DAY_BUDGET_FIRST_START_DATE, DAY_BUDGET_FIRST_START_DATE, budget);

        assertEquals(statistics.getTitle(), DAY_BUDGET_TITLE);
        assertEquals(statistics.getUnionDifferenceTable(), DAY_BUDGET_RESULTS);
    }

    @Test
    void tableView_weekPeriodOverlappingIntervals_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedWeekBudget();
        TabularStatistics statistics = TabularStatistics.run(Category.getValidCategories(),
                WEEK_BUDGET_FIRST_START_DATE, WEEK_BUDGET_SECOND_START_DATE, budget);

        assertEquals(statistics.getTitle(), WEEK_BUDGET_TITLE);
        assertEquals(statistics.getUnionDifferenceTable(), WEEK_BUDGET_RESULTS);
    }

    @Test
    void tableView_monthPeriodDisjointIntervals_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedMonthBudget();
        TabularStatistics statistics = TabularStatistics.run(Category.getValidCategories(),
                MONTH_BUDGET_FIRST_START_DATE, MONTH_BUDGET_SECOND_START_DATE, budget);


        assertEquals(statistics.getTitle(), MONTH_BUDGET_TITLE);
        assertEquals(statistics.getUnionDifferenceTable(), MONTH_BUDGET_RESULTS);
    }

    @Test
    void tableView_futureYearPeriodDisjointIntervals_correctOutput() {
        Budget budget = TypicalMooLah.getPopulatedYearBudget();
        TabularStatistics statistics = TabularStatistics.run(Category.getValidCategories(),
                YEAR_BUDGET_FIRST_START_DATE, YEAR_BUDGET_SECOND_START_DATE, budget);


        assertEquals(statistics.getTitle(), YEAR_BUDGET_TITLE);
        assertEquals(statistics.getUnionDifferenceTable(), YEAR_BUDGET_RESULTS);
    }
}
