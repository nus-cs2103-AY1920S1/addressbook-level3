package seedu.guilttrip.model.statistics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.guilttrip.testutil.TypicalEntries.CLOTHING_EXPENSE;
import static seedu.guilttrip.testutil.TypicalEntries.SALARY_INCOME;
import static seedu.guilttrip.testutil.TypicalEntries.getTypicalGuiltTrip;
import static seedu.guilttrip.testutil.TypicalStatistics.MONTH_NOVEMBER;
import static seedu.guilttrip.testutil.TypicalStatistics.YEAR_2019;

import java.time.Month;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.guilttrip.model.Model;
import seedu.guilttrip.model.ModelManager;
import seedu.guilttrip.model.UserPrefs;
import seedu.guilttrip.model.entry.Category;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;


public class MonthListTest {

    private Model model;
    private MonthList monthList;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalGuiltTrip(), new UserPrefs());
        FilteredList<Expense> filteredListExpense = new FilteredList<>(model.getFilteredExpenses());
        FilteredList<Income> filteredListIncome = new FilteredList<>(model.getFilteredIncomes());
        monthList = new MonthList(model.getCategoryList(), filteredListExpense, filteredListIncome,
                Month.of(MONTH_NOVEMBER), YEAR_2019);
    }

    @Test
    public void monthList_filteredExpenseListIsWithinMonth_success() {
        //Filtered Expense is correct
        FilteredList<Expense> expenseOfMonthNov = monthList.getFilteredListForExpense();
        FilteredList<Expense> copyOfExpenseFromMonthNov = new FilteredList<>(model.getFilteredExpenses());
        copyOfExpenseFromMonthNov.setPredicate(new EntryContainsMonthYearPredicate(MONTH_NOVEMBER, YEAR_2019));
        assertEquals(expenseOfMonthNov, copyOfExpenseFromMonthNov);

        //Filtered Income is correct
        FilteredList<Income> incomeOfMonthNov = monthList.getFilteredListForIncome();
        FilteredList<Income> copyOfIncomeInMonthNov = new FilteredList<>(model.getFilteredIncomes());
        copyOfIncomeInMonthNov.setPredicate(new EntryContainsMonthYearPredicate(MONTH_NOVEMBER, YEAR_2019));
        assertEquals(incomeOfMonthNov, copyOfIncomeInMonthNov);
    }

    @Test
    public void monthList_updateListOfStats_returnsCorrectValue() {
        //0 is clothing Expense.
        Category testCategory = model.getFilteredExpenses().get(0).getCategory();
        Double valueOfCategory = model.getFilteredExpenses().get(0).getAmount().value;
        assertEquals(valueOfCategory, monthList.updateListOfStats(testCategory));
    }


    @Test
    public void monthList_calculateBarChart_returnsCorrectValue() {
        //0 is clothing Expense.
        ObservableList<DailyStatistics> toVerify = monthList.calculateStatisticsForBarChart();
        DailyStatistics statsForClothing = new DailyStatistics(CLOTHING_EXPENSE.getDate().getDate(),
                CLOTHING_EXPENSE.getAmount().value, 0.00);
        DailyStatistics statsForSalary = new DailyStatistics(SALARY_INCOME.getDate().getDate(),
                0.00, SALARY_INCOME.getAmount().value);
        ObservableList<DailyStatistics> actualCorrectStats = FXCollections.observableArrayList();
        actualCorrectStats.addAll(statsForClothing, statsForSalary);
        assertTrue(toVerify.contains(statsForClothing) && toVerify.contains(statsForSalary));
    }
}
