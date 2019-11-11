package seedu.guilttrip.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.guilttrip.testutil.TypicalEntries.TOTAL_EXPENSE;
import static seedu.guilttrip.testutil.TypicalEntries.TOTAL_EXPENSE_FOR_SEPTEMBER;
import static seedu.guilttrip.testutil.TypicalEntries.TOTAL_INCOME;
import static seedu.guilttrip.testutil.TypicalEntries.TOTAL_INCOME_FOR_SEPTEMBER;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalGuiltTrip;
import static seedu.guilttrip.testutil.TypicalStatistics.END_DATE;
import static seedu.guilttrip.testutil.TypicalStatistics.START_DATE;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.transformation.FilteredList;

import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.UserPrefs;
import seedu.guilttrip.model.entry.Date;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;

public class StatisticsManagerTest {
    private Model model;
    private Statistics statsManager;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());
        FilteredList<Expense> filteredListExpense = new FilteredList<>(model.getFilteredExpenses());
        FilteredList<Income> filteredListIncome = new FilteredList<>(model.getFilteredIncomes());
        statsManager = new StatisticsManager(filteredListExpense, filteredListIncome, model.getCategoryList());
    }

    @Test
    public void statisticsManager_updateListOfStats_returnsCorrectValues() {
        //filter Values for Month of September to November
        Date startDate = new Date(START_DATE);
        Date endDate = new Date(END_DATE);
        List<Date> periods = Arrays.asList(startDate, endDate);
        statsManager.updateListOfStats(periods);
        assertEquals(TOTAL_EXPENSE, statsManager.getTotalExpenseForPeriod().getValue());
        assertEquals(TOTAL_INCOME, statsManager.getTotalIncomeForPeriod().getValue());

        //filter Values for September to September
        periods = Arrays.asList(startDate, startDate);
        statsManager.updateListOfStats(periods);
        assertEquals(TOTAL_EXPENSE_FOR_SEPTEMBER, statsManager.getTotalExpenseForPeriod().getValue());
        assertEquals(TOTAL_INCOME_FOR_SEPTEMBER, statsManager.getTotalIncomeForPeriod().getValue());
    }
}
