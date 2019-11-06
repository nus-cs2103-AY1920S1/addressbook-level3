package seedu.guilttrip.model.statistics;

import java.time.LocalDate;
import java.time.Month;

import javafx.collections.transformation.FilteredList;
import seedu.guilttrip.model.entry.Expense;
import seedu.guilttrip.model.entry.Income;

/**
 * Represents a SortType in the finance manager.
 * Guarantees: dateOfList cannot be changed.
 */
public class DailyList {
    private FilteredList<Expense> listOfExpenses;
    private FilteredList<Income> listOfIncomes;
    private LocalDate date;

    public DailyList(FilteredList<Expense> retrievedExpenseList, FilteredList<Income> retrievedIncomeList, int day,
                     Month month, int year) {
        listOfExpenses = new FilteredList<Expense>(retrievedExpenseList);
        listOfIncomes = new FilteredList<Income>(retrievedIncomeList);
        this.date = LocalDate.of(year, month, day);
    }

    /**
     * Calculates the total expense and income for the day.
     *
     * @return statisticsForDay which is used for the BarChart.
     */
    public DailyStatistics calculateBarChart() {
        double totalExpense = 0.00;
        double totalIncome = 0.00;
        for (int i = 0; i < listOfExpenses.size(); i++) {
            totalExpense = totalExpense + listOfExpenses.get(i).getAmount().value;
        }
        for (int i = 0; i < listOfIncomes.size(); i++) {
            totalIncome = totalIncome + listOfIncomes.get(i).getAmount().value;
        }
        DailyStatistics statisticsForDay = new DailyStatistics(this.date, totalExpense, totalIncome);
        return statisticsForDay;
    }
}
