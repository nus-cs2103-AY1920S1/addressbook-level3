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

    public FilteredList<Expense> getFilteredListOfExpenses() {
        return this.listOfExpenses;
    }

    public FilteredList<Income> getFilteredListOfIncome() {
        return this.listOfIncomes;
    }

    /**
     * Calculates the total expense and income for the day.
     *
     * @return statisticsForDay which is used for the BarChart.
     */
    public DailyStatistics calculateStatisticsForBarChart() {
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

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DailyList)) {
            return false;
        }

        DailyList otherDailyList = (DailyList) other;

        return otherDailyList.getFilteredListOfExpenses().equals(getFilteredListOfExpenses())
                && otherDailyList.getFilteredListOfIncome().equals(getFilteredListOfIncome());
    }
}
